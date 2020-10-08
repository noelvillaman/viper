package com.noelvillaman.software.viperproject.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.software.namalliv.noelviewmodel.model.Repo
import com.software.namalliv.noelviewmodel.networking.RepoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListViewModel : ViewModel() {
    val repos = MutableLiveData<List<Repo>>()
    val repoLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    private var repoCall: Call<List<Repo>>? = null

    internal val error: LiveData<Boolean>
        get() = repoLoadError

    init {
        fetchRepos()
    }

    internal fun getRepos(): LiveData<List<Repo>> {
        return repos
    }

    internal fun getLoading(): LiveData<Boolean> {
        return loading
    }

    private fun fetchRepos() {
        loading.value = true
        repoCall = RepoApi.instance.repositories
        repoCall!!.enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                repoLoadError.value = false
                repos.value = response.body()
                loading.value = false
                repoCall = null
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                Log.e(javaClass.simpleName, "Error loading repos", t)
                repoLoadError.value = true
                loading.value = false
                repoCall = null
            }
        })
    }

    override fun onCleared() {
        if (repoCall != null) {
            repoCall!!.cancel()
            repoCall = null
        }
    }
}