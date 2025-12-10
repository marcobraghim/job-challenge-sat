package br.restful_one.home.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

  @GetMapping("/")
  @CrossOrigin(origins = "*", maxAge = 3600)
  public String index() {
    return "Silence is golden!";
  }
}