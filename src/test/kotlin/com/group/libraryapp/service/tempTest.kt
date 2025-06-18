package com.group.libraryapp.service

import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.service.user.UserService
import com.group.libraryapp.util.TxHelper
import org.assertj.core.api.Assertions
import org.assertj.core.api.PredicateAssert
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
class tempTest @Autowired constructor(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val txHelper: TxHelper,
){

    //@Transactional
    @Test
    @DisplayName("유저 1명과 책 2권을 저장하고 대출한다")
    fun saveLoanTest() {
        // when
        userService.saveUserAndLoanTwoBooks()

        // then
        txHelper.exec {
            val users = userRepository.findAll()
            Assertions.assertThat(users).hasSize(1)
            Assertions.assertThat(users[0].userLoanHistories).hasSize(2)
        }
    }
}