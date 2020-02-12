package com.chsltutorials.nytbooks.bases

import com.chsltutorials.nytbooks.model.repository.BookRepository
import io.reactivex.disposables.CompositeDisposable

abstract class BaseRepository : BookRepository.Presenter {

    protected var compositeDisposable: CompositeDisposable? = null

    override fun onAttach() {
        compositeDisposable = CompositeDisposable()
    }

    override fun onDetach() {
        compositeDisposable?.let { compositeDisposable!!.clear()   }
    }
}