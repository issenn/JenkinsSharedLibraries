#!/usr/bin/env groovy

import java.text.SimpleDateFormat;

def datetime() {
    def dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    def date = new Date()
    return dateFormat.format(date)
}