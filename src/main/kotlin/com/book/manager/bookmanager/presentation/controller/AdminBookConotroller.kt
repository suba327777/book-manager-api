package com.book.manager.bookmanager.presentation.controller

import com.book.manager.bookmanager.application.service.AdminBookService
import com.book.manager.bookmanager.domain.model.Book
import com.book.manager.bookmanager.presentation.form.RegisterBookRequest
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("admin/book")
@CrossOrigin(origins = ["http://localhost:8081"], allowCredentials = "true")
class AdminBookConotroller(private val adminBookService: AdminBookService) {
    @PostMapping("/register")
    fun register(@RequestBody request:RegisterBookRequest){
        adminBookService.register(
            Book(
                request.id,
                request.title,
                request.author,
                request.releaseDate
            )
        )
    }
}