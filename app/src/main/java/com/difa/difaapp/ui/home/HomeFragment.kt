package com.difa.difaapp.ui.home

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.difa.difaapp.R
import com.difa.difaapp.data.local.entity.Quotes
import com.difa.difaapp.databinding.FragmentHomeBinding
import com.difa.difaapp.ui.home.adapter.QuotesAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var quotesAdapter: QuotesAdapter
    private lateinit var listDot : ArrayList<TextView>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val data = listOf(
            Quotes(
                1,
                "Di mana tidak ada pergumulan, di situ tidak ada kekuatan",
                "Oprah Winfrey"
            ),
            Quotes(
                2,
                "Jangan biarkan opini orang lain menenggelamkan suara dari dalam dirimu",
                "Steve Jobs"
            ),
            Quotes(
                3,
                "Ia yang mengerjakan lebih dari apa yang dibayar pada suatu saat akan dibayar lebih dari apa yang ia kerjakan",
                "Napoleon Hill"
            ),
        )

        quotesAdapter = QuotesAdapter()
        quotesAdapter.submitList(data)
        binding.vpQuotes.adapter = quotesAdapter
        listDot = ArrayList()
        setQuotesIndicator(data)

        binding.vpQuotes.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                selectedDots(position, data)
            }
        })
    }

    private fun selectedDots(position: Int, data: List<Quotes>) {
        for(i in 0 until data.size){
            if(i == position){
                listDot[i].setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue_600))
            }else {
                listDot[i].setTextColor(ContextCompat.getColor(requireActivity(), R.color.blue_100))
            }
        }
    }

    private fun setQuotesIndicator(data: List<Quotes>) {
        for(i in 0 until data.size){
            listDot.add(TextView(requireContext()))
            listDot[i].text = Html.fromHtml("&#9679", Html.FROM_HTML_MODE_LEGACY).toString()
            listDot[i].textSize = 15f
            binding.dotsIndicator.addView(listDot[i])
        }
    }
}