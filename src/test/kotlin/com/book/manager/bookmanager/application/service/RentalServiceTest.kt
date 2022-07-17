package com.book.manager.bookmanager.application.service

import com.book.manager.bookmanager.domain.Enum.RoleType
import com.book.manager.bookmanager.domain.model.Book
import com.book.manager.bookmanager.domain.model.BookWithRental
import com.book.manager.bookmanager.domain.model.Rental
import com.book.manager.bookmanager.domain.model.User
import com.book.manager.bookmanager.domain.repositoory.BookRepoository
import com.book.manager.bookmanager.domain.repositoory.RentalRepository
import com.book.manager.bookmanager.domain.repositoory.UserRepository
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

internal class RentalServiceTest{
    private val userRepository = mock<UserRepository>()
    private val bookRepository = mock<BookRepoository>()
    private val rentalRepository = mock<RentalRepository>()

    private  val rentalService =  RentalService(userRepository,bookRepository,rentalRepository)

    //モックオブジェクトの関数呼び出しの検証
    @Test
    fun `endRental when book is rental then delete to rental`(){
        val userId = 100L
        val bookId = 1000L
        val user = User(userId,"test@test.com","pass","kotlin",RoleType.USER)
        val book = Book(bookId,"kotlin","kotlinkun", LocalDate.now())
        val rental = Rental(bookId,userId, LocalDateTime.now(), LocalDateTime.MAX)
        val bookWithRental = BookWithRental(book, rental)

        //渡した引数で該当の関数が実行されたらthenReturnの値を返す
        //any()は全ての値を扱うようにする
        //userRepositoryを例にするとどの値が渡されてもuserを返却する
        whenever(userRepository.find(any() as  Long)).thenReturn(user)
        whenever(bookRepository.findWithRental(any())).thenReturn(bookWithRental)

        rentalService.endRental(bookId,userId)

        //モック化した関数が想定通りの引数を渡して実行されているかの検証
        //userRepositoryを例にするとuserIdの値(100)を渡して実行されているのかの検証
        //timesは実行回数で指定していないとデフォルトで1が指定される　
        verify(userRepository, times(1)).find(userId)
        verify(bookRepository).findWithRental(bookId)
        verify(rentalRepository).endRental(bookId)
    }
    //例外スローの検証
    @Test
    fun `endRental  when book  is not rental then throw exception`(){
        val userId = 100L
        val bookId = 1000L
        val user = User(userId,"test@test.com","pass","kotlin",RoleType.USER)
        val book = Book(bookId,"kotlin","kotlinkun", LocalDate.now())
        val bookWithRental = BookWithRental(book, null)

        //渡した引数で該当の関数が実行されたらthenReturnの値を返す
        //any()は全ての値を扱うようにする
        //userRepositoryを例にするとどの値が渡されてもuserを返却する
        whenever(userRepository.find(any() as  Long)).thenReturn(user)
        whenever(bookRepository.findWithRental(any())).thenReturn(bookWithRental)

        //引数に想定される型の例外を渡し、後ろにラムダ式で例外をthrowする
        //想定通りにthrowされたらスローした値が戻り値として返却
        val exception = Assertions.assertThrows(IllegalStateException::class.java) {
            rentalService.endRental(bookId,userId)
        }

        assertThat(exception.message).isEqualTo("貸出中の商品です bookId:${bookId}")

        verify(userRepository, times(1)).find(userId)
        verify(bookRepository).findWithRental(bookId)
        verify(rentalRepository,times(0)).endRental(any())
    }
}
