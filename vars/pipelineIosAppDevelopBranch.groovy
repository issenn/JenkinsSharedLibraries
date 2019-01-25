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
                        customWorkspace "workspace/${JOB_NAME.replace('%2F', '/')}"
                    }
                }
                when {
                    beforeAgent true
                    not {
                        anyOf {
                            branch "develop"
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
                        label 'mac-mini3'
                        customWorkspace "workspace/${JOB_NAME.replace('%2F', '/')}"
                    }
                }
                when {
                    beforeAgent true
                    branch "develop"
                }
                steps {
                    script {
                        def scmVars = checkoutGitlab()
                    }
                }
            }

            stage('Build') {
                agent {
                    node {
                        label 'mac-mini3'
                        customWorkspace "workspace/${JOB_NAME.replace('%2F', '/')}"
                    }
                }
                environment {
                    PATH = "/usr/local/bin:${PATH}"
                }
                steps {
                    buildDeveopBranch()
                }
                post {
                    success {
                        archiveArtifacts artifacts: 'build/IPA/*.dSYM.zip', fingerprint: true
                        archiveArtifacts artifacts: 'build/IPA/*.ipa', fingerprint: true
                    }
                }
            }
        }
        post {
            success {
                node('master') {
                    echo 'end'
                }
            }
            always {
                node('master') {
                    step([$class: 'Mailer', notifyEveryUnstableBuild: true, recipients: "issenn@hellotalk.com", sendToIndividuals: true])
                }
            }
        }
    }
}

def buildDeveopBranch() {
    echo "Develop branch - Build"
    sh 'bundle install'
    sh 'bundle exec fastlane ios do_publish_all'
}
