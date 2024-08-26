package com.kfadli.fdj.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeaguesResponse(
    @SerialName("leagues")
    val leagues: List<LeagueResponse>,
)
