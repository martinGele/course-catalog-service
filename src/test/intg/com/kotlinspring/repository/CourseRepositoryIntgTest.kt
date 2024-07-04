package com.kotlinspring.repository

import com.kotlinspring.util.courseEntityList
import org.junit.jupiter.api.Assertions.assertEquals

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryIntgTest {

    @Autowired
    lateinit var courseRepository: CourseRepository


    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        val course = courseEntityList()
        courseRepository.saveAll(course)
    }


    @Test
    fun findByNameContaining() {
        val courses = courseRepository.findByNameContaining("SpringBoot")
        assert(courses.size == 2)
    }


    @Test
    fun findByName() {
        val courses = courseRepository.findCoursesByName("SpringBoot")
        assert(courses.size == 2)
    }

    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findCoursesByName_approach2(name: String, expectedSize: Int) {
        val courses = courseRepository.findCoursesByName(name)
        assertEquals(expectedSize, courses.size)
    }

    companion object {
        @JvmStatic
        fun courseAndSize(): java.util.stream.Stream<Arguments> {
            return java.util.stream.Stream.of(
                Arguments.of("SpringBoot", 2),
                Arguments.of("Spring", 2),
                Arguments.of("SpringCloud", 0)
            )
        }
    }
}