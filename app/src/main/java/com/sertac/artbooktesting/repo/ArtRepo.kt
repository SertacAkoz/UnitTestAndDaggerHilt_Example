package com.sertac.artbooktesting.repo

import androidx.lifecycle.LiveData
import com.sertac.artbooktesting.api.ArtAPI
import com.sertac.artbooktesting.model.ImageResponse
import com.sertac.artbooktesting.roomdb.Art
import com.sertac.artbooktesting.roomdb.ArtDao
import com.sertac.artbooktesting.util.Resource
import javax.inject.Inject

class ArtRepo @Inject constructor(
    private val artDao: ArtDao,
    private val artAPI: ArtAPI
) : ArtRepoInterface {
    override suspend fun insertArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = artAPI.imageSearch(imageString)
            if (response.isSuccessful){
                response.body()?.let {
                    println("ArtRepo-Success --> $it")
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            }else{
                Resource.error("Error", null)
            }
        }catch (e:Exception){
            println("Exception-ArtRepo-searchImage --> ${e}")
            Resource.error("No data! --> ${e}", null)
        }
    }
}