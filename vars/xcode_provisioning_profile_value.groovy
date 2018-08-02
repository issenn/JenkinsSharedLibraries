#!/usr/bin/env groovy

def call(args = [:]) {
    args = [
        filename: 'default',
        key: 'null'
    ] << args
    cmd = '/usr/libexec/PlistBuddy -c ' + "'Print ${args.key}'" + ' /dev/stdin <<< $(/usr/bin/security cms -D -i ' + "${args.filename})"
    script {
        return sh(returnStdout: true, script: "${cmd}").trim()
    }
}