package models;

import java.sql.Timestamp;
import java.sql.Date;

public class BorrowingModel {
  private int id;
  private int member_id;
  private int book_id;
  private Timestamp borrowed_at;
  private Date due_date;
  private Timestamp returned_at;

  // constructor
  public BorrowingModel(int id, int member_id, int book_id, Timestamp borrowed_at, Date due_date,
      Timestamp returned_at) {
    this.id = id;
    this.member_id = member_id;
    this.book_id = book_id;
    this.borrowed_at = borrowed_at;
    this.due_date = due_date;
    this.returned_at = returned_at;
  }

  // getters
  public int getId() {
    return id;
  }

  public int getMember_id() {
    return member_id;
  }

  public int getBook_id() {
    return book_id;
  }

  public Timestamp getBorrowed_at() {
    return borrowed_at;
  }

  public Date getDue_date() {
    return due_date;
  }

  public Timestamp getReturned_at() {
    return returned_at;
  }

  // setters
  public void setId(int id) {
    this.id = id;
  }

  public void setMember_id(int member_id) {
    this.member_id = member_id;
  }

  public void setBook_id(int book_id) {
    this.book_id = book_id;
  }

  public void setBorrowed_at(Timestamp borrowed_at) {
    this.borrowed_at = borrowed_at;
  }

  public void setDue_date(Date due_date) {
    this.due_date = due_date;
  }

  public void setReturned_at(Timestamp returned_at) {
    this.returned_at = returned_at;
  }
}