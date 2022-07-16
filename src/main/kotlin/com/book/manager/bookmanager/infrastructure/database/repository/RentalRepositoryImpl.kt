package com.book.manager.bookmanager.infrastructure.database.repository

import com.book.manager.bookmanager.domain.model.Rental
import com.book.manager.bookmanager.domain.repositoory.RentalRepository
import com.book.manager.bookmanager.infrastructure.database.mapper.RentalMapper
import com.book.manager.bookmanager.infrastructure.database.mapper.deleteByPrimaryKey
import com.book.manager.bookmanager.infrastructure.database.mapper.insert
import com.book.manager.bookmanager.infrastructure.database.record.RentalRecord
import org.springframework.stereotype.Repository

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository

class RentalRepositoryImpl(private val rentalMapper: RentalMapper) :RentalRepository{
    //貸出時の処理でrentalテーブルへのデータを登録している
    override fun startRental(rental: Rental) {
        rentalMapper.insert(toRecord(rental))
    }
    //rental テーブルのレコード削除
    override fun endRental(bookId: Long) {
        rentalMapper.deleteByPrimaryKey(bookId)
    }
    //RentalクラスからRentalRecordクラスへの変換
    private fun toRecord(model:Rental):RentalRecord{
        return RentalRecord(model.userId,model.bookId,model.rentalDatetime,model.returnDeadline)
    }
}