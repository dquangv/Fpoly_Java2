package formModel;

import dataModel.Employee;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author Quang
 */
public class EmpManagement extends javax.swing.JFrame {

    DefaultTableModel tblModel;
    List<Employee> list = new ArrayList<>();
    private static String url = "jdbc:sqlserver://localhost:1433;databaseName=Java2_Employee;user=sa;password=123;encrypt=false;";
    int index = -1;

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }

    /**
     * Creates new form EmpManagement
     */
    public EmpManagement() {
        initComponents();
        setLocationRelativeTo(null);
    }

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

    public void initData() {
        try {
            Connection con = getConnection();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select * from employee");

            while (rs.next()) {
                list.add(new Employee(rs.getString("code"), rs.getString("name"), rs.getInt("age"), rs.getString("email"), rs.getDouble("salary")));
            }
            System.out.println(list);
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

    public void clearForm() {
        txtCode.setText(null);
        txtName.setText(null);
        txtAge.setText(null);
        txtEmail.setText(null);
        txtSalary.setText(null);

        fillTable(list);
        index = -1;

        lblRecord.setText("Record: " + (index + 1) + " of " + list.size());
    }

    public void saveForm() {
        if (index == -1) {
            Employee emp = new Employee(txtCode.getText(), txtName.getText(), Integer.parseInt(txtAge.getText()), txtEmail.getText(), Double.parseDouble(txtSalary.getText()));

            list.add(emp);
            try {
                Connection con = getConnection();
                PreparedStatement stm = con.prepareStatement("insert into employee values (?, ?, ?, ?, ?);");
                stm.setString(1, emp.getCode());
                stm.setString(2, emp.getName());
                stm.setInt(3, emp.getAge());
                stm.setString(4, emp.getEmail());
                stm.setDouble(5, emp.getSalary());
                stm.executeQuery();
            } catch (Exception ex) {
            }

            JOptionPane.showMessageDialog(this, "Added");
        } else {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure to update?", "Confirm", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                Employee emp = list.get(index);

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

                emp.setCode(txtCode.getText());
                emp.setName(txtName.getText());
                emp.setAge(Integer.parseInt(txtAge.getText()));
                emp.setEmail(txtEmail.getText());
                emp.setSalary(Double.parseDouble(txtSalary.getText()));

                JOptionPane.showMessageDialog(this, "Updated");

            }
        }

        fillTable(list);
        clearForm();
    }

    public void deleteEmployee() {
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Please select a staff you want to delete from the table below");
        } else {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure to delete?", "Confirm", JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                try {
                    Connection con = getConnection();
                    PreparedStatement stm = con.prepareStatement("delete from employee where code = ?");
                    stm.setString(1, txtCode.getText());
                    stm.executeQuery();
                } catch (Exception ex) {
                }

                list.remove(index);
                JOptionPane.showMessageDialog(this, "Deleted");
                fillTable(list);
                clearForm();
            }
        }
    }

    public void findEmployee() {
        List<Employee> findList = new ArrayList<>();

        for (Employee emp : list) {
            if (emp.getCode().equals(txtCode.getText())) {
                findList.add(emp);

                txtCode.setText(emp.getCode());
                txtName.setText(emp.getName());
                txtAge.setText(String.valueOf(emp.getAge()));
                txtEmail.setText(emp.getEmail());
                txtSalary.setText(String.valueOf(emp.getSalary()));

                fillTable(findList);
                lblRecord.setText("Record: 1 of 1");
            }
        }

        if (findList.size() == 0) {
            JOptionPane.showMessageDialog(this, "This code was not found");
        }
    }

    private void selectEmp() {
        tblEmployee.setRowSelectionInterval(index, index);
        showDetail();
        lblRecord.setText("Record: " + (index + 1) + " of " + list.size());
    }

    public void firstEmp() {
        index = 0;
        selectEmp();
    }

    public void lastEmp() {
        index = list.size() - 1;
        selectEmp();
    }

    public void preEmp() {
        if (index > 0) {
            index -= 1;
            selectEmp();
        } else if (index == 0) {
            lastEmp();
        }
    }
    
    public void nextEmp() {
        if (index < list.size() - 1 ) {
            index += 1;
            selectEmp();
        } else if (index == list.size() - 1) {
            firstEmp();
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
        jLabel8 = new javax.swing.JLabel();

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

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 51));
        jLabel8.setText("00:00 AM");

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
                                .addComponent(jLabel8))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 669, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel8))
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
        firstEmp();
    }//GEN-LAST:event_btnFirstActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        initData();
        initTalbe();
        fillTable(list);
        lblRecord.setText("Record: " + (index + 1) + " of " + list.size());
    }//GEN-LAST:event_formWindowOpened

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        clearForm();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveForm();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblEmployeeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEmployeeMouseClicked
        showDetail();
    }//GEN-LAST:event_tblEmployeeMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        deleteEmployee();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        findEmployee();
    }//GEN-LAST:event_btnFindActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        lastEmp();
    }//GEN-LAST:event_btnLastActionPerformed

    private void tbnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbnPreviousActionPerformed
        preEmp();
    }//GEN-LAST:event_tbnPreviousActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        nextEmp();
    }//GEN-LAST:event_btnNextActionPerformed

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
            java.util.logging.Logger.getLogger(EmpManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EmpManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EmpManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EmpManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
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
