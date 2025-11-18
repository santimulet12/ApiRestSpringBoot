package com.example.task_api.repository

import com.example.task_api.model.Task
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository: JpaRepository<Task, Long> {
    // Crear métodos especiales
    fun findByCompleted(completed: Boolean): List<Task>
    fun findByTitleContaining(title: String): List<Task>

}