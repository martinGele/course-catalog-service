package com.kotlinspring.dto

import com.kotlinspring.entity.Instructor
import javax.persistence.FetchType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.NotBlank

data class CourseDTO(
    val id: Int?,
    @get: NotBlank(message = "courseDTO.name must not be blank")
    val name: String,
    @get: NotBlank(message = "courseDTO.category must not be blank")
    val category: String,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "INSTRUCTOR_ID", nullable = false)
    val instructor: Instructor? = null
)