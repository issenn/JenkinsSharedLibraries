#!/usr/bin/env groovy

def call() {

    if (env.BRANCH_NAME.startsWith('test/')) {
        pipelineAndroidAppTestBranch()
    } else {
        pipelineAndroidAppTagBranch()
    }
    /*if (env.BRANCH_NAME.startsWith('feature/')) {
        pipelineAndroidAppFeatureBranch()
    } else if (env.BRANCH_NAME == 'develop') {
        pipelineAndroidAppDevelopBranch()
    } else if (env.BRANCH_NAME.startsWith('test/')) {
        pipelineAndroidAppTestBranch()
    } else if (env.BRANCH_NAME == 'release') {
        pipelineAndroidAppReleaseBranch()
    } else if (env.BRANCH_NAME == 'master') {
        pipelineAndroidAppMasterBranch()
    } else if (env.BRANCH_NAME.startsWith('hotfix/')) {
        buildHotfixBranch()
    } else {
        // error "Don't know what to do with this branch: ${env.BRANCH_NAME}"
        pipelineAndroidAppTagBranch()
    }
}

/**
 * feature/* for feature branches; merge back into develop
 * develop for ongoing development work
 * test/*
 * release/* to prepare production releases; merge back into develop and tag master
 * master for production-ready releases
 * hotfix/* to patch master quickly; merge back into develop and tag master
 */



def unittestMasterBranch(String buildTypes='', String productFlavors='') {
    echo "Master branch - Unit Testing"
    buildTypes = changeStringGradleStyle(buildTypes)
    productFlavors = changeStringGradleStyle(productFlavors)
    def args = ((productFlavors ?: '') + (buildTypes ?: '')) ? (((productFlavors ?: '') + (buildTypes ?: '')) + 'UnitTest' ) : ''
    unittest(args)
}

def unittestHotfixBranch(String buildTypes='', String productFlavors='') {
    echo "Hotfix branch - Unit Testing"
    buildTypes = changeStringGradleStyle(buildTypes)
    productFlavors = changeStringGradleStyle(productFlavors)
    def args = ((productFlavors ?: '') + (buildTypes ?: '')) ? (((productFlavors ?: '') + (buildTypes ?: '')) + 'UnitTest' ) : ''
    unittest(args)
}





def buildReleaseBranch(String buildTypes='', String productFlavors='') {
    echo "Release branch - Build"
    buildTypes = changeStringGradleStyle(buildTypes)
    productFlavors = changeStringGradleStyle(productFlavors)
    def args = ((productFlavors ?: '') + (buildTypes ?: '')) + " -PBUILD_NUMBER=${PBUILD_NUMBER}"
    build(args)
}

def buildMasterBranch(String buildTypes='', String productFlavors='') {
    echo "Master branch - Build"
    buildTypes = changeStringGradleStyle(buildTypes)
    productFlavors = changeStringGradleStyle(productFlavors)
    def args = ((productFlavors ?: '') + (buildTypes ?: '')) + " -PBUILD_NUMBER=${PBUILD_NUMBER}"
    build(args)
}

def buildHotfixBranch(String buildTypes='', String productFlavors='') {
    echo "Hotfix branch - Build"
    buildTypes = changeStringGradleStyle(buildTypes)
    productFlavors = changeStringGradleStyle(productFlavors)
    def args = ((productFlavors ?: '') + (buildTypes ?: '')) + " -PBUILD_NUMBER=${PBUILD_NUMBER}"
    build(args)
}





def artifactsReleaseBranch(String buildTypes = '', String productFlavors = '') {
    echo "Release branch - Artifacts"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    artifacts(name, path)
}

def artifactsMasterBranch(String buildTypes = '', String productFlavors = '') {
    echo "Master branch - Artifacts"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    artifacts(name, path)
}

def artifactsHotfixBranch(String buildTypes = '', String productFlavors = '') {
    echo "Hotfix branch - Artifacts"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    artifacts(name, path)
}





def deployReleaseBranch(String buildTypes = '', String productFlavors = '') {
    echo "Release branch - Deploy"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    def targetPath = "/var/www/nginx/html/testing.hellotalk.com/android/package/"
    deploy(name, path, targetPath)
}

def deployMasterBranch(String buildTypes = '', String productFlavors = '') {
    echo "Master branch - Deploy"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    def targetPath = "/var/www/nginx/html/testing.hellotalk.com/android/package/"
    deploy(name, path, targetPath)
}

def deployHotfixBranch(String buildTypes = '', String productFlavors = '') {
    echo "Hotfix branch - Deploy"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    def targetPath = "/var/www/nginx/html/testing.hellotalk.com/android/package/"
    deploy(name, path, targetPath)
}
