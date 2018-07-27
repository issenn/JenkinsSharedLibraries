#!/usr/bin/env groovy

def call(String command) {
    sh "set +x && ./gradlew ${command}"
}

def version() {
    gradle '-v'
}

def clean() {
    gradle "clean --offlline"
}