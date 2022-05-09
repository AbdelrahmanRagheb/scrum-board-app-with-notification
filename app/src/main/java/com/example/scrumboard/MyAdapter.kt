package com.example.scrumboard

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.scrumboard.room.Task

class MyAdapter(
    val context: Context,
    var list: MutableList<Task>,
    private val onTaskClick: OnTaskClickListener
) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View, list: MutableList<Task>, onTaskClick: OnTaskClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.task_title)
        val content: TextView = itemView.findViewById(R.id.task_content)
        val date: TextView = itemView.findViewById(R.id.task_date)
        val state: TextView = itemView.findViewById(R.id.state)


        private val deleteIcon: ImageView = itemView.findViewById(R.id.task_delete)
        val card: CardView = itemView.findViewById(R.id.card)

        init {
            deleteIcon.setOnClickListener {
                onTaskClick.onDeleteListener(list[adapterPosition])
            }
            itemView.setOnClickListener {
                onTaskClick.onUpdateListener(list[adapterPosition])
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.cart, parent, false
            ),
            list,
            onTaskClick
        )
    }

    interface OnTaskClickListener {
        fun onDeleteListener(task: Task)
        fun onUpdateListener(task: Task)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val task = list[position]
        holder.title.text = task.title
        holder.content.text = task.content
        holder.date.text = task.date
        var flag=""
        when(task.flag){
            0-> flag = "Not Started".uppercase()
            1-> flag = "In Progress".uppercase()
            2-> flag = "Done".uppercase()
        }
        holder.state.text = flag
        var color = 0
        when (task.importance) {
            0 -> {
                color = R.color.Yellow
                holder.card.setCardBackgroundColor(ContextCompat.getColor(context,color))
            }
            1 -> {
                color = R.color.Blue
                holder.card.setCardBackgroundColor(ContextCompat.getColor(context, color))

            }
            2 -> {
                color = R.color.Red
                holder.card.setCardBackgroundColor(ContextCompat.getColor(context, color))

            }
        }

    }

    fun updateList(task: MutableList<Task>) {
        list = task
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}