package com.chsltutorials.nytbooks.data.service

import com.chsltutorials.nytbooks.data.entity.BookResponseAPI
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface NYTEndpoint {

    @GET("lists.json")
    fun getAllBooks(
        @Query("api-key") apiKey : String = "rvLk05UYY0TgLi7fcnATCnqj5yQ0i61O",
        @Query("list") list : String = "hardcover-fiction"
    ): Observable<BookResponseAPI>
}


object NYTService {

    private var retrofit = Retrofit.Builder()
        .baseUrl("https://api.nytimes.com/svc/books/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    var service: NYTEndpoint = retrofit.create(NYTEndpoint::class.java)
}