package io.issenn.devops.jenkins.pipeline.gradle

/**
 * Constants for environment variables used by Pipeline scripts and by Jenkins
 */
class GradleConstants implements Serializable {

    GradleConstants() {}

    def mvn() {
        sh "ls"
        return "123"
    }
    def gradle(String command) {
        sh "set +x && ./gradlew ${command}"
    }

    def gradle_version() {
        gradle '-v'
    }
}