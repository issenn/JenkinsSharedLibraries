#!/usr/bin/env groovy

import io.issenn.devops.jenkins.pipeline.environment.EnvironmentConstants

def call() {
    t = datetime.datetime()
    println(t)

    if (env.CICD_TYPE == 'AndroidApp') {
        log.info("entry pipelineAndroidApp")
        pipelineAndroidApp()
    } else if (env.CICD_TYPE == 'IosApp') {
        log.info("entry pipelineIosApp")
        pipelineIosApp()
    } else {
        error "Don't know what to do with this CICD_TYPE: ${env.CICD_TYPE}"
    }
}