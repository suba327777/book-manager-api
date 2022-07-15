package com.book.manager.bookmanager.infrastructure.database.repository

import com.book.manager.bookmanager.domain.model.Book
import com.book.manager.bookmanager.domain.model.BookWithRental
import com.book.manager.bookmanager.domain.model.Rental
import com.book.manager.bookmanager.domain.repositoory.BookRepoository
import com.book.manager.bookmanager.infrastructure.database.mapper.BookMapper
import com.book.manager.bookmanager.infrastructure.database.mapper.custom.BookWithRentalMapper
import com.book.manager.bookmanager.infrastructure.database.mapper.custom.select
import com.book.manager.bookmanager.infrastructure.database.mapper.custom.selectByPrimaryKey
import com.book.manager.bookmanager.infrastructure.database.record.custom.BookWithRentalRecord
import com.book.manager.bookmanager.infrastructure.database.mapper.insert
import com.book.manager.bookmanager.infrastructure.database.mapper.updateByPrimaryKeySelective
import com.book.manager.bookmanager.infrastructure.database.record.BookRecord
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository
class BookRepositoryImpl(
     private val bookWithRentalMapper : BookWithRentalMapper,
     private val bookMapper:BookMapper
):BookRepoository{

     override fun findAllWithRental(): List<BookWithRental> {
          //データを取得し、RecordクラスをmapでBookWithRentalクラスで変換した値を返却
          return bookWithRentalMapper.select().map { toModel(it) }
     }
     //データを取得した場合はbookに変換
     override fun findWithRental(id: Long): BookWithRental? {
          //データを取得できなかった場合はnullを返却
          return bookWithRentalMapper.selectByPrimaryKey(id)?.let { toModel(it) }
     }

     override fun register(book: Book) {
          bookMapper.insert(toRecord(book))
     }
     //?となっているのは更新が必要な場所を自由に設定できるようにするため
     //nullが入ってきたカラムは更新されない
     override fun update(id: Long, title: String?, author: String?, releaseDate: LocalDate?) {
          bookMapper.updateByPrimaryKeySelective(BookRecord(id,title,author,releaseDate))
     }
     private fun toModel(record: BookWithRentalRecord): BookWithRental {
          val book = Book(
               record.id!!,
               record.title!!,
               record.author!!,
               record.releaseDate!!
          )
          val rental = record.userId?.let {
               Rental(
                    record.id!!,
                    record.userId!!,
                    record.rentalDateTime!!,
                    record.returnDeadline!!
               )
          }
          return BookWithRental(book, rental)
     }
     //Book(model)クラスからBookRecord(テーブルのカラム)クラスに変換
     private fun toRecord(model:Book): BookRecord {
          return BookRecord(model.id,model.title,model.author,model.releaseDate)
     }
}
