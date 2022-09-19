package com.example.urlkeeper

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OgpParseUseCase @Inject constructor(
    private val htmlParseRepository: HTMLParseRepository
) {
    suspend fun getOgp(url: String): OgpMeta {
        val keys = listOf("og:image", "og:description", "og:url", "og:title")
        val response = mutableMapOf<String, String>()

        return withContext(Dispatchers.IO) {
            htmlParseRepository.parseHtml(url)
                .select("meta[property^=og:]")
                .map {
                    if (keys.contains(it.attr("property"))) {
                        response[it.attr("property")] = it.attr("content")
                    }
                }
            
            return@withContext OgpMeta(
                title = response["og:title"].orEmpty(),
                description = response["og:description"].orEmpty(),
                imageUrl = response["og:image"].orEmpty(),
                url = response["og:url"].orEmpty()
            )
        }
    }
}