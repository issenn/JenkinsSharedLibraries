#!/usr/bin/env groovy

def call(String path, String type, String os) {
    script {
        def api_token = "9611b6a99d280463039cbb64b7eb24ca"
        def json = "{\"type\":\"${os}\", \"bundle_id\":\"${env.bundleId}\", \"api_token\":\"${api_token}\"}"
        def cmd1 = "curl -X \"POST\" \"http://api.fir.im/apps\" \
            -H \"Content-Type: application/json\" \
            -d  '${json}'"
        def stdout1 = sh(returnStdout: true, script: "${cmd1}").trim()
        def json1 = readJSON text: stdout1
        log.info(json1)
        def key = json1.cert.binary.key
        def token = json1.cert.binary.token
        def cmd2 = "curl -F \"key=${key}\"              \
            -F \"token=${token}\"             \
            -F \"file=@${path}\"            \
            -F \"x:name=${bundleId}\"             \
            -F \"x:version=${env.versionName}\"         \
            -F \"x:build=${env.versionCode}\"               \
            -F \"x:release_type=${type}\"   \
            -F 'x:changelog=${env.CHANGELOG}'       \
            https://upload.qbox.me"
        def stdout2 = sh(returnStdout: true, script: "${cmd2}").trim()
        def json2 = readJSON text: stdout2
        log.info(json2)
        def download_url = json2.download_url
        def is_completed = json2.is_completed
        def release_id = json2.release_id
    }
}
