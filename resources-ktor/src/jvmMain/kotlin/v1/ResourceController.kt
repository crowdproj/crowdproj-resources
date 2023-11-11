package ru.otus.otuskotlin.marketplace.app.v1

import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.api.v1.models.*
import ru.otus.otuskotlin.marketplace.app.ResourcesAppSettings
import kotlin.reflect.KClass

private val clCreate: KClass<*> = ApplicationCall::createRes::class
suspend fun ApplicationCall.createRes(appSettings: ResourcesAppSettings) =
    processV1<ResourceCreateRequest, ResourceCreateResponse>(appSettings, clCreate, "create")

private val clRead: KClass<*> = ApplicationCall::readRes::class
suspend fun ApplicationCall.readRes(appSettings: ResourcesAppSettings) =
    processV1<ResourceReadRequest, ResourceReadResponse>(appSettings, clRead, "read")

private val clUpdate: KClass<*> = ApplicationCall::updateRes::class
suspend fun ApplicationCall.updateRes(appSettings: ResourcesAppSettings) =
    processV1<ResourceUpdateRequest, ResourceUpdateResponse>(appSettings, clUpdate, "update")

private val clDelete: KClass<*> = ApplicationCall::deleteRes::class
suspend fun ApplicationCall.deleteRes(appSettings: ResourcesAppSettings) =
    processV1<ResourceDeleteRequest, ResourceDeleteResponse>(appSettings, clDelete, "delete")

private val clSearch: KClass<*> = ApplicationCall::searchRes::class
suspend fun ApplicationCall.searchRes(appSettings: ResourcesAppSettings) =
    processV1<ResourceSearchRequest, ResourceSearchResponse>(appSettings, clSearch, "search")
