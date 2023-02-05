package ru.demchuk.kino.View

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import ru.demchuk.kino.R

class MainActivity : Activity() {

    private var orientation : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        orientation = Configuration.ORIENTATION_PORTRAIT
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        orientation = newConfig.orientation
        setBackgroundImage(newConfig.orientation)
    }

    @SuppressLint("ResourceType")
    private fun setBackgroundImage(orientation : Int) {
        val layout : LinearLayout = findViewById(R.id.main)
        when (orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> layout.setBackgroundResource(R.drawable.menu2)
            Configuration.ORIENTATION_PORTRAIT -> layout.setBackgroundResource(R.drawable.menu)
        }

    }

    override fun onResume() {
        super.onResume()
        setBackgroundImage(orientation)
    }

    fun onClickStart(view: View) {
        val intent = Intent(this, Top100Films::class.java)
        startActivity(intent)
    }
}