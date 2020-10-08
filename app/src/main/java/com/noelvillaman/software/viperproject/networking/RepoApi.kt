package com.software.namalliv.noelviewmodel.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RepoApi {
    private val BASE_URL = "https://api.github.com/"

    private var retrofit: Retrofit? = null
    private var repoService: RepoService? = null

    val instance: RepoService
        get() {
            if (repoService != null) {
                return repoService!!
            }
            if (retrofit == null) {
                initializeRetrofit()
            }
            repoService = retrofit!!.create(RepoService::class.java)
            return repoService as RepoService
        }

    private fun initializeRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
