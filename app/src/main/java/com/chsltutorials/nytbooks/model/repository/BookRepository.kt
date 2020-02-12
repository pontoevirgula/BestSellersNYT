package com.chsltutorials.nytbooks.model.repository

import com.chsltutorials.nytbooks.model.entity.BooksResult

interface BookRepository {

    fun fetchBooks(booksResultCallback : (result : BooksResult) -> Unit)

    interface Presenter {
        fun onDetach()
        fun onAttach()
    }

}