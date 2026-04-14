package models;

import java.sql.Timestamp;

public class MemberModel {
  private int id;
  private String name;
  private String email;
  private String phone;
  private Timestamp joined;

  // constructor
  public MemberModel(int id, String name, String email, String phone, Timestamp joined) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.joined = joined;
  }

  // getters
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public Timestamp getJoined() {
    return joined;
  }

  // setters
  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setJoined(Timestamp joined) {
    this.joined = joined;
  }
}
