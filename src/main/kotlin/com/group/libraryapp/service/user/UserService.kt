package com.group.libraryapp.service.user

import com.group.libraryapp.domain.book.Book
import com.group.libraryapp.domain.book.BookRepository
import com.group.libraryapp.domain.book.BookType
import com.group.libraryapp.domain.user.User
import com.group.libraryapp.domain.user.UserRepository
import com.group.libraryapp.domain.user.loanhistory.UserLoanStatus
import com.group.libraryapp.dto.user.request.UserCreateRequest
import com.group.libraryapp.dto.user.request.UserUpdateRequest
import com.group.libraryapp.dto.user.response.BookHistoryResponse
import com.group.libraryapp.dto.user.response.UserLoanHistoryResponse
import com.group.libraryapp.dto.user.response.UserResponse
import com.group.libraryapp.util.fail
import com.group.libraryapp.util.findByIdOrThrow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
  private val userRepository: UserRepository,
  private val bookRepository: BookRepository,
) {

  @Transactional
  fun saveUserAndLoanTwoBooks() {
    val newUser = User("A", 123)
    val books = bookRepository.saveAll(listOf(Book("책1", BookType.COMPUTER), Book("책2", BookType.COMPUTER)))
    books.forEach { book -> newUser.loanBook(book)} //UserLoanHistory 2개 생성
    userRepository.save(newUser)
  }

  @Transactional
  fun saveUser(request: UserCreateRequest) {
    val newUser = User(request.name, request.age)
    userRepository.save(newUser)
  }

  @Transactional(readOnly = true)
  fun getUsers(): List<UserResponse> {
    return userRepository.findAll()
      .map { user -> UserResponse.of(user) }
  }

  @Transactional
  fun updateUserName(request: UserUpdateRequest) {
    val user = userRepository.findByIdOrThrow(request.id)
    user.updateName(request.name)
  }

  @Transactional
  fun deleteUser(name: String) {
    val user = userRepository.findByName(name) ?: fail()
    userRepository.delete(user)
  }

  @Transactional(readOnly = true)
  fun getUserLoanHistories(): List<UserLoanHistoryResponse> {
    return userRepository.findAllWithHistories()
      .map(UserLoanHistoryResponse::of)
  }

}