package com.example.scrumboard

import android.app.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.content.Intent
import com.google.gson.Gson
import java.util.*
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.example.scrumboard.interfaces.DatePickerFragment
import com.example.scrumboard.interfaces.OnDateSet
import com.example.scrumboard.interfaces.OnTimeSet
import com.example.scrumboard.interfaces.TimePickerFragment
import com.example.scrumboard.room.Task
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.time.LocalDateTime


class AddTaskActivity : AppCompatActivity() {
    private lateinit var ed_title: EditText
    private lateinit var ed_content: EditText
    private lateinit var pickedDate: TextView
    private lateinit var datePicker: ImageView
    private lateinit var pickedTime: TextView
    private lateinit var timePicker: ImageView
    private lateinit var priorityHigh: ImageView
    private lateinit var priorityMeduim: ImageView
    private lateinit var priorityLow: ImageView
    lateinit var notStartedText: TextView
    lateinit var inProgressText: TextView
    lateinit var doneText: TextView
    private var priority = 0
    private var flag = 0
    private var date: String = ""
    private var time: String = ""
    private var update = false
    private var task = Task()
    val cal: Calendar =Calendar.getInstance();
    var hour: Int = -1
    var minute: Int = -1
    var year: Int = -1
    var month: Int = -1
    var day: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add New Task"
        createNotificationChannel()
        initComp()
        val date = intent.extras?.getString(Const.TASK)
        val task = Gson().fromJson(date, Task::class.java)
        if (task.title != "" && task.content != "") {
            updateTask(task)
        }
        datePicker.setOnClickListener {
            showDatePickerDialog()
        }
        timePicker.setOnClickListener {
            showTimePickerDialog()
        }
        priorityHigh.setOnClickListener {
            priority = 2
            updateTaskPriority(priority)
        }
        priorityMeduim.setOnClickListener {
            priority = 1
            updateTaskPriority(priority)
        }
        priorityLow.setOnClickListener {
            priority = 0
            updateTaskPriority(priority)
        }
        notStartedText.setOnClickListener {
            flag = 0
            updateTaskFlag(flag)
        }
        inProgressText.setOnClickListener {
            flag = 1
            updateTaskFlag(flag)

        }
        doneText.setOnClickListener {
            flag = 2
            updateTaskFlag(flag)
        }
    }
    private fun updateTaskPriority(importance: Int) {
        when (importance) {
            0 -> {
                priorityLow.setImageResource(R.drawable.selected_yellow_circle)
                priorityMeduim.setImageResource(R.drawable.blue_circle)
                priorityHigh.setImageResource(R.drawable.red_circle)

            }
            1 -> {

                priorityLow.setImageResource(R.drawable.yellow_circle)
                priorityMeduim.setImageResource(R.drawable.selected_blue_circle)
                priorityHigh.setImageResource(R.drawable.red_circle)
            }
            2 -> {
                priorityLow.setImageResource(R.drawable.yellow_circle)
                priorityMeduim.setImageResource(R.drawable.blue_circle)
                priorityHigh.setImageResource(R.drawable.selected_red_circle)
            }
        }
    }
    private fun updateTaskFlag(flag: Int) {
        val activeColor = R.color.Sharmorck
        val notActiveColor = R.color.Caparol
        val activeTextColor = R.color.white
        val notActiveTextColor = R.color.black
        when (flag) {
            0 -> {

                inProgressText.setTextColor(ContextCompat.getColor(this@AddTaskActivity, notActiveTextColor))
                inProgressText.setBackgroundResource(notActiveColor)
                notStartedText.setTextColor(ContextCompat.getColor(this@AddTaskActivity, activeTextColor))
                notStartedText.setBackgroundResource(activeColor)
                doneText.setTextColor(ContextCompat.getColor(this@AddTaskActivity, notActiveTextColor))
                doneText.setBackgroundResource(notActiveColor)

            }
            1 -> {

                inProgressText.setTextColor(ContextCompat.getColor(this@AddTaskActivity, activeTextColor))
                inProgressText.setBackgroundResource(activeColor)
                notStartedText.setTextColor(ContextCompat.getColor(this@AddTaskActivity, notActiveTextColor))
                notStartedText.setBackgroundResource(notActiveColor)
                doneText.setTextColor(ContextCompat.getColor(this@AddTaskActivity, notActiveTextColor))
                doneText.setBackgroundResource(notActiveColor)
            }
            2 -> {

                inProgressText.setTextColor(ContextCompat.getColor(this@AddTaskActivity, notActiveTextColor))
                inProgressText.setBackgroundResource(notActiveColor)
                notStartedText.setTextColor(ContextCompat.getColor(this@AddTaskActivity, notActiveTextColor))
                 notStartedText.setBackgroundResource(notActiveColor)
                doneText.setTextColor(ContextCompat.getColor(this@AddTaskActivity, activeTextColor))
                 doneText.setBackgroundResource(activeColor)
            }
        }
    }

    private fun updateTask(task: Task) {
        update = true
        this.task = task
        updateTaskFlag(task.flag)
        updateTaskPriority(task.importance)
        ed_title.setText(task.title)
        ed_content.setText(task.content)
        priority = task.importance
        flag=task.flag
        date = task.date
        pickedDate.text = date
        pickedTime.text=task.time
    }

    private fun showDatePickerDialog() {
        DatePickerFragment(object : OnDateSet {
            override fun pickedDate(year: Int, month: Int, day: Int) {
                val format = SimpleDateFormat("MMM dd, yyyy")

                cal[Calendar.YEAR]=year
                cal[Calendar.MONTH]=month
                cal[Calendar.DAY_OF_MONTH]=day
                this@AddTaskActivity.date = format.format(cal.time)
                this@AddTaskActivity.day = day
                this@AddTaskActivity.month = month
                this@AddTaskActivity.year = year
                pickedDate.text=date
                Log.d("ALARM", "pickedTime: ${year}/${month}/${day}")
            }
        }).show(supportFragmentManager, "datePicker")
    }

    private fun showTimePickerDialog() {
        TimePickerFragment(object : OnTimeSet {
            override fun pickedTime(hoursOfDay: Int, minutes: Int) {
               hour=hoursOfDay
                this@AddTaskActivity.hour=hoursOfDay
                this@AddTaskActivity.minute=minutes
                this@AddTaskActivity.time = "${hoursOfDay}:$minutes"
                pickedTime.text=time
                Log.d("ALARM", "pickedTime: $hoursOfDay : $minutes")
            }
        }).show(supportFragmentManager, "timePicker")

    }
    private fun generateRequestCode(): Int {
        val nums = "1239843219843219832168749846521321984165165465132721984910651"
        var generatedNum = ""
        for (i in 1..7) {
            generatedNum += nums[(nums.indices).random()]
        }
        return generatedNum.toInt()
    }
    private fun setAlarm(millis: Long, requestCode: Int) {

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(applicationContext, AlarmReceiver::class.java)
       intent.putExtra("title", ed_title.text.toString())
        intent.putExtra("content", ed_content.text.toString())
       val pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                millis,
                pendingIntent
            )
        } else {
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                millis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }
        Toast.makeText(applicationContext, "alarm set successfully", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun initComp() {
        ed_title = findViewById(R.id.title)
        ed_content = findViewById(R.id.content)
        pickedDate = findViewById(R.id.picked_date)
        datePicker = findViewById(R.id.date_picker)
        timePicker = findViewById(R.id.time_picker)
        pickedTime=findViewById(R.id.picked_time)
        priorityHigh = findViewById(R.id.priority_high)
        priorityMeduim = findViewById(R.id.priority_meduim)
        priorityLow = findViewById(R.id.priority_low)
        notStartedText = findViewById(R.id.not_started_option)
        inProgressText = findViewById(R.id.in_progress_option)
        doneText = findViewById(R.id.done_option)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.save -> {
                val intent = Intent()
                val title = ed_title.text.toString()
                val content = ed_content.text.toString()
                task.title = title
                task.content = content
                task.importance = priority
                task.date = date
                task.time=time
                task.flag=flag
                if (title == "" && content == "") {
                    setResult(RESULT_CANCELED, intent)
                    finish()
                }
                val task_json = Gson().toJson(task)
                intent.putExtra(Const.TASK, task_json)
                intent.putExtra(Const.UPDATE_TASK, update)
                setResult(RESULT_OK, intent)
                Log.d("TAG", "onOptionsItemSelected: $task_json -- ${task.id}")
                if (time!=""){
                    val calendar =Calendar.getInstance()
                    calendar.set(calendar[Calendar.YEAR],calendar[Calendar.MONTH],calendar[Calendar.DAY_OF_MONTH],hour,minute)
                    if (date!=""){
                        calendar.set(year, month, day, hour, minute, 0)
                    }
                    setAlarm(calendar.timeInMillis,generateRequestCode())
                    Log.d("TAG", "onReceive: alarm set successfully!")

                }

                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "abdelrahman_fathy_channel"
            val desc = "scrum board channel "
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("abdelrahman_fathy_channel", name, importance)
            channel.description = desc
            val nm = getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(channel)
        }
    }

}