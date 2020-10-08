package com.noelvillaman.software.viperproject.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.noelvillaman.software.viperproject.R
import com.noelvillaman.software.viperproject.classes.SelectedRepoViewModel


class DetailFragment : Fragment() {

    @BindView(R.id.tv_repo_name)
    lateinit var repoNameTextView: TextView
    @BindView(R.id.tv_repo_description)
    lateinit var repoDescriptionTextView: TextView
    @BindView(R.id.tv_forks)
    lateinit var forksTextView: TextView
    @BindView(R.id.tv_stars)
    lateinit var starsTextView: TextView

    private lateinit var unbinder: Unbinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.screen_details, container, false)
        repoNameTextView = view.findViewById(R.id.tv_repo_name)
        repoDescriptionTextView = view.findViewById(R.id.tv_repo_description)
        forksTextView = view.findViewById(R.id.tv_forks)
        starsTextView = view.findViewById(R.id.tv_stars)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        displayRepo()
    }

    private fun displayRepo() {
        val selectedRepoViewModel = activity?.let {
            ViewModelProvider(it)
                .get(SelectedRepoViewModel::class.java)
        }
        selectedRepoViewModel!!.getSelectedRepo().observe(viewLifecycleOwner, Observer {repo ->
            repoNameTextView.text = repo.name
            repoDescriptionTextView.text = repo.description
            forksTextView.text = repo.forks.toString()
            starsTextView.text = repo.stars.toString()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (unbinder != null){
            unbinder.unbind()
        }
    }


}
