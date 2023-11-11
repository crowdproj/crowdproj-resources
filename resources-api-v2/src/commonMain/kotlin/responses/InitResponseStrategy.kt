package ru.otus.otuskotlin.marketplace.api.v2.requests

import kotlinx.serialization.KSerializer
import ru.otus.otuskotlin.marketplace.api.v2.models.ResourceInitResponse
import ru.otus.otuskotlin.marketplace.api.v2.models.IResponse
import kotlin.reflect.KClass

object InitResponseStrategy: IResponseStrategy {
    override val discriminator: String = "init"
    override val clazz: KClass<out IResponse> = ResourceInitResponse::class
    override val serializer: KSerializer<out IResponse> = ResourceInitResponse.serializer()
    override fun <T : IResponse> fillDiscriminator(req: T): T {
        require(req is ResourceInitResponse)
        @Suppress("UNCHECKED_CAST")
        return req.copy(responseType = discriminator) as T
    }
}