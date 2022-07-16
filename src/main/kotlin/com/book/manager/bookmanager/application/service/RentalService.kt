package com.book.manager.bookmanager.application.service

import com.book.manager.bookmanager.domain.model.Rental
import com.book.manager.bookmanager.domain.repositoory.BookRepoository
import com.book.manager.bookmanager.domain.repositoory.RentalRepository
import com.book.manager.bookmanager.domain.repositoory.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

// 貸出期間
private const val RENTAL_TERM_DAYS = 14L

@Service
class RentalService(
    private val userRepository: UserRepository,
    private val bookRepoository: BookRepoository,
    private val rentalRepository: RentalRepository
) {
    @Transactional
    //対象の書籍IDと貸し出すユーザーIDで貸出情報の登録
    fun startRental(bookId:Long,userId:Long){
        userRepository.find(userId)?:throw IllegalArgumentException("該当するユーザーが存在していません userId:${userId}")
        val book = bookRepoository.findWithRental(bookId)?:throw  IllegalArgumentException("該当する書籍が存在しません bookId:${bookId}")
        //貸出中のチェック
        if(book.isRental)  throw IllegalArgumentException("貸出中の商品です bookId:${bookId}")

        val rentalDateTime = LocalDateTime.now()
        val returnDeadline = rentalDateTime.plusDays(RENTAL_TERM_DAYS)
        val rental = Rental(bookId,userId,rentalDateTime,returnDeadline)

        rentalRepository.startRental(rental)
    }
}