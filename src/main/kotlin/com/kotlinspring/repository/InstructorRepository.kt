package com.kotlinspring.repository

import com.kotlinspring.entity.Instructor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface InstructorRepository : CrudRepository<Instructor,Int>{


    @Query(value = "SELECT * FROM INSTRUCTORS where name like %?1%", nativeQuery = true)
    fun findInstructor(instructorName : String) : List<Instructor>
}