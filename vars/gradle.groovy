#!/usr/bin/env groovy

def call(String command) {
    sh "set +x && ./gradlew ${command}"
}