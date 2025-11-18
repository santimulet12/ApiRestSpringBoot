package com.example.task_api.controllers

import com.example.task_api.model.Task
import com.example.task_api.service.TaskService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val taskService: TaskService
) {
    @GetMapping
    fun getAllTasks(): List<Task>{
        return taskService.getAllTasks()
    }

    @GetMapping("/{id}")
    fun getTaskById(
        @PathVariable id: Long
    ): ResponseEntity<Task>{
        return taskService.getTaskById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())
    }

    @PostMapping()
    fun crateTask(
        @RequestBody
        task: Task
    ): ResponseEntity<Task>{
        val createdTask = taskService.createTask(task)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask)
    }

    @PutMapping("/{id}")
    fun updateTask(
        @PathVariable
        id: Long,
        @RequestBody
        task: Task
    ): ResponseEntity<Task>{
        return taskService.updateTask(id, task)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteTak(
        @PathVariable
        id: Long
    ): ResponseEntity<Void>{
        return if (taskService.deleteTask(id)){
            ResponseEntity.noContent().build()
        }else{
            ResponseEntity.noContent().build()
        }
    }

    @GetMapping("/completed/{completed}")
    fun getCompletedTasks(
      @PathVariable
      completed: Boolean
    ):List<Task>{
        return taskService.getCompletedTasks(completed)
    }

}