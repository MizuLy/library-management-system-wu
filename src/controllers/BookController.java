package controllers;

import models.BookModel;
import services.BookService;
import java.util.List;

public class BookController {
  private BookService bookService = new BookService();

  public void addBook(String title, String author, String genre, int quantity) {
    BookModel book = new BookModel(0, title, author, genre, quantity);
    bookService.addBook(book);
  }

  public List<BookModel> getAllBooks() {
    return bookService.getAllBooks();
  }

  public void updateBook(int id, String title, String author, String genre, int quantity) {
    BookModel book = new BookModel(id, title, author, genre, quantity);
    bookService.updateBook(book);
  }

  public void deleteBook(int id) {
    bookService.deleteBook(id);
  }

  public void decreaseQuantity(int bookId) {
    bookService.decreaseQuantity(bookId);
  }

  public void increaseQuantity(int bookId) {
    bookService.increaseQuantity(bookId);
  }
}