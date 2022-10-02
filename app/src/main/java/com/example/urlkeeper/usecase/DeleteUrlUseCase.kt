package com.example.urlkeeper.usecase

import com.example.urlkeeper.DeleteUrlMutation
import com.example.urlkeeper.repository.UrlRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteUrlUseCase @Inject constructor(
    private val urlRepository: UrlRepository
) {

    fun deleteUrl(id: String): Flow<DeleteUrlMutation.Data> {
        return urlRepository.deleteUrl(id)
    }
}