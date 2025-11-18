package com.example.task_api.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tasks")
data class Task(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id : Long? = null,

    @Column(nullable = false)
    val title: String,

    @Column(columnDefinition = "TEXT")
    val description: String,

    @Column(nullable = false)
    val completed: Boolean,

    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
