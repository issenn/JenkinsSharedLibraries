#!/usr/bin/env groovy

import groovy.json.JsonSlurper

def call(String path, String type, String os) {
    script {
        def api_token = "9611b6a99d280463039cbb64b7eb24ca"
        def json = "{\"type\":\"${os}\", \"bundle_id\":\"${env.bundleId}\", \"api_token\":\"${api_token}\"}"
        def cmd1 = "curl -X \"POST\" \"http://api.fir.im/apps\" \
            -H \"Content-Type: application/json\" \
            -d  '${json}'"
        def stdout1 = sh(returnStdout: true, script: "${cmd1}").trim()
        def slurper = new JsonSlurper().parseText(stdout1)
        def key = slurper.cert.binary.key
        def token = slurper.cert.binary.token
        println(key)
        println(token)
        def cmd2 = """curl -F "key=${key}"              \
            -F "token=${token}"             \
            -F "file=@${path}"            \
            -F "x:name=${bundleId}"             \
            -F "x:version=${env.versionName}"         \
            -F "x:build=${env.versionCode}"               \
            -F "x:release_type=${type}"   \
            -F "x:changelog=${env.CHANGELOG}"       \
            https://upload.qbox.me"""
        def stdout2 = sh(returnStdout: true, script: "${cmd2}").trim()
        println(stdout2)
        // bbb = sh(returnStdout: true, script: "${cmd2}").trim()
    }
}
