#!/usr/bin/env groovy

def call() {

    if (env.BRANCH_NAME.startsWith('feature/')) {
        pipelineAndroidAppFeatureBranch()
    } else if (env.BRANCH_NAME == 'develop') {
        pipelineAndroidAppDevelopBranch()
    } else if (env.BRANCH_NAME.startsWith('test/')) {
        pipelineAndroidAppTestBranch()
    } else if (env.BRANCH_NAME == 'release') {
        pipelineAndroidAppReleaseBranch()
    } else if (env.BRANCH_NAME == 'master') {
        pipelineAndroidAppMasterBranch()
    /*} else if (env.BRANCH_NAME.startsWith('hotfix/')) {
        buildHotfixBranch()*/
    } else {
        error "Don't know what to do with this branch: ${env.BRANCH_NAME}"
    }
}
/*
def aaa(Closure body={}) {


    pipeline {
        agent none
        options {
            skipDefaultCheckout()
        }
        triggers { pollSCM('H * * * *') }
        environment {
            LANG = "C.UTF-8"
            LC_ALL = "en_US.UTF-8"
            LANGUAGE = "en_US.UTF-8"
            UNITTESTING_STATE = 'false'
            TESTING_STATE = 'false'
            App = "HelloTalk"
            CHINAPRODUCTFLAVORS_STATE = 'true'
            GOOGLEPRODUCTFLAVORS_STATE = 'true'
            HTPRIVATEPRODUCTFLAVORS_STATE = 'false'
            PBUILD_NUMBER = "${BUILD_NUMBER}"
        }
        stages {
            stage('Branch and Tag - error') {
                agent { node {
                    label 'master'
                    customWorkspace "workspace/${JOB_NAME}"
                } }
                when {
                    beforeAgent true
                    not {
                        anyOf {
                            branch "feature/*"
                            branch "develop"
                            //branch "test"
                            branch "release"
                            branch "master"
                            branch "hotfix/*"
                            buildingTag()
                        }
                    }
                }
                steps {
                    error "Don't know what to do with this branch or tag: ${env.BRANCH_NAME}"
                }
            }

            stage('entry - develop') {
                environment {
                    buildTypes = "release"
                }
                when {
                    beforeAgent true
                    branch "develop"
                }
                failFast false
                parallel {
                    stage('entry - china flavor - develop') {
                        environment {
                            productFlavors = "china"
                        }
                        when {
                            beforeAgent true
                            environment name: 'CHINAPRODUCTFLAVORS_STATE', value: 'true'
                        }
                        stages {
                            stage('Checkout SCM - china flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    checkoutGitlab()
                                }
                            }
                            stage('Prepare - china flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.version()
                                    }
                                }
                            }
                            stage('clean - china flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.clean()
                                    }
                                }
                            }
                            stage('Unit Testing - china flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'UNITTESTING_STATE', value: 'true'
                                }
                                steps {
                                    unittestDevelopBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Build - china flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    buildDevelopBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Artifacts - china flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    artifactsDevelopBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Deploy - china flavor - develop') {
                                agent {
                                    node {
                                        label 'master'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    deployDevelopBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Testing - china flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'TESTING_STATE', value: 'true'
                                }
                                steps {
                                    echo "Test"
                                }
                            }
                        }
                    }
                    stage('entry - google flavor - develop') {
                        environment {
                            productFlavors = "google"
                        }
                        when {
                            beforeAgent true
                            environment name: 'GOOGLEPRODUCTFLAVORS_STATE', value: 'true'
                        }
                        stages {
                            stage('Checkout SCM - google flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    checkoutGitlab()
                                }
                            }
                            stage('Prepare - google flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.version()
                                    }
                                }
                            }
                            stage('clean - google flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.clean()
                                    }
                                }
                            }
                            stage('Unit Testing - google flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'UNITTESTING_STATE', value: 'true'
                                }
                                steps {
                                    unittestDevelopBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Build - google flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    buildDevelopBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Artifacts - google flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    artifactsDevelopBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Deploy - google flavor - develop') {
                                agent {
                                    node {
                                        label 'master'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    deployDevelopBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Testing - google flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'TESTING_STATE', value: 'true'
                                }
                                steps {
                                    echo "Test"
                                }
                            }
                        }
                    }
                    stage('entry - HTPrivate flavor - develop') {
                        environment {
                            productFlavors = "HTPrivate"
                        }
                        when {
                            beforeAgent true
                            environment name: 'HTPRIVATEPRODUCTFLAVORS_STATE', value: 'true'
                        }
                        stages {
                            stage('Checkout SCM - HTPrivate flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    checkoutGitlab()
                                }
                            }
                            stage('Prepare - HTPrivate flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.version()
                                    }
                                }
                            }
                            stage('clean - HTPrivate flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.clean()
                                    }
                                }
                            }
                            stage('Unit Testing - HTPrivate flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'UNITTESTING_STATE', value: 'true'
                                }
                                steps {
                                    unittestDevelopBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Build - HTPrivate flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    buildDevelopBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Artifacts - HTPrivate flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    artifactsDevelopBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Deploy - HTPrivate flavor - develop') {
                                agent {
                                    node {
                                        label 'master'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    deployDevelopBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Testing - HTPrivate flavor - develop') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'TESTING_STATE', value: 'true'
                                }
                                steps {
                                    echo "Test"
                                }
                            }
                        }
                    }
                }
            }

            stage('entry - release') {
                environment {
                    buildTypes = "release"
                }
                when {
                    beforeAgent true
                    branch "release"
                }
                failFast false
                parallel {
                    stage('entry - china flavor - release') {
                        environment {
                            productFlavors = "china"
                        }
                        when {
                            beforeAgent true
                            environment name: 'CHINAPRODUCTFLAVORS_STATE', value: 'true'
                        }
                        stages {
                            stage('Checkout SCM - china flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    checkoutGitlab()
                                }
                            }
                            stage('Prepare - china flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.version()
                                    }
                                }
                            }
                            stage('clean - china flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.clean()
                                    }
                                }
                            }
                            stage('Unit Testing - china flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'UNITTESTING_STATE', value: 'true'
                                }
                                steps {
                                    unittestReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Build - china flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    buildReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Artifacts - china flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    artifactsReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Deploy - china flavor - release') {
                                agent {
                                    node {
                                        label 'master'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    deployReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Testing - china flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'TESTING_STATE', value: 'true'
                                }
                                steps {
                                    echo "Test"
                                }
                            }
                        }
                    }
                    stage('entry - google flavor - release') {
                        environment {
                            productFlavors = "google"
                        }
                        when {
                            beforeAgent true
                            environment name: 'GOOGLEPRODUCTFLAVORS_STATE', value: 'true'
                        }
                        stages {
                            stage('Checkout SCM - google flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    checkoutGitlab()
                                }
                            }
                            stage('Prepare - google flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.version()
                                    }
                                }
                            }
                            stage('clean - google flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.clean()
                                    }
                                }
                            }
                            stage('Unit Testing - google flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'UNITTESTING_STATE', value: 'true'
                                }
                                steps {
                                    unittestReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Build - google flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    buildReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Artifacts - google flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    artifactsReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Deploy - google flavor - release') {
                                agent {
                                    node {
                                        label 'master'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    deployReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Testing - google flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'TESTING_STATE', value: 'true'
                                }
                                steps {
                                    echo "Test"
                                }
                            }
                        }
                    }
                    stage('entry - HTPrivate flavor - release') {
                        environment {
                            productFlavors = "HTPrivate"
                        }
                        when {
                            beforeAgent true
                            environment name: 'HTPRIVATEPRODUCTFLAVORS_STATE', value: 'true'
                        }
                        stages {
                            stage('Checkout SCM - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    checkoutGitlab()
                                }
                            }
                            stage('Prepare - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.version()
                                    }
                                }
                            }
                            stage('clean - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    script {
                                        gradle.clean()
                                    }
                                }
                            }
                            stage('Unit Testing - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'UNITTESTING_STATE', value: 'true'
                                }
                                steps {
                                    unittestReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Build - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                environment {
                                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                                }
                                steps {
                                    buildReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Artifacts - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    artifactsReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Deploy - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'master'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    deployReleaseBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Testing - HTPrivate flavor - release') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                when {
                                    beforeAgent true
                                    environment name: 'TESTING_STATE', value: 'true'
                                }
                                steps {
                                    echo "Test"
                                }
                            }
                        }
                    }
                }
            }

            stage('entry - master') {
                when {
                    branch "master"
                }
                steps {
                    buildMasterBranch()
                }
            }

            stage('entry - hotfix/*') {
                when {
                    branch "hotfix/*"
                }
                steps {
                    buildHotfixBranch()
                }
            }
        }
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
