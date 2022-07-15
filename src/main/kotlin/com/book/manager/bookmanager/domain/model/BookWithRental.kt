package com.book.manager.bookmanager.domain.model

//一覧機能で必要な書籍と貸出情報を紐ずけたデータを取得するのに使用
data class BookWithRental(
    val book:Book,
//    データがない場合はnull
    val rental:Rental?,
){
    val isRental:Boolean
        get()=rental!=null
}
