package com.noelvillaman.software.viperproject.classes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.software.namalliv.noelviewmodel.model.Repo
import retrofit2.Call

class SelectedRepoViewModel : ViewModel(){
    val selectedRepo = MutableLiveData<Repo>()

    lateinit var repoCall : Call<Repo>

    init {
        loadRepo()
    }

    private fun loadRepo(){
        //repoCall = RepoApi.instance.repositories(repo)
    }

    fun getSelectedRepo() : LiveData<Repo>{
        return selectedRepo as LiveData<Repo>
    }

    fun setSelectedRepo(repo: Repo){
        selectedRepo.value = repo
    }
}