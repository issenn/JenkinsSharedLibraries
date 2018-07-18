#!/usr/bin/env groovy

import java.text.SimpleDateFormat;

def datetime(Date date = new Date()) {
    def dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    return dateFormat.format(date)
}

def timestamp(Date date = new Date()){
    return date.format('yyyyMMddHHmmss',TimeZone.getTimeZone('GMT')) as String
}