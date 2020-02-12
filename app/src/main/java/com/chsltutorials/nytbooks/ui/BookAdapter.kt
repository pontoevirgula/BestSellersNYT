package com.chsltutorials.nytbooks.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chsltutorials.nytbooks.R
import com.chsltutorials.nytbooks.model.entity.Book
import kotlinx.android.synthetic.main.adapter_item_book.view.*

class BookAdapter(
    private val books: List<Book>,
    private val onItemClick: (book: Book) -> Unit
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_book, parent, false)
        return BookViewHolder(view, onItemClick)
    }

    override fun getItemCount() = books.count()

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) = holder.bind(books[position])


    class BookViewHolder(
        view: View,
        private val onItemClick: (book: Book) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(book: Book) {
            itemView.tvTitle.text = book.title
            itemView.tvAuthor.text = book.author
            itemView.setOnClickListener { onItemClick.invoke(book) }
        }

    }
}