package com.kotlinspring.dto

import javax.persistence.*
import javax.validation.constraints.NotBlank

data class InstructorDTO(
    val id: Int?,
    @get:NotBlank(message = "instructorDTO.name must not be blank")
    var name: String
)