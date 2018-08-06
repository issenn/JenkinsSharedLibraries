#!/usr/bin/env groovy


def call() {

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

    if (isGitProject > 0) {
        println("ERROR: can't generate a git version, this is not a git project")
        println(" -> Not a git repository (or any of the parent directories): .git")
        return new GitVersion()
    }

    // read ext properties
    Map configuration = getPropertyOrDefault(rootProject, "gitVersioner", [:]) as Map
    def String[] stableBranches = configuration.get("stableBranches") ?: []
    def defaultBranch = configuration.get("defaultBranch") ?: "master"
    float yearFactor = (configuration.get("yearFactor") ?: "1000").toString().toFloat()
    boolean snapshotEnabled = configuration.get("snapshotEnabled") != false
    boolean localChangesCountEnabled = configuration.get("localChangesCountEnabled") != false
    def shortNameClosure = configuration.get("shortName")

    // get information from git
    def currentBranch = 'git symbolic-ref --short -q HEAD'.execute([], rootDir).text.trim()

    // use a defined stable branch when on such a branch
    if (stableBranches.contains(currentBranch)) {
        defaultBranch = currentBranch
    }

    def currentCommit = 'git rev-parse HEAD'.execute([], rootDir).text.trim()

    def log = "git log --pretty=format:'%at' --reverse".execute([], rootDir)
            .text.trim().readLines()
    long initialCommitDate = log.size() > 0 ? log.first().trim().replaceAll('\'', '').toLong() : 0
    def localChangesCount = 'git diff-index HEAD'.execute([], rootDir).text.trim().readLines().
            size()
    boolean hasLocalChanges = localChangesCount > 0

    def diffToDefault = "git rev-list --no-merges $defaultBranch..".execute([], rootDir)
    diffToDefault.waitFor()
    if (diffToDefault.exitValue() != 0) {
        diffToDefault = "git rev-list --no-merges origin/$defaultBranch..".execute([], rootDir)
    }
    def featurelines = diffToDefault.text.trim().readLines()
    println(featurelines)
    def commitsInFeatureBranch = featurelines.size();

    def defaultAndFeatureLines = "git rev-list --no-merges $currentCommit"
            .execute([], rootDir).text.trim().readLines()

    // the sha1 of the latest commit in the default branch
    def lastestDefaultBranchCommitSha1 = defaultAndFeatureLines.size() == 1 ?
            defaultAndFeatureLines.first() : {
        try {
            return defaultAndFeatureLines.findAll { !featurelines.contains(it) }.first()
        } catch (NoSuchElementException e) {
            // no commits found
            return currentCommit
        }
    }()

    // get additional information
    def defaultBranchDatesLog = "git log $lastestDefaultBranchCommitSha1 --pretty=format:'%at' -n 1"
            .execute([], rootDir).text.trim().replaceAll('\'', '')
    long latestCommitDate = defaultBranchDatesLog.isLong() ? defaultBranchDatesLog.toLong() :
            initialCommitDate

    // commit count is the first part of the version
    def defautBranchCommitCount = "git rev-list --no-merges $lastestDefaultBranchCommitSha1 --count"
            .execute([], rootDir).text.trim()
    def commitCount = defautBranchCommitCount.isInteger() ? defautBranchCommitCount.toInteger() : 0

    // calculate the time part of the version. 2500 == 2 years, 6 months; 300 == 0.3 year
    def long YEAR_IN_SECONDS = 60 * 60 * 24 * 365
    def diff = latestCommitDate - initialCommitDate

    long time = {
        if (yearFactor <= 0) {
            return 0;
        } else {
            return (diff * yearFactor / YEAR_IN_SECONDS + 0.5).intValue()
        }
    }();

    // this is the version
    def combinedVersion = commitCount + time

    def currentTag = ""
    def mostRecentTag = "git describe --abbrev=0 --tags".execute([], rootDir)
    mostRecentTag.waitFor()
    if (mostRecentTag.exitValue() == 0) {
        currentTag = mostRecentTag.text.trim()
    } else {
        currentTag = "0.0.0"
    }

    def holder = new GitVersion()
    holder.version = combinedVersion
    holder.branchName = currentBranch
    holder.commit = currentCommit
    holder.branchVersion = commitsInFeatureBranch
    holder.localChanges = localChangesCount

    String featureBranchCommits = commitsInFeatureBranch == 0 ? "" : commitsInFeatureBranch

    // on feature branches, add a branch identifier and the commit count
    def shortBranch = (shortNameClosure ?: {
        if (currentBranch.isEmpty()) {
            return "${currentCommit.subSequence(0, 7)}"
        } else {
            return getTinyBranchName(currentBranch, 2)
        }
    })(holder)
    holder.shortBranch = shortBranch

    def snapshot = {
        def result = ""
        if (featureBranchCommits) {
            result += "-g${shortBranch}"
            result += "[$featureBranchCommits]"
        }
        if (localChangesCountEnabled && localChangesCount > 0) {
            result += "-($localChangesCount)"
        }
        println('--#featureBranchCommits')
        println(featureBranchCommits)
        println('--#hasLocalChanges')
        println(hasLocalChanges)
        if ((featureBranchCommits || hasLocalChanges) && snapshotEnabled) {
            result += "-SNAPSHOT"
        }
        return result
    }()

    holder.name = "$currentTag$snapshot"
    return holder
}

def Object getPropertyOrDefault(Object root, String name, Object fallback) {
    return root.hasProperty(name) ? root.property(name) : fallback
}

def String getTinyBranchName(String originalName, int length) {
    String nameBase64 = originalName.bytes.encodeBase64().toString()
    if (nameBase64.length() < length) {
        int charPos = originalName.size() % 26 + 65
        String lowerCase = Character.toChars(charPos).toString().toLowerCase()
        return "".padRight(2, lowerCase)
    }

    def outChars = new Integer[length]
    def chars = nameBase64.toCharArray()
    for (int i = 0; chars.size() > i; i++) {
        int c = chars[i]
        int pos = i % length
        if (outChars[pos] == null) {
            outChars[pos] = 0
        }
        int next = (c as int) + outChars[pos]
        outChars[pos] = next % 26
    }

    def result = ""
    for (int i = 0; outChars.size() > i; i++) {
        result += Character.toChars(outChars[i] + 65).toString().toLowerCase()
    }
    return result
}
