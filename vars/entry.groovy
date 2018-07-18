#!/usr/bin/env groovy

import io.issenn.devops.jenkins.pipeline.environment.EnvironmentConstants

def call(String repoNme='') {
    def repoName = libraryResource('repoName.json')
    println(repoName)
}