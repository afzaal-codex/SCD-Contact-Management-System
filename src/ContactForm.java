import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

// contact form class that creates the GUI
public class ContactForm extends JFrame {

    // form text fields
    private JTextField txtId;
    private JTextField txtName;
    private JTextField txtPhone;
    private JTextField txtEmail;
    private JTextField txtAddress;

    // five buttons
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnSearch;
    private JButton btnClear;

    // menu bar items
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem itemSave;
    private JMenuItem itemLoad;
    private JMenuItem itemExit;

    // table to display contacts
    private JTable table;
    private DefaultTableModel tableModel;

    // in-memory list to store contacts
    private ArrayList<Contact> contactList;

    public ContactForm() {
        contactList = new ArrayList<>();

        // frame settings
        setTitle("Contact Management System");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // initialize gui components
        initComponents();

        // display the window
        setVisible(true);
    }

    private void initComponents() {
        // create menu bar
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");

        itemSave = new JMenuItem("Save");
        itemLoad = new JMenuItem("Load");
        itemExit = new JMenuItem("Exit");

        menuFile.add(itemSave);
        menuFile.add(itemLoad);
        menuFile.addSeparator();
        menuFile.add(itemExit);
        menuBar.add(menuFile);
        setJMenuBar(menuBar);

        // create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // create left panel for form inputs and buttons
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setPreferredSize(new Dimension(350, 400));

        // input fields panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 5, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Contact Information"));

        inputPanel.add(new JLabel("ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        inputPanel.add(txtPhone);

        inputPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        inputPanel.add(txtEmail);

        inputPanel.add(new JLabel("Address:"));
        txtAddress = new JTextField();
        inputPanel.add(txtAddress);

        leftPanel.add(inputPanel, BorderLayout.CENTER);

        // buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnSearch = new JButton("Search");
        btnClear = new JButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnClear);

        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        // create right panel for JTable
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Contact List"));

        // columns for table
        String[] columns = {"ID", "Name", "Phone", "Email", "Address"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);

        // close application on exit click
        itemExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // add contact action
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        // update contact action
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateContact();
            }
        });

        // delete contact action
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });

        // clear form action
        btnClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
    }

    // method to add contact
    private void addContact() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String address = txtAddress.getText().trim();

        // create a new contact object
        Contact contact = new Contact(id, name, phone, email, address);

        // add it to the list
        contactList.add(contact);

        // reload the table
        loadTable();

        // reset form inputs
        clearForm();

        // show success message
        JOptionPane.showMessageDialog(this, "Contact added successfully!");
    }

    // method to update contact
    private void updateContact() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ID to update");
            return;
        }

        boolean found = false;
        for (Contact c : contactList) {
            if (c.getId().equals(id)) {
                c.setName(txtName.getText().trim());
                c.setPhone(txtPhone.getText().trim());
                c.setEmail(txtEmail.getText().trim());
                c.setAddress(txtAddress.getText().trim());
                found = true;
                break;
            }
        }

        if (found) {
            // reload table and clear inputs
            loadTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Contact updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found!");
        }
    }

    // method to delete contact
    private void deleteContact() {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter ID to delete");
            return;
        }

        boolean found = false;
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).getId().equals(id)) {
                contactList.remove(i);
                found = true;
                break;
            }
        }

        if (found) {
            loadTable();
            clearForm();
            JOptionPane.showMessageDialog(this, "Contact deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found!");
        }
    }

    // method to load the list data into table
    private void loadTable() {
        tableModel.setRowCount(0);
        for (Contact c : contactList) {
            String[] row = {c.getId(), c.getName(), c.getPhone(), c.getEmail(), c.getAddress()};
            tableModel.addRow(row);
        }
    }

    // method to clear all input fields
    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtAddress.setText("");
    }
}
