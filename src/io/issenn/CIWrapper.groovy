package io.issenn

class CIWrapper implements Serializable {
    def scm
    def env
    def steps
    def build
    def snippets
    def metainfo = [
        stages    : [],
        _git_token: '',
        _git_host : '',
        _pcm_host : '',
        artifacts : [],
        logs      : [],
        debug     : false
    ]

    CIWrapper(args = [:]) {
        scm = args.scm
        env = args.env
        steps = args.steps
        build = args.build

        snippets = [
            git       : new Git(this),
            node      : new Node(this),
            sonar     : new Sonar(this),
            utils     : new Utils(this),
            maven     : new Maven(this),
            django    : new Django(this),
            subversion: new Subversion(this),
            builder   : new Builder(this),
        ]
    }

    def prepare() {
        metainfo.causes = snippets.utils.causes()
        metainfo.refinfo = snippets.utils.resolveRef()
        metainfo.jobname = snippets.utils.jobname()

        if (!metainfo.refinfo) {
            return
        }
        if (scm.type.toLowerCase().contains('git')) {
            metainfo.scm_form = 'git'
            snippets.scm = snippets.git

            if (metainfo.refinfo.reftype in ['tag'] && 'debug'.equalsIgnoreCase("${snippets.git.tag_message()}")) {
                metainfo.debug = true
            }
        }

        for (cause in metainfo.causes) {
            if (cause.contains('Started by user') && cause.contains('administrator')) {
                metainfo.debug = true
                break
            }
        }

        if (scm.type.toLowerCase().contains('subversion')) {
            metainfo.scm_form = 'subversion'
            snippets.scm = snippets.subversion
        }

        def response_project_info = snippets.utils.request([
            url: ["${metainfo._pcm_host}/api/v1/model/project/", "key=${metainfo.jobname.project}"].join('?')
        ])

        if (200 != response_project_info.status || 1 != response_project_info.content.size()) {
            return
        }

        metainfo.projectinfo = response_project_info.content[0]

        def response_build_info = snippets.utils.request([
            url: [
                "${metainfo._pcm_host}/api/v1/model/project/${metainfo.projectinfo.pk}/method/buildinfo/",
                [
                    "ref=${env.BRANCH_NAME}",
                    "repo=${metainfo.jobname.repo}",
                    "reftype=${metainfo.refinfo.reftype}",
                    "refname=${metainfo.refinfo.refname}",
                ].join('&')
            ].join('?')
        ])

        if (200 != response_build_info.status) {
            return
        }

        metainfo.stages = response_build_info.content.steps
        metainfo.profile = response_build_info.content.profile
        metainfo.regex = snippets.utils.REGEX_REF_MAPPING
        switch (metainfo.profile.build_type) {
            case 'java/maven':
                snippets.builder = snippets.maven
                break
            case 'javascript/node':
                snippets.builder = snippets.node
                break
        }

        announce()
    }

    def restore() {
        if (!metainfo.projectinfo) {
            return
        }
        def response_restore = snippets.utils.request([
            method: 'POST',
            url   : "${metainfo._pcm_host}/api/v1/model/project/${metainfo.projectinfo.pk}/method/afterbuild/",
            data  : [
                ref      : env.BRANCH_NAME,
                repo     : metainfo.jobname.repo,
                reftype  : metainfo.refinfo.reftype,
                refname  : metainfo.refinfo.refname,
                artifacts: metainfo.artifacts
            ]
        ])
    }

    def announce() {
        def changelog = snippets.utils.changelog()
        def recipients = ['']
        if (!metainfo.debug && metainfo.refinfo.refname in ['archive', 'candidate', 'milestone', 'master']) {
            recipients += changelog.authors
        }
        def mail_content = ["当前时间 : ${new Date().format('YYYY/MM/dd HH:mm:ss')}"]

        mail_content += [''] + changelog.changes + ['']

        this.steps.mail(
            subject: "构建开始 ${env.JOB_NAME.tokenize('/')[-3..-1].join('/')} ${build.displayName}",
            to: recipients.join(','),
            // cc      : recipient,
            from: '',
            body: mail_content.join('\n')
        )
    }

    def report() {
        def changelog = snippets.utils.changelog()
        def recipients = ['']
        if (!metainfo.debug && metainfo.refinfo.refname in ['archive', 'candidate', 'milestone', 'master']) {
            recipients += changelog.authors
        }
        def mail_content = ["当前时间 : ${new Date().format('YYYY/MM/dd HH:mm:ss')}"]

        for (artifact in metainfo.artifacts) {

            switch (artifact.type) {
                case 'maven_war':
                case 'node_built_zip':
                    mail_content << "构建产出 : ${artifact.url}"
                    break
                case 'scan_report':
                    mail_content << "扫描报告 : ${artifact.url}"
                    break
            }
        }

        mail_content += [''] + changelog.changes + ['']

        def build_result = 'SUCCESS'.equals(build.currentResult) ? '构建成功' : '构建失败'
        this.steps.mail(
            subject: "${build_result} ${env.JOB_NAME.tokenize('/')[-3..-1].join('/')} ${build.displayName}",
            to: recipients.join(','),
            // cc      : recipient,
            from: '',
            body: (mail_content + metainfo.logs).join('\n')
        )
    }
}
