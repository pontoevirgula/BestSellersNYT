package com.chsltutorials.nytbooks.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chsltutorials.nytbooks.R
import com.chsltutorials.nytbooks.model.entity.Book
import kotlinx.android.synthetic.main.adapter_item_book.view.*

class BookAdapter(val books : List<Book>)
                : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_book, parent, false))
    }
    override fun getItemCount() = books.count()

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
         holder.bind(books[position])
    }


    class BookViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(book: Book) {
             itemView.tvTitle.text = book.title
             itemView.tvAuthor.text = book.author
             itemView.tvDescription.text = book.description
        }

    }
}