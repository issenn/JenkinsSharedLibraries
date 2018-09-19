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
                            buildingTag()
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
                    buildingTag()
                }
                steps {
                    script {
                        def scmVars = checkoutGithub()
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
                    // unittestTagBranch(buildTypes, productFlavors)
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
                    ANDROID_SDK_ROOT = "${HOME}/Library/Android/sdk"
                    ANDROID_HOME = "${ANDROID_SDK_ROOT}"
                    PATH = "/usr/local/bin:${PATH}"
                }
                steps {
                    buildTagBranch()
                }
            }
        }
    }
}

def unittestTagBranch(String buildTypes='', String productFlavors='') {
    echo "Tag branch - Unit Testing"
    buildTypes = pipelineAndroidAppSetup.changeStringGradleStyle(buildTypes)
    productFlavors = pipelineAndroidAppSetup.changeStringGradleStyle(productFlavors)
    def args = ((productFlavors ?: '') + (buildTypes ?: '')) ? (((productFlavors ?: '') + (buildTypes ?: '')) + 'UnitTest' ) : ''
    pipelineAndroidAppSetup.unittest(args)
}

def buildTagBranch() {
    echo "Tag branch - Build"
    // buildTypes = pipelineAndroidAppSetup.changeStringGradleStyle(buildTypes)
    // productFlavors = pipelineAndroidAppSetup.changeStringGradleStyle(productFlavors)
    // def args = ((productFlavors ?: '') + (buildTypes ?: '')) //+ " publish"
    // pipelineAndroidAppSetup.build(args)
    sh 'bundle install'
    sh 'bundle update'
    sh 'bundle exec fastlane android do_publish_all'
}

def artifactsTagBranch(String buildTypes = '', String productFlavors = '') {
    echo "Tag branch - Artifacts"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    pipelineAndroidAppSetup.artifacts(name, path)
}

def deployTagBranch(String buildTypes = '', String productFlavors = '') {
    echo "Tag branch - Deploy"
    def name = "${App}" + (((productFlavors ? ('-' + productFlavors) : '') + (buildTypes ? ('-'+ buildTypes) : '')) ?: '')
    def path = "${App}/build/outputs/apk/" + (productFlavors ?: '*') + '/' + (buildTypes ?: '*') + "/${App}-" + (productFlavors ?: '*') + '-' + (buildTypes ?: '*') + '.apk'
    def targetPath = "/var/www/nginx/html/testing.hellotalk.com/android/package/"
    pipelineAndroidAppSetup.deploy(name, path, targetPath)
}