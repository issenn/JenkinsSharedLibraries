#!/usr/bin/env groovy

def call(Map args=[:]) {
    args = [
        wrapper: 'default',
        labels: []
    ] << args

    def script = null
    println(args)
    out = libraryResource("Jenkinsfile/wrapper/default.groovy")
    println(out)
    try {
        script = libraryResource("Jenkinsfile/wrapper/${args.wrapper}.groovy")
        println("custom wrapper [${args.wrapper}] found")
    } catch(e) {
        script = libraryResource("Jenkinsfile/wrapper/default.groovy")
        println("custom wrapper [${args.wrapper}] not found")
    }

    writeFile(
        file: 'wrapper.groovy',
        encoding: 'UTF-8',
        text: ["env.CI_NODE_LABERLS = '${args.labers.join(' ')}'", script].join('\n')
    )
    return 'wrapper.groovy'
}