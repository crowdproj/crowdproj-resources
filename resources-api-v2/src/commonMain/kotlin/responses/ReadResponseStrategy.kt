package ru.otus.otuskotlin.marketplace.api.v2.requests

import kotlinx.serialization.KSerializer
import ru.otus.otuskotlin.marketplace.api.v2.models.ResourceReadResponse
import ru.otus.otuskotlin.marketplace.api.v2.models.IResponse
import kotlin.reflect.KClass

object ReadResponseStrategy: IResponseStrategy {
    override val discriminator: String = "read"
    override val clazz: KClass<out IResponse> = ResourceReadResponse::class
    override val serializer: KSerializer<out IResponse> = ResourceReadResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is ResourceReadResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}