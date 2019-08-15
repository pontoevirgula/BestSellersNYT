package com.chsltutorials.nytbooks.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chsltutorials.nytbooks.model.entity.Book
import com.chsltutorials.nytbooks.model.service.NYTService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BookViewModel : ViewModel() {


    var booksLiveData: MutableLiveData<List<Book>> = MutableLiveData()
    protected var compositeDisposable: CompositeDisposable? = null

    fun fetchBooks() {
        compositeDisposable = CompositeDisposable()
        val disposable = NYTService.service.getAllBooks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { booksResponse ->
                    if (!booksResponse.bookResults.isNullOrEmpty()) {
                        val bookList: MutableList<Book> = mutableListOf()
                        for (results in booksResponse.bookResults) {
                            val book = Book(
                                title = results.books[0].title,
                                author = results.books[0].author,
                                description = results.books[0].description
                            )
                            bookList.add(book)
                        }
                        booksLiveData.value = bookList
                    } else {
                        Log.e("LISTA VAZIA", "Lista sem resultados")
                    }

                },
                { error ->
                    //Toast.makeText(context, "CAIU NO ERRO: ${error.message}", Toast.LENGTH_SHORT).show()
                    Log.e("CAIU NO ERRO", error.message)
                }
            )

        compositeDisposable!!.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.let { compositeDisposable!!.clear() }
    }
}

