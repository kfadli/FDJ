package com.kfadli.fdj.data

import com.kfadli.fdj.data.network.response.LeagueResponse
import com.kfadli.fdj.data.network.response.LeaguesResponse
import com.kfadli.fdj.data.network.response.TeamResponse
import com.kfadli.fdj.data.network.response.TeamsResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LeaguesRepositoryTestUnit {
    private lateinit var instance: LeaguesRepository
    private val dataSource = mockk<LeaguesApiDataSource>()

    @Before
    fun setUp() {
        instance = LeaguesRepository(dataSource)
    }

    @Test
    fun `ensure getAllLeagues of LeaguesApiDataSource is called as expected`() {
        coEvery { dataSource.getAllLeagues() } answers {
            Result.success(
                LeaguesResponse(
                    listOf(
                        LeagueResponse("1", "League 1", "Soccer", alternate = null),
                        LeagueResponse("2", "League 2", "Soccer", alternate = null),
                    ),
                ),
            )
        }

        runBlocking {
            val result = instance.getAllLeagues().getOrNull()

            coVerify(exactly = 1) {
                dataSource.getAllLeagues()
            }

            val item = result?.get(0)

            assert(item?.id == 1L)
            assert(item?.name == "League 1")
        }
    }

    @Test
    fun `ensure getTeamsByLeague of LeaguesApiDataSource is called as expected`() {
        coEvery { dataSource.getTeamsByLeague("League 1") } answers {
            Result.success(
                TeamsResponse(
                    listOf(
                        TeamResponse(
                            id = "1",
                            name = "CBLOL",
                            sport = "Soccer",
                            badge = "https://my-image.png",
                            description = null,
                            formedYear = null,
                            stadium = null,
                        ),
                        TeamResponse(
                            id = "2",
                            name = "LCK",
                            sport = "Soccer",
                            badge = "https://my-image-2.png",
                            description = null,
                            formedYear = null,
                            stadium = null,
                        ),
                    ),
                ),
            )
        }

        runBlocking {
            val result = instance.getTeamsByLeague("League 1").getOrNull()

            coVerify(exactly = 1) {
                dataSource.getTeamsByLeague("League 1")
            }

            assert(result?.size == 2)

            val item = result?.get(0)

            assert(item?.id == 1L)
            assert(item?.name == "CBLOL")
            assert(item?.badgeUrl == "https://my-image.png")
        }
    }
}
