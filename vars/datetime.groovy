#!/usr/bin/env groovy

import java.text.SimpleDateFormat;

def dateFormat
def date

def datetime() {
    dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    date = new Date()
    return dateFormat.format(date)
}