package com.kfadli.fdj.domain

import com.kfadli.fdj.data.LeaguesRepository
import com.kfadli.fdj.data.model.Team
import com.kfadli.fdj.domain.models.TeamUI
import com.kfadli.fdj.domain.usecases.GetTeamsByLeagueUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetTeamsByLeagueUnitTest {
    private var leagueRepository = mockk<LeaguesRepository>()
    private lateinit var instance: GetTeamsByLeagueUseCase

    @Before
    fun setUp() {
        coEvery { leagueRepository.getTeamsByLeague(leagueName = "League of Legends") } answers {
            Result.success(
                listOf(
                    Team(1L, "CBLOL", "https://my-image.png"),
                    Team(2L, "LCK", "https://my-image-2.png"),
                    Team(3L, "VCS", "https://my-image-3.png"),
                ),
            )
        }

        instance = GetTeamsByLeagueUseCase(leagueRepository)
    }

    @Test
    fun `ensure getTeamsByLeague of LeagueRepository is called as expected`() {
        runBlocking {
            instance.invoke(leagueName = "League of Legends")

            coVerify(exactly = 1) {
                leagueRepository.getTeamsByLeague(leagueName = "League of Legends")
            }
        }
    }

    @Test
    fun `ensure order returned by usecase is sorted by descendent`() {
        runBlocking {
            val result = instance.invoke("League of Legends")

            assert(result.isSuccess)
            assert(result.getOrNull()?.size == 3)

            asserItem(result.getOrNull()?.get(0), 3L, "VCS")
            asserItem(item = result.getOrNull()?.get(1), id = 2L, name = "LCK")
            asserItem(item = result.getOrNull()?.get(2), id = 1L, name = "CBLOL")
        }
    }

    private fun asserItem(
        item: TeamUI?,
        id: Long,
        name: String,
    ) {
        assert(item?.id == id)
        assert(item?.name == name)
    }
}
