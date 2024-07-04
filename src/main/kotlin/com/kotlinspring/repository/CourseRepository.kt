package com.kotlinspring.repository

import com.kotlinspring.entity.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int>{

    fun findByNameContaining(name: String): List<Course>
}