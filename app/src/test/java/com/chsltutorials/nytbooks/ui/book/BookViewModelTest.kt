package com.chsltutorials.nytbooks.ui.book

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.chsltutorials.nytbooks.R
import com.chsltutorials.nytbooks.data.entity.Book
import com.chsltutorials.nytbooks.data.entity.BooksResult
import com.chsltutorials.nytbooks.data.repository.BookRepository
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BookViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var booksLiveDataObserver : Observer<List<Book>>

    @Mock
    private lateinit var viewFlipperLiveDataObserver : Observer<Pair<Int,Int?>>

    private lateinit var viewModel: BookViewModel
    private var statusCode : Int? = 0


//    @Before
//    fun setup(){
//        MockitoAnnotations.initMocks(this)
//    }

    @Test
    fun `when view model fetchBooks get success then sets booksLiveData`(){
        //Arrange
        val books = listOf(
            Book("author 1","description 1","title 1")
        )
        val resultSuccess = MockRepository(BooksResult.Success(books))
        viewModel = BookViewModel(resultSuccess)
        viewModel.booksLiveData.observeForever(booksLiveDataObserver)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        //Act
        viewModel.fetchBooks()

        //Assert
        verify(booksLiveDataObserver).onChanged(books)
        //assert(viewModel.booksLiveData.value == books)
        verify(viewFlipperLiveDataObserver).onChanged(Pair(1, null))
    }

    @Test
    fun `when view model fetchBooks get server error then sets viewFlipperLiveData`(){
        // Arrange
        val resultServerError = MockRepository(BooksResult.ServerError)
        viewModel = BookViewModel(resultServerError)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        // Act
        viewModel.fetchBooks()

        // Assert
        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.error_without_treatment))
    }

    @Test
    fun `when view model fetchBooks get failure then sets viewFlipperLiveData`(){
        // Arrange
        statusCode = 401
        val resultFailure = MockRepository(BooksResult.Failure(statusCode!!))
        viewModel = BookViewModel(resultFailure)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        // Act
        viewModel.fetchBooks()

        // Assert
        if(statusCode == 401){
            verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.incorrect_key))
        }else{
            verify(viewFlipperLiveDataObserver).onChanged(Pair(2, R.string.server_error))
        }

    }
}

class MockRepository(private val result: BooksResult) : BookRepository {
    override fun fetchBooks(booksResultCallback: (result: BooksResult) -> Unit) {
        booksResultCallback(result)
    }
}