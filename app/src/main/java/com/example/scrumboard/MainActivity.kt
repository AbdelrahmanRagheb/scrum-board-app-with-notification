package com.example.scrumboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.scrumboard.room.Task
import com.example.scrumboard.room.TaskViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*
import android.app.Activity
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var fragmentAdapter: FragmentAdapter
    private lateinit var fab: FloatingActionButton
    private val tabNames = listOf("Stories", "Not Started", "In Progress", "Done")

    private val viewModel: TaskViewModel by viewModels {
        TaskViewModel.TaskViewModelFactory(application)
    }
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val value = intent?.extras?.getString(Const.TASK)
                val update = intent?.extras?.getBoolean(Const.UPDATE_TASK)
                val task = Gson().fromJson(value, Task::class.java)
              if (update!!){
                  Log.d("TAG", "===========: $update")
                  viewModel.update(task)
              }else {
                  viewModel.insert(task)
                  Log.d("TAG", "===========: $update")
              }

            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        initComp()
//        val currentTime: String = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
        fab.setOnClickListener {
          addOrUpdateTask()

        }
    }

    fun addOrUpdateTask(task: Task= Task()){
        val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
        val toJson = Gson().toJson(task)
        intent.putExtra(Const.TASK,toJson)
        startForResult.launch(intent)
    }

      private fun initComp() {
        fab = findViewById(R.id.floatingActionButton)
        viewPager = findViewById(R.id.pager)
        tabLayout = findViewById(R.id.tabLayout)
        val fm = supportFragmentManager
        fragmentAdapter = FragmentAdapter(fm, lifecycle)
        viewPager.adapter = fragmentAdapter
    }

}