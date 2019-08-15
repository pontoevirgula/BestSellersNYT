package com.chsltutorials.nytbooks.bases


import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {


    protected  fun setupToolbar(toolbar: Toolbar, title : Int, snowBackButton : Boolean = false){
        toolbar.title = getString(title)
        setSupportActionBar(toolbar)
        if (snowBackButton) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }
}