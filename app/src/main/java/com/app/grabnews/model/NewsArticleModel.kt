package com.app.grabnews.model

data class NewsArticleModel(
    var id: Int = 0,
    var author: String = "",
    var content: String = "",
    var description: String = "",
    var publishedAt: String = "",
    var title: String = "",
    var url: String = "",
    var urlToImage: String = ""
)