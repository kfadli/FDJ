package com.kfadli.fdj.data.network.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TeamResponse(
    @SerialName("idTeam")
    val id: String,
    @SerialName("strTeam")
    val name: String,
    @SerialName("strSport")
    val sport: String,
    @SerialName("intFormedYear")
    val formedYear: String?,
    @SerialName("strStadium")
    val stadium: String?,
    @SerialName("strDescriptionEN")
    val description: String?,
    @SerialName("strBadge")
    val badge: String,
)
