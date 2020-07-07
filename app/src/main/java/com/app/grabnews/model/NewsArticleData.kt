package com.app.grabnews.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.jetbrains.annotations.NotNull
import javax.annotation.Nullable

open class NewsArticleData(
    @PrimaryKey var id: Int = 0,
    var author: String = "",
    var content: String = "",
    var description: String = "",
    var publishedAt: String = "",
    var title: String = "",
    var url: String = "",
    var urlToImage: String = ""
): RealmObject()