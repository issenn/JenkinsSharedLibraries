#!/usr/bin/env groovy

def call() {
    checkout changelog: true, poll: true, scm: [
        $class: 'GitSCM',
        branches: scm.branches,
        browser: [$class: 'GithubWeb', repoUrl: 'https://github.com'],
        doGenerateSubmoduleConfigurations: false,
        gitTool: 'git',
        extensions: scm.extensions + [
            [
                $class: 'CloneOption',
                // depth: 2147483647,
                // depth: 10000,
                honorRefspec: true,
                noTags: false,
                reference: '',
                shallow: true,
                timeout: 30
            ],
            [
                $class: 'LocalBranch',
                localBranch: '**'
            ],
            [
                $class: 'GitTagMessageExtension'
            ],
            [
                $class: 'CleanBeforeCheckout'
            ],
            [
                $class: 'CleanCheckout'
            ]
        ],
        submoduleCfg: scm.submoduleCfg,
        userRemoteConfigs: scm.userRemoteConfigs
    ]
}