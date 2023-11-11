package ru.otus.otuskotlin.marketplace.api.v2.requests

import kotlinx.serialization.KSerializer
import ru.otus.otuskotlin.marketplace.api.v2.models.ResourceCreateRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.IRequest
import kotlin.reflect.KClass

object CreateRequestStrategy: IRequestStrategy {
    override val discriminator: String = "create"
    override val clazz: KClass<out IRequest> = ResourceCreateRequest::class
    override val serializer: KSerializer<out IRequest> = ResourceCreateRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is ResourceCreateRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}