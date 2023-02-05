package ru.demchuk.kino.View.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ru.demchuk.kino.R
import ru.demchuk.kino.View.Film
import java.io.InputStream
import java.net.URL


class FilmsAdapter(val context: Context, list: ArrayList<Film>) : BaseAdapter() {

    private var listFilms = ArrayList<Film>()
    private var layoutInflater: LayoutInflater

    init {
        this.listFilms = list
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return listFilms.size
    }

    override fun getItem(p0: Int): Any {
        return listFilms.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View? {
        var view: View? = p1
        if (view == null) {
            view = layoutInflater.inflate(R.layout.film, p2, false)
        }
        setData(view, p0)
        return view
    }

    private fun getFilm(pos: Int): Film {
        return getItem(pos) as Film
    }

    private fun setData(view: View?, p0: Int) {
        val film = getFilm(p0)
        val name = view?.findViewById<TextView>(R.id.nameFilm)
        name?.text = film.nameRu
        val year = view?.findViewById<TextView>(R.id.year)
        year?.text = film.year.toString()
        val poster = view?.findViewById<ImageView>(R.id.imageView)
        val uri = Uri.parse(film.posterUrlPreview)
        if (poster != null) {
            Glide.with(context)
                .load(uri) // image url
                .placeholder(R.drawable.load) // any placeholder to load at start
                .error(R.drawable.error)  // any image in case of error
                .centerCrop()
                .into(poster)
        };
    }
}