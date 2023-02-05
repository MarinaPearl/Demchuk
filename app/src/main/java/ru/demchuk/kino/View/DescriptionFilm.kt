package ru.demchuk.kino.View


import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import org.json.JSONArray
import org.json.JSONObject
import ru.demchuk.kino.R
import ru.demchuk.kino.VM.BinderFilmIdWithModel

class DescriptionFilm : Activity() {

    var vm = BinderFilmIdWithModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description_film)
    }

    override fun onStart() {
        super.onStart()
        try {
            val observer = Observer<String> {
                runOnUiThread {
                    if (!it.equals("error")) {
                        setData(it)
                    }
                }
            }
            vm.liveData.observeForever(observer)
            val arguments = intent.extras
            val name = arguments!!["filmId"].toString()
            vm.requestDescription(name)

        } catch (error: Exception) {
            error.printStackTrace()
        }
    }

    private fun setData(it : String) {
        val jsonObject = JSONObject(it)
        val url = jsonObject.get("posterUrlPreview") as String?
        val description = jsonObject.get("description") as String?
        val jsonArrayGenres = jsonObject.get("genres") as JSONArray
        val jsonArrayCountry = jsonObject.get("countries") as JSONArray
        val uri = Uri.parse(url)
        val imageView = findViewById<ImageView>(R.id.imageView2)
        val descriptionView = findViewById<TextView>(R.id.description)
        val genreView = findViewById<TextView>(R.id.genre)
        val countryView = findViewById<TextView>(R.id.country)
        Glide.with(this)
            .load(uri) // image url
            .placeholder(R.drawable.load) // any placeholder to load at start
            .error(R.drawable.error)  // any image in case of error
            .centerCrop()
            .into(imageView)
        if (description != null) {
            descriptionView.text = description
        }
        if (jsonArrayGenres != JSONObject.NULL) {
            genreView.text = ""
            for (i in 0 until jsonArrayGenres.length()) {
                genreView.append(
                    jsonArrayGenres.getJSONObject(i).get("genre").toString() + "\n"
                )
            }
        }
        if (jsonArrayCountry != JSONObject.NULL) {
            countryView.text = ""
            for (i in 0 until jsonArrayCountry.length()) {
                countryView.append(
                    jsonArrayCountry.getJSONObject(i).get("country").toString() + "\n"
                )
            }
        }
    }
}