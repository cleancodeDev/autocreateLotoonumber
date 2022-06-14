package com.cleancode.fastcampus_part2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private val addBtn: Button by lazy {
        findViewById<Button>(R.id.addBtn)
    }

    private val clearBtn: Button by lazy {
        findViewById<Button>(R.id.clearBtn)
    }

    private val runButton: Button by lazy {
        findViewById<Button>(R.id.runBtn)
    }

    private val numberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }

    private val numberTextViewList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById<TextView>(R.id.tv1),
            findViewById<TextView>(R.id.tv2),
            findViewById<TextView>(R.id.tv3),
            findViewById<TextView>(R.id.tv4),
            findViewById<TextView>(R.id.tv5),
            findViewById<TextView>(R.id.tv6)
        )
    }
    private var didRun = false
    private val numberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
        initClearButton()
    }

    private fun initRunButton() {
        runButton.setOnClickListener {
            val list = getRandomNumber()
            didRun = true
            list.forEachIndexed { index, number ->
                val textView = numberTextViewList[index]
                textView.text = number.toString()
                textView.isVisible = true
                setTextViewBackground(number, textView)
            }
            Log.d("Tag", "${list.toString()}")
        }
    }

    private fun initAddButton() {
        addBtn.setOnClickListener {
            if (didRun) {
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (numberSet.size >= 5) {
                Toast.makeText(this, "번호는 다섯개 까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (numberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val textView = numberTextViewList[numberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()
            setTextViewBackground(numberPicker.value, textView)
            numberSet.add(numberPicker.value)
        }
    }

    private fun setTextViewBackground(number: Int, textView: TextView) {
        when (number) {
            in 1..10 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_yellow)
            in 11..20 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 21..30 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 31..40 -> textView.background =
                ContextCompat.getDrawable(this, R.drawable.circle_gray)
            else -> textView.background = ContextCompat.getDrawable(this, R.drawable.circle_green)
        }
    }

    private fun initClearButton() {
        clearBtn.setOnClickListener {
            numberSet.clear()
            numberTextViewList.forEach {
                it.isVisible = false
            }
            didRun = false
        }
    }


    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45) {
                if (numberSet.contains(i)) {
                    continue
                }
                this.add(i)
            }
        }
        numberList.shuffle()
        val newList = numberSet.toList() + numberList.subList(0, 6 - numberSet.size);
        return newList.sorted()
    }
}