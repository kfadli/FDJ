package com.kfadli.fdj.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamsResponse(
    @SerialName("teams")
    val teams: List<TeamResponse>?,
)
