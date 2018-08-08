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

def branchCode() {
    def defaultBranch = 'develop'
    def masterLines = sh(returnStdout: true, script: "git rev-list origin/master").trim()
    def currentCommit = sh(returnStdout: true, script: "git rev-parse HEAD").trim()
    if (masterLines.contains(currentCommit)) {
        defaultBranch = 'master'
    }
    def code = sh(returnStdout: true, script: "git rev-list --no-merges origin/$defaultBranch.. --count")
    println("branchCode: ${code}")
    return code
}

def versionName() {
    tag().replaceAll('(-\\(|-g)\\S*', '').replaceAll('-.*', '').toString()
}

def versionCode() {
    def defaultBranch = 'master'
    def currentCommit = sh(returnStdout: true, script: "git rev-parse HEAD").trim()
    println(currentCommit)
    def diffToDefault = sh(returnStdout: true, script: "git rev-list --no-merges origin/$defaultBranch..")
    def featurelines = diffToDefault.trim().readLines()
    def defaultAndFeatureLines = sh(returnStdout: true, script: "git rev-list --no-merges $currentCommit").trim().readLines()
    def lastestDefaultBranchCommitSha1 = defaultAndFeatureLines.size() == 1 ?
            defaultAndFeatureLines.first() : {
        try {
            return defaultAndFeatureLines.findAll { !featurelines.contains(it) }.first()
        } catch (NoSuchElementException e) {
            // no commits found
            return currentCommit
        }
    }()
    def defaultBranchDatesLog = sh(returnStdout: true, script: "git log $lastestDefaultBranchCommitSha1 --pretty=format:'%at' -n 1").trim().replaceAll('\'', '')
    long latestCommitDate = defaultBranchDatesLog.isLong() ? defaultBranchDatesLog.toLong() : initialCommitDate

    def defautBranchCommitCount = sh(returnStdout: true, script: "git rev-list --no-merges $lastestDefaultBranchCommitSha1 --count").trim()
    def commitCount = defautBranchCommitCount.isInteger() ? defautBranchCommitCount.toInteger() : 0

    def long YEAR_IN_SECONDS = 60 * 60 * 24 * 365
    def diff = latestCommitDate - initialCommitDate

    long time = {
        if (yearFactor <= 0) {
            return 0;
        } else {
            return (diff * yearFactor / YEAR_IN_SECONDS + 0.5).intValue()
        }
    }();
    println(commitCount)
    println(time)
    // this is the version
    def combinedVersion = commitCount + time
    println(combinedVersion)
}