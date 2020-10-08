package com.software.namalliv.noelviewmodel.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Repo{
    @kotlin.jvm.JvmField
    var id: Long? = null
    @kotlin.jvm.JvmField
    var name: String? = null
    @kotlin.jvm.JvmField
    var description: String? = null

    var owner: User? = null

    @field:SerializedName(
        "stargazers_count"
    )
    @field:Expose
    @kotlin.jvm.JvmField
    var stars: Long? = null

    @field:SerializedName("forks_count")
    @field:Expose
    @kotlin.jvm.JvmField
    var forks: Long? = null
}
