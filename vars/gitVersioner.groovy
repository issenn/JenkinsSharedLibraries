#!/usr/bin/env groovy

def call() {

    // check if git project
    def status = 'git status'.execute()
    status.waitFor()
    def isGitProject = status.exitValue()
}

def tag() {
    def currentTag = ""
    def mostRecentTag = "git describe --tags".execute()
    mostRecentTag.waitFor()
    println(mostRecentTag.exitValue())
    if (mostRecentTag.exitValue() == 0) {
        currentTag = mostRecentTag.text.trim()
    } else {
        currentTag = "0.0.0"
    }
    return currentTag
}
