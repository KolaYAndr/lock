package org.homesharing.lockbox.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.homesharing.lockbox.data.network.token.storage.TokenStorage
import org.homesharing.lockbox.domain.model.AccountInfo
import org.homesharing.lockbox.domain.repository.AccountRepository
import io.ktor.util.decodeBase64String

class AccountRepositoryImpl(private val tokenStorage: TokenStorage): AccountRepository {
    override suspend fun parseAccountInfo(): AccountInfo =
        withContext(Dispatchers.Default) {
            val token = async(Dispatchers.IO) { tokenStorage.getAccessToken() ?: "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ5Y2lIZW5kQVF4bDhTYVA5MVlvbnQxd0hIdXlLZHNCbjZOZmEtWkVaeXE0In0.eyJleHAiOjE3Mzg1ODc2NTEsImlhdCI6MTczODU4NzYyMSwianRpIjoiYmVhY2Y3MmQtMjBkNi00MzZlLWE2OWItMzJiZDg3ZWQwNjA2IiwiaXNzIjoiaHR0cHM6Ly9hcGkubG9ja2JveGVzLnJ1Ojg0NDMvcmVhbG1zL3NwcmluZy1hcHAtcmVhbG0iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiZDdiNzY5MTgtMWM5Ni00NWIyLWE0ZmQtYTM0MmMzYjRjNjg0IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicHVibGljLWNsaWVudCIsInNpZCI6IjI2NTZjNDBkLTIwZGMtNDkwMS04MzRjLTcyMmQ4NDRhZjZmMSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiKiJdLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsiZGVmYXVsdC1yb2xlcy1zcHJpbmctYXBwLXJlYWxtIiwib2ZmbGluZV9hY2Nlc3MiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoib3BlbmlkIG9mZmxpbmVfYWNjZXNzIHByb2ZpbGUgZW1haWwiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsInByZWZlcnJlZF91c2VybmFtZSI6ImY3NDZhYzg1LWJjMTItNDk5ZS1iZjJhLWIyNDFlOGM3NjkxMCIsImVtYWlsIjoiYWJvYmFAbWFsLnJ1In0.pqaTc35l_jPfjwJv_OxRR6l0dLpT3iT_JQvVSnLmAsPWOp3xvs6bZ41XlxSSJVPxR1-5ICYhMoZLlcsKKFqcD4JNQl5iI_lbXYQ6YChflbrm7Qomu5OYq-gBleXxkZUyWnP3-0LKgO0SAXbWJw-9rr-X97ZeuLUkvtF4kmn5s1sg5kvW20DBWU3QLSyQ0-gV9MC_XIlxf8eFld7p6WBrvXZqQ-_CRdHZLiCbmND98zU3p9v1X5dWId2so0vNSXwvmSm2GOTSnxP8O5JYs3sFhefeML0URHnCJO-5KYl0dkIJu49c6S4EKoojICNdb6MA8svOI-BuloaaeB5HtbjcFQ"}
            decodeJWT(token.await())
        }

    private fun decodeJWT(jwt: String): AccountInfo {
        val parts = jwt.split(".")
        val payload = parts[1].decodeBase64String()

        return Json.decodeFromString<AccountInfo>(payload)
    }

    override suspend fun logOut() = withContext(Dispatchers.IO) {
        tokenStorage.clearInfo()
    }
}