/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package lab4;

import java.awt.Color;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Quang
 */
public class StaffManage extends javax.swing.JFrame {

    /**
     * Creates new form StaffManage
     */
    public StaffManage() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public void check() throws MyException {
        Staff st = new Staff();

        if (txtName.getText().equals("")) {
            txtName.setBackground(Color.yellow);
            JOptionPane.showMessageDialog(this, "Không để trống ô tên");
            return;
        } else {
            txtName.setBackground(Color.white);
            st.setName(txtName.getText());
        }

        if (txtBDay.getText().equals("")) {
            txtBDay.setBackground(Color.yellow);
            JOptionPane.showMessageDialog(this, "Không để trống ô ngày sinh");
            return;
        } else {

            try {
                txtBDay.setBackground(Color.white);
                XDate.parse(txtBDay.getText(), "dd-MM-yyyy");
                st.setBirthday(new SimpleDateFormat("dd-MM-yyyy").parse(txtBDay.getText()));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Nhập ngày sinh theo định dạng dd-MM-yyyy");
                txtBDay.setBackground(Color.yellow);
                return;
            }
        }

        try {

            String regex = "(^-)*\\d+(.\\d+)*";

            if (txtSalary.getText().matches(regex) == false) {
                JOptionPane.showMessageDialog(this, "Salary must be a number");
                txtSalary.setBackground(Color.yellow);
                return;
            } else {
                st.setSalary(Double.parseDouble(txtSalary.getText()));
                txtSalary.setBackground(Color.white);
            }

            if (txtAge.getText().matches(regex) == false) {
                JOptionPane.showMessageDialog(this, "Age must be a number");
                txtAge.setBackground(Color.yellow);
                return;
            } else {
                st.setAge(Integer.parseInt(txtAge.getText()));
                txtAge.setBackground(Color.white);
            }
        } catch (MyException ex) {
            JOptionPane.showMessageDialog(this, ex.getError());
            if (ex.getErrorCode().compareToIgnoreCase("02") == 0 || ex.getErrorCode().compareToIgnoreCase("03") == 0) {
                txtSalary.setBackground(Color.yellow);

            }
            if (ex.getErrorCode().compareToIgnoreCase("04") == 0) {
                txtAge.setBackground(Color.yellow);

            }
            return;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Not valid");
            return;
        }

        JOptionPane.showMessageDialog(this, "Nhập thành công");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtBDay = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSalary = new javax.swing.JTextField();
        btnCheck = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtAge = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("QUẢN LÝ NHÂN VIÊN");

        jLabel2.setText("HỌ VÀ TÊN");

        jLabel3.setText("NGÀY SINH");

        jLabel4.setText("LƯƠNG");

        btnCheck.setText("CHECK");
        btnCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCheckActionPerformed(evt);
            }
        });

        jLabel5.setText("TUỔI");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCheck)
                            .addComponent(txtName)
                            .addComponent(txtBDay)
                            .addComponent(txtSalary, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(txtAge))))
                .addContainerGap(34, Short.MAX_VALUE))
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
                    .addComponent(txtBDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCheck)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCheckActionPerformed
        try {
            check();
        } catch (MyException ex) {
        }
    }//GEN-LAST:event_btnCheckActionPerformed

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
            java.util.logging.Logger.getLogger(StaffManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StaffManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StaffManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StaffManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StaffManage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCheck;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField txtAge;
    private javax.swing.JTextField txtBDay;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtSalary;
    // End of variables declaration//GEN-END:variables
}
