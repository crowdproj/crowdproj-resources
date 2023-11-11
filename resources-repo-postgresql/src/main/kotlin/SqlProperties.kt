package com.crowdproj.resources.repo.sql

data class SqlProperties(
    val url: String = "jdbc:postgresql://localhost:5432/marketplace",
    val user: String = "postgres",
    val password: String = "postgres",
    val schema: String = "postgres",
    val table: String = "resources",
)