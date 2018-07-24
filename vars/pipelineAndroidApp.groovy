#!/usr/bin/env groovy

import org.apache.commons.lang3.StringUtils

def call(Closure body={}) {

    // evaluate the body block, and collect configuration into the object
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
        agent none

        options {
            skipDefaultCheckout()
        }

        triggers {
            pollSCM('H * * * *')
        }

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
                agent {
                    node {
                        label 'master'
                        customWorkspace "workspace/${JOB_NAME}"
                    }
                }
                when {
                    beforeAgent true
                    not {
                        anyOf {
                            branch "feature/*"
                            branch "develop"
                            //branch "test"
                            branch "release"
                            branch "release/*"
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
            stage('entry - feature/*') {
                environment {
                    buildTypes = "release"
                }
                when {
                    beforeAgent true
                    branch "feature/*"
                }
                failFast false
                parallel {
                    stage('entry - china flavor - feature/*') {
                        environment {
                            productFlavors = "china"
                        }
                        when {
                            beforeAgent true
                            environment name: 'CHINAPRODUCTFLAVORS_STATE', value: 'true'
                        }
                        stages {
                            stage('Checkout SCM - china flavor - feature/*') {
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
                            stage('Prepare - china flavor - feature/*') {
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
                            stage('clean - china flavor - feature/*') {
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
                            stage('Unit Testing - china flavor - feature/*') {
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
                                    unittestFeatureBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Build - china flavor - feature/*') {
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
                                    buildFeatureBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Artifacts - china flavor - feature/*') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    artifactsFeatureBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Deploy - china flavor - feature/*') {
                                agent {
                                    node {
                                        label 'master'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    deployFeatureBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Testing - china flavor - feature/*') {
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
                    stage('entry - google flavor - feature/*') {
                        environment {
                            productFlavors = "google"
                        }
                        when {
                            beforeAgent true
                            environment name: 'GOOGLEPRODUCTFLAVORS_STATE', value: 'true'
                        }
                        stages {
                            stage('Checkout SCM - google flavor - feature/*') {
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
                            stage('Prepare - google flavor - feature/*') {
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
                            stage('clean - google flavor - feature/*') {
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
                            stage('Unit Testing - google flavor - feature/*') {
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
                                    unittestFeatureBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Build - google flavor - feature/*') {
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
                                    buildFeatureBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Artifacts - google flavor - feature/*') {
                                agent {
                                    node {
                                        label 'mac-mini2'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    artifactsFeatureBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Deploy - google flavor - feature/*') {
                                agent {
                                    node {
                                        label 'master'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    deployFeatureBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Testing - google flavor - feature/*') {
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
                    stage('entry - HTPrivate flavor - feature/*') {
                        environment {
                            productFlavors = "HTPrivate"
                        }
                        when {
                            beforeAgent true
                            environment name: 'HTPRIVATEPRODUCTFLAVORS_STATE', value: 'true'
                        }
                        stages {
                            stage('Checkout SCM - HTPrivate flavor - feature/*') {
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
                            stage('Prepare - HTPrivate flavor - feature/*') {
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
                            stage('clean - HTPrivate flavor - feature/*') {
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
                            stage('Unit Testing - HTPrivate flavor - feature/*') {
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
                                    unittestFeatureBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Build - HTPrivate flavor - feature/*') {
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
                                    buildFeatureBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Artifacts - HTPrivate flavor - feature/*') {
                                agent {
                                    node {
                                        label 'mac-mini3'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    artifactsFeatureBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Deploy - HTPrivate flavor - feature/*') {
                                agent {
                                    node {
                                        label 'master'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }
                                steps {
                                    deployFeatureBranch(buildTypes, productFlavors)
                                }
                            }
                            stage('Testing - HTPrivate flavor - feature/*') {
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

            stage('Build snapshot - release/*') {
                when {
                    branch "release/*"
                }
                steps {
                    buildReleaseBranch()
                }
            }

            stage('Build @ Prod - master') {
                when {
                    branch "master"
                }
                steps {
                    buildMasterBranch()
                }
            }

            stage('Build snapshot - hotfix/*') {
                when {
                    branch "hotfix/*"
                }
                steps {
                    buildHotfixBranch()
                }
            }
            /*
            // artifacts
            stage('artifacts') {
                steps {
                    echo "artifacts"
                }
            }
            // Deploy
            stage('Deploy snapshot - feature/*') {
                when {
                    branch "feature/*"
                }
                steps {
                    deployFeatureBranch()
                }
            }

            stage('Deploy snapshot - develop') {
                when {
                    branch "develop"
                }
                steps {
                    deployDevelopBranch()
                }
            }

            stage('Deploy snapshot - release/*') {
                when {
                    branch "release/*"
                }
                steps {
                    deployReleaseBranch()
                }
            }

            stage('Deploy @ Prod - master') {
                when {
                    branch "master"
                }
                steps {
                    deployMasterBranch()
                }
            }

            stage('Deploy snapshot - hotfix/*') {
                when {
                    branch "hotfix/*"
                }
                steps {
                    deployHotfixBranch()
                }
            }

            stage('Test') {
                steps {
                    echo "Test"
                }
            }*/
        }
    }
}

def changeStringStyle(String str, boolean toCamel) {
    if(!str || str.size() <= 1)
        return str

    if(toCamel){
        String r = str.toLowerCase().split('_').collect{cc -> StringUtils.capitalize(cc)}.join('')
        return r
    } else {
        str = str[0].toLowerCase() + str[1..-1]
        return str.collect{cc -> ((char)cc).isUpperCase() ? '_' + cc.toLowerCase() : cc}.join('')
    }
}

def changeStringGradleStyle(String str) {
    if(!str || str.size() <= 1)
        return str

    return str[0].toUpperCase() + str[1..-1]
}

/**
 * feature/* for feature branches; merge back into develop
 * develop for ongoing development work
 * test/*
 * release/* to prepare production releases; merge back into develop and tag master
 * master for production-ready releases
 * hotfix/* to patch master quickly; merge back into develop and tag master
 */

def unittestFeatureBranch(String buildTypes='', String productFlavors='') {
    echo "Feature branch - Unit Testing"
    buildTypes = changeStringGradleStyle(buildTypes)
    productFlavors = changeStringGradleStyle(productFlavors)
    def args = ((productFlavors ?: '') + (buildTypes ?: '')) ? (((productFlavors ?: '') + (buildTypes ?: '')) + 'UnitTest' ) : ''
    unittest(args)
}

def unittestDevelopBranch(String buildTypes='', String productFlavors='') {
    echo "Develop branch - Unit Testing"
    buildTypes = changeStringGradleStyle(buildTypes)
    productFlavors = changeStringGradleStyle(productFlavors)
    def args = ((productFlavors ?: '') + (buildTypes ?: '')) ? (((productFlavors ?: '') + (buildTypes ?: '')) + 'UnitTest' ) : ''
    unittest(args)
}

def buildFeatureBranch(String buildTypes='', String productFlavors='') {
    echo "Feature branch - Build"
    buildTypes = changeStringGradleStyle(buildTypes)
    productFlavors = changeStringGradleStyle(productFlavors)
    def args = ((productFlavors ?: '') + (buildTypes ?: '')) + " -PBUILD_NUMBER=${PBUILD_NUMBER}"
    build(args)
}

def buildDevelopBranch(String buildTypes='', String productFlavors='') {
    echo "Develop branch - Build"
    buildTypes = changeStringGradleStyle(buildTypes)
    productFlavors = changeStringGradleStyle(productFlavors)
    def args = ((productFlavors ?: '') + (buildTypes ?: '')) + " -PBUILD_NUMBER=${PBUILD_NUMBER}"
    build(args)
    // sonar()
    // javadoc()
    // deploy(env.JBOSS_TST)
}

def buildReleaseBranch() {
    echo "Release branch"
}

def buildMasterBranch() {
    echo "Master branch"
}

def buildHotfixBranch() {
    echo "Hotfix branch"
}

def artifactsFeatureBranch(String buildTypes = '', String productFlavors = '') {
    echo "Feature branch - Artifacts"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    artifacts(name, path)
}

def artifactsDevelopBranch(String buildTypes = '', String productFlavors = '') {
    echo "Develop branch - Artifacts"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    artifacts(name, path)
}


def deployFeatureBranch(String buildTypes = '', String productFlavors = '') {
    echo "Feature branch - Deploy"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    def targetPath = "/var/www/nginx/html/testing.hellotalk.com/android/package/"
    deploy(name, path, targetPath)
}

def deployDevelopBranch(String buildTypes = '', String productFlavors = '') {
    echo "Develop branch - Deploy"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    def targetPath = "/var/www/nginx/html/testing.hellotalk.com/android/package/"
    deploy(name, path, targetPath)
}

def deployReleaseBranch() {
    echo "Feature branch"
}

def deployMasterBranch() {
    echo "Feature branch"
}

def deployHotfixBranch() {
    echo "Feature branch"
}

def unittest(String args='') {
    echo "Unit Testing"
    gradle "test${args}"
}

def build(String args='') {
    echo "Build args='${args}'"
    gradle "assemble${args}"
}

def artifacts(String name, String path) {
    echo "stash '${name}' '${path}'"
    stash name: "${name}", includes: "${path}"
}

def deploy(String name, String path, String targetPath) {
    echo "unstash '${name}' '${path}'"
    unstash "${name}"
    sh "mv ${WORKSPACE}/${path} ${targetPath}"
}