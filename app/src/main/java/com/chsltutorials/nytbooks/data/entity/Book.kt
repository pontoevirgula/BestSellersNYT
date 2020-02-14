package com.chsltutorials.nytbooks.data.entity

data class Book(
    val author: String,
    val description: String,
    val title: String
) {
    fun getBookModel() = Book(
        title = title,
        author = author,
        description = description
    )
}