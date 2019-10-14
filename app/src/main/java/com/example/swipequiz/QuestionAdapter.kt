package com.example.swipequiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class QuestionAdapter(private val questions: List<Question>) :
    RecyclerView.Adapter<QuestionAdapter.ViewHolder>()
{

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionAdapter.ViewHolder
    {
        context = parent.context

        return ViewHolder(

            LayoutInflater.from(parent.context).inflate(
                android.R.layout.simple_list_item_1,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int
    {
        return questions.size
    }

    override fun onBindViewHolder(holder: QuestionAdapter.ViewHolder, position: Int)
    {
        holder.bind(questions[position])
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)
    {
        private val tvQuestion: TextView =
            itemView.findViewById(android.R.id.text1)

        fun bind(question: Question)
        {
            tvQuestion.text = question.question

            tvQuestion.setOnClickListener {
                Snackbar.make(
                    itemView,
                    context.resources.getString(R.string.answer, question.answer.toString()),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}