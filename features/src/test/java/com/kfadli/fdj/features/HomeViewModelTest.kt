package com.kfadli.fdj.features

import app.cash.turbine.test
import com.kfadli.fdj.domain.models.LeagueUI
import com.kfadli.fdj.domain.models.TeamUI
import com.kfadli.fdj.domain.usecases.GetAllLeaguesUseCase
import com.kfadli.fdj.domain.usecases.GetTeamsByLeagueUseCase
import com.kfadli.fdj.features.screens.home.HomeViewModel
import com.kfadli.fdj.features.screens.home.UIState.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {
    private var getAllLeaguesUseCase = mockk<GetAllLeaguesUseCase>()
    private var getTeamsByLeagueUseCase = mockk<GetTeamsByLeagueUseCase>()

    private val leagues =
        listOf(
            LeagueUI(id = 1L, name = "My custom League"),
            LeagueUI(id = 2L, name = "My custom League 2"),
        )

    private val teams =
        listOf(
            TeamUI(id = 1L, name = "My custom Team", iconUrl = "http://my-custom-icon-url"),
            TeamUI(id = 2L, name = "My custom Team 2", iconUrl = "http://my-custom-icon-url-2"),
        )

    private lateinit var instance: HomeViewModel

    @Before
    fun setUp() {
        instance =
            HomeViewModel(
                getLeaguesUseCase = getAllLeaguesUseCase,
                getTeamsUseCase = getTeamsByLeagueUseCase,
            )
    }

    @Test
    fun `getAllLeaguesUseCase is called as expected when getLeagues is called`() {
        runTest {
            coEvery { getAllLeaguesUseCase.invoke() } returns Result.success(leagues)

            instance.getLeagues()

            coVerify(exactly = 1) {
                getAllLeaguesUseCase.invoke()
            }
        }
    }

    @Test
    fun `state will be set into Success when Result of getAllLeaguesUseCase is Succeeded`() {
        runTest {
            coEvery { getAllLeaguesUseCase.invoke() } returns Result.success(leagues)

            instance.getLeagues()

            instance.state.test {
                val item = awaitItem()

                assert(item is LeagueUISuccess)
                assert((item as LeagueUISuccess).items.size == 2)
            }
        }
    }

    @Test
    fun `state will be set into Failure when Result of getAllLeaguesUseCase is Failed`() {
        runTest {
            coEvery { getAllLeaguesUseCase.invoke() } returns Result.failure(IOException("Unexpected Error"))

            instance.getLeagues()

            instance.state.test {
                val error = awaitItem()

                assert(error is Failure)
                assert((error as Failure).cause.message == "Unexpected Error")

                assert(awaitItem() is Idle)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }

    @Test
    fun `getTeamsByLeagueUseCase is called as expected when getTeams is called`() {
        runTest {
            val leagueName = "League 1"
            coEvery { getTeamsByLeagueUseCase.invoke(leagueName) } returns Result.success(teams)

            instance.getTeams(leagueName)

            coVerify(exactly = 1) {
                getTeamsByLeagueUseCase.invoke(leagueName)
            }
        }
    }

    @Test
    fun `state will be set into Success when Result of getTeamsByLeagueUseCase is Succeeded`() {
        runTest {
            val leagueName = "League 1"
            coEvery { getTeamsByLeagueUseCase.invoke(leagueName) } returns Result.success(teams)

            instance.getTeams(leagueName)

            instance.state.test {
                val loading = awaitItem()
                val item = awaitItem()

                assert(item is TeamsUISuccess)
                assert((item as TeamsUISuccess).items.size == 2)
            }
        }
    }

    @Test
    fun `state will be set into Failure when Result of getTeamsByLeagueUseCase is Failed`() {
        runTest {
            val leagueName = "League 1"

            coEvery { getTeamsByLeagueUseCase.invoke(leagueName) } returns Result.failure(IOException("Unexpected Error"))

            instance.getTeams(leagueName)

            instance.state.test {
                val loading = awaitItem()
                val error = awaitItem()

                assert(error is Failure)
                assert((error as Failure).cause.message == "Unexpected Error")

                assert(awaitItem() is Idle)

                cancelAndIgnoreRemainingEvents()
            }
        }
    }
}
