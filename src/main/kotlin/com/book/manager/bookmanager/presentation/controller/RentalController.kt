package com.book.manager.bookmanager.presentation.controller

import com.book.manager.bookmanager.application.service.RentalService
import com.book.manager.bookmanager.application.service.security.BookManagerUserDetails
import com.book.manager.bookmanager.presentation.form.RentalStartRequest
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("rental")
@CrossOrigin
class RentalController(private val rentalService: RentalService) {
    @PostMapping("/start")
    fun startRental(@RequestBody request:RentalStartRequest){
        //認証したユーザーの情報が保持されていて、それの取得　principalはオブジェクト型なのでキャスト
        val user = SecurityContextHolder.getContext().authentication.principal as BookManagerUserDetails
        rentalService.startRental(request.bookId,user.id)
    }
    @DeleteMapping("/end/{book_id}")
    fun endRental(@PathVariable("book_id") bookId:Long){
        val user = SecurityContextHolder.getContext().authentication.principal as BookManagerUserDetails
        rentalService.endRental(bookId,user.id)
    }

}