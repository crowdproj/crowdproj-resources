package ru.otus.otuskotlin.marketplace.api.v2

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import ru.otus.otuskotlin.marketplace.api.v2.models.*

@OptIn(ExperimentalSerializationApi::class)
val apiV2Mapper = Json {
    classDiscriminator = "_"
    encodeDefaults = true
    ignoreUnknownKeys = true

    serializersModule = SerializersModule {
        polymorphicDefaultSerializer(IRequest::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is ResourceCreateRequest ->  RequestSerializer(ResourceCreateRequest.serializer()) as SerializationStrategy<IRequest>
                is ResourceReadRequest   ->  RequestSerializer(ResourceReadRequest  .serializer()) as SerializationStrategy<IRequest>
                is ResourceUpdateRequest ->  RequestSerializer(ResourceUpdateRequest.serializer()) as SerializationStrategy<IRequest>
                is ResourceDeleteRequest ->  RequestSerializer(ResourceDeleteRequest.serializer()) as SerializationStrategy<IRequest>
                is ResourceSearchRequest ->  RequestSerializer(ResourceSearchRequest.serializer()) as SerializationStrategy<IRequest>
                else -> null
            }
        }
        polymorphicDefault(IRequest::class) {
            AdRequestSerializer
        }
        polymorphicDefaultSerializer(IResponse::class) {
            @Suppress("UNCHECKED_CAST")
            when(it) {
                is ResourceCreateResponse ->  ResponseSerializer(ResourceCreateResponse.serializer()) as SerializationStrategy<IResponse>
                is ResourceReadResponse   ->  ResponseSerializer(ResourceReadResponse  .serializer()) as SerializationStrategy<IResponse>
                is ResourceUpdateResponse ->  ResponseSerializer(ResourceUpdateResponse.serializer()) as SerializationStrategy<IResponse>
                is ResourceDeleteResponse ->  ResponseSerializer(ResourceDeleteResponse.serializer()) as SerializationStrategy<IResponse>
                is ResourceSearchResponse ->  ResponseSerializer(ResourceSearchResponse.serializer()) as SerializationStrategy<IResponse>
                is ResourceInitResponse   ->  ResponseSerializer(ResourceInitResponse  .serializer()) as SerializationStrategy<IResponse>
                else -> null
            }
        }
        polymorphicDefault(IResponse::class) {
            AdResponseSerializer
        }

        contextual(AdRequestSerializer)
        contextual(AdResponseSerializer)
    }
}

//fun Json.encodeResponse(response: IResponse): String = encodeToString(AdResponseSerializer, response)

fun apiV2ResponseSerialize(Response: IResponse): String = apiV2Mapper.encodeToString(AdResponseSerializer, Response)

@Suppress("UNCHECKED_CAST", "unused")
fun <T : IResponse> apiV2ResponseDeserialize(json: String): T = apiV2Mapper.decodeFromString(AdResponseSerializer, json) as T

@Suppress("unused")
fun apiV2RequestSerialize(request: IRequest): String = apiV2Mapper.encodeToString(AdRequestSerializer, request)

@Suppress("UNCHECKED_CAST")
fun <T : IRequest> apiV2RequestDeserialize(json: String): T = apiV2Mapper.decodeFromString(AdRequestSerializer, json) as T
