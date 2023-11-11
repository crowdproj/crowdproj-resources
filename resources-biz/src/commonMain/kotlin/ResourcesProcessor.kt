package ru.otus.otuskotlin.marketplace.biz

import ru.otus.otuskotlin.marketplace.biz.groups.operation
import ru.otus.otuskotlin.marketplace.biz.groups.stubs
import ru.otus.otuskotlin.marketplace.biz.validation.*
import ru.otus.otuskotlin.marketplace.biz.workers.*
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.ResourcesCorSettings
import ru.otus.otuskotlin.marketplace.common.models.OtherResourcesId
import ru.otus.otuskotlin.marketplace.common.models.ResourcesCommand
import ru.otus.otuskotlin.marketplace.common.models.ResourcesId
import ru.otus.otuskotlin.marketplace.common.models.ScheduleId
import ru.otus.otuskotlin.marketplace.cor.rootChain
import ru.otus.otuskotlin.marketplace.cor.worker

class ResourcesProcessor(
    @Suppress("unused")
    private val corSettings: ResourcesCorSettings = ResourcesCorSettings.NONE
) {
    suspend fun exec(ctx: ResourcesContext) = BusinessChain.exec(ctx)

    companion object {
        private val BusinessChain = rootChain<ResourcesContext> {
            initStatus("Инициализация статуса")

            operation("Создание ресурса", ResourcesCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationBadOtherId("Имитация ошибки валидации id других ресурсов")
                    stubValidationBadScheduleId("Имитация ошибки валидации id других моделей")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { resourceValidating = resourceRequest.deepCopy() }
                    worker("Очистка id") { resourceValidating.id = ResourcesId.NONE }
                    worker("Очистка OtherId") { resourceValidating.resourcesId = OtherResourcesId(resourceValidating.resourcesId.asString().trim()) }
                    worker("Очистка scheduleId") { resourceValidating.scheduleId = ScheduleId(resourceValidating.scheduleId.asString().trim()) }
                    validateOtherIdNotEmpty("Проверка, что поле OtherId не пустое")
                    validateOtherIdIsNumber("Проверка, что поле OtherId содержит только цифры")
                    validateScheduleIdNotEmpty("Проверка, что поле ScheduleId не пустое")
                    validateScheduleIdIsNumber("Проверка, что поле ScheduleId содержит только цифры")

                    finishAdValidation("Завершение проверок")
                }
            }
            operation("Получить объявление", ResourcesCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { resourceValidating = resourceRequest.deepCopy() }
                    worker("Очистка id") { resourceValidating.id = ResourcesId(resourceValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Изменить ресурс", ResourcesCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubValidationBadOtherId("Имитация ошибки валидации id других ресурсов")
                    stubValidationBadScheduleId("Имитация ошибки валидации id других моделей")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { resourceValidating = resourceRequest.deepCopy() }
                    worker("Очистка id") { resourceValidating.id = ResourcesId(resourceValidating.id.asString().trim()) }
                    worker("Очистка OtherId") { resourceValidating.resourcesId = OtherResourcesId(resourceValidating.resourcesId.asString().trim()) }
                    worker("Очистка scheduleId") { resourceValidating.scheduleId = ScheduleId(resourceValidating.scheduleId.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateOtherIdNotEmpty("Проверка, что поле OtherId не пустое")
                    validateOtherIdIsNumber("Проверка, что поле OtherId содержит только цифры")
                    validateScheduleIdNotEmpty("Проверка, что поле ScheduleId не пустое")
                    validateScheduleIdIsNumber("Проверка, что поле ScheduleId содержит только цифры")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Удалить объявление", ResourcesCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adValidating") { resourceValidating = resourceRequest.deepCopy() }
                    worker("Очистка id") { resourceValidating.id = ResourcesId(resourceValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
            }
            operation("Поиск объявлений", ResourcesCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в adFilterValidating") { resourceFilterValidating = resourceFilterRequest.copy() }

                    finishAdFilterValidation("Успешное завершение процедуры валидации")
                }
            }
        }.build()
    }
}