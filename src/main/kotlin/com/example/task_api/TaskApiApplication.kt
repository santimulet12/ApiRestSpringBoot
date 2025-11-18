package com.example.task_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TaskApiApplication

fun main(args: Array<String>) {
	runApplication<TaskApiApplication>(*args)
}
