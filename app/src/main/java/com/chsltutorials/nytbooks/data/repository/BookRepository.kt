package com.chsltutorials.nytbooks.data.repository

import com.chsltutorials.nytbooks.data.entity.BooksResult

interface BookRepository {

    fun fetchBooks(booksResultCallback : (result : BooksResult) -> Unit)

    interface Presenter {
        fun onDetach()
        fun onAttach()
    }

}