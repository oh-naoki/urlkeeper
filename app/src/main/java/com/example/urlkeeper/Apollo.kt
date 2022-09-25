package com.example.urlkeeper

import com.apollographql.apollo3.ApolloClient
import javax.inject.Inject

class Apollo @Inject constructor() {
    val apolloClient = ApolloClient.Builder()
        .serverUrl("https://url-memo-backend.herokuapp.com/graphql")
        .build()
}