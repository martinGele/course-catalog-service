package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.repository.CourseRepository
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import com.kotlinspring.util.courseEntityList as courses
import org.junit.jupiter.api.Assertions.*
import org.springframework.web.util.UriComponentsBuilder


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository


    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        val courses = courses()
        courseRepository.saveAll(courses)
    }

    @Test
    fun addCourse() {
        val courseDTO = CourseDTO(null, "Build restfull api", "Martin")

        val savedDTO = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody


        assertTrue(
            savedDTO!!.id != null
        )
    }

    @Test
    fun retrieveAllCourses() {
        val courseDTO = webTestClient.get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus()
            .isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody
        println("courseDTOs : $courseDTO")
        assertEquals(3, courseDTO?.size)
    }


    @Test
    fun retrieveAllCoursesByName() {

        val uriString = UriComponentsBuilder.fromUriString("/v1/courses")
            .queryParam("course_name", "SpringBoot")
            .toUriString()

        val courseDTO = webTestClient.get()
            .uri(uriString)
            .exchange()
            .expectStatus()
            .isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody
        println("courseDTOs : $courseDTO")
        assertEquals(2, courseDTO?.size)
    }


    @Test
    fun updateCourse() {
        val course = Course(null, "Build restfull api", "Development")
        courseRepository.save(course)

        val updatedCourseDTO = CourseDTO(course.id, "Build restfull api", "Development")

        val updatedCourse = webTestClient
            .put()
            .uri("/v1/courses/{course.id}", course.id)
            .bodyValue(updatedCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals("Build restfull api", updatedCourse?.name)
    }

}