#!/usr/bin/env groovy

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
            buildTypes = "release"
        }

        stages {
            stage('Check Branch/Tag') {
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
                        }
                    }
                }

                steps {
                    error "Don't know what to do with this branch or tag: ${env.BRANCH_NAME}"
                }
            }

            stage('echo') {
                agent {
                    node {
                        label 'mac-mini1'
                        customWorkspace "workspace/${JOB_NAME}"
                    }
                }
                steps {
                    sh 'env|sort'
                    sh 'printenv|sort'
                }
            }

            stage('Start') {
                when {
                    beforeAgent true
                    branch "feature/*"
                }

                failFast false

                parallel {
                    stage('Start - china flavor') {
                        environment {
                            productFlavors = "china"
                        }

                        when {
                            beforeAgent true
                            environment name: 'CHINAPRODUCTFLAVORS_STATE', value: 'true'
                        }

                        stages {
                            stage('Checkout SCM - china flavor') {
                                agent {
                                    node {
                                        label 'mac-mini1'
                                        customWorkspace "workspace/${JOB_NAME}"
                                    }
                                }

                                steps {
                                    script {
                                        def scmVars = checkoutGitlab()
                                        println(scmVars.GIT_COMMIT)
                                    }
                                    sh "echo ${env.CHANGE_ID}"
                                    sh 'env|sort'
                                    sh 'printenv|sort'
                                }
                            }

                            stage('Prepare - china flavor') {
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

                            stage('clean - china flavor') {
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

                            stage('Unit Testing - china flavor') {
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

                            stage('Build - china flavor') {
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

                            stage('Artifacts - china flavor') {
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

                            stage('Deploy - china flavor') {
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

                            stage('Testing - china flavor') {
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

                    stage('Start - google flavor') {
                        environment {
                            productFlavors = "google"
                        }

                        when {
                            beforeAgent true
                            environment name: 'GOOGLEPRODUCTFLAVORS_STATE', value: 'true'
                        }

                        stages {
                            stage('Checkout SCM - google flavor') {
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

                            stage('Prepare - google flavor') {
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

                            stage('clean - google flavor') {
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
                            stage('Unit Testing - google flavor') {
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

                            stage('Build - google flavor') {
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

                            stage('Artifacts - google flavor') {
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

                            stage('Deploy - google flavor') {
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

                            stage('Testing - google flavor') {
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

                    stage('Start - HTPrivate flavor - feature/*') {
                        environment {
                            productFlavors = "HTPrivate"
                        }

                        when {
                            beforeAgent true
                            environment name: 'HTPRIVATEPRODUCTFLAVORS_STATE', value: 'true'
                        }

                        stages {
                            stage('Checkout SCM - HTPrivate flavor') {
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
                            stage('Prepare - HTPrivate flavor') {
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

                            stage('clean - HTPrivate flavor') {
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

                            stage('Unit Testing - HTPrivate flavor') {
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

                            stage('Build - HTPrivate flavor') {
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

                            stage('Artifacts - HTPrivate flavor') {
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
                            stage('Deploy - HTPrivate flavor') {
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

                            stage('Testing - HTPrivate flavor') {
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
        }
    }
}

def unittestFeatureBranch(String buildTypes='', String productFlavors='') {
    echo "Feature branch - Unit Testing"
    buildTypes = pipelineAndroidAppSetup.changeStringGradleStyle(buildTypes)
    productFlavors = pipelineAndroidAppSetup.changeStringGradleStyle(productFlavors)
    def args = ((productFlavors ?: '') + (buildTypes ?: '')) ? (((productFlavors ?: '') + (buildTypes ?: '')) + 'UnitTest' ) : ''
    pipelineAndroidAppSetup.unittest(args)
}

def buildFeatureBranch(String buildTypes='', String productFlavors='') {
    echo "Feature branch - Build"
    buildTypes = pipelineAndroidAppSetup.changeStringGradleStyle(buildTypes)
    productFlavors = pipelineAndroidAppSetup.changeStringGradleStyle(productFlavors)
    def args = ((productFlavors ?: '') + (buildTypes ?: '')) + " -PBUILD_NUMBER=${PBUILD_NUMBER}"
    pipelineAndroidAppSetup.build(args)
}

def artifactsFeatureBranch(String buildTypes = '', String productFlavors = '') {
    echo "Feature branch - Artifacts"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    pipelineAndroidAppSetup.artifacts(name, path)
}

def deployFeatureBranch(String buildTypes = '', String productFlavors = '') {
    echo "Feature branch - Deploy"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    def targetPath = "/var/www/nginx/html/testing.hellotalk.com/android/package/"
    pipelineAndroidAppSetup.deploy(name, path, targetPath)
}

