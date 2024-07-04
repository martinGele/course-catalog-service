package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.service.CourseService
import com.kotlinspring.util.courseDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient


@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient


    @MockkBean
    lateinit var courseServiceMockk: CourseService


    @Test
    fun addCourse() {
        //given
        val courseDTO = CourseDTO(null, "Build restfull api", "Martin")

        //when
        every { courseServiceMockk.addCourse(any()) } returns courseDTO(id = 1)

        val savedCourseDTO = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        //then
        assertTrue(savedCourseDTO!!.id != null)
    }


    @Test
    fun retrieveAllCourses() {

        every { courseServiceMockk.retrieveAllCourses(null) }.returnsMany(
            listOf(
                courseDTO(id = 1),
                courseDTO(
                    id = 2, name = "Build Reactive Microservices using Spring WebFlux/SpringBoot"
                )
            )
        )


        val courseDTOs = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        println("courseDTOs : $courseDTOs")

        Assertions.assertEquals(2, courseDTOs!!.size)

    }

    @Test
    fun updateCourse() {

        val updatedCourseEntity = Course(
            null,
            "Apache Kafka for Developers using Spring Boot1", "Development"
        )

        every { courseServiceMockk.updateCourse(any(), any()) } returns CourseDTO(
            100,
            "Apache Kafka for Developers using Spring Boot1", "Development"
        )


        val updatedCourseDTO = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", 100)
            .bodyValue(updatedCourseEntity)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertEquals("Apache Kafka for Developers using Spring Boot1", updatedCourseDTO?.name)

    }


    @Test
    fun deleteCourse() {
        every { courseServiceMockk.deleteCourse(any()) } just runs
        val deleteCourse = webTestClient
            .delete()
            .uri("/v1/courses/{courseId}", 100)
            .exchange()
            .expectStatus().isNoContent
            .expectBody(Void::class.java)
            .returnResult()
            .responseBody

        Assertions.assertNull(deleteCourse)
    }


    @Test
    fun addCourse_validation() {
        //given
        val courseDTO = courseDTO(name = "", category = "")

        every { courseServiceMockk.addCourse(any()) } returns courseDTO(id = 1)

        //when
        val response = webTestClient
            .post()
            .uri("/v1/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        println("response : $response")
        assertEquals(
            "courseDTO.category must not be blank, courseDTO.name must not be blank", response
        )
    }

    @Test
    fun addCourse_runtime_exception(){
        val courseDTO = courseDTO()
        val errorMessage = "Unexpected error Occurred"
        every { courseServiceMockk.addCourse(any()) } throws RuntimeException(errorMessage)

        val response = webTestClient.post()
            .uri("/v1/courses")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals(errorMessage, response)
    }


}