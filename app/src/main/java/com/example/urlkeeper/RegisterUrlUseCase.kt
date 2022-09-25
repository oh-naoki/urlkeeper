package com.example.urlkeeper

import android.util.Patterns
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUrlUseCase @Inject constructor(
    private val urlRepository: UrlRepository
) {

    fun registerUrl(url: String): Flow<RegisterUrlMutation.Data> {
        if (Patterns.WEB_URL.matcher(url).matches().not()) throw IllegalArgumentException()

        return urlRepository.registerUrl(url = url)
    }
}