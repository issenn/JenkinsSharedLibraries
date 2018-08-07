#!/usr/bin/env groovy

def call() {

    // check if git project
    def status = 'git status'.execute()
    status.waitFor()
    def isGitProject = status.exitValue()
}

def tag() {
    def currentTag = ""
    println("ls".execute().text)
    def mostRecentTag = "git describe --tags".execute()
    mostRecentTag.waitFor()
    sleep 1
    println(mostRecentTag.exitValue())
    println(mostRecentTag.text)
    if (mostRecentTag.exitValue() == 0) {
        currentTag = mostRecentTag.text.trim()
    } else {
        currentTag = "0.0.0"
    }
    return currentTag
}
