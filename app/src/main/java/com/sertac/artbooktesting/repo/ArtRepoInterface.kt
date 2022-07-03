package com.sertac.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.sertac.artbooktesting.model.ImageResponse
import com.sertac.artbooktesting.roomdb.Art
import com.sertac.artbooktesting.util.Resource

interface ArtRepoInterface {

    suspend fun insertArt(art:Art)

    suspend fun deleteArt(art:Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun searchImage(imageString: String) : Resource<ImageResponse>

}