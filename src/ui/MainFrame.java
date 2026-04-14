package ui;

import javax.swing.*;
// import java.awt.*;

public class MainFrame extends JFrame {

  public MainFrame() {
    setTitle("Library Management System");
    setSize(900, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JTabbedPane tabs = new JTabbedPane();
    tabs.addTab("Books", new BookPanel());
    tabs.addTab("Members", new MemberPanel());
    // tabs.addTab("Borrowings", new BorrowingPanel());

    add(tabs);
    setVisible(true);
  }
}