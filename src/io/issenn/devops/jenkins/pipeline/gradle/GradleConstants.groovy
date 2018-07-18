package io.issenn.devops.jenkins.pipeline.gradle

/**
 * Constants for environment variables used by Pipeline scripts and by Jenkins
 */
class GradleConstants implements Serializable {

    GradleConstants() {}

    def mvn() {
        return sh(returnStdout: true, script: "ls").trim()
    }
    def gradle(String command) {
        sh "set +x && ./gradlew ${command}"
    }

    def gradle_version() {
        gradle '-v'
    }
}