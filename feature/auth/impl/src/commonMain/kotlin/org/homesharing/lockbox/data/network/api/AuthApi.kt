package org.homesharing.lockbox.data.network.api

import org.homesharing.lockbox.data.network.api.response.ApiResponse
import org.homesharing.lockbox.data.network.api.response.AuthResponse
import org.homesharing.lockbox.data.network.dto.confirm_email.ConfirmEmailDto
import org.homesharing.lockbox.data.network.dto.login.LoginRequestDto
import org.homesharing.lockbox.data.network.dto.otp.OtpResponseDto
import org.homesharing.lockbox.data.network.dto.otp.OtpVerifyResponseDto
import org.homesharing.lockbox.data.network.dto.password.ChangePasswordRequestDto
import org.homesharing.lockbox.data.network.dto.password_reset.PasswordResetConfirmRequestDto
import org.homesharing.lockbox.data.network.dto.password_reset.PasswordResetRequestDto
import org.homesharing.lockbox.data.network.dto.register.RegisterRequestDto
import org.homesharing.lockbox.data.network.dto.sessions.SessionsDto
import org.homesharing.lockbox.data.network.dto.token.TokenResponseDto

/**
 * Сетевой API для аутентификации и авторизации пользователей.
 */
internal interface AuthApi {
    suspend fun signUp(
        username: String,
        password: String,
        passwordRepeated: String,
    ): ApiResponse<Unit>

    /**
     * Создаёт аккаунт и сразу выполняет вход (auto-login).
     * Публичный эндпоинт, авторизация не требуется.
     *
     * @param request объект [RegisterRequestDto] с email и паролем
     * @return [ApiResponse] с парой токенов [TokenResponseDto] (201 Created)
     */
    suspend fun register(
        request: RegisterRequestDto,
    ): ApiResponse<TokenResponseDto>

    suspend fun loadTokensInitially(
        username: String,
        password: String,
    ): AuthResponse

    /**
     * Обменивает email и пароль на пару токенов через Keycloak password grant.
     * Публичный эндпоинт, авторизация не требуется.
     *
     * @param request объект [LoginRequestDto] с email и паролем
     * @return [ApiResponse] с парой токенов [TokenResponseDto] (200 OK)
     */
    suspend fun login(
        request: LoginRequestDto,
    ): ApiResponse<TokenResponseDto>

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
     * Запрашивает одноразовый код для сброса пароля.
     * Публичный эндпоинт, ответ одинаков для существующих и несуществующих адресов.
     *
     * @param request объект [PasswordResetRequestDto] с email
     * @return [ApiResponse] с параметрами повторной отправки и временем жизни кода (202 Accepted)
     */
    suspend fun requestPasswordReset(
        request: PasswordResetRequestDto,
    ): ApiResponse<OtpResponseDto>

    /**
     * Устанавливает новый пароль по коду из письма.
     * Публичный эндпоинт, авторизация не требуется.
     *
     * @param request объект [PasswordResetConfirmRequestDto] с email, кодом и новым паролем
     * @return [ApiResponse] с пустым телом ответа (204 No Content)
     */
    suspend fun confirmPasswordReset(
        request: PasswordResetConfirmRequestDto,
    ): ApiResponse<Unit>

    /**
     * Меняет пароль текущего пользователя.
     * Требует авторизации (Bearer token), текущий пароль проверяется на сервере.
     *
     * @param request объект [ChangePasswordRequestDto] с текущим и новым паролем
     * @return [ApiResponse] с пустым телом ответа (204 No Content)
     */
    suspend fun changePassword(
        request: ChangePasswordRequestDto,
    ): ApiResponse<Unit>

    /**
     * Получает список активных сессий текущего пользователя.
     * Требует авторизации (Bearer token).
     *
     * @return [ApiResponse] со списком активных сессий пользователя [SessionsDto]
     */
    suspend fun getSessions(): ApiResponse<SessionsDto>

    /**
     * Завершает все остальные активные сессии текущего пользователя.
     * Требует авторизации (Bearer token).
     *
     * @return [ApiResponse] с пустым телом ответа (204 No Content)
     */
    suspend fun closeOtherSessions(): ApiResponse<Unit>

    /**
     * Выполняет выход из текущей сессии. Требует авторизации (Bearer token).
     *
     * @param refreshToken refresh токен текущей сессии для инвалидации
     * @return [ApiResponse] с пустым телом ответа (204 No Content)
     */
    suspend fun logout(refreshToken: String): ApiResponse<Unit>
}
