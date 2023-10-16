package formModel;

import Interface.IEmpManagement;
import MyException.MyException;
import dataModel.Employee;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Quang
 */
public class EmpManagement extends javax.swing.JFrame implements IEmpManagement, Runnable {

    DefaultTableModel tblModel;
    List<Employee> list = new ArrayList<>();
    int index = -1;
    // default directory when jfilechooser opens
    File defaultDirectory = new File("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java2\\official\\asm\\Asm\\src");
    // url of database (sql server)
    private String url = "jdbc:sqlserver://localhost:1433;databaseName=Java2_Employee;user=sa;password=123;encrypt=false;";
    // identify the opened file is DAT file or not
    boolean isDATFile = true;

    // method connect to sql server
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    /**
     * Creates new form EmpManagement
     */
    public EmpManagement() {
        initComponents();
        setLocationRelativeTo(null);
    }

    // set name of column in model for table
    public void initTalbe() {
        tblModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] cols = new String[]{"MÃ", "HỌ VÀ TÊN", "TUỔI", "EMAIL", "LƯƠNG"};
        tblModel.setColumnIdentifiers(cols);
        tblEmployee.setModel(tblModel);
    }

    // get data
    public void initData() {
        JFileChooser openDialog = new JFileChooser();

        // filter the jFileChooser with DAT and SQL extension
        FileNameExtensionFilter openFileExt = new FileNameExtensionFilter("Database", "dat", "sql");
        openDialog.setFileFilter(openFileExt);

        openDialog.setCurrentDirectory(defaultDirectory);

        if (openDialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = openDialog.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath().toLowerCase();

            if (filePath.endsWith("sql")) {
                isDATFile = false;
                openFileSQL();
                return;
            }

            if (filePath.endsWith("dat")) {
                try {
                    isDATFile = true;
                    openFileDAT(filePath);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex);
                }
                return;
            }

            JOptionPane.showMessageDialog(this, "App just reads file with DAT or SQL extension");
            return;
        }
    }

    public void openFileSQL() {
        try {
            Connection con = getConnection();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select * from employee");

            while (rs.next()) {
                list.add(new Employee(rs.getString("code"), rs.getString("name"), rs.getInt("age"), rs.getString("email"), rs.getDouble("salary")));
            }
        } catch (Exception ex) {
        }
    }

    public void openFileDAT(String path) throws FileNotFoundException, IOException {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
            list = (List<Employee>) ois.readObject();

            ois.close();
        } catch (Exception ex) {
        }
    }

    public void fillTable(List<Employee> list) {
        tblModel.setRowCount(0);

        for (Employee emp : list) {
            tblModel.addRow(new Object[]{emp.getCode(), emp.getName(), emp.getAge(), emp.getEmail(), emp.getSalary()});
        }
    }

    public void showDetail() {
        index = tblEmployee.getSelectedRow();

        txtCode.setText(list.get(index).getCode());
        txtName.setText(list.get(index).getName());
        txtAge.setText(String.valueOf(list.get(index).getAge()));
        txtEmail.setText(list.get(index).getEmail());
        txtSalary.setText(String.valueOf(list.get(index).getSalary()));

        lblRecord.setText("Record: " + (index + 1) + " of " + list.size());
    }

    private void selectEmp() {
        // get only 1 row
        tblEmployee.setRowSelectionInterval(index, index);
        showDetail();
        lblRecord.setText("Record: " + (index + 1) + " of " + list.size());
    }

    @Override
    public void Open() {
        initData();
        fillTable(list);

        // auto fill text field with the 1st object if list is not null
        if (list != null) {
            txtCode.setText(list.get(0).getCode());
            txtName.setText(list.get(0).getName());
            txtAge.setText(String.valueOf(list.get(0).getAge()));
            txtEmail.setText(list.get(0).getEmail());
            txtSalary.setText(String.valueOf(list.get(0).getSalary()));
            lblRecord.setText("Record: 1 of " + list.size());
        } else {
            lblRecord.setText("Record: " + (index + 1) + " of " + list.size());
        }
    }

    @Override
    public void Exit() {
        // if user opened a DAT file or did not open a file, when click the Exit button, app asks for overwriting or saving a new file
        if (isDATFile) {
            int confirmSave = JOptionPane.showConfirmDialog(this, "Do you want to save this to DAT file?");

            if (confirmSave == JOptionPane.YES_OPTION) {
                JFileChooser saveDialog = new JFileChooser();
                saveDialog.setCurrentDirectory(defaultDirectory);

                FileNameExtensionFilter fileSaveExt = new FileNameExtensionFilter("DAT file", "dat");
                saveDialog.setFileFilter(fileSaveExt);

                if (saveDialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = saveDialog.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();

                    if (selectedFile.exists()) {
                        int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to overwrite this file?", "Confirm", JOptionPane.YES_NO_OPTION);

                        // if do not overwrite when user chose the existed file, save a new file
                        if (confirm == JOptionPane.NO_OPTION) {
                            selectedFile = saveToNewFile(saveDialog.getCurrentDirectory());
                            filePath = selectedFile.getAbsolutePath();
                        } else {
                            // if yes option, save to the existed file selected
                            try {
                                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
                                oos.writeObject(list);
                                JOptionPane.showMessageDialog(this, "Saved");
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, ex);
                            }
                        }
                    } else {
                        // if user enter a name of file and it has not existed, app save a new file with extention dat below
                        try {
                            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath + ".dat"));
                            oos.writeObject(list);
                            JOptionPane.showMessageDialog(this, "Saved");
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, ex);
                        }
                    }
                }
            }
        }

        // shut down app after all
        System.exit(0);
    }

    public File saveToNewFile(File currentDirectory) {
        JFileChooser saveDialog = new JFileChooser();

        FileNameExtensionFilter fileSaveExt = new FileNameExtensionFilter("DAT file", "dat");
        saveDialog.setFileFilter(fileSaveExt);

        if (saveDialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = saveDialog.getSelectedFile();

            // in case user choose an existed file again
            if (selectedFile.exists()) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to overwrite this file?", "Confirm", JOptionPane.YES_NO_OPTION);

                // if still not want to overwrite, ask for saving a new file again
                if (confirm == JOptionPane.NO_OPTION) {
                    return saveToNewFile(currentDirectory);
                }
            }

            return selectedFile;
        }

        return null;
    }

    @Override
    public void New() {
        txtCode.setText(null);
        txtName.setText(null);
        txtAge.setText(null);
        txtEmail.setText(null);
        txtSalary.setText(null);

        txtCode.setBackground(Color.white);
        txtName.setBackground(Color.white);
        txtAge.setBackground(Color.white);
        txtEmail.setBackground(Color.white);
        txtSalary.setBackground(Color.white);

        fillTable(list);
        index = -1;
        System.out.println(list.size());
        lblRecord.setText("Record: " + (index + 1) + " of " + list.size());
    }

    @Override
    public void Save() throws MyException {
        // add new object to list if user has not selected an object on the table
        if (index == -1) {
            Employee emp = new Employee();
            try {
                // throw exception if this field is empty
                if (txtCode.getText().equals("")) {
                    txtCode.setBackground(Color.yellow);
                    throw new MyException("Code is empty", "emptyCode");
                } else {
                    // throw ex if data input in this field has been existed in the list
                    if (list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            if (txtCode.getText().equalsIgnoreCase(list.get(i).getCode())) {
                                txtCode.setBackground(Color.yellow);
                                throw new MyException("This code has been existed", "duplicatedCode");
                            }
                        }
                    }

                    txtCode.setBackground(Color.white);
                    // after meet all the conditions above, app check conditions of setter in the Object class
                    emp.setCode(txtCode.getText());
                }

                if (txtName.getText().equals("")) {
                    txtName.setBackground(Color.yellow);
                    throw new MyException("Name is empty", "emptyName");
                } else {
                    txtName.setBackground(Color.white);
                    emp.setName(txtName.getText());
                }

                //throw ex if data input in this field is not a digit
                if (!txtAge.getText().matches("\\d+")) {
                    txtAge.setBackground(Color.yellow);
                    throw new MyException("Age must be a number", "emptyAge");
                } else {
                    txtAge.setBackground(Color.white);
                    emp.setAge(Integer.parseInt(txtAge.getText()));

                }

                if (txtEmail.getText().equals("")) {
                    txtEmail.setBackground(Color.yellow);
                    throw new MyException("Email is empty", "emptyMail");
                } else {
                    txtEmail.setBackground(Color.white);
                    emp.setEmail(txtEmail.getText());
                }

                if (!txtSalary.getText().matches("\\d+")) {
                    txtSalary.setBackground(Color.yellow);
                    throw new MyException("Salary must be a number", "emptySalary");
                } else {
                    txtSalary.setBackground(Color.white);
                    emp.setSalary(Double.parseDouble(txtSalary.getText()));
                }

                list.add(emp);
            } catch (MyException ex) {
                JOptionPane.showMessageDialog(this, ex.getError());

                // turn the color of the filed infringed the condition in the object class
                if (ex.getCodeError().equalsIgnoreCase("name")) {
                    txtName.setBackground(Color.yellow);
                }
                if (ex.getCodeError().equalsIgnoreCase("age")) {
                    txtAge.setBackground(Color.yellow);
                }
                if (ex.getCodeError().equalsIgnoreCase("mail")) {
                    txtEmail.setBackground(Color.yellow);
                }
                if (ex.getCodeError().equalsIgnoreCase("salary")) {
                    txtSalary.setBackground(Color.yellow);
                }
                return;
            }

            //if app was running the SQL file, execute these code below to add a new object to sql server
            if (!isDATFile) {
                try {
                    Connection con = getConnection();

                    // "?" means parameters and they are set below
                    PreparedStatement stm = con.prepareStatement("insert into employee values (?, ?, ?, ?, ?);");

                    // set code of object for the first parameter "?"
                    stm.setString(1, emp.getCode());

                    // the second
                    stm.setString(2, emp.getName());
                    stm.setInt(3, emp.getAge());
                    stm.setString(4, emp.getEmail());
                    stm.setDouble(5, emp.getSalary());
                    stm.executeQuery();
                } catch (Exception ex) {
                }
            }

            JOptionPane.showMessageDialog(this, "Added");
        } else {
            // update data if that data was selected (index not equal -1)
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure to update?", "Confirm", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                Employee emp = list.get(index);

                //if app was running the SQL file, execute these code below to update data of the selected object to sql server
                if (!isDATFile) {
                    try {
                        Connection con = getConnection();
                        PreparedStatement stm = con.prepareStatement("update employee set code = ?, name = ?, age = ?, email = ?, salary = ? where code = ?;");
                        stm.setString(1, txtCode.getText());
                        stm.setString(2, txtName.getText());
                        stm.setInt(3, Integer.parseInt(txtAge.getText()));
                        stm.setString(4, txtEmail.getText());
                        stm.setDouble(5, Double.parseDouble(txtSalary.getText()));
                        stm.setString(6, emp.getCode());
                        stm.executeQuery();

                    } catch (Exception ex) {
                    }
                }

                emp.setCode(txtCode.getText());
                emp.setName(txtName.getText());
                emp.setAge(Integer.parseInt(txtAge.getText()));
                emp.setEmail(txtEmail.getText());
                emp.setSalary(Double.parseDouble(txtSalary.getText()));

                JOptionPane.showMessageDialog(this, "Updated");

            }

        }

        // load the table after having editted
        fillTable(list);

        New();
    }

    // the same with add and update, delete needs an object selected before executing
    @Override
    public void Delete() {
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Please select a staff you want to delete from the table below");
        } else {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure to delete?", "Confirm", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                if (!isDATFile) {
                    try {
                        Connection con = getConnection();
                        PreparedStatement stm = con.prepareStatement("delete from employee where code = ?");
                        stm.setString(1, txtCode.getText());
                        stm.executeQuery();
                    } catch (Exception ex) {
                    }
                }

                list.remove(index);
                JOptionPane.showMessageDialog(this, "Deleted");
                fillTable(list);
                New();
            }
        }
    }

    // find an object based on code name
    @Override
    public void Find() {
        try {
            // create a new list to contain the object found and only show it to table
            List<Employee> findList = new ArrayList<>();

            for (Employee emp : list) {
                if (emp.getCode().equals(txtCode.getText())) {
                    findList.add(emp);

                    txtCode.setText(emp.getCode());
                    txtName.setText(emp.getName());
                    txtAge.setText(String.valueOf(emp.getAge()));
                    txtEmail.setText(emp.getEmail());
                    txtSalary.setText(String.valueOf(emp.getSalary()));

                    // show this list
                    fillTable(findList);
                    lblRecord.setText("Record: 1 of 1");
                }
            }

            // notify if there is not any object having code like the input code
            if (findList.size() == 0) {
                JOptionPane.showMessageDialog(this, "This code was not found");
            }

        } catch (Exception ex) {
        }
    }

    @Override
    public void First() {
        index = 0;
        selectEmp();
    }

    @Override
    public void Pre() {
        if (index > 0) {
            index -= 1;
            selectEmp();
        } else if (index == 0) {
            Last();
        }
    }

    @Override
    public void Next() {
        if (index < list.size() - 1) {
            index += 1;
            selectEmp();
        } else if (index == list.size() - 1) {
            First();
        }
    }

    @Override
    public void Last() {
        index = list.size() - 1;
        selectEmp();
    }

    // a clock run in a new thread
    @Override
    public void run() {
        while (true) {
            // create an instance is the current time
            Date time = new Date();
            
            //format time
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa");

            // set format for the current time and then set for Clock label
            lblClock.setText(dateFormat.format(time));

            try {
                // thread sleeps and wakes up after 1 second
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtAge = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        txtSalary = new javax.swing.JTextField();
        btnFirst = new javax.swing.JButton();
        tbnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmployee = new javax.swing.JTable();
        btnNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnFind = new javax.swing.JButton();
        btnOpen = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        lblRecord = new javax.swing.JLabel();
        lblClock = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("QUẢN LÝ NHÂN VIÊN");

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("MÃ NHÂN VIÊN");

        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("HỌ VÀ TÊN");

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("TUỔI");

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("EMAIL");

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("LƯƠNG");

        txtCode.setForeground(new java.awt.Color(0, 0, 0));
        txtCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodeFocusLost(evt);
            }
        });
        txtCode.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCodeMouseClicked(evt);
            }
        });

        txtName.setForeground(new java.awt.Color(0, 0, 0));

        txtAge.setForeground(new java.awt.Color(0, 0, 0));

        txtEmail.setForeground(new java.awt.Color(0, 0, 0));

        txtSalary.setForeground(new java.awt.Color(0, 0, 0));

        btnFirst.setForeground(new java.awt.Color(0, 0, 0));
        btnFirst.setText("|<");
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        tbnPrevious.setForeground(new java.awt.Color(0, 0, 0));
        tbnPrevious.setText("<<");
        tbnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbnPreviousActionPerformed(evt);
            }
        });

        btnNext.setForeground(new java.awt.Color(0, 0, 0));
        btnNext.setText(">>");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setForeground(new java.awt.Color(0, 0, 0));
        btnLast.setText(">|");
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        jScrollPane2.setForeground(new java.awt.Color(0, 0, 0));

        tblEmployee.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblEmployee.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEmployeeMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblEmployee);

        btnNew.setForeground(new java.awt.Color(0, 0, 0));
        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnSave.setForeground(new java.awt.Color(0, 0, 0));
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setForeground(new java.awt.Color(0, 0, 0));
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnFind.setForeground(new java.awt.Color(0, 0, 0));
        btnFind.setText("Find");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        btnOpen.setForeground(new java.awt.Color(0, 0, 0));
        btnOpen.setText("Open");
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });

        btnExit.setForeground(new java.awt.Color(0, 0, 0));
        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        lblRecord.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblRecord.setForeground(new java.awt.Color(255, 0, 51));
        lblRecord.setText("Record: 1 of 10");

        lblClock.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblClock.setForeground(new java.awt.Color(255, 0, 51));
        lblClock.setText("00:00 AM");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtSalary)
                                .addGap(358, 358, 358)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                    .addComponent(btnNew, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnSave, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnDelete, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnFind, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnOpen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(tbnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblRecord)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1)
                                    .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                                    .addComponent(txtEmail)
                                    .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblClock))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblClock))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNew))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSave))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOpen))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFirst)
                    .addComponent(tbnPrevious)
                    .addComponent(btnNext)
                    .addComponent(btnLast)
                    .addComponent(btnExit)
                    .addComponent(lblRecord))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        First();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        initTalbe();
        lblRecord.setText("Record: " + (index + 1) + " of " + list.size());
        jLabel1.requestFocusInWindow();

        // create a new thread to run parallel with the main
        Thread t1 = new Thread(this);
        t1.start();
    }//GEN-LAST:event_formWindowOpened

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        New();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            Save();
        } catch (MyException ex) {
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmployeeMouseClicked
        showDetail();
    }//GEN-LAST:event_tblEmployeeMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        Delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        Exit();
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        Find();
    }//GEN-LAST:event_btnFindActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        Last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void tbnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbnPreviousActionPerformed
        Pre();
    }//GEN-LAST:event_tbnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        Next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        Open();
    }//GEN-LAST:event_btnOpenActionPerformed

    private void txtCodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodeFocusGained
    }//GEN-LAST:event_txtCodeFocusGained

    private void txtCodeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodeFocusLost
    }//GEN-LAST:event_txtCodeFocusLost

    private void txtCodeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodeMouseClicked
    }//GEN-LAST:event_txtCodeMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EmpManagement.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmpManagement.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmpManagement.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmpManagement.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EmpManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblClock;
    private javax.swing.JLabel lblRecord;
    private javax.swing.JTable tblEmployee;
    private javax.swing.JButton tbnPrevious;
    private javax.swing.JTextField txtAge;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSalary;
    // End of variables declaration//GEN-END:variables

}
