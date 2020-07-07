package com.app.grabnews.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.app.grabnews.model.NewsListResponse
import com.app.grabnews.api.ApiClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.http.Query
import weather.android.com.util.Constants
import weather.android.com.util.Constants.Companion.API_KEY
import weather.android.com.util.Constants.Companion.COUNTRY
import weather.android.com.util.Constants.Companion.PAGE_SIZE
import java.lang.Exception

class NewsListViewModel(application: Application) : AndroidViewModel(application) {

    var successResponseData: MutableLiveData<NewsListResponse> = MutableLiveData()
    var errorResponseData: MutableLiveData<String> = MutableLiveData()

    fun getNewsList(page:Int){
        val apiService = ApiClient.create()
        val observable: Observable<NewsListResponse> = apiService.getNewsList(API_KEY, COUNTRY, PAGE_SIZE, page)
        observable.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError)
    }

    private fun handleResponse(response: NewsListResponse) {
        successResponseData.value = response
    }

    private fun handleError(error: Throwable) {
        if (error is HttpException) {

            val responseBody = error.response().errorBody();
            val jsonObject = JSONObject(responseBody?.string())
            val jsonResult = jsonObject.getJSONObject("error")

            errorResponseData.value = jsonResult.getString("message")
        }else if(error is Exception){
            errorResponseData.value = error.message
        }
    }
}