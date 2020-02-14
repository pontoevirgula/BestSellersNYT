package com.chsltutorials.nytbooks.data.entity

sealed class BooksResult {
    class Success(val books: List<Book>) : BooksResult()
    class Failure(val statusCode : Int) : BooksResult()
    object ServerError : BooksResult()
}