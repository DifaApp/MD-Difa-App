package com.difa.difaapp.ui.home

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.difa.difaapp.R
import com.difa.difaapp.data.local.entity.Quotes
import com.difa.difaapp.databinding.FragmentHomeBinding
import com.difa.difaapp.ui.ViewModelFactory
import com.difa.difaapp.ui.home.aboutApp.AboutAppActivity
import com.difa.difaapp.ui.home.adapter.QuotesAdapter
import com.difa.difaapp.ui.home.adapter.RecommendationAdapter
import com.difa.difaapp.ui.objdetection.ObjectDetectionActivity
import com.takusemba.spotlight.Spotlight
import com.takusemba.spotlight.Target
import com.takusemba.spotlight.shape.RoundedRectangle
import com.difa.difaapp.data.Result
import com.difa.difaapp.data.local.entity.Recommendation

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var quotesAdapter: QuotesAdapter
    private lateinit var recommendationAdapter: RecommendationAdapter
    private lateinit var listDot : ArrayList<TextView>
    private lateinit var loadingHome: Dialog

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireContext().applicationContext)
    }

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

        loadingHome = Dialog(requireActivity())


        viewModel.getAllRecommendation().observe(requireActivity()){result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        showDialog()
                    }

                    is Result.Success -> {
                        dismissLoading()
                        setupRecommendation(result.data)
                    }

                    is Result.Error -> {
                        dismissLoading()
                        Log.d("HomeFragment", "onViewCreated: ${result.error}")
                    }
                }
            }
        }

        binding.vpQuotes.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                selectedDots(position, data)
            }
        })

        binding.cardLearnApp.setOnClickListener { startButton ->
            setSpotlight()
        }

        binding.cardInfoApp.setOnClickListener {
            startActivity(Intent(requireContext(), AboutAppActivity::class.java))
        }

        binding.cardCamera.setOnClickListener {
            startActivity(Intent(requireContext(), ObjectDetectionActivity::class.java))
        }
    }

    private fun setupRecommendation(data: List<Recommendation>) {
        recommendationAdapter = RecommendationAdapter(requireActivity())
        recommendationAdapter.submitList(data)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvSibiLearn.adapter = recommendationAdapter
        binding.rvSibiLearn.layoutManager = layoutManager
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

    private fun dismissLoading() {
        if(loadingHome.isShowing){
            loadingHome.dismiss()
        }
    }

    private fun showDialog() {
        loadingHome.setContentView(R.layout.bg_loading_auth)
        loadingHome.setCancelable(false)
        loadingHome.setCanceledOnTouchOutside(false)
        loadingHome.show()
    }

    private fun setSpotlight(){
        val target = ArrayList<Target>()

        val firstRoot = FrameLayout(requireContext())
        val first = layoutInflater.inflate(R.layout.layout_spotlight_home_1, firstRoot)
        val firstTarget = Target.Builder()
            .setAnchor(binding.vpQuotes)
            .setShape(RoundedRectangle(500f, 1050f, 10f))
            .setOverlay(first)
            .build()

        target.add(firstTarget)

        val secondRoot = FrameLayout(requireContext())
        val second = layoutInflater.inflate(R.layout.layout_spotlight_home_2, secondRoot)
        val secondTarget = Target.Builder()
            .setAnchor(binding.cardCamera)
            .setShape(RoundedRectangle(400f, 1050f, 10f))
            .setOverlay(second)
            .build()

        target.add(secondTarget)

        val thirdRoot = FrameLayout(requireContext())
        val third = layoutInflater.inflate(R.layout.layout_spotlight_home_3, thirdRoot)
        val thirdTarget = Target.Builder()
            .setAnchor(binding.tvSibi)
            .setShape(RoundedRectangle(100f, 1000f, 10f))
            .setOverlay(third)
            .build()

        target.add(thirdTarget)

        val spotlight = Spotlight.Builder(requireActivity())
            .setTargets(target)
            .setBackgroundColor(R.color.spotlightBackground)
            .setDuration(1000L)
            .setAnimation(DecelerateInterpolator(2f))
            .build()

        spotlight.start()

        val nextTarget = View.OnClickListener { spotlight.next() }

        val prevTarget = View.OnClickListener { spotlight.previous() }

        val closeSpotlight = View.OnClickListener { spotlight.finish() }

        first.findViewById<View>(R.id.btn_next).setOnClickListener(nextTarget)
        first.findViewById<View>(R.id.btn_prev).setOnClickListener(prevTarget)
        first.findViewById<View>(R.id.iv_stop).setOnClickListener(closeSpotlight)

        second.findViewById<View>(R.id.btn_next).setOnClickListener(nextTarget)
        second.findViewById<View>(R.id.btn_prev).setOnClickListener(prevTarget)
        second.findViewById<View>(R.id.iv_stop).setOnClickListener(closeSpotlight)

        third.findViewById<View>(R.id.btn_next).setOnClickListener(nextTarget)
        third.findViewById<View>(R.id.btn_prev).setOnClickListener(prevTarget)
        third.findViewById<View>(R.id.iv_stop).setOnClickListener(closeSpotlight)
    }
}