package com.example.stock.service

import com.example.stock.repository.StockRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class OptimisticLockStockService (val stockRepository: StockRepository){

    @Transactional
    fun decrease(id: Long, quantity: Long){
        val stock = stockRepository.findByOptimisticLock(id)

        stock.decrease(quantity)

        stockRepository.saveAllAndFlush(listOf(stock))
    }
}