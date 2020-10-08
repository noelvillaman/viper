package com.noelvillaman.software.viperproject.home

import android.graphics.drawable.ClipDrawable.VERTICAL
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.noelvillaman.software.viperproject.R
import com.noelvillaman.software.viperproject.classes.RepoListAdapter
import com.noelvillaman.software.viperproject.classes.RepoSelectedListener
import com.noelvillaman.software.viperproject.classes.SelectedRepoViewModel
import com.noelvillaman.software.viperproject.details.DetailFragment
import com.software.namalliv.noelviewmodel.model.Repo

class ListFragment : Fragment(), RepoSelectedListener{

    override fun onRepoSelected(repo: Repo) {
        val selectedViewModel = ViewModelProvider(this).get(SelectedRepoViewModel::class.java)
        selectedViewModel!!.setSelectedRepo(repo)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.screen_container, DetailFragment())
            .addToBackStack(null)
            .commit()
    }

    @BindView(R.id.recycler_view)
    internal lateinit var listView: RecyclerView
    @BindView(R.id.tv_error)
    internal lateinit var errorTextView: TextView
    @BindView(R.id.loading_view)
    internal lateinit var loadingView: View

    private var unbinder: Unbinder? = null
    private var viewModel: ListViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.screen_list, container, false)
        listView = view.findViewById(R.id.recycler_view)
        loadingView = view.findViewById(R.id.loading_view)
        errorTextView = view.findViewById(R.id.tv_error)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        listView!!.adapter = RepoListAdapter(viewModel!!, this, this)
        listView!!.layoutManager = LinearLayoutManager(context)
        observeViewModel()
    }

    private fun observeViewModel() {
        // Create the observer which updates the UI.
        val nameObserver = Observer<List<Repo>> { repos ->
            if (repos != null) {
                // Update the UI, in this case, a TextView.
                listView!!.visibility = View.VISIBLE
            }
        }
        viewModel!!.getRepos().observe(viewLifecycleOwner, nameObserver)

        viewModel!!.error.observe(viewLifecycleOwner, Observer<Boolean>{ isError ->
            if (isError) {
                errorTextView!!.visibility = View.VISIBLE
                listView!!.visibility = View.GONE
                errorTextView!!.setText(R.string.api_error_repos)
            } else {
                errorTextView!!.visibility = View.GONE
                errorTextView!!.text = null
            }
        })
        viewModel!!.getLoading().observe(viewLifecycleOwner, Observer<Boolean>{ isLoading ->

            loadingView!!.visibility = if (isLoading) View.VISIBLE else View.GONE
            if (isLoading) {
                errorTextView!!.visibility = View.GONE
                listView!!.visibility = View.GONE
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (unbinder != null) {
            unbinder!!.unbind()
            unbinder = null
        }
    }

}