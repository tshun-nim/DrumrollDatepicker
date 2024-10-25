package com.example.drumrolldatepicker

import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button: Button = findViewById(R.id.showDatepicker)
        button.setOnClickListener {
            val dialog = DrumrollDatepickerFragment { year, month, day ->
                // 選択された日付を受け取る
                println("Selected date: $year-$month-$day")

                val dateTextView: TextView = findViewById(R.id.dateTextView)
                val strYear  = year.toString()
                val strMonth = month.toString()
                val strDay   = day.toString()
                dateTextView.text = "${strYear}年${strMonth}月${strDay}日"
            }
            dialog.isCancelable = false
            dialog.show(supportFragmentManager, "DrumrollDatepickerFragment")
        }
    }
}