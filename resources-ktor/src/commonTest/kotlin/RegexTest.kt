package com.crowdproj.resources.app

import com.crowdproj.resources.app.plugins.replaceServers
import kotlin.test.Test
import kotlin.test.assertContains

class RegexTest {
    val yml = """
        openapi: 3.0.3
        info:
          title: 'Marketplace Resources service '
          description: 'Resources for Marketplace'
          license:
            #    identifier: Apache-2.0
            name: Apache 2.0
            url: https://www.apache.org/licenses/LICENSE-2.0.html
          version: 1.0.0
        servers:
          - url: http://localhost:8080/resources
        tags:
          - name: resource
            description: Ресурс (время / товар и т.д.)
        paths:
          /v1/create:
            post:
              tags:
                - resource
              summary: Create resource

    """.trimIndent()
    val servers = listOf(
        "http://xxx:8090/app",
        "https://yyy:8090/app",
    )

    @Test
    fun test() {
        val res = yml.replaceServers(servers)

        assertContains(res, "http://xxx:8090/app")
        assertContains(res, Regex("^tags:\$", setOf(RegexOption.IGNORE_CASE, RegexOption.MULTILINE)))
        println(res)
    }
}
