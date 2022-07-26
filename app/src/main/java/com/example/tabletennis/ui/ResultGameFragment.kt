package com.example.tabletennis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletennis.data.GameResultAdapter
import com.example.tabletennis.databinding.FragmentResultGameBinding

class ResultGameFragment : BaseFragment() {

    private lateinit var binding: FragmentResultGameBinding
    private val dbViewModel: DatabaseViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GameResultAdapter

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
    }

    //Init Database, RecyclerView, Adapter
    private fun init() {
        dbViewModel.initDatabase()
        val gameResult = dbViewModel.getAllResults()
        recyclerView = binding.rvSavedResults
        adapter = GameResultAdapter(gameResult)
        recyclerView.adapter = adapter
    }
}