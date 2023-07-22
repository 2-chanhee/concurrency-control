package com.example.stock.service

import com.example.stock.domain.Stock
import com.example.stock.repository.StockRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class StockService (val stockRepository: StockRepository) {
    // 재고 감소
    @Transactional
    fun decrease(id: Long, quantity: Long) {
        val stock: Stock = stockRepository.findById(id).orElseThrow()

        stock.decrease(quantity);

        stockRepository.saveAllAndFlush(listOf(stock))
    }
}