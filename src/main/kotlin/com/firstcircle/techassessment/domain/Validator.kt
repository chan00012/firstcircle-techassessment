package com.firstcircle.techassessment.domain

fun interface Validator<INPUT> {
    fun validate(input: INPUT)
}