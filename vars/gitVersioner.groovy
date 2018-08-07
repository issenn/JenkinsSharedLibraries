#!/usr/bin/env groovy

def call() {

    // check if git project
    def status = 'git status'.execute()
    status.waitFor()
    def isGitProject = status.exitValue()
}

def tag() {
    def currentTag = sh(returnStdout: true, script: "git describe --tags")
    return currentTag
}
