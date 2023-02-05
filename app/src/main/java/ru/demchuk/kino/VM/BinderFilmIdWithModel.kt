package ru.demchuk.kino.VM

import androidx.lifecycle.MutableLiveData
import ru.demchuk.kino.Model.RequestFilm

class BinderFilmIdWithModel {
    val liveData = MutableLiveData<String>()

    fun requestDescription(filmId: String) {
        val requestFilm = RequestFilm(this, filmId)
        requestFilm.makeRequest()
    }

    fun listenAnswerWithDescription(description : String) {
        liveData.postValue(description)
    }
}