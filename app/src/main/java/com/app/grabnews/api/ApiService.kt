package com.app.grabnews.api

import com.app.grabnews.model.NewsListResponse
import io.reactivex.Observable
import retrofit2.http.*

interface ApiService {

    @GET("top-headlines")
    fun getNewsList(
        @Query("apiKey") apiKey : String?,
        @Query("country") country : String?,
        @Query("pageSize") pageSize : Int?,
        @Query("page") page : Int?
    ): Observable<NewsListResponse>

}