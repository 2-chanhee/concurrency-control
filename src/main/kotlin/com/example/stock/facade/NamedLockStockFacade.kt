package com.example.stock.facade

import com.example.stock.repository.LockRepository
import com.example.stock.service.StockService
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class NamedLockStockFacade (val lockRepository: LockRepository, val stockService: StockService){
    @Transactional(propagation = Propagation.REQUIRES_NEW )
    fun decrease(id: Long, quantity: Long){
        try {
            lockRepository.getLock(id.toString())
            stockService.decrease(id, quantity)
        } finally {
            lockRepository.releaseLock(id.toString())
        }
    }
}