#!/usr/bin/env groovy

import io.issenn.devops.jenkins.pipeline.environment.EnvironmentConstants
import io.issenn.devops.jenkins.pipeline.gradle.gradleConstants

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
                        def z = new gradleConstants()
                        z.gradle_version()
                    }
                }
            }

        }
    }
}