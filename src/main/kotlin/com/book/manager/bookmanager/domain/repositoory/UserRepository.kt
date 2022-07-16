package com.book.manager.bookmanager.domain.repositoory

import com.book.manager.bookmanager.domain.model.User

interface UserRepository {
    fun find(email: String): User?
//    fun find(id: Long): User?
}