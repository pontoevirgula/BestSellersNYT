package com.chsltutorials.nytbooks.presentation.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chsltutorials.nytbooks.R
import com.chsltutorials.nytbooks.model.entity.Book
import com.chsltutorials.nytbooks.presentation.view.adapter.BookAdapter
import com.chsltutorials.nytbooks.presentation.viewmodel.BookViewModel
import kotlinx.android.synthetic.main.activity_books.*

class BooksActivity : AppCompatActivity() {

//    private val viewModel by lazy {
//        ViewModelProviders.of(this).get(BookViewModel::class.java)
//    }
    lateinit var viewModel : BookViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        setSupportActionBar(toolbarMain)

        viewModel = ViewModelProviders.of(this).get(BookViewModel::class.java)

        viewModel.booksLiveData.observe(this, Observer {
            if (!it.isNullOrEmpty()){
                with(rvBookList) {
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    addItemDecoration(DividerItemDecoration(this@BooksActivity, LinearLayoutManager.VERTICAL))
                    adapter = BookAdapter(it)
                }
            }
        })

        viewModel.fetchBooks()

    }


}
