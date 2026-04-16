package services;

import config.DBConnection;
import models.BorrowingModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowingService {

  public void addBorrowing(BorrowingModel borrowing) {
    String sql = "INSERT INTO borrowings (member_id, book_id, borrowed_at, due_date, returned_at) VALUES (?,?,?,?,?)";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, borrowing.getMember_id());
      stmt.setInt(2, borrowing.getBook_id());
      stmt.setTimestamp(3, borrowing.getBorrowed_at());
      stmt.setDate(4, borrowing.getDue_date());
      stmt.setTimestamp(5, borrowing.getReturned_at());
      stmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println("Error adding borrowing: " + e.getMessage());
    }
  }

  public List<BorrowingModel> getAllBorrowings() {
    List<BorrowingModel> borrowings = new ArrayList<>();
    String sql = "SELECT b.id, m.name AS member_name, bk.title AS book_title, " +
        "b.borrowed_at, b.due_date, b.returned_at " +
        "FROM borrowings b " +
        "JOIN members m ON b.member_id = m.id " +
        "JOIN books bk ON b.book_id = bk.id";
    try (Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        borrowings.add(new BorrowingModel(
            rs.getInt("id"),
            rs.getString("member_name"),
            rs.getString("book_title"),
            rs.getTimestamp("borrowed_at"),
            rs.getDate("due_date"),
            rs.getTimestamp("returned_at")));
      }
    } catch (SQLException e) {
      System.out.println("Error fetching borrowings: " + e.getMessage());
    }
    return borrowings;
  }

  public void updateBorrowing(BorrowingModel borrowing) {
    String sql = "UPDATE borrowings SET member_id=?, book_id=?, borrowed_at=?, due_date=?, returned_at=? WHERE id=?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, borrowing.getMember_id());
      stmt.setInt(2, borrowing.getBook_id());
      stmt.setTimestamp(3, borrowing.getBorrowed_at());
      stmt.setDate(4, borrowing.getDue_date());
      stmt.setTimestamp(5, borrowing.getReturned_at());
      stmt.setInt(6, borrowing.getId());
      stmt.executeUpdate();

    } catch (Exception e) {
      System.out.println("Error updating borrowings: " + e.getMessage());
    }
  }

  public void deleteBorrowing(int id) {
    String sql = "DELETE FROM borrowings WHERE id=?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, id);
      stmt.executeUpdate();

    } catch (Exception e) {
      System.out.println("Error deleting borrowings: " + e.getMessage());
    }
  }
}