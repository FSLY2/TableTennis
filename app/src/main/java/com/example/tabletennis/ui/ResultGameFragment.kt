package com.example.tabletennis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletennis.common.MyViewModelFactory
import com.example.tabletennis.data.GameResultAdapter
import com.example.tabletennis.databinding.FragmentResultGameBinding

class ResultGameFragment : BaseFragment() {

    private lateinit var binding: FragmentResultGameBinding
    private val dbViewModel: DatabaseViewModel by viewModels{
        MyViewModelFactory(requireContext())
    }

    private val adapter = GameResultAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultGameBinding.inflate(inflater, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initObservers()
        dbViewModel.getAllResults()
    }

    //RecyclerView Decorator
    private fun init() {
        binding.rvSavedResults.adapter = adapter
    }

    private fun initObservers() {
        dbViewModel.allResults.observe(viewLifecycleOwner) {
            adapter.initList(it)
        }
    }
}