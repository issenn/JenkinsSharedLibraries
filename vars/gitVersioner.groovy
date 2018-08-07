#!/usr/bin/env groovy

def rootDir = "${WORKSPACE}"

def call() {
    println('--------in')

    // check if git project
    def status = 'git status'.execute([], rootDir)
    status.waitFor()
    def isGitProject = status.exitValue()
    println('---')
    println(isGitProject)
    println('---')

    if (isGitProject == 69) {
        println("git returned with error 69\n" +
                "If you are a mac user that message is telling you is that you need to open the " +
                "application XCode on your Mac OS X/macOS and since it hasn’t run since the last " +
                "update, you need to accept the new license EULA agreement that’s part of the " +
                "updated XCode.")
    }
}
