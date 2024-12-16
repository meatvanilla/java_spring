package com.example.mod5.service;

import com.example.mod5.model.Book;
import com.example.mod5.model.Category;
import com.example.mod5.repository.BookRepository;
import com.example.mod5.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Cacheable(value = "bookCache", key = "#title + #author")
    public Book findByTitleAndAuthor(String title, String author) {
        return bookRepository.findByTitleAndAuthor(title, author);
    }

    @Cacheable(value = "categoryCache", key = "#categoryName")
    public List<Book> findByCategoryName(String categoryName) {
        return bookRepository.findByCategoryName(categoryName);
    }

    public Book createBook(Book book) {
        // Create or reuse category
        Category category = categoryRepository.findByName(book.getCategory().getName());
        if (category == null) {
            category = book.getCategory();
            categoryRepository.save(category);
        } else {
            book.setCategory(category);
        }
        return bookRepository.save(book);
    }

    @CacheEvict(value = "bookCache", key = "#book.title + #book.author")
    public Book updateBook(Book book) {
        return bookRepository.save(book);
    }

    @CacheEvict(value = "bookCache", key = "#id")
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }
}