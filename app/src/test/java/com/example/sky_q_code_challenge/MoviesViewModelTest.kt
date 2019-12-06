package com.example.sky_q_code_challenge

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.sky_q_code_challenge.data.Movie
import com.example.sky_q_code_challenge.data.MovieResponse
import com.example.sky_q_code_challenge.enums.ProcessStep
import com.example.sky_q_code_challenge.misc.Preferences
import com.example.sky_q_code_challenge.network.ApiClient
import com.example.sky_q_code_challenge.viewModel.MoviesViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.BlockJUnit4ClassRunner

@RunWith(BlockJUnit4ClassRunner::class)
class MoviesViewModelTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()


    @MockK
    lateinit var apiClient: ApiClient

    @MockK
    lateinit var preferences: Preferences

    private lateinit var viewModel: MoviesViewModel

    private val movieResponse =
        MovieResponse(listOf(Movie(1, "Demo", 2019, "History", "demo.com/jpg")))

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = MoviesViewModel(apiClient, preferences)

        every { preferences.clearPref() } returns Unit
        every { preferences.setMoviesData(movieResponse) } returns true
        every { preferences.setTimerForCache() } returns Unit
    }

    @Test
    fun noCachedDataLoadDataFromNetworkSuccess_shouldStoreInPrefAndSetMutableLiveData() {
        //when
        every { preferences.getExpiredTime() } returns -1
        every { apiClient.callApi() } returns Single.just(movieResponse)

        //then
        viewModel.getMoviesData()

        //verify
        verify { preferences.clearPref() }
        verify { preferences.setMoviesData(movieResponse) }
        verify { preferences.setTimerForCache() }

        Assert.assertEquals(movieResponse.data, viewModel.moviesLiveData.value)
        Assert.assertEquals(ProcessStep.SUCCESS, viewModel.stepLiveData.value)

    }


    @Test
    fun noCachedDataLoadDataFromNetworkFailure_shouldNotStoreInPrefAndNotSetMutableLiveData() {
        //when
        every { preferences.getExpiredTime() } returns -1
        every { apiClient.callApi() } returns Single.error(RuntimeException("My Dummy Error Message"))

        //then
        viewModel.getMoviesData()

        //verify
        verify { preferences.clearPref() }
        verify(exactly = 0) { preferences.setMoviesData(movieResponse) }
        verify(exactly = 0) { preferences.setTimerForCache() }

        Assert.assertEquals(null, viewModel.moviesLiveData.value)
        Assert.assertEquals(ProcessStep.FAILURE, viewModel.stepLiveData.value)
        Assert.assertEquals("My Dummy Error Message", viewModel.errorLiveData.value)

    }


    @Test
    fun cachedData_DoNotLoadFromNetwork() {
        //when
        every { preferences.getExpiredTime() } returns System.currentTimeMillis() + 10000
        every { preferences.getSavedMoviesData() } returns movieResponse


        //then
        viewModel.getMoviesData()

        //verify
        verify(exactly = 0) { preferences.clearPref() }
        verify(exactly = 0) { preferences.setMoviesData(movieResponse) }
        verify(exactly = 0) { preferences.setTimerForCache() }

        Assert.assertEquals(movieResponse.data, viewModel.moviesLiveData.value)
        Assert.assertEquals(ProcessStep.SUCCESS, viewModel.stepLiveData.value)

    }

}