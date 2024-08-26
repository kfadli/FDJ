package com.kfadli.fdj.domain

import com.kfadli.fdj.data.LeaguesRepository
import com.kfadli.fdj.data.model.League
import com.kfadli.fdj.domain.usecases.GetAllLeaguesUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetAllLeaguesUnitTest {
    private var leagueRepository = mockk<LeaguesRepository>()
    private lateinit var instance: GetAllLeaguesUseCase

    @Before
    fun setUp() {
        instance = GetAllLeaguesUseCase(leagueRepository)
    }

    @Test
    fun `ensure getAllLeagues of LeagueRepository is called as expected`() {
        coEvery { leagueRepository.getAllLeagues() } answers {
            Result.success(
                listOf(
                    League(1L, "League 1"),
                    League(2L, "League 2"),
                ),
            )
        }

        runBlocking {
            val result = instance.invoke()

            coVerify(exactly = 1) {
                leagueRepository.getAllLeagues()
            }
        }
    }

    @Test
    fun `ensure mapping League into LeagueUI is correct`() {
        coEvery { leagueRepository.getAllLeagues() } answers {
            Result.success(
                listOf(
                    League(1L, "League 1"),
                    League(2L, "League 2"),
                ),
            )
        }

        runBlocking {
            val result = instance.invoke()

            assert(result.isSuccess)
            assert(result.getOrNull()?.size == 2)

            val first = result.getOrNull()?.get(0)
            assert(first?.id == 1L)
            assert(first?.name == "League 1")

            val second = result.getOrNull()?.get(1)
            assert(second?.id == 2L)
            assert(second?.name == "League 2")
        }
    }
}
