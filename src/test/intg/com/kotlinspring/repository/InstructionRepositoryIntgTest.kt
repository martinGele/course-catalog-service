package com.kotlinspring.repository

import com.kotlinspring.util.instructorEntityList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles


@DataJpaTest
@ActiveProfiles("test")
class InstructionRepositoryIntgTest {


    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setUp() {

        instructorRepository.deleteAll()
        val courses = instructorEntityList()
        instructorRepository.saveAll(courses)
    }


    @Test
    fun findCoursesbyName() {

        val courses = instructorRepository.findInstructor("Martin")
        println("courses : $courses")

        assertEquals(1, courses.size)

    }
}