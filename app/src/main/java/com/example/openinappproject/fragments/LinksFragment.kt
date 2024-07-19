package com.example.openinappproject.fragments

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openinappproject.R
import com.example.openinappproject.adapter.AnalyticsAdapter
import com.example.openinappproject.adapter.LinksAdapter
import com.example.openinappproject.databinding.FragmentLinksBinding
import com.example.openinappproject.model.AnalyticsModel
import com.example.openinappproject.model.LinksModel
import com.example.openinappproject.model.OpenInApp
import com.example.openinappproject.utils.formatTimestampToIST
import com.example.openinappproject.utils.getCurrentTimeOfDay
import com.example.openinappproject.viewmodel.AppViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class LinksFragment : Fragment() {
    lateinit var lineDataSet: LineDataSet
    val entries: ArrayList<Entry> = arrayListOf()
    private lateinit var analyticsAdapter: AnalyticsAdapter
    private lateinit var linksAdapter: LinksAdapter
    private lateinit var binding: FragmentLinksBinding
    private lateinit var appViewModel: AppViewModel
    private lateinit var openInApp: OpenInApp
    private var analyticsList = arrayListOf<AnalyticsModel>()
    private var topLinksList = arrayListOf<LinksModel>()
    private var recentLinksList = arrayListOf<LinksModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLinksBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialise()
        initialiseUIviews()
        setUpChartData()
        Log.d("LinksFragment", "onViewCreated: $isAdded")
        appViewModel.fetchResponse()
        lifecycleScope.launch {
            appViewModel.openInAppResponse.collectLatest { response ->
                addDataToAnalyticsList(response)
                addDataToTopLinksList(response)
                addDataToRecentLinksList(response)
                Handler(Looper.getMainLooper()).post {
                    analyticsAdapter.updateAnalyticsList(analyticsList)
                    linksAdapter.updateLinksList(topLinksList)
                    addChartData(response)
                    lineDataSet.notifyDataSetChanged()
                    binding.linechartAnalytics.notifyDataSetChanged()
                    binding.linechartAnalytics.invalidate()
                }
            }
        }

        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        binding.topLinks.setOnClickListener {
            binding.topLinks.background = resources.getDrawable(R.drawable.back_blue)
            binding.recentLinks.background = resources.getDrawable(R.drawable.back_transparent)
            binding.textTopLinks.setTextColor(Color.WHITE)
            binding.textRecentLinks.setTextColor(resources.getColor(R.color.text_grey))
            linksAdapter.updateLinksList(topLinksList)
        }
        binding.recentLinks.setOnClickListener {
            binding.recentLinks.background = resources.getDrawable(R.drawable.back_blue)
            binding.topLinks.background = resources.getDrawable(R.drawable.back_transparent)
            binding.textRecentLinks.setTextColor(Color.WHITE)
            binding.textTopLinks.setTextColor(resources.getColor(R.color.text_grey))
            linksAdapter.updateLinksList(recentLinksList)
        }
    }

    private fun addDataToRecentLinksList(response: OpenInApp) {
        val topLinks = response.data.recent_links

        for (links in topLinks) {
            recentLinksList.add(
                LinksModel(
                    links.title,
                    formatTimestampToIST(links.created_at),
                    links.total_clicks.toString(),
                    links.web_link
                )
            )
        }
    }

    private fun addChartData(response: OpenInApp) {

        val chartResponse = response.data.overall_url_chart
        Log.d("MainAc", "addChartData: $chartResponse")
        if (chartResponse != null) {
            entries.add(Entry(0F, chartResponse.start.toFloat()))
            entries.add(Entry(1F, chartResponse.one.toFloat()))
            entries.add(Entry(2F, chartResponse.two.toFloat()))
            entries.add(Entry(3F, chartResponse.three.toFloat()))
            entries.add(Entry(4F, chartResponse.four.toFloat()))
            entries.add(Entry(5F, chartResponse.five.toFloat()))
            entries.add(Entry(6F, chartResponse.six.toFloat()))
            entries.add(Entry(7F, chartResponse.seven.toFloat()))
            entries.add(Entry(8F, chartResponse.eight.toFloat()))
            entries.add(Entry(9F, chartResponse.nine.toFloat()))
            entries.add(Entry(10F, chartResponse.ten.toFloat()))
            entries.add(Entry(11F, chartResponse.eleven.toFloat()))
            entries.add(Entry(12F, chartResponse.twelve.toFloat()))
            entries.add(Entry(13F, chartResponse.thirteen.toFloat()))
            entries.add(Entry(14F, chartResponse.fourteen.toFloat()))
            entries.add(Entry(15F, chartResponse.fifteen.toFloat()))
            entries.add(Entry(16F, chartResponse.sixteen.toFloat()))
            entries.add(Entry(17F, chartResponse.seventeen.toFloat()))
            entries.add(Entry(18F, chartResponse.eighteen.toFloat()))
            entries.add(Entry(19F, chartResponse.ninteen.toFloat()))
            entries.add(Entry(20F, chartResponse.twenty.toFloat()))
            entries.add(Entry(21F, chartResponse.twentyone.toFloat()))
            entries.add(Entry(22F, chartResponse.twentytwo.toFloat()))
            entries.add(Entry(23F, chartResponse.twentythree.toFloat()))
        }
        if (binding.linechartAnalytics.data != null) {
            lineDataSet = binding.linechartAnalytics.data.getDataSetByIndex(0) as LineDataSet

            lineDataSet.values = entries
            binding.linechartAnalytics.description.isEnabled = false
            val gradient = LinearGradient(
                0f, 600f, 0f, 0f,
                ContextCompat.getColor(requireContext(), R.color.blue),
                ContextCompat.getColor(requireContext(), R.color.blue),
                Shader.TileMode.CLAMP
            )

            val paint = binding.linechartAnalytics.renderer.paintRender
            paint.setShader(gradient)
            binding.linechartAnalytics.data.notifyDataChanged()
            lineDataSet.disableDashedLine()
            binding.linechartAnalytics.notifyDataSetChanged()
        } else {
            lineDataSet = LineDataSet(entries, null)
            lineDataSet.setDrawIcons(false)
            lineDataSet.setDrawCircleHole(false)
            lineDataSet.setDrawFilled(true)
            val gradient = LinearGradient(
                0f, 600f, 0f, 0f,
                ContextCompat.getColor(requireContext(), R.color.blue),
                ContextCompat.getColor(requireContext(), R.color.blue),
                Shader.TileMode.CLAMP
            )

            val paint = binding.linechartAnalytics.renderer.paintRender
            paint.setShader(gradient)
            if (Utils.getSDKInt() >= 18) {
                val drawable = ContextCompat.getDrawable(requireContext(), R.color.blue)
                lineDataSet.setFillDrawable(drawable)
            } else {
                lineDataSet.setFillColor(Color.DKGRAY)
            }
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(lineDataSet)
            val data = LineData(dataSets)
            lineDataSet.disableDashedLine()
            binding.linechartAnalytics.description.isEnabled = false
            binding.linechartAnalytics.setData(data)
        }
    }

    private fun addDataToTopLinksList(response: OpenInApp) {
        val topLinks = response.data.top_links

        for (links in topLinks) {
            topLinksList.add(
                LinksModel(
                    links.title,
                    formatTimestampToIST(links.created_at),
                    links.total_clicks.toString(),
                    links.web_link
                )
            )
        }
    }

    private fun addDataToAnalyticsList(response: OpenInApp) {
        analyticsList.add(
            AnalyticsModel(
                R.drawable.clicks,
                response.today_clicks.toString(),
                "Today's Clicks"
            )
        )
        analyticsList.add(
            AnalyticsModel(
                R.drawable.location,
                response.top_location,
                "Top Location"
            )
        )
        analyticsList.add(
            AnalyticsModel(
                R.drawable.internet,
                response.top_source,
                "Top Sources"
            )
        )
    }

    private fun setUpChartData() {
        val entries = listOf<Entry>()

        if (binding.linechartAnalytics.data != null) {
            lineDataSet = binding.linechartAnalytics.data.getDataSetByIndex(0) as LineDataSet

            lineDataSet.values = entries
            binding.linechartAnalytics.description.isEnabled = false
            val gradient = LinearGradient(
                0f, 600f, 0f, 0f,
                ContextCompat.getColor(requireContext(), R.color.blue),
                ContextCompat.getColor(requireContext(), R.color.blue),
                Shader.TileMode.CLAMP
            )

            val paint = binding.linechartAnalytics.renderer.paintRender
            paint.setShader(gradient)
            binding.linechartAnalytics.data.notifyDataChanged()
            lineDataSet.disableDashedLine()
            binding.linechartAnalytics.notifyDataSetChanged()
        } else {
            lineDataSet = LineDataSet(entries, null)
            lineDataSet.setDrawIcons(false)
            lineDataSet.setDrawCircleHole(false)
            lineDataSet.setDrawFilled(true)
            val gradient = LinearGradient(
                0f, 600f, 0f, 0f,
                ContextCompat.getColor(requireContext(), R.color.blue),
                ContextCompat.getColor(requireContext(), R.color.blue),
                Shader.TileMode.CLAMP
            )

            val paint = binding.linechartAnalytics.renderer.paintRender
            paint.setShader(gradient)
            if (Utils.getSDKInt() >= 18) {
                val drawable = ContextCompat.getDrawable(requireContext(), R.color.blue)
                lineDataSet.setFillDrawable(drawable)
            } else {
                lineDataSet.setFillColor(Color.DKGRAY)
            }
            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(lineDataSet)
            val data = LineData(dataSets)
            lineDataSet.disableDashedLine()
            binding.linechartAnalytics.description.isEnabled = false
            binding.linechartAnalytics.setData(data)
        }
    }

    private fun initialiseUIviews() {
        binding.goodMorningTv.text = "Good ${getCurrentTimeOfDay()}"
        binding.linechartAnalytics.apply {
            setTouchEnabled(true)
            setPinchZoom(true)
        }
        binding.detailsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.linksRv.layoutManager = LinearLayoutManager(requireContext())
        binding.detailsRv.adapter = analyticsAdapter
        binding.linksRv.adapter = linksAdapter
    }

    private fun initialise() {
        appViewModel = AppViewModel()
        analyticsAdapter = AnalyticsAdapter()
        linksAdapter = LinksAdapter(requireContext())
    }
}