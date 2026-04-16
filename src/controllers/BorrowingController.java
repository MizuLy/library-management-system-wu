package controllers;

import models.BorrowingModel;
import services.BorrowingService;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class BorrowingController {
  private BorrowingService borrowingService = new BorrowingService();

  public void addBorrowing(int member_id, int book_id, Timestamp borrowed_at, Date due_date, Timestamp returned_at) {
    BorrowingModel borrowing = new BorrowingModel(0, member_id, book_id, borrowed_at, due_date, returned_at);
    borrowingService.addBorrowing(borrowing);
  }

  public List<BorrowingModel> getAllBorrowings() {
    return borrowingService.getAllBorrowings();
  }

  public void updateBorrowing(int id, int member_id, int book_id, Timestamp borrowed_at, Date due_date,
      Timestamp returned_at) {
    BorrowingModel borrowing = new BorrowingModel(id, member_id, book_id, borrowed_at, due_date, returned_at);
    borrowingService.updateBorrowing(borrowing);
  }

  public void deleteBorrowing(int id) {
    borrowingService.deleteBorrowing(id);
  }
}