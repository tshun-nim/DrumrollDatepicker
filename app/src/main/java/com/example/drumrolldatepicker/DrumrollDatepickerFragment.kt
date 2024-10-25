package com.example.drumrolldatepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import java.time.LocalDate
import kotlin.properties.Delegates

class DrumrollDatepickerFragment(
    private val onDateSelected: (year: Int, month: Int, day: Int) -> Unit
): DialogFragment() {

    // 選択された年
    private var selectedYearItem by Delegates.notNull<Int>()
    // 選択された月
    private var selectedMonthItem by Delegates.notNull<Int>()
    // 選択された日
    private var selectedDayItem by Delegates.notNull<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.drumroll_datepicker_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //現在日付の取得
        val current = LocalDate.now()

        /**
         * 年のNumberpickerの初期化
         * デフォルト値：現在年
         * 最小値：現在年 - 1000
         * 最大値：現在年 + 1000
         */
        val yearNumberPicker = view.findViewById<NumberPicker>(R.id.yearNumberPicker)
        //現在値の変更があった時に、現在フォーカスの当たっている値を取得し、selectedYearItemに格納
        yearNumberPicker.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener{
            override fun onValueChange(picker: NumberPicker?, old: Int, new: Int) {
                selectedYearItem = new
                // 月の最終日をセット
                setLastDayOfMonth(view)
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
        val monthNumberPicker = view.findViewById<NumberPicker>(R.id.monthNumberPicker)
        monthNumberPicker.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener{
            override fun onValueChange(picker: NumberPicker?, old: Int, new: Int) {
                selectedMonthItem = new
                // 月の最終日をセット
                setLastDayOfMonth(view)
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
        val dayNumberPicker = view.findViewById<NumberPicker>(R.id.dayNumberPicker)
        dayNumberPicker.setOnValueChangedListener(object : NumberPicker.OnValueChangeListener{
            override fun onValueChange(picker: NumberPicker?, old: Int, new: Int) {
                selectedDayItem = new
            }
        })
        dayNumberPicker.minValue = 1
        dayNumberPicker.maxValue = lengthOfMonth
        dayNumberPicker.value = current.dayOfMonth
        selectedDayItem = dayNumberPicker.value

        // 日付確定ボタン
        val okButton: Button = view.findViewById(R.id.okButton)
        okButton.setOnClickListener {
            onDateSelected(selectedYearItem, selectedMonthItem, selectedDayItem)
            dismiss()
        }
    }

    private fun setLastDayOfMonth(view: View) {
        val selectedDate = LocalDate.of(selectedYearItem, selectedMonthItem, 1)
        val lengthOfMonth = selectedDate.lengthOfMonth()
        val dayNumberPicker = view.findViewById<NumberPicker>(R.id.dayNumberPicker)
        if (lengthOfMonth < selectedDayItem) {
            dayNumberPicker.value = lengthOfMonth
            selectedDayItem = dayNumberPicker.value
        }
        dayNumberPicker.maxValue = lengthOfMonth
    }
}