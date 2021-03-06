package com.book.manager.bookmanager.domain.model


import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

internal class BookWithRentalTest {
    @Test
    fun `isRental when rental is null then return false`(){
        val book = Book(1,"kotlin","kotlinkun", LocalDate.now())
        val bookWithRental = BookWithRental(book,null)
        Assertions.assertThat(bookWithRental.isRental).isEqualTo(false)
    }
    @Test
    fun `isRental when rental is  not null then return true`(){
        val book = Book(1,"kotlin","kotlinkun", LocalDate.now())
        val rental = Rental(1,100, LocalDateTime.now(),LocalDateTime.MAX)
        val bookWithRental = BookWithRental(book,rental)
        Assertions.assertThat(bookWithRental.isRental).isEqualTo(true)
    }
}