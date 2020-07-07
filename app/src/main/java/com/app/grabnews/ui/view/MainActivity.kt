package com.app.grabnews.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.grabnews.R
import com.app.grabnews.util.ManageFragment
import weather.android.com.view.NewsListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ManageFragment.addFrag(this, NewsListFragment(), "")
    }
}