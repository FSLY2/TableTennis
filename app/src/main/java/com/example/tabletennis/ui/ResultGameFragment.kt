package com.example.tabletennis.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletennis.common.PlayerNumber
import com.example.tabletennis.databinding.FragmentResultGameBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultGameFragment : BaseFragment() {

    private lateinit var binding: FragmentResultGameBinding
    private val dbViewModel: DatabaseViewModel by viewModels()

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
        swipeToDelete()
        initOnBackPressCallback()
    }

    private fun initOnBackPressCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    //RecyclerView
    private fun init() {
        binding.rvSavedResults.adapter = adapter
    }

    private fun initObservers() {
        dbViewModel.allResults.observe(viewLifecycleOwner) {
            adapter.initList(it)
        }
    }

    //Implementing a method that takes care of the swipe to delete
    private fun swipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, LEFT or RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val item = adapter.getItemByPosition(position)
                if (item != null) {
                    adapter.deleteItem(item)
                    dbViewModel.deleteItem(item.id)
                    Snackbar.make(
                        requireView(),
                        "Successfully deleted item",
                        Snackbar.LENGTH_LONG
                    ).apply {
                        setAction("Undo") {
                            dbViewModel.insert(item)
                            adapter.setItem(item)
                        }
                        show()
                    }
                }
            }
        }).attachToRecyclerView(binding.rvSavedResults)
    }
}