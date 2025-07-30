package com.example.imdbmovies.di

import com.example.imdbmovies.repository.MovieRepository
import com.example.imdbmovies.repository.TMDBApi
import com.example.imdbmovies.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
	
	@Provides
	@Singleton
	fun provideOkHttpClient(): OkHttpClient {
		val logging = HttpLoggingInterceptor()
		logging.level = HttpLoggingInterceptor.Level.BODY
		return OkHttpClient.Builder()
			.addInterceptor(logging)
			.build()
	}
	
	@Provides
	@Singleton
	fun provideTMDBApi(client: OkHttpClient): TMDBApi {
		return Retrofit.Builder()
			.baseUrl(Constants.TMDB_BASE_URL)
			.client(client)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
			.create(TMDBApi::class.java)
	}
	
	@Provides
	@Singleton
	fun provideMovieRepository(api: TMDBApi): MovieRepository = MovieRepository(api)
}