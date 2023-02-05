package ru.demchuk.kino.Model

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.demchuk.kino.VM.BinderFilmIdWithModel
import java.io.InputStream
import java.net.HttpURLConnection
import java.util.*

class RequestFilm (val vm : BinderFilmIdWithModel, val filmId : String){

    private val KEY = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b"

    fun makeRequest() {
        GlobalScope.launch {
            try {
                    val url =
                        java.net.URL("https://kinopoiskapiunofficial.tech/api/v2.2/films/$filmId")
                    val httpConn: HttpURLConnection = url.openConnection() as HttpURLConnection
                    httpConn.requestMethod = "GET"

                    httpConn.setRequestProperty("accept", "application/json")
                    httpConn.setRequestProperty("X-API-KEY", KEY)

                    val responseStream: InputStream =
                        if (httpConn.getResponseCode() / 100 === 2) httpConn.inputStream else httpConn.errorStream
                    val s: Scanner = Scanner(responseStream).useDelimiter("\\A")
                    val response = if (s.hasNext()) s.next() else ""
                    vm.listenAnswerWithDescription(response)
            } catch (error: Exception) {
                vm.listenAnswerWithDescription("error")
                error.printStackTrace()
            }
        }
    }
}