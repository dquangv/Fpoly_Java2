/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lab3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lab3.dataModel.Student;

/**
 *
 * @author Quang
 */
public class StudentManage extends javax.swing.JFrame {

    //tạo ds chứa thông tin sv
    private List<Student> list = new ArrayList<>();
    //tạo đối tượng quản lý dữ liệu trong bảng
    private DefaultTableModel tblModel;

    /**
     * Creates new form StudentManage
     */
    public StudentManage() {
        initComponents();
        //thiết lập vị trí cửa sổ hiện thị ở giữa màn hình
        setLocationRelativeTo(null);
    }

    //tạo đối tượng chứa dữ liệu rồi thêm vào ds
    public void initData() {
        list.add(new Student("Quang", 9.7, "Phát triển phần mềm"));
        list.add(new Student("Long", 7, "Thiết kế đồ hoạ"));
        list.add(new Student("Nhân", 3, "Lập trình Web"));
        list.add(new Student("Độ", 6, "Ứng dụng phần mềm"));
        list.add(new Student("Thông", 8.5, "Xử lý dữ liệu"));
    }

    //tạo và thiết lập ds cho ComboBox
    public void initMajor() {
        String[] major = new String[]{"Ứng dụng phần mềm", "Phát triển phần mềm", "Lập trình Web", "Xử lý dữ liệu", "Thiết kế đồ hoạ"};
        DefaultComboBoxModel<String> boxModel = new DefaultComboBoxModel<String>(major);

        cboMajor.setModel(boxModel);
    }

    //tạo và thiết lập ds cột tiêu đề cho bảng
    public void initTable() {
        tblModel = (DefaultTableModel) tblStudent.getModel();
        String[] cols = new String[]{"HỌ VÀ TÊN", "ĐIỂM", "NGÀNH", "HỌC LỰC", "THƯỞNG"};

        tblModel.setColumnIdentifiers(cols);
    }

    //đổ dữ liệu từ danh sách vào bảng
    public void fillToTable() {
        // thiết lập bảng rỗng để tránh trường hợp đổ dữ liệu giống nhau vào nhiều lần
        tblModel.setRowCount(0);

        for (Student sv : list) {
            Object[] row = new Object[]{sv.getName(), sv.getMark(), sv.getMarjor(), sv.getAcademic(), sv.isBonus()};
            tblModel.addRow(row);
        }
    }

    public void addStudent() {
        //tạo đối tượng sv để chứa dữ liệu
        Student sv = new Student();

        // lấy dữ liệu được nhập trong cửa sổ gán vào thuộc tính của đối tượng
        sv.setName(txtName.getText());
        sv.setMark(Double.parseDouble(txtMark.getText()));
        sv.setMarjor((String) cboMajor.getSelectedItem());
        txtAcademic.setText(sv.getAcademic());
        chkPrize.setSelected(sv.isBonus());

        //thêm đối tượng vào ds để đổ vào bảng
        list.add(sv);

        //đổ dữ liệu lại từ ds vào bảng sau khi vừa thêm đối tượng mới
        fillToTable();
        
        //hiện thông báo sau khi thêm
        JOptionPane.showMessageDialog(this, "Đã thêm");
    }

    //đổ dữ liệu từ bảng vào các ô nhập khi click vào dữ liệu trong bảng
    public void showDetail() {
        //lấy chỉ số hàng được click
        int index = tblStudent.getSelectedRow();
        
        txtName.setText(list.get(index).getName());
        txtMark.setText(String.valueOf(list.get(index).getMark()));
        cboMajor.setSelectedItem(list.get(index).getMarjor());
        txtAcademic.setText(list.get(index).getAcademic());
        chkPrize.setSelected(list.get(index).isBonus());
    }

    public void removeStudent() {
        int index = tblStudent.getSelectedRow();
        //hỏi xác nhận trước khi xoá
        int choice = JOptionPane.showConfirmDialog(this, "Chắc chắn xoá?", "Xác nhận", JOptionPane.YES_NO_CANCEL_OPTION);

        if (choice == JOptionPane.YES_OPTION) {
            list.remove(index);
            fillToTable();
            JOptionPane.showMessageDialog(this, "Đã xoá");
        }
    }

    public void updateStudent() {
        int index = tblStudent.getSelectedRow();

        //chỉ click vào dữ liệu trong bảng rồi thay đổi thì mới hiệu lực, không thể tự nhập dữ liệu từ đầu
        if (index >= 0) {
            Student sv = list.get(index);

            sv.setName(txtName.getText());
            sv.setMark(Double.parseDouble(txtMark.getText()));
            sv.setMarjor((String) cboMajor.getSelectedItem());
            txtAcademic.setText(sv.getAcademic());
            chkPrize.setSelected(sv.isBonus());

            fillToTable();
            JOptionPane.showMessageDialog(this, "Đã cập nhật");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sinh viên muốn cập nhật");
        }
    }
    
    public void sortName() {
        Collections.sort(list, (o1, o2) -> (o1.getName().compareTo(o2.getName())));
        fillToTable();
    }
    
    public void sortMark() {
        Collections.sort(list, ((o1, o2) -> (int) (o1.getMark() - o2.getMark())));
        fillToTable();
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
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMark = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cboMajor = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtAcademic = new javax.swing.JTextField();
        chkPrize = new javax.swing.JCheckBox();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblStudent = new javax.swing.JTable();
        btnSortName = new javax.swing.JButton();
        btnSortMark = new javax.swing.JButton();

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
        jLabel1.setForeground(new java.awt.Color(255, 0, 51));
        jLabel1.setText("QUẢN LÝ SINH VIÊN");

        jLabel2.setText("HỌ VÀ TÊN");

        jLabel3.setText("ĐIỂM");

        jLabel4.setText("NGÀNH");

        cboMajor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("HỌC LỰC");

        txtAcademic.setEnabled(false);

        chkPrize.setText("Có phần thưởng?");

        btnAdd.setText("THÊM");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setText("XOÁ");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpdate.setText("CẬP NHẬT");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnReset.setText("NHẬP MỚI");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        tblStudent.setModel(new javax.swing.table.DefaultTableModel(
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
        tblStudent.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblStudentMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblStudent);

        btnSortName.setText("Sắp xếp theo tên");
        btnSortName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortNameActionPerformed(evt);
            }
        });

        btnSortMark.setText("Sắp xếp theo điểm");
        btnSortMark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSortMarkActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(213, 213, 213)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAdd)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnDelete)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnUpdate)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnReset))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtName)
                                        .addComponent(txtMark)
                                        .addComponent(cboMajor, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtAcademic, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(chkPrize))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSortName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnSortMark)))))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMark, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cboMajor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtAcademic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkPrize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnDelete)
                    .addComponent(btnUpdate)
                    .addComponent(btnReset))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSortName)
                    .addComponent(btnSortMark))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //đưa các dữ liệu cần nhập về trạng thái rỗng ban đầu
    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtName.setText(null);
        txtMark.setText(null);
        txtAcademic.setText(null);
        chkPrize.setSelected(false);
        initMajor();
    }//GEN-LAST:event_btnResetActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        initMajor();
        initData();
        initTable();
        fillToTable();
    }//GEN-LAST:event_formWindowOpened

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        addStudent();
    }//GEN-LAST:event_btnAddActionPerformed

    private void tblStudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblStudentMouseClicked
        showDetail();
    }//GEN-LAST:event_tblStudentMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        removeStudent();
        btnResetActionPerformed(evt);
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        updateStudent();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnSortNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortNameActionPerformed
        sortName();
    }//GEN-LAST:event_btnSortNameActionPerformed

    private void btnSortMarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSortMarkActionPerformed
        sortMark();
    }//GEN-LAST:event_btnSortMarkActionPerformed

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
            java.util.logging.Logger.getLogger(StudentManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentManage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSortMark;
    private javax.swing.JButton btnSortName;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cboMajor;
    private javax.swing.JCheckBox chkPrize;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable tblStudent;
    private javax.swing.JTextField txtAcademic;
    private javax.swing.JTextField txtMark;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables
}
