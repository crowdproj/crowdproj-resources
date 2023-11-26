package com.crowdproj.resources.repo.sql

data class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/resources",
    val user: String = "postgres",
    val password: String = "postgres",
    val schema: String = "resources",
    val table: String = "resources",
)