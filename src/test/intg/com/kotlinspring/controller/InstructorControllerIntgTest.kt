package com.kotlinspring.controller

import com.kotlinspring.dto.InstructorDTO
import com.kotlinspring.repository.InstructorRepository
import com.kotlinspring.util.instructorEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
@Testcontainers
class InstructorControllerIntgTest {


    @Autowired
    lateinit var webTestClient: WebTestClient


    @Autowired
    lateinit var instructorRepository: InstructorRepository


    companion object {

        @Container
        val postgresDB = PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:13-alpine")).apply {
            withDatabaseName("testdb")
            withUsername("postgres")
            withPassword("secret")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresDB::getJdbcUrl)
            registry.add("spring.datasource.username", postgresDB::getUsername)
            registry.add("spring.datasource.password", postgresDB::getPassword)
        }
    }
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

        val savedInstructorDTO = webTestClient.post().uri("/v1/instructors").bodyValue(instructorDTO).exchange()
            .expectStatus().isCreated.expectBody(InstructorDTO::class.java).returnResult().responseBody

        Assertions.assertTrue {
            savedInstructorDTO!!.id != null
        }
    }

}