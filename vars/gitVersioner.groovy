#!/usr/bin/env groovy

def call() {

    // check if git project
    def status = 'git status'.execute()
    status.waitFor()
    def isGitProject = status.exitValue()
}
