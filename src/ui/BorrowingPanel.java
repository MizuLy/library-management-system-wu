package ui;

import controllers.BorrowingController;
import controllers.MemberController;
import controllers.BookController;
import models.BorrowingModel;
import models.MemberModel;
import models.BookModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class BorrowingPanel extends JPanel {

  private BorrowingController borrowingController = new BorrowingController();
  private MemberController memberController = new MemberController();
  private BookController bookController = new BookController();
  private DefaultTableModel tableModel;
  private JTable table;

  public BorrowingPanel() {
    setLayout(new BorderLayout(10, 10));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    add(createTopPanel(), BorderLayout.NORTH);
    add(createTablePanel(), BorderLayout.CENTER);

    loadBorrowings();
  }

  private JPanel createTopPanel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));

    JLabel title = new JLabel("Borrowings");
    title.setFont(new Font("Sans Serif", Font.BOLD, 20));

    JButton addBtn = new JButton("+ Add Borrowing");
    addBtn.addActionListener(e -> showAddDialog());

    JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    topRight.add(addBtn);

    panel.add(title, BorderLayout.WEST);
    panel.add(topRight, BorderLayout.EAST);

    return panel;
  }

  private JScrollPane createTablePanel() {
    String[] columns = { "ID", "Member", "Book", "Borrowed At", "Due Date", "Returned At" };
    tableModel = new DefaultTableModel(columns, 0) {
      public boolean isCellEditable(int row, int col) {
        return false;
      }
    };

    table = new JTable(tableModel);
    table.setRowHeight(28);
    table.getTableHeader().setReorderingAllowed(false);

    JPopupMenu menu = new JPopupMenu();
    JMenuItem returnItem = new JMenuItem("Mark Returned");
    JMenuItem deleteItem = new JMenuItem("Delete");

    returnItem.addActionListener(e -> markReturned());
    deleteItem.addActionListener(e -> deleteSelectedBorrowing());

    menu.add(returnItem);
    menu.add(deleteItem);
    table.setComponentPopupMenu(menu);

    return new JScrollPane(table);
  }

  private void loadBorrowings() {
    tableModel.setRowCount(0);
    List<BorrowingModel> borrowings = borrowingController.getAllBorrowings();
    for (BorrowingModel b : borrowings) {
      tableModel.addRow(new Object[] {
          b.getId(),
          b.getMemberName(),
          b.getBookTitle(),
          b.getBorrowed_at(),
          b.getDue_date(),
          b.getReturned_at() != null ? b.getReturned_at() : "Not returned"
      });
    }
  }

  private void showAddDialog() {
    List<MemberModel> members = memberController.getAllMembers();
    JComboBox<String> memberBox = new JComboBox<>();
    for (MemberModel m : members) {
      memberBox.addItem(m.getId() + " - " + m.getName());
    }

    List<BookModel> books = bookController.getAllBooks();
    JComboBox<String> bookBox = new JComboBox<>();
    for (BookModel b : books) {
      bookBox.addItem(b.getId() + " - " + b.getTitle());
    }

    JTextField dueDateField = new JTextField("YYYY-MM-DD");

    Object[] fields = {
        "Member:", memberBox,
        "Book:", bookBox,
        "Due Date:", dueDateField
    };

    int result = JOptionPane.showConfirmDialog(this, fields, "Add Borrowing", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      try {
        int memberId = Integer.parseInt(memberBox.getSelectedItem().toString().split(" - ")[0]);
        int bookId = Integer.parseInt(bookBox.getSelectedItem().toString().split(" - ")[0]);
        Timestamp borrowedAt = new Timestamp(System.currentTimeMillis());
        Date dueDate = Date.valueOf(dueDateField.getText());

        borrowingController.addBorrowing(memberId, bookId, borrowedAt, dueDate, null);
        loadBorrowings();
      } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(this, "Invalid date. Use YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  private void markReturned() {
    int row = table.getSelectedRow();
    if (row == -1)
      return;

    int id = (int) tableModel.getValueAt(row, 0);
    int confirm = JOptionPane.showConfirmDialog(this, "Mark as returned?", "Confirm", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
      borrowingController.markReturned(id);
      loadBorrowings();
    }
  }

  private void deleteSelectedBorrowing() {
    int row = table.getSelectedRow();
    if (row == -1)
      return;

    int id = (int) tableModel.getValueAt(row, 0);
    int confirm = JOptionPane.showConfirmDialog(this, "Delete this borrowing?", "Confirm", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
      borrowingController.deleteBorrowing(id);
      loadBorrowings();
    }
  }
}