package controllers;

import models.MemberModel;
import services.MemberService;

import java.util.List;

public class MemberController {
  private MemberService memberService = new MemberService();

  public void addMember(String name, String email, String phone) {
    MemberModel member = new MemberModel(0, name, email, phone, null);
    memberService.addMember(member);
  }

  public List<MemberModel> getAllMembers() {
    return memberService.getAllMembers();
  }

  public void updateMember(int id, String name, String email, String phone) {
    MemberModel member = new MemberModel(id, name, email, phone, null);
    memberService.updateMember(member);
  }

  public void deleteMember(int id) {
    memberService.deleteMember(id);
  }
}
