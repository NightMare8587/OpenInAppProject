package com.example.openinappproject.model

data class Data(
    val favourite_links: List<Any>,
    val overall_url_chart: OverallUrlChart? = null,
    val recent_links: List<RecentLink>,
    val top_links: List<TopLink>
)