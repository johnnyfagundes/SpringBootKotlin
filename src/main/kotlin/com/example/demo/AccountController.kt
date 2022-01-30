package com.example.demo

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("accounts")
class AccountController(val repository: AccountRepository) {

    //Estilo JAVA
//    @PostMapping
//    fun create(@RequestBody account: Account): ResponseEntity<Account> {
//        val accountSave = repository.save(account)
//        return ResponseEntity.ok(accountSave)
//    }

    @PostMapping
    fun create(@RequestBody account: Account) = ResponseEntity.ok(repository.save(account))

    @GetMapping
    fun read() = ResponseEntity.ok(repository.findAll())

    @PutMapping("{document}")
    fun update(@PathVariable document: String, @RequestBody account: Account): ResponseEntity<Account> {
        val accountDBOptional = repository.findByDocument(document)
        val toSave = accountDBOptional
            .orElseThrow { RuntimeException("Account document: $document not found") }
            .copy(name = account.name, balance = account.balance)
        return ResponseEntity.ok(repository.save(toSave))
    }

    @DeleteMapping("{document}")
    fun delete(@PathVariable document: String) = repository
        .findByDocument(document)
        .ifPresent { repository.delete(it) }
}
