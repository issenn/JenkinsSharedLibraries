#!/usr/bin/env groovy

def call() {

    if (env.BRANCH_NAME.startsWith('feature/')) {
        pipelineIosAppFeatureBranch()
    } else if (env.BRANCH_NAME == 'dev') {
        pipelineIosAppDevelopBranch()
    /*} else if (env.BRANCH_NAME.startsWith('test/')) {
        pipelineIosAppTestBranch()
    } else if (env.BRANCH_NAME == 'release') {
        pipelineIosAppReleaseBranch()
    } else if (env.BRANCH_NAME == 'master') {
        pipelineIosAppMasterBranch()
    /*} else if (env.BRANCH_NAME.startsWith('hotfix/')) {
        buildHotfixBranch()*/
    /*} else {
        // error "Don't know what to do with this branch: ${env.BRANCH_NAME}"
        pipelineIosAppTagBranch()*/
    }
}