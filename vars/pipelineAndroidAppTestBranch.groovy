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
            ansiColor('xterm')
            retry(3)
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
                            branch "test/*"
                        }
                    }
                }
                steps {
                    error "Don't know what to do with this branch or tag: ${env.BRANCH_NAME}"
                }
            }

            stage('Checkout SCM') {
                agent {
                    node {
                        label 'mac-mini1'
                        customWorkspace "workspace/${JOB_NAME}"
                    }
                }
                when {
                    beforeAgent true
                    branch "test/*"
                }
                steps {
                    script {
                        def scmVars = checkoutGitlab()
                    }
                }
            }

            stage('Unit Testing') {
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
                    // unittestTestBranch(buildTypes, productFlavors)
                    echo 'pass'
                }
            }

            stage('Build') {
                agent {
                    node {
                        label 'mac-mini1'
                        customWorkspace "workspace/${JOB_NAME}"
                    }
                }
                environment {
                    // ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                    ANDROID_SDK_ROOT = "/usr/local/Caskroom/android-sdk/4333796"
                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                    PATH = "/Users/mac/.rbenv/shims:/usr/local/bin:${PATH}"
                }
                steps {
                    buildTestBranch()
                }
            }
        }
    }
}

def unittestTestBranch(String buildTypes='', String productFlavors='') {
    echo "Test branch - Unit Testing"
    buildTypes = pipelineAndroidAppSetup.changeStringGradleStyle(buildTypes)
    productFlavors = pipelineAndroidAppSetup.changeStringGradleStyle(productFlavors)
    def args = ((productFlavors ?: '') + (buildTypes ?: '')) ? (((productFlavors ?: '') + (buildTypes ?: '')) + 'UnitTest' ) : ''
    pipelineAndroidAppSetup.unittest(args)
}

def buildTestBranch() {
    echo "Test branch - Build"
    // buildTypes = pipelineAndroidAppSetup.changeStringGradleStyle(buildTypes)
    // productFlavors = pipelineAndroidAppSetup.changeStringGradleStyle(productFlavors)
    // def args = ((productFlavors ?: '') + (buildTypes ?: '')) //+ " publish"
    // pipelineAndroidAppSetup.build(args)
    sh 'bundle install'
    // sh 'bundle update'
    sh 'bundle exec fastlane android do_publish_all'
}

def artifactsTestBranch(String buildTypes = '', String productFlavors = '') {
    echo "Test branch - Artifacts"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    pipelineAndroidAppSetup.artifacts(name, path)
}

def deployTestBranch(String buildTypes = '', String productFlavors = '') {
    echo "Test branch - Deploy"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    def targetPath = "/var/www/nginx/html/testing.hellotalk.com/android/package/"
    pipelineAndroidAppSetup.deploy(name, path, targetPath)
}
