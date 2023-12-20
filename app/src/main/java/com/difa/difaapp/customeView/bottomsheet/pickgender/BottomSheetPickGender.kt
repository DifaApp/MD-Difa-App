package com.difa.difaapp.customeView.bottomsheet.pickgender

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.difa.difaapp.R
import com.difa.difaapp.ui.ViewModelFactory
import com.difa.difaapp.ui.profile.update.UpdateProfileViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetPickGender: BottomSheetDialogFragment() {

    private lateinit var viewModel: ViewModelBottomSheetGender

    private lateinit var rgGender: RadioGroup
    private lateinit var rbFemale: RadioButton
    private lateinit var rbMale: RadioButton
    private lateinit var cardSaveGender: CardView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.layout_bs_pick_gender, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ViewModelBottomSheetGender::class.java)

        rgGender = view.findViewById(R.id.rg_gender)
        rbFemale = view.findViewById(R.id.rb_female)
        rbMale = view.findViewById(R.id.rb_male)


        cardSaveGender = view.findViewById(R.id.card_save_gender)
        cardSaveGender.setOnClickListener {
            val checkedRadioButtonId = rgGender.checkedRadioButtonId
            if(checkedRadioButtonId != -1){
                val selectedRadioButton = view.findViewById<RadioButton>(checkedRadioButtonId)
                val selectedText = selectedRadioButton.text.toString()
                viewModel.gender.value = selectedText
                dismiss()
            }
            Log.d("TAG", "onViewCreated: ${viewModel.gender.value}")
        }
    }

}