package com.sertac.artbooktesting.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.sertac.artbooktesting.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@SmallTest
@ExperimentalCoroutinesApi
class ArtDaoTest {

    // Herşey Main Threadde çalışacak
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var dao : ArtDao
    private lateinit var database: ArtDatabase

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),ArtDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.artDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun insertArtTesting() = runBlockingTest {
        val exampleArt = Art("Mona Lisa","Da Vinci", 1700, "test.com",1)
        dao.insertArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValueTest()
        assertThat(list).contains(exampleArt)
    }

    @Test
    fun deleteArtTesting() = runBlockingTest {
        val exampleArt = Art("Mona Lisa","Da Vinci", 1700, "test.com",1)
        dao.deleteArt(exampleArt)

        val list = dao.observeArts().getOrAwaitValueTest()
        assertThat(list).doesNotContain(exampleArt)


    }

}