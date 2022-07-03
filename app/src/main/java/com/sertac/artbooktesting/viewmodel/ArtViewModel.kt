package com.sertac.artbooktesting.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sertac.artbooktesting.model.ImageResponse
import com.sertac.artbooktesting.repo.ArtRepoInterface
import com.sertac.artbooktesting.roomdb.Art
import com.sertac.artbooktesting.util.Resource
import kotlinx.coroutines.launch

class ArtViewModel @ViewModelInject constructor(
    private val repo : ArtRepoInterface
) : ViewModel() {

    // Art Fragment

    val artList = repo.getArt()

    // Image API Fragment

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imageList : LiveData<Resource<ImageResponse>>
        get() = images

    private val selectedImage = MutableLiveData<String>()
    val selectedImageUrl : LiveData<String>
        get() = selectedImage

    // Art Details Fragment

    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertArtMessage : LiveData<Resource<Art>>
        get() = insertArtMsg

    fun resetInsertArtMessage(){
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url : String){
        selectedImage.postValue(url)
    }

    fun deleteArt(art:Art) = viewModelScope.launch{
        repo.deleteArt(art)
    }

    fun insertArt(art:Art) = viewModelScope.launch{
        repo.insertArt(art)
    }

    fun searchForImage(searchString : String){

        if (searchString.isEmpty()){
            return
        }else{
            images.value = Resource.loading(null)

            viewModelScope.launch {
                val response = repo.searchImage(searchString)
                images.value = response
            }
        }

    }

    fun makeArt(name:String, artistName:String, year:String){
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
            insertArtMsg.postValue(Resource.error("Enter namei artist, year", null))
            return
        }

        val yearInt = try {
            year.toInt()
        }catch (e:Exception){
            insertArtMsg.postValue(Resource.error("Year should be number", null))
            return
        }

        val art = Art(name, artistName, yearInt, selectedImage.value ?: "")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }
}