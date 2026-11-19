package org.homesharing.lockbox.domain.repository

import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.confirm_email.ConfirmEmail
import org.homesharing.lockbox.domain.model.otp.OtpResponse
import org.homesharing.lockbox.domain.model.otp.OtpVerifyResult

/**
 * Репозиторий для аутентификации и авторизации пользователей.
 */
interface AuthRepository {
    suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String,
    ): Response<Unit>

    /**
     * Создаёт аккаунт и сразу выполняет вход (auto-login), сохраняя полученные токены.
     *
     * @param email адрес электронной почты
     * @param password пароль (не короче 8 символов)
     * @return [Response] с результатом регистрации
     */
    suspend fun register(email: String, password: String): Response<Unit>

    suspend fun loadTokensInitially(
        username: String,
        password: String,
    ): Response<Unit>

    /**
     * Обменивает email и пароль на пару токенов через Keycloak password grant
     * (`POST /api/auth/login`) и сохраняет их локально.
     *
     * @param email адрес электронной почты
     * @param password пароль
     * @return [Response] с результатом входа
     */
    suspend fun login(email: String, password: String): Response<Unit>

    suspend fun sendConfirmation(email: String): Response<ConfirmEmail>

    /**
     * Запрашивает отправку одноразового кода (OTP) на указанный email.
     *
     * @param email адрес электронной почты
     * @return [Response] с метаданными запроса OTP
     */
    suspend fun requestOtp(email: String): Response<OtpResponse>

    /**
     * Проверяет одноразовый код (OTP), сохраняет полученные токены и возвращает результат верификации.
     *
     * @param email адрес электронной почты
     * @param code проверочный код
     * @return [Response] с результатом проверки OTP
     */
    suspend fun verifyOtp(email: String, code: String): Response<OtpVerifyResult>

    /**
     * Запрашивает одноразовый код для сброса пароля.
     * Ответ одинаков для существующих и несуществующих адресов (anti-enumeration).
     *
     * @param email адрес электронной почты
     * @return [Response] с параметрами повторной отправки и временем жизни кода
     */
    suspend fun requestPasswordReset(email: String): Response<OtpResponse>

    /**
     * Выполняет выход из текущей сессии и очищает локально сохранённые токены.
     *
     * @return [Response] с результатом операции
     */
    suspend fun logout(): Response<Unit>
}
