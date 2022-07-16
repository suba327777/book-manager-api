package com.book.manager.bookmanager.domain.repositoory

import com.book.manager.bookmanager.domain.model.Rental

interface RentalRepository {
    fun startRental(rental:Rental)
}