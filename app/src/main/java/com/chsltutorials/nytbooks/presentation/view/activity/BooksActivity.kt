package com.chsltutorials.nytbooks.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chsltutorials.nytbooks.R
import com.chsltutorials.nytbooks.bases.BaseActivity
import com.chsltutorials.nytbooks.model.entity.Book
import com.chsltutorials.nytbooks.presentation.view.adapter.BookAdapter
import com.chsltutorials.nytbooks.presentation.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.activity_books.*
import kotlinx.android.synthetic.main.include_toolbar.*

class BooksActivity : BaseActivity() {

    private val viewModel by lazy {
          ViewModelProviders.of(this).get(BookViewModel::class.java)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        setupToolbar(toolbarMain,R.string.title_toolbar)

        viewModel.booksLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                with(rvBookList) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    addItemDecoration(DividerItemDecoration(this@BooksActivity, LinearLayoutManager.VERTICAL))
                    adapter = BookAdapter(it) { book ->
                        val intent = BookDetailsActivity.getStartIntent(this@BooksActivity, book.title, book.description)
                        this@BooksActivity.startActivity(intent)
                    }
                }
            }
        })

        viewModel.fetchBooks()

    }


}
