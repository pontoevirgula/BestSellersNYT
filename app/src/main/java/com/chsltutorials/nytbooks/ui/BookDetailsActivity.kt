package com.chsltutorials.nytbooks.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.chsltutorials.nytbooks.R
import com.chsltutorials.nytbooks.bases.BaseActivity
import kotlinx.android.synthetic.main.activity_book_details.*
import kotlinx.android.synthetic.main.include_toolbar.*

class BookDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        setupToolbar(includeToolbar,R.string.title_toolbar_detail,true)

        intent.let {
            txtTitle.text = it.getStringExtra(EXTRA_TITLE)
            txtDescription.text = it.getStringExtra(EXTRA_DESCRIPTION)
        }
    }


    companion object {
        private const val EXTRA_TITLE = "EXTRA_TITLE"
        private const val EXTRA_DESCRIPTION = "EXTRA_DESCRIPTION"

        fun getStartIntent(context: Context, title: String, description: String): Intent {
            return Intent(context, BookDetailsActivity::class.java).apply {
                putExtra(EXTRA_TITLE, title)
                putExtra(EXTRA_DESCRIPTION, description)
            }
        }
    }
}
