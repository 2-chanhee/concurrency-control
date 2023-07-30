package com.example.stock.service

import com.example.stock.domain.Stock
import com.example.stock.repository.StockRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class PessimisticLockStockService (val stockRepository: StockRepository){
    @Transactional
    fun decrease(id: Long, quantity: Long){
        val stock: Stock = stockRepository.findByPessimisticLock(id)

        println(stock)

        stock.decrease(quantity)

        stockRepository.saveAllAndFlush(listOf(stock))
    }

}