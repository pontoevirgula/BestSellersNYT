package com.chsltutorials.nytbooks.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chsltutorials.nytbooks.R
import com.chsltutorials.nytbooks.model.entity.Book
import com.chsltutorials.nytbooks.model.service.NYTService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.net.HttpURLConnection

class BookViewModel : ViewModel() {

    val viewFlipperLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()
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
                            val book = results.books[0].getBookModel()
                            bookList.add(book)
                        }
                        booksLiveData.value = bookList
                        viewFlipperLiveData.value = Pair(VIEW_FLIPPER_SUCCESS, null)
                    } else {
                        //TODO criar layout pra lista vazia ou  inserir imagem
                        Log.e("LISTA VAZIA", "Lista sem resultados")
                    }

                },
                { error ->
                    val exceptionError = error as HttpException
                    Log.e("CAIU NO ERRO", error.message)
                    when (exceptionError.code()) {
                        401 -> viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.incorrect_key)
                        500 -> viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.server_error)
                        else -> viewFlipperLiveData.value = Pair(VIEW_FLIPPER_ERROR, R.string.error_without_treatment)
                    }
                }
            )

        compositeDisposable!!.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable?.let { compositeDisposable!!.clear() }
    }

    companion object {
        private const val VIEW_FLIPPER_SUCCESS = 1
        private const val VIEW_FLIPPER_ERROR = 2
    }
}

