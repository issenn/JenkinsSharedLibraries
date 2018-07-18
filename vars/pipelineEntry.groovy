#!/usr/bin/env groovy

import io.issenn.devops.jenkins.pipeline.environment.EnvironmentConstants

def call() {
    if (env.CICD_TYPE == 'AndroidApp') {
        println(pipelineAndroidApp)
        log.info("pipelineAndroidApp")
        pipelineAndroidApp()
    } else if (env.CICD_TYPE == 'IosApp') {
        println(pipelineIosApp)
        log.info("pipelineIosApp")
        pipelineIosApp()
    } else {
        error "Don't know what to do with this CICD_TYPE: ${env.CICD_TYPE}"
    }
}