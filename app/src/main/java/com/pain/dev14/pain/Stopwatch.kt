package com.pain.dev14.pain

/**
 * Created by dev14 on 07.08.17.
 */

class Stopwatch {
    var startTime: Long = 0
    var stopTime: Long = 0
    var running = false


    fun start() {
        this.startTime = System.currentTimeMillis()
        this.running = true
    }


    fun stop() {
        this.stopTime = System.currentTimeMillis()
        this.running = false
    }


    // elaspsed time in milliseconds
    val elapsedTime: Long
        get() {
            if (running) {
                return System.currentTimeMillis() - startTime
            }
            return stopTime - startTime
        }


    // elaspsed time in seconds
    val elapsedTimeSecs: Long
        get() {
            if (running) {
                return (System.currentTimeMillis() - startTime) / 1000
            }
            return (stopTime - startTime) / 1000
        }
}
