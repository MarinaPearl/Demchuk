package ru.demchuk.kino.VM

import androidx.lifecycle.MutableLiveData
import ru.demchuk.kino.Model.URL

class BinderTopListWithModel {
      val liveData = MutableLiveData<String>()

    fun requestList () {
        val url = URL(this)
        url.makeRequest()
    }

    fun listenAnswerWithList(list : String) {
        liveData.postValue(list)
    }
}