package com.murat.international.scorpcase.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.murat.international.scorpcase.core.*
import com.murat.international.scorpcase.data.localdata.ErrorTypes
import com.murat.international.scorpcase.R
import com.murat.international.scorpcase.databinding.ListFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var binding: ListFragmentBinding

    private val viewModel: ListViewModel by viewModels()

    @Inject
    lateinit var listAdapter: ListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        initView()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = listAdapter
    }

    private fun initView() {

        binding.swipeLayout.setOnRefreshListener {
            viewModel.refreshPage()
            if (binding.swipeLayout.isRefreshing)
                binding.swipeLayout.isRefreshing = false
        }

        binding.tvEmptyListMessage.clickWithDebounce {
            viewModel.refreshPage()
        }

        binding.recyclerView.addOnScrollListener(object : ScrollListener() {
            override fun onScrollLimit() {
                viewModel.loadNextPage()
            }
        })

    }

    private fun setupObservers() {
        viewModel.personList.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                listAdapter.setItems(ArrayList(it))
                binding.tvEmptyListMessage.gone(false)
            } else if (it != null) {
                errorStatusChanged(ErrorTypes.NO_DATA)
            }
        }

        viewModel.loadingProgressState.observe(this.viewLifecycleOwner) { state ->
            when (state) {
                true -> binding.progressBar.visible(false)
                false -> binding.progressBar.gone(false)
            }
        }

        viewModel.errors.observe(viewLifecycleOwner) {
            errorStatusChanged(it)
        }
    }

    private fun errorStatusChanged(error: ErrorTypes) =
        when (error) {
            ErrorTypes.NO_DATA -> showToast(getString(R.string.no_data))
            ErrorTypes.NO_CONNECTION_DB -> showToast(getString(R.string.no_connection_db))
            ErrorTypes.NO_NEW_ITEM -> showToast(getString(R.string.no_new_item))
            ErrorTypes.INTERNET_CONNECTION -> showToast(getString(R.string.no_internet_connection))
            ErrorTypes.INTERNAL_SERVER_ERROR -> showToast(getString(R.string.internal_server_error))
            ErrorTypes.PARAMETER_ERROR -> showToast(getString(R.string.parameter_error))
            else -> showToast(getString(R.string.error_somenting_wrong))

        }

    private fun showToast(msg: String) {
        context?.toastShort(message = msg)
    }

}
