package ru.demchuk.kino.View

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.get
import androidx.lifecycle.Observer
import org.json.JSONArray
import org.json.JSONObject
import ru.demchuk.kino.R
import ru.demchuk.kino.VM.BinderTopListWithModel
import ru.demchuk.kino.View.adapter.FilmsAdapter


class Top100Films : Activity() {

    private var vm = BinderTopListWithModel()
    private val listTop = ArrayList<Film>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top100_films)
    }

    override fun onStart() {
        super.onStart()
        listenClickOnList()
        try {
            val observer = Observer<String> {
                runOnUiThread {
                    val listTopView = findViewById<ListView>(R.id.listTop)
                    if (!it.equals("error")) {
                        setList(it)
                        val adapter = FilmsAdapter(this, listTop)
                        listTopView.adapter = adapter
                    }else {
                        val listNotFound = ArrayList<String>()
                        listNotFound.add("NOT FOUND")
                        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listNotFound)
                        listTopView.adapter = adapter
                    }
                }
            }
            vm.liveData.observeForever(observer)
            vm.requestList()
        } catch (error: Exception) {
            error.printStackTrace()
        }
    }

    private fun setList(str: String) {
        val jsonObject = JSONObject(str)
        val jsonArray = jsonObject.get("films") as JSONArray
        for (i in 0 until jsonArray.length()) {
            var year: String? = null
            if (jsonArray.getJSONObject(i).get("year") != JSONObject.NULL) {
                year = jsonArray.getJSONObject(i).get("year") as String?
            }
            val film: Film = Film(
                jsonArray.getJSONObject(i)?.get("filmId") as Int?,
                jsonArray.getJSONObject(i)?.get("nameRu") as String?,
                year,
                jsonArray.getJSONObject(i)?.get("posterUrlPreview") as String?
            )
            listTop.add(film)
        }
    }

    private fun listenClickOnList() {
        val listTopView = findViewById<ListView>(R.id.listTop)
        listTopView.onItemClickListener = OnItemClickListener { parent, itemClicked, position, id ->
            val intent = Intent(this, DescriptionFilm::class.java)
            intent.putExtra("filmId", listTop[position].filmId)
            startActivity(intent)
        }
    }
}