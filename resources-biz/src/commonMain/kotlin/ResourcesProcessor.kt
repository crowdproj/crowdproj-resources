package ru.otus.otuskotlin.marketplace.biz

import ru.otus.otuskotlin.marketplace.biz.groups.operation
import ru.otus.otuskotlin.marketplace.biz.groups.stubs
import ru.otus.otuskotlin.marketplace.biz.workers.*
import ru.otus.otuskotlin.marketplace.common.ResourcesContext
import ru.otus.otuskotlin.marketplace.common.ResourcesCorSettings
import ru.otus.otuskotlin.marketplace.common.models.ResourcesCommand
import ru.otus.otuskotlin.marketplace.cor.rootChain

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
            }
            operation("Получить объявление", ResourcesCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
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
            }
            operation("Удалить объявление", ResourcesCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
            operation("Поиск объявлений", ResourcesCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
            }
        }.build()
    }
}