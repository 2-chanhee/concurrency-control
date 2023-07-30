package com.example.stock.facade

import com.example.stock.repository.RedisLockRepository
import com.example.stock.service.StockService
import org.springframework.stereotype.Component

@Component
class LettuceLockStockFacade (val redisLockRepository: RedisLockRepository, val stockService: StockService){
    fun decrease(id: Long, quantity: Long) {
        while (redisLockRepository.lock(id) != true){
            Thread.sleep(100) // 100ms 텀을 두고 락 획득 재시도
        }

        try {
            stockService.decrease(id, quantity)
        } finally {
            redisLockRepository.unlock(id)
        }
    }
}