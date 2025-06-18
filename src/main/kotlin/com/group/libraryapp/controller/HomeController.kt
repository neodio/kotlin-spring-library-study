package com.group.libraryapp.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(
) {

  @GetMapping("/")
  fun index(): String {
    return "/v2/index.html"
  }
}