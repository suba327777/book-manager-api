package com.book.manager.bookmanager.presentation.contrroller

import com.book.manager.bookmanager.application.service.BookService
import com.book.manager.bookmanager.domain.model.Book
import com.book.manager.bookmanager.domain.model.BookWithRental
import com.book.manager.bookmanager.presentation.controller.BookController
import com.book.manager.bookmanager.presentation.form.BookInfo
import com.book.manager.bookmanager.presentation.form.GetBookListResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.nio.charset.StandardCharsets
import java.time.LocalDate

//MockMvcでControllerのtest
internal class BookControllerTest {
    private val bookService = mock<BookService>()
    private val bookController = BookController(bookService)

    @Test
    fun `getList is success`(){
        val bookId = 100L
        val book = Book(bookId,"kotlin","kotlinkun", LocalDate.now())
        val bookList = listOf(BookWithRental(book,null))

        whenever(bookService.getList()).thenReturn(bookList)

        //GetBookListResponseのインスタンスを生成
        val expectResponse = GetBookListResponse(listOf(BookInfo(bookId,"kotlin","kotlinkun",false)))
        //Json文字列に変換
        val expected = ObjectMapper().registerKotlinModule().writeValueAsString(expectResponse)
        // 対象のControllerオブジェクト(bookController)を設定する
        val mockMvc = MockMvcBuilders.standaloneSetup(bookController).build()
        //結果のレスポンスを取得する　
        //perform HTTPメソッド,対象のControllerPath設定 andExpect 期待されるHTTPステータス設定
        //andReturn 結果の返却 response 結果からレスポンスのオブジェクトを取得
        val resultResponse = mockMvc.perform(get("/book/list")).andExpect(status().isOk).andReturn().response
        //レスポンスのオブジェクトをJson文字列に変換
        val result = resultResponse.getContentAsString(StandardCharsets.UTF_8)
        //レスポンスの文字列と比較し結果の検証
        Assertions.assertThat(expected).isEqualTo(result)
    }
}