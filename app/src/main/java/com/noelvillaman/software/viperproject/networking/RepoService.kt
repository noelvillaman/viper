package com.software.namalliv.noelviewmodel.networking

import com.software.namalliv.noelviewmodel.model.Repo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoService {
    @get:GET("orgs/Google/repos")
    val repositories: Call<List<Repo>>

    @GET("repos/{owuner}/{name}")
    fun getRepo(@Path("owner") repoOwner : String, @Path("name") repoName : String) : Call<Repo>
}
