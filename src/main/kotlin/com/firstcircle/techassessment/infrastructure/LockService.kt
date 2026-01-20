package com.firstcircle.techassessment.infrastructure

interface LockService {
    fun <T> withLock(id: Long, action: () -> T): T
}