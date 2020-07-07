package com.app.grabnews.model

data class NewsListResponse(
    val articles: List<NewsArticleModel>,
    val status: String,
    val totalResults: Int
)