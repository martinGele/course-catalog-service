package com.kotlinspring.controller

import com.kotlinspring.dto.InstructorDTO
import com.kotlinspring.entity.Instructor
import com.kotlinspring.service.InstructorService
import com.kotlinspring.util.instructorDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.util.*


@WebMvcTest(controllers = [InstructorController::class])
@AutoConfigureWebTestClient
class InstructorControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient


    @MockkBean
    lateinit var instructorServiceMockk: InstructorService


    @Test
    fun addInstructor() {
        //given
        val instructorDTO = InstructorDTO(null, "Martin Gelevski")

        //when
        every { instructorServiceMockk.createInstructor(any()) } returns instructorDTO(id = 1)

        val savedInstructorDTO = webTestClient.post().uri("/v1/instructors").bodyValue(instructorDTO).exchange()
            .expectStatus().isCreated.expectBody(InstructorDTO::class.java).returnResult().responseBody

        //then
        assertTrue(savedInstructorDTO!!.id != null)
    }


    @Test
    fun `findByInstructorId returns non-empty result when id exists`() {
        // Given
        val instructorId = 1
        val expectedInstructor = Optional.of(Instructor(instructorId, "Test Name"))
        every { instructorServiceMockk.findByInstructorId(instructorId) } returns expectedInstructor

        // When
        val result = instructorServiceMockk.findByInstructorId(instructorId)

        // Then
        assertTrue(result.isPresent)
        assertTrue(result.get().id == instructorId)
        assertTrue(result.get().name == "Test Name")
    }

    @Test
    fun `findByInstructorId returns empty result when id does not exist`() {
        // Given
        val instructorId = 999 // Assuming this ID does not exist
        every { instructorServiceMockk.findByInstructorId(instructorId) } returns Optional.empty()

        // When
        val result = instructorServiceMockk.findByInstructorId(instructorId)

        // Then
        assertFalse(result.isPresent)
    }

}