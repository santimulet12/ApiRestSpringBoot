package com.example.task_api.service

import com.example.task_api.model.Task
import com.example.task_api.repository.TaskRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class TaskService(
    private val taskRepository: TaskRepository
) {

    fun getAllTasks(): List<Task> {
        return taskRepository.findAll()
    }

    fun getTaskById(id: Long): Optional<Task>{
        return taskRepository.findById(id)
    }

    fun createTask(task: Task): Task{
        return taskRepository.save(task)
    }

    fun updateTask(id: Long, updatedTask: Task): Task? {
        return if (taskRepository.existsById(id)) {
            val task = updatedTask.copy(id = id)
            taskRepository.save(task)
        } else {
            null
        }
    }

    fun deleteTask(id: Long): Boolean{
        return if (taskRepository.existsById(id)){
            taskRepository.deleteById(id)
            true
        }else{
            throw IllegalArgumentException("Id does not exists.")
        }
    }

    fun getCompletedTasks(completed: Boolean): List<Task> = taskRepository.findByCompleted(completed)
}