package com.firstcircle.techassessment.infrastructure

interface LockService {
    fun acquireLock(id: Long)
}