package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.dto.InstructorDTO
import com.kotlinspring.repository.CourseRepository
import com.kotlinspring.repository.InstructorRepository
import com.kotlinspring.util.courseEntityList
import com.kotlinspring.util.instructorEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class InstructorControllerIntgTest {


    @Autowired
    lateinit var webTestClient: WebTestClient


    @Autowired
    lateinit var instructorRepository: InstructorRepository


    @BeforeEach
    fun setUp() {

        instructorRepository.deleteAll()
        val instructor = instructorEntity()
        instructorRepository.save(instructor)

    }


    @Test
    fun addCourse() {

        val instructor = instructorRepository.findAll().first()


        val instructorDTO = InstructorDTO(null, "Martin Gelevski")

        val savedInstructorDTO = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            savedInstructorDTO!!.id != null
        }
    }

}