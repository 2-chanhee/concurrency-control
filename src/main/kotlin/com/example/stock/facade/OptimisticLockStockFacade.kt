package com.example.stock.facade

import com.example.stock.service.OptimisticLockStockService
import org.springframework.stereotype.Service

@Service
class OptimisticLockStockFacade (private val optimisticLockStockService: OptimisticLockStockService) {
    /**
     * version을 보고 업데이트하기때문에 별도의 재시도 로직이 필요
     * 충돌이 빈번하게 일어나는 경우는 추천되지 않음
     */
    fun decrease(id: Long, quantity: Long){
        while (true){
            try {
                optimisticLockStockService.decrease(id, quantity)

                break
            } catch (e: Exception) {
                Thread.sleep(50)
            }
        }

    }
}