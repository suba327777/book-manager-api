package com.book.manager.bookmanager.application.service

import com.book.manager.bookmanager.domain.model.BookWithRental
import com.book.manager.bookmanager.domain.repositoory.BookRepoository
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepoository) {
    fun getList():List<BookWithRental>{
        return bookRepository.findAllWithRental()
    }
    fun getDetail(bookId:Long):BookWithRental{
        //書籍の情報を取得し、存在しない場合は例外を投げる(エルビス演算子)
        return bookRepository.findWithRental(bookId)?:throw IllegalArgumentException("存在しない書籍ID:$bookId")
    }
}