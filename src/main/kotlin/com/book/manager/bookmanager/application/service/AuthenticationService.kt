package com.book.manager.bookmanager.application.service

import com.book.manager.bookmanager.domain.model.User
import com.book.manager.bookmanager.domain.repositoory.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthenticationService(private val userRepository: UserRepository) {
    fun findUser(email: String): User? {
        return userRepository.find(email)
    }
}