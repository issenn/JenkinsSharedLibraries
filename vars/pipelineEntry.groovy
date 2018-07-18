#!/usr/bin/env groovy

import io.issenn.devops.jenkins.pipeline.environment.EnvironmentConstants

def call() {
    if (env.CICD_TYPE == 'AndroidApp') {
        pipelineAndroidApp()
    } else if (env.CICD_TYPE == 'IosApp') {
        pipelineIosApp()
    } else {
        error "Don't know what to do with this CICD_TYPE: ${env.CICD_TYPE}"
    }
}