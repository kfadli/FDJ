package com.kfadli.fdj.data

import com.kfadli.fdj.data.network.response.LeaguesResponse
import com.kfadli.fdj.data.network.response.TeamsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodeURLPath

class LeaguesApiDataSource(
    private val clientApi: HttpClient,
) {
    suspend fun getTeamsByLeague(leagueName: String): Result<TeamsResponse> =
        kotlin.runCatching {
            clientApi.get<TeamsResponse>(url = SportsApi.AllTeams(query = leagueName.encodeURLPath()).url)
        }

    suspend fun getAllLeagues(): Result<LeaguesResponse> = runCatching { clientApi.get<LeaguesResponse>(url = SportsApi.AllLeagues.url) }
}

suspend inline fun <reified R> HttpClient.get(url: String): R =
    get(url) {
        contentType(ContentType.Application.Json)
    }.body()

suspend fun HttpClient.getOrNull(url: String): HttpResponse =
    get(url) {
        contentType(ContentType.Application.Json)
    }

sealed class SportsApi(
    path: String,
) {
    data object AllLeagues : SportsApi("all_leagues.php")

    data class AllTeams(
        val query: String? = null,
    ) : SportsApi("search_all_teams.php?l=$query")

    val url = "$URL_API/$path"
}

private const val THE_SPORTS_DB_KEY = "50130162"
private const val URL_API = "https://www.thesportsdb.com/api/v1/json/${THE_SPORTS_DB_KEY}"
