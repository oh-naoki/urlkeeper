package com.example.urlkeeper

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import javax.inject.Inject

class HTMLParseRepository @Inject constructor() {
    suspend fun parseHtml(url: String): Document {
        return Jsoup.connect(url).execute().parse()
    }
}