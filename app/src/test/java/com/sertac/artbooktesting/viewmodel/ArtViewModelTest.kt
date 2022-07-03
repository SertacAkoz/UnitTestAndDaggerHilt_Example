package com.sertac.artbooktesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.sertac.artbooktesting.MainCoroutineRule
import com.sertac.artbooktesting.getOrAwaitValueTest
import com.sertac.artbooktesting.repo.FakeArtRepo
import com.sertac.artbooktesting.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup(){
        // Test Doubles
        viewModel = ArtViewModel(FakeArtRepo())
    }

    @Test
    fun `insert art without year returns error`(){
        viewModel.makeArt("Mona Lisa", "Da Vinci", "")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without name returns error`(){
        viewModel.makeArt("", "Da Vinci", "1500")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName returns error`(){
        viewModel.makeArt("Mona Lisa", "", "1500")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}