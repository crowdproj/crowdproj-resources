package com.crowdproj.resources.biz

import com.crowdproj.resources.biz.general.*
import com.crowdproj.resources.biz.repo.*
import com.crowdproj.resources.biz.stubs.*
import com.crowdproj.resources.biz.validation.*
import com.crowdproj.resources.common.ResourcesContext
import com.crowdproj.kotlin.cor.handlers.chain
import com.crowdproj.kotlin.cor.handlers.worker
import com.crowdproj.kotlin.cor.rootChain
import com.crowdproj.resources.common.ResourcesCorSettings
import com.crowdproj.resources.common.models.*

class ResourcesProcessor(val settings: ResourcesCorSettings = ResourcesCorSettings()) {
    suspend fun exec(ctx: ResourcesContext) = BusinessChain.exec(ctx.apply { settings = this@ResourcesProcessor.settings })

    companion object {
        private val BusinessChain = rootChain<ResourcesContext> {
            initStatus("Инициализация статуса")
            initRepo("Инициализация репозитория")

            operation("Создание ресурса", ResourcesCommand.CREATE) {
                stubs("Обработка стабов") {
                    stubCreateSuccess("Имитация успешной обработки")
                    stubValidationOtherId("Имитация ошибки валидации ресурса")
                    stubValidationScheduleId("Имитация ошибки валидации метки расписания")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в resourceValidating") { resourceValidating = resourceRequest.deepCopy() }
                    worker("Очистка id") { resourceValidating.id = ResourcesId.NONE }
                    worker("Очистка наименования ресурса") { resourceValidating.resourcesId = OtherResourcesId(resourceValidating.resourcesId.asString().trim()) }
                    worker("Очистка наименования расписания") { resourceValidating.scheduleId = ScheduleId(resourceValidating.scheduleId.asString().trim()) }
                    validateOtherIdNotEmpty("Проверка, что название ресурса не пустое")
                    validateOtherIdProperFormat("Проверка символов")
                    validateScheduleIdNotEmpty("Проверка, что расписание не пусто")
                    validateScheduleIdProperFormat("Проверка символов")

                    finishAdValidation("Завершение проверок")
                }
                chain {
                    title = "Логика сохранения"
                    repoPrepareCreate("Подготовка объекта для сохранения")
                    repoCreate("Создание ресурса в БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Получить ресурс", ResourcesCommand.READ) {
                stubs("Обработка стабов") {
                    stubReadSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в resourceValidating") { resourceValidating = resourceRequest.deepCopy() }
                    worker("Очистка id") { resourceValidating.id = ResourcesId(resourceValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика чтения"
                    repoRead("Чтение объявления из БД")
                    worker {
                        title = "Подготовка ответа для Read"
                        on { state == ResourcesState.RUNNING }
                        handle { resourceRepoDone = resourceRepoRead }
                    }
                }
                prepareResult("Подготовка ответа")
            }
            operation("Изменить ресурс", ResourcesCommand.UPDATE) {
                stubs("Обработка стабов") {
                    stubUpdateSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    validateOtherIdNotEmpty("Проверка, что название ресурса не пустое")
                    validateScheduleIdNotEmpty("Проверка, что наименования расписание не пусто")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в resourceValidating") { resourceValidating = resourceRequest.deepCopy() }
                    worker("Очистка id") { resourceValidating.id = ResourcesId.NONE }
                    worker("Очистка наименования ресурса") { resourceValidating.resourcesId = OtherResourcesId(resourceValidating.resourcesId.asString().trim()) }
                    worker("Очистка наименования расписания") { resourceValidating.scheduleId = ScheduleId(resourceValidating.scheduleId.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    validateOtherIdNotEmpty("Проверка, что название ресурса не пустое")
                    validateOtherIdProperFormat("Проверка символов")
                    validateScheduleIdNotEmpty("Проверка, что наименование расписание не пустое")
                    validateScheduleIdProperFormat("Проверка символов")

                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика сохранения"
                    repoRead("Чтение ресурса из БД")
                    repoPrepareUpdate("Подготовка объекта для обновления")
                    repoUpdate("Обновление ресурса в БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Удалить ресурс", ResourcesCommand.DELETE) {
                stubs("Обработка стабов") {
                    stubDeleteSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в resourceValidating") { resourceValidating = resourceRequest.deepCopy() }
                    worker("Очистка id") { resourceValidating.id = ResourcesId(resourceValidating.id.asString().trim()) }
                    validateIdNotEmpty("Проверка на непустой id")
                    validateIdProperFormat("Проверка формата id")
                    finishAdValidation("Успешное завершение процедуры валидации")
                }
                chain {
                    title = "Логика удаления"
                    repoRead("Чтение ресурса из БД")
                    repoPrepareDelete("Подготовка объекта для удаления")
                    repoDelete("Удаление ресурса из БД")
                }
                prepareResult("Подготовка ответа")
            }
            operation("Поиск ресурсов", ResourcesCommand.SEARCH) {
                stubs("Обработка стабов") {
                    stubSearchSuccess("Имитация успешной обработки")
                    stubValidationBadId("Имитация ошибки валидации id")
                    stubDbError("Имитация ошибки работы с БД")
                    stubNoCase("Ошибка: запрошенный стаб недопустим")
                }
                validation {
                    worker("Копируем поля в resourceFilterValidating") { resourceFilterValidating = resourceFilterRequest.copy() }
                    finishAdFilterValidation("Успешное завершение процедуры валидации")
                }
                repoSearch("Поиск ресурса в БД по фильтру")
                prepareResult("Подготовка ответа")
            }
        }.build()
    }
}