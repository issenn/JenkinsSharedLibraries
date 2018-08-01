#!/usr/bin/env groovy

def call(String buildStatus = 'STARTED') {
    // build status of null means successful
    buildStatus = buildStatus ?: 'SUCCESS'

    // Default values
    def subject = "JENKINS-NOTIFICATION: ${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
    def details = """<p>${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>"""

    // Send email to user who has started the build
    emailext(
        subject: subject,
        body: details,
        attachLog: true,
        compressLog: true,
        recipientProviders: [[$class: 'RequesterRecipientProvider'], [$class:'UpstreamComitterRecipientProvider']]
    )
}