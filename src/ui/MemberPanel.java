package ui;

import controllers.MemberController;
import models.MemberModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MemberPanel extends JPanel {

  private MemberController memberController = new MemberController();
  private DefaultTableModel tableModel;
  private JTable table;
  private JTextField searchField;

  public MemberPanel() {
    setLayout(new BorderLayout(10, 10));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    add(createTopPanel(), BorderLayout.NORTH);
    add(createTablePanel(), BorderLayout.CENTER);

    loadMembers();
  }

  private JPanel createTopPanel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));

    JLabel title = new JLabel("Members");
    title.setFont(new Font("Sans Serif", Font.BOLD, 20));

    JButton addBtn = new JButton("+ Add Member");
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
    String[] columns = { "ID", "Name", "Email", "Phone", "Joined" };
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

    editItem.addActionListener(e -> editSelectedMember());
    deleteItem.addActionListener(e -> deleteSelectedMember());

    menu.add(editItem);
    menu.add(deleteItem);
    table.setComponentPopupMenu(menu);

    return new JScrollPane(table);
  }

  private void loadMembers() {
    tableModel.setRowCount(0);
    List<MemberModel> members = memberController.getAllMembers();
    for (MemberModel m : members) {
      tableModel.addRow(new Object[] { m.getId(), m.getName(), m.getEmail(), m.getPhone(), m.getJoined() });
    }
  }

  private void filterTable() {
    String query = searchField.getText().toLowerCase();
    tableModel.setRowCount(0);
    List<MemberModel> members = memberController.getAllMembers();
    for (MemberModel m : members) {
      if (m.getName().toLowerCase().contains(query) || m.getEmail().toLowerCase().contains(query)) {
        tableModel.addRow(new Object[] { m.getId(), m.getName(), m.getEmail(), m.getPhone(), m.getJoined() });
      }
    }
  }

  private void showAddDialog() {
    JTextField nameField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField phoneField = new JTextField();

    Object[] fields = {
        "Name:", nameField,
        "Email:", emailField,
        "Phone:", phoneField
    };

    int result = JOptionPane.showConfirmDialog(this, fields, "Add Member", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      memberController.addMember(
          nameField.getText(),
          emailField.getText(),
          phoneField.getText());
      loadMembers();
    }
  }

  private void editSelectedMember() {
    int row = table.getSelectedRow();
    if (row == -1)
      return;

    int id = (int) tableModel.getValueAt(row, 0);
    JTextField nameField = new JTextField((String) tableModel.getValueAt(row, 1));
    JTextField emailField = new JTextField((String) tableModel.getValueAt(row, 2));
    JTextField phoneField = new JTextField((String) tableModel.getValueAt(row, 3));

    Object[] fields = {
        "Name:", nameField,
        "Email:", emailField,
        "Phone:", phoneField
    };

    int result = JOptionPane.showConfirmDialog(this, fields, "Edit Member", JOptionPane.OK_CANCEL_OPTION);
    if (result == JOptionPane.OK_OPTION) {
      memberController.updateMember(
          id,
          nameField.getText(),
          emailField.getText(),
          phoneField.getText());
      loadMembers();
    }
  }

  private void deleteSelectedMember() {
    int row = table.getSelectedRow();
    if (row == -1)
      return;

    int id = (int) tableModel.getValueAt(row, 0);
    int confirm = JOptionPane.showConfirmDialog(this, "Delete this member?", "Confirm", JOptionPane.YES_NO_OPTION);
    if (confirm == JOptionPane.YES_OPTION) {
      memberController.deleteMember(id);
      loadMembers();
    }
  }
}