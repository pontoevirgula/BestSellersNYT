package com.chsltutorials.nytbooks.model.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BookDetails (
    @SerializedName("book_details")
    val books : List<Book>
) : Serializable
