package repository

import com.kotlinspring.repository.CourseRepository
import com.kotlinspring.util.courseEntityList
import org.aspectj.lang.annotation.Before
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
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


}