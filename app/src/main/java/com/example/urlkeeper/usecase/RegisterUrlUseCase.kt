package com.example.urlkeeper.usecase

import android.util.Patterns
import com.example.urlkeeper.RegisterUrlMutation
import com.example.urlkeeper.repository.UrlRepository
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