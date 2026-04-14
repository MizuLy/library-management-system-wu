package ui;

import controllers.BookController;
import models.BookModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookPanel extends JPanel {

  private BookController bookController = new BookController();
  private DefaultTableModel tableModel;
  private JTable table;
  private JTextField searchField;

  public BookPanel() {
    setLayout(new BorderLayout(10, 10));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    add(createTopPanel(), BorderLayout.NORTH);
    add(createTablePanel(), BorderLayout.CENTER);

    loadBooks();
  }

  private JPanel createTopPanel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));

    JLabel title = new JLabel("Books");
    title.setFont(new Font("Sans Serif", Font.BOLD, 20));

    JButton addBtn = new JButton("+ Add BookModel");
    addBtn.addActionListener(e -> showAddDialog());

    searchField = new JTextField();
    searchField.setPreferredSize(new Dimension(200, 30));
    searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
      public void insertUpdate(javax.swing.event.DocumentEvent e) {
        filterTable();
      }

      public void removeUpdate(javax.swing.event.DocumentEvent e) {
        filterTable();
      }

      public void changedUpdate(javax.swing.event.DocumentEvent e) {
        filterTable();
      }
    });

    JPanel topRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    topRight.add(new JLabel("Search: "));
    topRight.add(searchField);
    topRight.add(addBtn);

    panel.add(title, BorderLayout.WEST);
    panel.add(topRight, BorderLayout.EAST);

    return panel;
  }

  private JScrollPane createTablePanel() {
    String[] columns = { "ID", "Title", "Author", "Genre", "Quantity" };
    tableModel = new DefaultTableModel(columns, 0) {
      public boolean isCellEditable(int row, int col) {
        return false;
      }
    };

    table = new JTable(tableModel);
    table.setRowHeight(28);
    table.getTableHeader().setReorderingAllowed(false);

    JPopupMenu menu = new JPopupMenu();
    JMenuItem editItem = new JMenuItem("Edit");
    JMenuItem deleteItem = new JMenuItem("Delete");

    editItem.addActionListener(e -> editSelectedBook());
    deleteItem.addActionListener(e -> deleteSelectedBook());

    menu.add(editItem);
    menu.add(deleteItem);
    table.setComponentPopupMenu(menu);

    return new JScrollPane(table);
  }

  private void loadBooks() {
    tableModel.setRowCount(0);
    List<BookModel> books = bookController.getAllBooks();
    for (BookModel b : books) {
      tableModel.addRow(new Object[] { b.getId(), b.getTitle(), b.getAuthor(), b.getGenre(), b.getQuantity() });
    }
  }

  private void filterTable() {
    String query = searchField.getText().toLowerCase();
    tableModel.setRowCount(0);
    List<BookModel> books = bookController.getAllBooks();
    for (BookModel b : books) {
      if (b.getTitle().toLowerCase().contains(query) || b.getAuthor().toLowerCase().contains(query)) {
        tableModel.addRow(new Object[] { b.getId(), b.getTitle(), b.getAuthor(), b.getGenre(), b.getQuantity() });
      }
    }
  }

  private void showAddDialog() {
    JTextField titleField = new JTextField();
    JTextField authorField = new JTextField();
    JTextField genreField = new JTextField();
    JTextField quantityField = new JTextField();

    Object[] fields = {
        "Title:", titleField,
        "Author:", authorField,
        "Genre:", genreField,
        "Quantity:", quantityField
    };

    int result = JOptionPane.showConfirmDialog(this, fields, "Add BookModel", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      bookController.addBook(
          titleField.getText(),
          authorField.getText(),
          genreField.getText(),
          Integer.parseInt(quantityField.getText()));
      loadBooks();
    }
  }

  private void editSelectedBook() {
    int row = table.getSelectedRow();
    if (row == -1)
      return;

    int id = (int) tableModel.getValueAt(row, 0);
    JTextField titleField = new JTextField((String) tableModel.getValueAt(row, 1));
    JTextField authorField = new JTextField((String) tableModel.getValueAt(row, 2));
    JTextField genreField = new JTextField((String) tableModel.getValueAt(row, 3));
    JTextField quantityField = new JTextField(String.valueOf(tableModel.getValueAt(row, 4)));

    Object[] fields = {
        "Title:", titleField,
        "Author:", authorField,
        "Genre:", genreField,
        "Quantity:", quantityField
    };

    int result = JOptionPane.showConfirmDialog(this, fields, "Edit BookModel", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      bookController.updateBook(
          id,
          titleField.getText(),
          authorField.getText(),
          genreField.getText(),
          Integer.parseInt(quantityField.getText()));
      loadBooks();
    }
  }

  private void deleteSelectedBook() {
    int row = table.getSelectedRow();
    if (row == -1)
      return;

    int id = (int) tableModel.getValueAt(row, 0);
    int confirm = JOptionPane.showConfirmDialog(this, "Delete this book?", "Confirm", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
      bookController.deleteBook(id);
      loadBooks();
    }
  }
}