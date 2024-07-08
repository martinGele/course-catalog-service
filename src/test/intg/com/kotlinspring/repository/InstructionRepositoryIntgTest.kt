package com.kotlinspring.repository

import com.kotlinspring.db.PostgreSQLContainerInitializer
import com.kotlinspring.util.instructorEntityList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles


@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
class InstructionRepositoryIntgTest : PostgreSQLContainerInitializer() {


    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setUp() {

        instructorRepository.deleteAll()
        val courses = instructorEntityList()
        instructorRepository.saveAll(courses)
    }


    @Test
    fun findInstructorByName() {
        val courses = instructorRepository.findInstructor("Martin")
        assertEquals(1, courses.size)
    }
}