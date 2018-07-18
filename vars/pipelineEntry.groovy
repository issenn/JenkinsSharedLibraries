#!/usr/bin/env groovy

import java.text.SimpleDateFormat;
import io.issenn.devops.jenkins.pipeline.environment.EnvironmentConstants

def call() {
    //def dateFormat
    //def date
    def formattedDate

    //formattedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date())
    //date = new Date()
    //formattedDate = dateFormat.format(date)

    echo "${formattedDate}"

    if (env.CICD_TYPE == 'AndroidApp') {
        log.info("entry pipelineAndroidApp")
        pipelineAndroidApp(formattedDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()))
    } else if (env.CICD_TYPE == 'IosApp') {
        log.info("entry pipelineIosApp")
        pipelineIosApp()
    } else {
        error "Don't know what to do with this CICD_TYPE: ${env.CICD_TYPE}"
    }
}