package com.book.manager.bookmanager.application.service

import com.book.manager.bookmanager.domain.model.Book
import com.book.manager.bookmanager.domain.model.BookWithRental
import com.book.manager.bookmanager.domain.repositoory.BookRepoository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class BookServiceTest {
    //モック化したオブジェクトを生成
    private val bookRepoository= mock<BookRepoository>()
    // BookServiceのインスタンスを生成
    private val bookService= BookService(bookRepoository)

    @Test
    fun `getList when book list is exist then return list`(){
        val book = Book(1,"kotlin","kotlinkun", LocalDate.now())
        val bookWithRental = BookWithRental(book,null)
        val expected = listOf(bookWithRental)
        //モックオブジェクトが処理する関数と戻り値(thenReturn)の設定
        whenever(bookRepoository.findAllWithRental()).thenReturn(expected)

        //findAllがモックで指定したオブジェクトを返す想定で、getListを実行した結果を検証する
        val result = bookService.getList()
        Assertions.assertThat(expected).isEqualTo(result)
    }
}