#!/usr/bin/env groovy

def call(String repoNme='') {
    def repoName = libraryResource('repoName.json')
    println(repoName)
}