/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mySumary;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Quang
 */
public class ProForm extends javax.swing.JFrame {

    DefaultTableModel tblModel;
    List<Product> list = new ArrayList<>();
    int index = -1;

    /**
     * Creates new form ProForm
     */
    public ProForm() {
        initComponents();
        setLocationRelativeTo(null);
    }

    public void initTable() {
        tblModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] cols = new String[]{"Name", "Price"};
        tblModel.setColumnIdentifiers(cols);
        tblMenu.setModel(tblModel);
    }

    public void initData() {
        try {
            list = (List<Product>) XFile.read("C:\\Users\\Quang\\OneDrive - FPT Polytechnic\\Desktop\\fpl\\hk3\\Java2\\official\\test\\outline\\test\\src\\mySumary\\product.dat");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void fillTable() {
        tblModel.setRowCount(0);

        for (Product pro : list) {
            tblModel.addRow(new Object[]{pro.getName(), pro.getPrice()});
        }
    }

    public void lock() {
        txtName.setEditable(false);
        txtPrice.setEditable(false);
        btnPhoto.setEnabled(false);
        btnNew.setEnabled(true);
        btnEdit.setEnabled(true);
        btnDelete.setEnabled(false);
        btnSave.setEnabled(false);
        btnSkip.setEnabled(false);
        menSave.setEnabled(false);
        index = -1;
    }

    public void unlock() {
        txtName.setEditable(true);
        txtPrice.setEditable(true);
        btnPhoto.setEnabled(true);
        btnNew.setEnabled(false);
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(true);
        btnSave.setEnabled(true);
        btnSkip.setEnabled(true);
        tblMenu.setEnabled(true);
        menSave.setEnabled(true);
    }

    public void clear() {
        txtName.setText(null);
        txtPrice.setText(null);
        txtPhoto.setIcon(null);
    }

    public void showInfo() {
        index = tblMenu.getSelectedRow();

        txtName.setText(list.get(index).getName());
        txtPrice.setText(String.valueOf(list.get(index).getPrice()));
        txtPhoto.setIcon(list.get(index).getPhoto());
    }

    public void add() {
        for (int i = 0; i < list.size(); i++) {
            if (txtName.getText().equals(list.get(index).getName())) {
                JOptionPane.showMessageDialog(this, "This name has been existed");
                return;
            }
        }

        list.add(new Product(txtName.getText(), Integer.parseInt(txtPrice.getText()), txtPhoto.getIcon()));
        fillTable();
        clear();
    }

    public void edit() {
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product in the table to edit");
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            if (txtName.getText().equalsIgnoreCase(list.get(i).getName())) {
                unlock();
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Your input name is not contained in list");
    }

    public void delete() {
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Please select a product in the table to edit");
            return;
        }

        list.remove(list.get(index));
        fillTable();
    }

    public void open() throws IOException, FileNotFoundException, ClassNotFoundException {
        JFileChooser openDialog = new JFileChooser();

        if (openDialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            list = (List<Product>) XFile.read(openDialog.getSelectedFile().getAbsolutePath());
            fillTable();
            tblMenu.setEnabled(true);
        }
    }

    public void save() throws IOException {
        JFileChooser saveDialog = new JFileChooser();

        FileNameExtensionFilter fileSaveExt = new FileNameExtensionFilter("DAT file", "dat");
        saveDialog.setFileFilter(fileSaveExt);

        if (saveDialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = saveDialog.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            if (selectedFile.exists()) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to overwrite this file?", "Confirm", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.NO_OPTION) {
                    selectedFile = saveToNewFile(saveDialog.getCurrentDirectory());
                    filePath = selectedFile.getAbsolutePath();
                }
            }

            try {
                XFile.write(filePath, list);
                JOptionPane.showMessageDialog(this, "Saved");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex);
            }
        }
    }

    public File saveToNewFile(File currentDirectory) {
        JFileChooser saveDialog = new JFileChooser();

        FileNameExtensionFilter fileSaveExt = new FileNameExtensionFilter("DAT file", "dat");
        saveDialog.setFileFilter(fileSaveExt);

        if (saveDialog.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = saveDialog.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            if (selectedFile.exists()) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure to overwrite this file?", "Confirm", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.NO_OPTION) {
                    return saveToNewFile(currentDirectory);
                }
            }

            return selectedFile;
        }

        return null;
    }

    public void photo() {
        JFileChooser photoDialog = new JFileChooser();
        FileNameExtensionFilter photoExt = new FileNameExtensionFilter("jpg", "jpeg", "png", "Images");

        photoDialog.setFileFilter(photoExt);
        if (photoDialog.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = photoDialog.getSelectedFile();

            if (checkPhotoFile(selectedFile)) {
                ImageIcon img = new ImageIcon(selectedFile.getAbsolutePath());

                int labelWidth = txtPhoto.getWidth();
                int labelHeight = txtPhoto.getHeight();

                double scaleX = (double) labelWidth / img.getIconWidth();
                double scaleY = (double) labelHeight / img.getIconHeight();
                double scale = Math.min(scaleX, scaleY);

                int scaleWidth = (int) (img.getIconWidth() * scale);
                int scaleHeight = (int) (img.getIconHeight() * scale);

                Image scaledImg = img.getImage().getScaledInstance(scaleWidth, scaleHeight, Image.SCALE_SMOOTH);
                ImageIcon imgIcon = new ImageIcon(scaledImg);

                txtPhoto.setIcon(imgIcon);
            } else {
                JOptionPane.showMessageDialog(this, "This file is not a photo");
            }
        }
    }

    public boolean checkPhotoFile(File file) {
        String path = file.getName().toLowerCase();
        return path.endsWith("jpg") || path.endsWith("jpeg") || path.endsWith("png");
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
        txtName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        btnPhoto = new javax.swing.JButton();
        txtPhoto = new javax.swing.JLabel();
        btnSkip = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMenu = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        JMenu = new javax.swing.JMenu();
        menOpen = new javax.swing.JMenuItem();
        menSave = new javax.swing.JMenuItem();
        menClose = new javax.swing.JMenuItem();

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

        jLabel1.setText("Name");

        jLabel2.setText("Price");

        btnPhoto.setText("Add photo");
        btnPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhotoActionPerformed(evt);
            }
        });

        btnSkip.setText("Skip");
        btnSkip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSkipActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        tblMenu.setModel(new javax.swing.table.DefaultTableModel(
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
        tblMenu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMenuMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblMenu);

        JMenu.setText("File");

        menOpen.setText("Open");
        menOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menOpenActionPerformed(evt);
            }
        });
        JMenu.add(menOpen);

        menSave.setText("Save to File");
        menSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menSaveActionPerformed(evt);
            }
        });
        JMenu.add(menSave);

        menClose.setText("Close File");
        JMenu.add(menClose);

        jMenuBar1.add(JMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(36, 36, 36)
                                .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(btnPhoto)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(29, 29, 29)
                .addComponent(txtPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSkip)))
                .addGap(56, 56, 56))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(btnPhoto))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtPhoto, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSkip)
                    .addComponent(btnSave)
                    .addComponent(btnDelete)
                    .addComponent(btnEdit)
                    .addComponent(btnNew))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        initTable();
        lock();
        tblMenu.setEnabled(false);
    }//GEN-LAST:event_formWindowOpened

    private void menOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menOpenActionPerformed
        try {
            open();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_menOpenActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        unlock();
        clear();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnSkipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSkipActionPerformed
        lock();
        clear();
    }//GEN-LAST:event_btnSkipActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        add();
        clear();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        edit();
    }//GEN-LAST:event_btnEditActionPerformed

    private void tblMenuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMenuMouseClicked
        showInfo();
    }//GEN-LAST:event_tblMenuMouseClicked

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        delete();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void menSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menSaveActionPerformed
        try {
            save();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }//GEN-LAST:event_menSaveActionPerformed

    private void btnPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhotoActionPerformed
        photo();
    }//GEN-LAST:event_btnPhotoActionPerformed

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
            java.util.logging.Logger.getLogger(ProForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu JMenu;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnPhoto;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSkip;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JMenuItem menClose;
    private javax.swing.JMenuItem menOpen;
    private javax.swing.JMenuItem menSave;
    private javax.swing.JTable tblMenu;
    private javax.swing.JTextField txtName;
    private javax.swing.JLabel txtPhoto;
    private javax.swing.JTextField txtPrice;
    // End of variables declaration//GEN-END:variables
}
