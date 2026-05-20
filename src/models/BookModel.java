package models;

public class BookModel {
  private int id;
  private String title;
  private String author;
  private String genre;

  public BookModel(int id, String title, String author, String genre) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.genre = genre;
  }

  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public String getGenre() {
    return genre;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }
}