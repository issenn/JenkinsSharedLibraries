#!/usr/bin/env groovy

def call(Closure body={}) {

    // evaluate the body block, and collect configuration into the object
    def pipelineParams= [:]
    body.resolveStrategy = Closure.DELEGATE_FIRST
    body.delegate = pipelineParams
    body()

    pipeline {
        agent {
            label 'mac-mini'
        }

        environment {
            ANDROID_SDK_ROOT="${HOME}/Library/Android/sdk"
            ANDROID_HOME="${ANDROID_SDK_ROOT}"
        }

        stages {
            stage('Checkout') {
                steps {
                    checkoutGitlab()
                }
            }

            stage('Prepare') {
                steps {
                    script {
                        //def z = new gradleConstants(this)
                        //z.gradle_version()
                        //mvn this
                        def utils = io.issenn.gradleConstants.new(this)
                        println(utils.mvn())
                    }
                }
            }

        }
    }
}