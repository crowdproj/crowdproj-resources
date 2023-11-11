package ru.otus.otuskotlin.marketplace.api.v2.requests

import kotlinx.serialization.KSerializer
import ru.otus.otuskotlin.marketplace.api.v2.models.ResourceReadRequest
import ru.otus.otuskotlin.marketplace.api.v2.models.IRequest
import kotlin.reflect.KClass

object ReadRequestStrategy: IRequestStrategy {
    override val discriminator: String = "read"
    override val clazz: KClass<out IRequest> = ResourceReadRequest::class
    override val serializer: KSerializer<out IRequest> = ResourceReadRequest.serializer()
    override fun <T : IRequest> fillDiscriminator(req: T): T {
        require(req is ResourceReadRequest)
        @Suppress("UNCHECKED_CAST")
        return req.copy(requestType = discriminator) as T
    }
}