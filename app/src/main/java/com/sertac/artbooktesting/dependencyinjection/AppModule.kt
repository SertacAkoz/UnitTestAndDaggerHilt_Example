package com.sertac.artbooktesting.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sertac.artbooktesting.R
import com.sertac.artbooktesting.api.ArtAPI
import com.sertac.artbooktesting.repo.ArtRepo
import com.sertac.artbooktesting.repo.ArtRepoInterface
import com.sertac.artbooktesting.roomdb.ArtDao
import com.sertac.artbooktesting.roomdb.ArtDatabase
import com.sertac.artbooktesting.util.Util
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        ArtDatabase::class.java,
        "ArtBookDB"
    ).build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectArtAPI(): ArtAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Util.BASE_URL)
            .build()
            .create(ArtAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao: ArtDao, api: ArtAPI) = ArtRepo(dao, api) as ArtRepoInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
        )
}