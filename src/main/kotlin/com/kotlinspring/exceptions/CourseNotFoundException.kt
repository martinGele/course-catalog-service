package com.kotlinspring.exception

import java.lang.RuntimeException

class CourseNotFoundException(message: String) : RuntimeException(message)
