package com.chsltutorials.nytbooks.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BookResponseAPI (

   val copyright : String,

   @SerializedName("results")
   val bookResults : List<BookDetails>,

   val last_modified : String,

   val num_results : Int,

   val status : String
): Serializable