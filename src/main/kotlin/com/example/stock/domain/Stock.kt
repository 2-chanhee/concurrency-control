package com.example.stock.domain

import java.lang.RuntimeException
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Stock (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", nullable = false)
        val id: Long = 0L,
        @Column(name = "productId")
        val productId: Long,
        @Column(name = "quantity")
        var quantity: Long) : MutableIterable<Any> {
    // 재고 감소
    fun decrease(requestQuantity: Long) {
        if((this.quantity - requestQuantity) < 0) {
            throw RuntimeException("error")
        }

        this.quantity = this.quantity - requestQuantity
    }

    override fun iterator(): MutableIterator<Any> {
        TODO("Not yet implemented")
    }
}