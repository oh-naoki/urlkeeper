package com.example.urlkeeper.repository

import com.apollographql.apollo3.ApolloClient
import com.example.urlkeeper.RegisterUrlMutation
import com.example.urlkeeper.UrlListQuery
import com.example.urlkeeper.type.RegisterUrlInput
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject


class UrlRepository @Inject constructor(
    private val apolloClient: ApolloClient
) {

    fun getUrls(): Flow<UrlListQuery.Data> {
        return apolloClient.query(UrlListQuery())
            .toFlow()
            .mapNotNull { it.data }
    }

    fun registerUrl(url: String): Flow<RegisterUrlMutation.Data> {
        val input = RegisterUrlInput(
            url = url
        )
        return apolloClient.mutation(RegisterUrlMutation(input = input))
            .toFlow()
            .mapNotNull { it.data }
    }
}