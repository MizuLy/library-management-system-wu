package services;

import config.DBConnection;
import models.MemberModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberService {

  public void addMember(MemberModel member) {
    String sql = "INSERT INTO members (name, email, phone) VALUES (?,?,?)";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, member.getName());
      stmt.setString(2, member.getEmail());
      stmt.setString(3, member.getPhone());
      stmt.executeUpdate();
    } catch (SQLException e) {
      System.out.println("Error adding member: " + e.getMessage());
    }
  }

  public List<MemberModel> getAllMembers() {
    List<MemberModel> members = new ArrayList<>();
    String sql = "SELECT * FROM members";
    try (Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        members.add(new MemberModel(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("phone"),
            rs.getTimestamp("joined")));
      }
    } catch (SQLException e) {
      System.out.println("Error fetching members: " + e.getMessage());
    }
    return members;
  }

  public void updateMember(MemberModel member) {
    String sql = "UPDATE members SET name=?, email=?, phone=? WHERE id=?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setString(1, member.getName());
      stmt.setString(2, member.getEmail());
      stmt.setString(3, member.getPhone());
      stmt.setTimestamp(4, member.getJoined());
      stmt.setInt(5, member.getId());
      stmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println("Error updating member: " + e.getMessage());
    }
  }

  public void deleteMember(int id) {
    String sql = "DELETE FROM members WHERE id = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      stmt.setInt(1, id);
      stmt.executeUpdate();

    } catch (SQLException e) {
      System.out.println("Error deleting member: " + e.getMessage());
    }
  }
}
