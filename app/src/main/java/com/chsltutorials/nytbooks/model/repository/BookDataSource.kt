package com.chsltutorials.nytbooks.model.repository

import android.util.Log
import com.chsltutorials.nytbooks.bases.BaseRepository
import com.chsltutorials.nytbooks.model.entity.Book
import com.chsltutorials.nytbooks.model.entity.BooksResult
import com.chsltutorials.nytbooks.model.service.NYTService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class BookDataSource : BookRepository, BaseRepository(){

    override fun fetchBooks(booksResultCallback : (result : BooksResult) -> Unit){
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
                        booksResultCallback(BooksResult.Success(bookList))

                    } else {
                        //TODO criar layout pra lista vazia ou inserir imagem
                        Log.e("LISTA VAZIA", "Lista sem resultados")
                        booksResultCallback(BooksResult.Failure(statusCode = 401))
                    }

                },
                { error ->
                    val exceptionError = error as HttpException
                    Log.e("CAIU NO ERRO", error.message)
                    when (exceptionError.code()) {
                        401 -> booksResultCallback(BooksResult.Failure(statusCode = 401))
                        500 -> booksResultCallback(BooksResult.ServerError)
                        else -> booksResultCallback(BooksResult.ServerError)
                    }
                }
            )

        compositeDisposable?.add(disposable)
    }

}

