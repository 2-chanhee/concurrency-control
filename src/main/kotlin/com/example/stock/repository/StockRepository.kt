package com.example.stock.repository

import com.example.stock.domain.Stock
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import javax.persistence.LockModeType

interface StockRepository: JpaRepository<Stock, Long> {
    /**
     * PESSIMISTIC_WRITE
     * 충돌이 빈번하게 일어난다면 성능이 좋을수도..
     * 디비에 직접 락을 걸기 때문에 안정성 보장
     * 
     * 상황에 따라 성능저하가 있을 수 있음
     */
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Stock s where s.id = :id")
    fun findByPessimisticLock(id: Long): Stock

    /**
     * OPTIMISTIC
     */
    @Lock(value = LockModeType.OPTIMISTIC)
    @Query("select s from Stock s where s.id = :id")
    fun findByOptimisticLock(id: Long): Stock
}