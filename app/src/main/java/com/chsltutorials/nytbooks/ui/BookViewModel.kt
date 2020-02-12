package com.chsltutorials.nytbooks.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chsltutorials.nytbooks.R
import com.chsltutorials.nytbooks.model.entity.Book
import com.chsltutorials.nytbooks.model.entity.BooksResult
import com.chsltutorials.nytbooks.model.repository.BookRepository
import io.reactivex.disposables.CompositeDisposable
import java.lang.IllegalArgumentException

class BookViewModel(val dataSource: BookRepository) : ViewModel() {

    val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()
    var booksLiveData: MutableLiveData<List<Book>> = MutableLiveData()

    fun fetchBooks() {
        dataSource.fetchBooks { result: BooksResult ->
            when(result){
                is BooksResult.Success -> {
                    booksLiveData.value = result.books
                    viewFlipperLiveData.value = Pair(VIEW_FLIPPER_SUCCESS, null)
                }
                is BooksResult.Failure -> {
                    if (result.statusCode == 401){
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.incorrect_key)
                    }else{
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.server_error)
                    }
                }
                is BooksResult.ServerError -> {
                    viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_without_treatment)
                }
            }
        }
    }

    class ViewModelFactory(val dataSource: BookRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
                return BookViewModel(dataSource) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

    companion object {
        private const val VIEW_FLIPPER_SUCCESS = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }
}

