package com.book.manager.bookmanager.application.service

import com.book.manager.bookmanager.domain.model.Book
import com.book.manager.bookmanager.domain.repositoory.BookRepoository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class AdminBookService(private val bookRepoository: BookRepoository) {
    @Transactional
    fun register(book:Book){
        //登録しようとしているIDデータが既に存在していた場合例外を投げる
        bookRepoository.findWithRental(book.id)?.let { throw java.lang.IllegalArgumentException("既に存在する書籍ID:${book.id}") }
        //なかった場合はbookをそのまま渡している
        bookRepoository.register(book)
    }
    @Transactional
    fun update(bookId:Long,title:String?,author:String?,releaseDate:LocalDate?){
        //書籍情報を検索し、存在しなかった場合は例外を渡す
        bookRepoository.findWithRental(bookId)?:throw IllegalArgumentException("存在しない書籍ID:${bookId}")
        //存在する場合はupdate実行
        bookRepoository.update(bookId,title,author,releaseDate)
    }
    @Transactional
    fun delete(bookId: Long){
        //書籍情報を検索し、存在しなかった場合は例外
        bookRepoository.findWithRental(bookId)?:throw IllegalArgumentException("存在しない書籍ID:${bookId}")
        //存在する場合はdelete実行
        bookRepoository.delete(bookId)
    }
}