package com.example.drumrolldatepicker

import android.os.Bundle
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    // 選択された年
    private var selectedYearItem by Delegates.notNull<Int>()
    // 選択された月
    private var selectedMonthItem by Delegates.notNull<Int>()
    // 選択された日
    private var selectedDayItem by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //現在日付の取得
        val current = LocalDate.now()

        /**
         * 年のNumberpickerの初期化
         * デフォルト値：現在年
         * 最小値：現在年 - 1000
         * 最大値：現在年 + 1000
         */
        val yearNumberPicker = findViewById<NumberPicker>(R.id.yearNumberPicker)
        //現在値の変更があった時に、現在フォーカスの当たっている値を取得し、selectedYearItemに格納
        yearNumberPicker.setOnValueChangedListener(object :NumberPicker.OnValueChangeListener{
            override fun onValueChange(picker: NumberPicker?, old: Int, new: Int) {
                selectedYearItem = new
                // 月の最終日をセット
                setLastDayOfMonth()
            }
        })
        //最小値の設定
        yearNumberPicker.minValue = current.year-1000
        //最大値の設定
        yearNumberPicker.maxValue = current.year+1000
        //yearNumberPickerの初期値に今年を入れた
        yearNumberPicker.value = current.year
        //selectedYearItemにも同じく今年を入れておく
        selectedYearItem = yearNumberPicker.value

        /**
         * 月のNumberpickerの初期化
         * デフォルト値：今月
         * 最小値：1
         * 最大値：12
         */
        val monthNumberPicker = findViewById<NumberPicker>(R.id.monthNumberPicker)
        monthNumberPicker.setOnValueChangedListener(object :NumberPicker.OnValueChangeListener{
            override fun onValueChange(picker: NumberPicker?, old: Int, new: Int) {
                selectedMonthItem = new
                // 月の最終日をセット
                setLastDayOfMonth()
            }
        })
        monthNumberPicker.minValue = 1
        monthNumberPicker.maxValue = 12
        monthNumberPicker.value = current.monthValue
        selectedMonthItem = monthNumberPicker.value

        /**
         * 日のNumberpickerの初期化
         * デフォルト値：今日
         * 最小値：1
         * 最大値：月ごとに設定
         */
        val lengthOfMonth = LocalDate.now().lengthOfMonth()
        val dayNumberPicker = findViewById<NumberPicker>(R.id.dayNumberPicker)
        dayNumberPicker.setOnValueChangedListener(object :NumberPicker.OnValueChangeListener{
            override fun onValueChange(picker: NumberPicker?, old: Int, new: Int) {
                selectedDayItem = new
            }
        })
        dayNumberPicker.minValue = 1
        dayNumberPicker.maxValue = lengthOfMonth
        dayNumberPicker.value = current.dayOfMonth
        selectedDayItem = dayNumberPicker.value
    }

    private fun setLastDayOfMonth() {
        val selectedDate = LocalDate.of(selectedYearItem, selectedMonthItem, 1)
        val lengthOfMonth = selectedDate.lengthOfMonth()
        val dayNumberPicker = findViewById<NumberPicker>(R.id.dayNumberPicker)
        if (lengthOfMonth < selectedDayItem) {
            dayNumberPicker.value = lengthOfMonth
        }
        dayNumberPicker.maxValue = lengthOfMonth
    }
}