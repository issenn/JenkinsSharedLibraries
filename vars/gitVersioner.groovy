#!/usr/bin/env groovy

def call() {
    def rootDir = "${WORKSPACE}"
    println('--------in')

    // check if git project
    def status = 'git status'.execute([], "${rootDir}")
    status.waitFor()
    def isGitProject = status.exitValue()
    println('---')
    println(isGitProject)
    println('---')
}
