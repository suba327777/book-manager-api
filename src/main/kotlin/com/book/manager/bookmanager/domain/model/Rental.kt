package com.book.manager.bookmanager.domain.model

import java.time.LocalDateTime

//貸出情報を扱う
data class Rental(
    var bookId:Long,
    var userId:Long,
    val rentalDatetime:LocalDateTime,
    val returnDeadline:LocalDateTime
)
