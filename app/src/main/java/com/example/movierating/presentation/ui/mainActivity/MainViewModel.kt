package com.example.movierating.presentation.ui.mainActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.movierating.data.internet.MovieResult
import com.example.movierating.data.repositoriesImpl.MovieRatingRepository
import com.example.movierating.data.repositoriesImpl.MovieRatingRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor (application: Application) : AndroidViewModel(application) {
    private val movieRepository: MovieRatingRepository = MovieRatingRepositoryImpl()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    fun loadData() {
        coroutineScope.launch {
            movieRepository.loadData()
        }
    }

    fun getMoviesData(): LiveData<List<MovieResult>> {
        return movieRepository.getMoviesData()
    }

}
