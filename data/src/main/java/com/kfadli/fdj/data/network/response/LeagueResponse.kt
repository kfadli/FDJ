package com.kfadli.fdj.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LeagueResponse(
    @SerialName("idLeague")
    val id: String,
    @SerialName("strLeague")
    val name: String,
    @SerialName("strSport")
    val sport: String,
    @SerialName("strLeagueAlternate")
    val alternate: String?,
)
