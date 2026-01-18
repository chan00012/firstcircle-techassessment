package com.firstcircle.techassessment.interfaces

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    @GetMapping("/get")
    fun get() {
        TODO()
    }
}