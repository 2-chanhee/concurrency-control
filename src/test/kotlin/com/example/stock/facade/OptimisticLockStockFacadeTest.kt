package com.example.stock.facade

import com.example.stock.domain.Stock
import com.example.stock.repository.StockRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.lang.RuntimeException
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
/**
 * No ParameterResolver registered for parameter
 * 생성자를 통한 의존성 주입 방식을 사용하는 경우, 생성자에 @Autowired 어노테이션을 명시해야 한다.
 */
class OptimisticLockStockFacadeTest @Autowired constructor(
    private val optimisticLockStockFacade: OptimisticLockStockFacade,
    private val stockRepository: StockRepository
){
    @BeforeEach
    fun before() {
        val stock = Stock(productId = 1, quantity = 100)

        stockRepository.save(stock)
    }

    @AfterEach
    fun after() {
        stockRepository.deleteAll()
    }

    @Test
    fun concurrency_100() {
        val threadCount: Int = 100
        // Executors: 비동기 실행 작업을 단순화하여 사용할 수 있게 해줌
        // 최대 32개의 스레드만 동시에 실행될 수 있도록 세팅
        val executorService = Executors.newFixedThreadPool(32)
        val latch = CountDownLatch(threadCount) // 100개의 스레드가 모두 종료될 때까지 기다림

        for (i: Int in 1..threadCount) {
            executorService.submit {
                try {
                    optimisticLockStockFacade.decrease(id = 1, quantity = 1)
                } catch (e: InterruptedException) {
                    throw RuntimeException(e)
                } finally {
                    latch.countDown() // latch의 값을 100에서부터 1씩 감소시킴
                }
            }
        }

        // latch의 값이 0이 될때까지 기다림
        latch.await()

        val stock = stockRepository.findById(1).orElseThrow()

        assertEquals(0, stock.quantity)
    }
}
