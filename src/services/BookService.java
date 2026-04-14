package services;

import config.DBConnection;
import models.BookModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookService {

  public void addBook(BookModel book) {
    String sql = "INSERT INTO books (title, author, genre, quantity) VALUES (?, ?, ?, ?)";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, book.getTitle());
      stmt.setString(2, book.getAuthor());
      stmt.setString(3, book.getGenre());
      stmt.setInt(4, book.getQuantity());
      stmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println("Error adding book: " + e.getMessage());
    }
  }

  public List<BookModel> getAllBooks() {
    List<BookModel> books = new ArrayList<>();
    String sql = "SELECT * FROM books";
    try (Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        books.add(new BookModel(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("genre"),
            rs.getInt("quantity")));
      }

    } catch (SQLException e) {
      System.out.println("Error fetching books: " + e.getMessage());
    }
    return books;
  }

  public void updateBook(BookModel book) {
    String sql = "UPDATE books SET title=?, author=?, genre=?, quantity=? WHERE id=?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, book.getTitle());
      stmt.setString(2, book.getAuthor());
      stmt.setString(3, book.getGenre());
      stmt.setInt(4, book.getQuantity());
      stmt.setInt(5, book.getId());
      stmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println("Error updating book: " + e.getMessage());
    }
  }

  public void deleteBook(int id) {
    String sql = "DELETE FROM books WHERE id=?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, id);
      stmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println("Error deleting book: " + e.getMessage());
    }
  }
}