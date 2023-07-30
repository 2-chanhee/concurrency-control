package com.example.stock.service

import com.example.stock.domain.Stock
import com.example.stock.repository.StockRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class StockService (val stockRepository: StockRepository) {
    /**
     *  재고 감소
     *  Synchronized - 해당 메소드를 한 스레드에서만 실행할 수 있다.
     *  Synchronized 문제점
     *  - 서버가 한대일 때만 동시성 제어를 보장한다. 멀티서버에서는 보장 못함
     */
    // @Transactional
    @Synchronized
    fun  decrease(id: Long, quantity: Long) {
        val stock: Stock = stockRepository.findById(id).orElseThrow()

        stock.decrease(quantity);

        stockRepository.saveAllAndFlush(listOf(stock))
    }
}