package com.example.scrumboard.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scrumboard.*
import com.example.scrumboard.room.Task
import com.example.scrumboard.room.TaskViewModel

class StoriesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_stories, container, false)
        val viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]

        val listener = object : MyAdapter.OnTaskClickListener {
            override fun onDeleteListener(task: Task) {
                viewModel.delete(task)
            }

            override fun onUpdateListener(task: Task) {
                (activity as MainActivity).addOrUpdateTask(task)
                Log.d("TAG", "===========: ${task.title}")
            }
        }
        val adapter = MyAdapter(requireActivity(), mutableListOf(), listener)
        val rv: RecyclerView = view.findViewById(R.id.rv_stories)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.hasFixedSize()
        rv.adapter = adapter
        viewModel.allTasks.observe(requireActivity(), {
            adapter.updateList(it)
            rv.adapter = adapter
        })

        return view
    }

}