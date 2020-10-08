package com.noelvillaman.software.viperproject.classes


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.software.namalliv.noelviewmodel.model.Repo
import java.util.ArrayList
import butterknife.BindView
import butterknife.ButterKnife
import com.noelvillaman.software.viperproject.R
import com.noelvillaman.software.viperproject.home.ListViewModel

class RepoListAdapter internal constructor(
    viewModel: ListViewModel,
    lifecycleOwner: LifecycleOwner,
    private val repoSelectedListener: RepoSelectedListener
) : RecyclerView.Adapter<RepoListAdapter.RepoViewHolder>() {

    private var repoSelectedListener1 : RepoSelectedListener? = null
    private val data = ArrayList<Repo>()

    init {
        this.repoSelectedListener1 = repoSelectedListener
        viewModel.repos.observe(lifecycleOwner, Observer { repos ->
            data.clear()
            if (repos != null) {
                data.addAll(repos!!)
            }
            notifyDataSetChanged() //TODO: Use DiffUtil when we have AutoValue models
        })
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_repo_list_item, parent, false)
        return RepoViewHolder(view, repoSelectedListener)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size


    override fun getItemId(position: Int): Long {
        return data[position].id!!
    }

    class RepoViewHolder(itemView: View, respoSelectedListener: RepoSelectedListener) :
        RecyclerView.ViewHolder(itemView) {

        @BindView(R.id.tv_repo_name)
        lateinit var repoNameTextView: TextView
        @BindView(R.id.tv_repo_description)
        lateinit var repoDescriptionTextView: TextView
        @BindView(R.id.tv_forks)
        lateinit var forksTextView: TextView
        @BindView(R.id.tv_stars)
        lateinit var starsTextView: TextView

        private var repo: Repo

        init {
            ButterKnife.bind(this, itemView)
            repo = Repo()
            repoNameTextView = itemView.findViewById(R.id.tv_repo_description)
            repoDescriptionTextView = itemView.findViewById(R.id.tv_repo_description)
            forksTextView = itemView.findViewById(R.id.tv_forks)
            starsTextView = itemView.findViewById(R.id.tv_stars)
            itemView.setOnClickListener(View.OnClickListener {
                if (repo != null) {
                    respoSelectedListener.onRepoSelected(repo)
                }
            })
        }

        fun bind(repo: Repo) {
            this.repo = repo
            repoNameTextView!!.text = repo.name
            repoDescriptionTextView!!.text = repo.description
            forksTextView!!.text = repo.forks.toString()
            starsTextView!!.text = repo.stars.toString()
        }
    }
}
