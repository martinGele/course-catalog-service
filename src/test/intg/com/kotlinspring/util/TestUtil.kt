package com.kotlinspring.util

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.dto.InstructorDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.entity.Instructor

fun courseEntityList() = listOf(
    Course(
        null,
        "Build RestFul APis using SpringBoot and Kotlin", "Development"
    ),
    Course(
        null,
        "Build Reactive Microservices using Spring WebFlux/SpringBoot", "Development",
    ),
    Course(
        null,
        "Wiremock for Java Developers", "Development",
    )
)

fun courseDTO(
    id: Int? = null,
    name: String = "Build RestFul APis using Spring Boot and Kotlin",
    category: String = "Dilip Sundarraj",
    //instructorId: Int? = 1
) = CourseDTO(
    id,
    name,
    category,
    //instructorId
)


fun instructorDTO(
    id: Int? = null,
    name: String = "Martin Gelevski",
) = InstructorDTO(id, name)


fun courseEntityList(instructor: Instructor? = null) = listOf(
    Course(
        null,
        "Build RestFul APis using SpringBoot and Kotlin", "Development",
        instructor
    ),
    Course(
        null,
        "Build Reactive Microservices using Spring WebFlux/SpringBoot", "Development", instructor
    ),
    Course(
        null,
        "Wiremock for Java Developers", "Development",
        instructor
    )
)

fun instructorEntity(name: String = "Dilip Sundarraj") = Instructor(null, name)


fun instructorEntityList() = listOf(
    Instructor(null, "Martin"),
    Instructor(null, "Martin Gelevski"),
    Instructor(null, "Martin Gelevski")
)


