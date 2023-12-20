package com.difa.difaapp.customeView.bottomsheet.pickdate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.difa.difaapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BottomSheetPickDate: BottomSheetDialogFragment() {

    private lateinit var viewModel: ViewModelBottomSheetDate
    private lateinit var datePick: DatePicker
    private lateinit var cardSaveDate: CardView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.layout_bs_pick_date, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModelBottomSheetDate::class.java)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        cardSaveDate = view.findViewById(R.id.card_save_birthday)
        datePick = view.findViewById(R.id.datePicker)
        datePick.updateDate(year, month, dayOfMonth)
        datePick.setOnDateChangedListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
        }

        cardSaveDate.setOnClickListener {
            updateSelectedDate(calendar)
            dismiss()
        }
    }

    private fun updateSelectedDate(calendar: Calendar) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = dateFormat.format(calendar.time)
        viewModel.selectedDate.value = formattedDate
    }


}