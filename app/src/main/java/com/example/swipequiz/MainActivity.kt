package com.example.swipequiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{
    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)

    private val SWIPE_TRUE_DIR = 4
    private val SWIPE_FALSE_DIR = 8

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews()
    {
        for (i in Question.QUESTIONS.indices)
        {
            questions.add(Question(Question.QUESTIONS[i], Question.ANSWERS[i]))
        }

        questionAdapter.notifyDataSetChanged()

        rvQuestions.layoutManager = LinearLayoutManager(
            this@MainActivity,
            RecyclerView.VERTICAL, false
        )

        rvQuestions.adapter = questionAdapter

        rvQuestions.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        createItemTouchHelper().attachToRecyclerView(rvQuestions)
    }

    private fun createItemTouchHelper(): ItemTouchHelper
    {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        )
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean
            {
                return false
            }

            //Detect swipe and checks if answer is correct
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
            {
                val target = questions[viewHolder.adapterPosition]

                if ((direction == SWIPE_TRUE_DIR && target.answer) ||
                    (direction == SWIPE_FALSE_DIR && !target.answer)
                )
                {
                    Snackbar.make(
                        rvQuestions, getString(R.string.correct_answer, target.answer.toString()),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                else
                {
                    Snackbar.make(
                        rvQuestions, getString(R.string.incorrect_answer, target.answer.toString()),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                questionAdapter.notifyItemChanged(viewHolder.adapterPosition)
            }
        }

        return ItemTouchHelper(callback)
    }
}
