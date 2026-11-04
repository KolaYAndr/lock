package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.AuthResponse
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailDto
import org.homesharing.lockbox.data.network.dto.otp.OtpResponseDto
import org.homesharing.lockbox.data.network.dto.otp.OtpVerifyResponseDto

/**
 * Сетевой API для аутентификации и авторизации пользователей.
 */
internal interface AuthApi {
    suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String,
    ): ApiResponse<Unit>

    suspend fun loadTokensInitially(
        username: String,
        password: String,
    ): AuthResponse

    suspend fun confirmEmail(email: String): ApiResponse<ConfirmEmailDto>

    /**
     * Запрашивает отправку одноразового кода (OTP) на указанный email.
     *
     * @param email адрес электронной почты
     * @return [ApiResponse] с параметрами повторной отправки и временем жизни кода
     */
    suspend fun requestOtp(email: String): ApiResponse<OtpResponseDto>

    /**
     * Проверяет одноразовый код (OTP) для указанного email.
     *
     * @param email адрес электронной почты
     * @param code шестизначный проверочный код
     * @return [ApiResponse] с токенами авторизации и статусом нового пользователя
     */
    suspend fun verifyOtp(email: String, code: String): ApiResponse<OtpVerifyResponseDto>

    /**
     * Выполняет выход из текущей сессии. Требует авторизации (Bearer token).
     *
     * @param refreshToken refresh токен текущей сессии для инвалидации
     * @return [ApiResponse] с пустым телом ответа (204 No Content)
     */
    suspend fun logout(refreshToken: String): ApiResponse<Unit>
}
