#!/usr/bin/env groovy

import io.issenn.devops.jenkins.pipeline.environment.EnvironmentConstants

def call() {
    def repoDict = libraryResource('repoName.json')
    println(env.BRANCH_NAME)
    println(BRANCH_NAME)
    script {
        sh 'printenv'
        sh 'env'
    }
}

/*

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
                    echo "${formattedDate} - Checkout"

                    // checkout_gitlab()
                    checkoutGitlab()
                }
            }




        }
    }
*/