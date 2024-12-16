package com.example.mod5.controller;

import com.example.mod5.model.Book;
import com.example.mod5.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/find")
    public ResponseEntity<Book> findByTitleAndAuthor(@RequestParam String title, @RequestParam String author) {
        System.out.println(title + " " + author);
        return ResponseEntity.ok(bookService.findByTitleAndAuthor(title, author));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Book>> findByCategoryName(@PathVariable String categoryName) {
        return ResponseEntity.ok(bookService.findByCategoryName(categoryName));
    }

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.createBook(book));
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAllBooks() {
        return ResponseEntity.ok(bookService.findAllBooks());
    }
}