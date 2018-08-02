#!/usr/bin/env groovy

def call(String path) {
    sh "fir publish -T 9611b6a99d280463039cbb64b7eb24ca ${path}"
}