#!/usr/bin/env groovy

def call(path) {
    return createFilePath("${path}").list()
}