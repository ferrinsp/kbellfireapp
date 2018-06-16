package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NSupplier extends javax.swing.JFrame {
    
    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;
    int id = -1;
    
    private void updateSupplier(){
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellfire?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select companyname, contact, address1, city, state, postalcode, phone, fax, terms, comments from supplier where supplierid = "+id);
            while (resultObj.next()){
                companyName.setText(resultObj.getString("companyname"));
                contact.setText(resultObj.getString("contact"));
                address.setText(resultObj.getString("address1"));
                city.setText(resultObj.getString("city"));
                state.setText(resultObj.getString("state"));
                postalCode.setText(resultObj.getString("postalcode"));
                phone.setText(resultObj.getString("phone"));
                fax.setText(resultObj.getString("fax"));
                terms.setText(resultObj.getString("terms"));
                comments.setText(resultObj.getString("comments"));
            }
            connObj.close();
        } catch (SQLException ex) {    
            Logger.getLogger(NSupplier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void I_U_Supplier()    {
        try {
            String query;
        //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
        connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellfire?useSSL=false", "admin", "1qaz2wsx");
        if (id == -1) {
            query = "INSERT into supplier (companyname,contact,address1,city,state,postalcode,phone,fax,terms,comments)"
                + "values(?,?,?,?,?,?,?,?,?,?)";
        }
        else{
            query = "UPDATE supplier SET companyname=?,contact =?,address1=?,city=?,state=?,postalcode=?,phone=?,"
                    + "fax=?,terms=?,comments=? where supplierid ="+id;
        }
        //Get Values to insert
        PreparedStatement preparedStmt =connObj.prepareStatement(query);
        preparedStmt.setString (1, companyName.getText());
        preparedStmt.setString (2, contact.getText());
        preparedStmt.setString (3, address.getText());
        preparedStmt.setString (4, city.getText());
        preparedStmt.setString (5, state.getText());
        preparedStmt.setString (6, postalCode.getText());
        preparedStmt.setString (7, phone.getText());
        preparedStmt.setString (8, fax.getText());
        preparedStmt.setString (9, terms.getText());
        preparedStmt.setString (10, comments.getText());
        if (id == -1)
            preparedStmt.execute();
        else
            preparedStmt.executeUpdate();
      
        connObj.close();
        this.id = -1;
        this.dispose();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Creates new form update_suppliers
     * @param id
     */
    public NSupplier(int id) {
        initComponents();
        this.id=id;
        updateSupplier();
    }
    public NSupplier() {
        initComponents();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        new_supplier_input = new javax.swing.JPanel();
        new_vendor_panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        companyName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        contact = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        address = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        city = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        state = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        postalCode = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        phone = new javax.swing.JTextField();
        fax = new javax.swing.JTextField();
        terms = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        comments = new javax.swing.JTextArea();
        save_cancel_buttonpanel = new javax.swing.JPanel();
        saveSupplier = new javax.swing.JButton();
        Cancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add New Supplier");
        setResizable(false);

        jLabel1.setText("Company Name");

        companyName.setText("Company Name");
        companyName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                companyNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                companyNameFocusLost(evt);
            }
        });

        jLabel2.setText("Contact");

        contact.setText("Contact");
        contact.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                contactFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                contactFocusLost(evt);
            }
        });

        jLabel3.setText("Address");

        address.setText("Address");
        address.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                addressFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                addressFocusLost(evt);
            }
        });

        jLabel4.setText("City");

        city.setText("City");
        city.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                cityFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                cityFocusLost(evt);
            }
        });

        jLabel5.setText("State");

        state.setText("State");
        state.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                stateFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                stateFocusLost(evt);
            }
        });

        jLabel6.setText("Postal Code");

        postalCode.setText("Postal Code");
        postalCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                postalCodeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                postalCodeFocusLost(evt);
            }
        });

        jLabel8.setText("Phone Number");

        jLabel9.setText("Fax Number");

        jLabel10.setText("Terms");

        phone.setText("Phone Number");
        phone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                phoneFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                phoneFocusLost(evt);
            }
        });

        fax.setText("Fax Number");
        fax.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                faxFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                faxFocusLost(evt);
            }
        });

        terms.setText("Terms");
        terms.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                termsFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                termsFocusLost(evt);
            }
        });

        jLabel11.setText("Comments");

        comments.setColumns(20);
        comments.setRows(5);
        jScrollPane1.setViewportView(comments);

        javax.swing.GroupLayout new_vendor_panelLayout = new javax.swing.GroupLayout(new_vendor_panel);
        new_vendor_panel.setLayout(new_vendor_panelLayout);
        new_vendor_panelLayout.setHorizontalGroup(
            new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(new_vendor_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(new_vendor_panelLayout.createSequentialGroup()
                        .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(terms, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)))
                    .addGroup(new_vendor_panelLayout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(postalCode))
                    .addGroup(new_vendor_panelLayout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(state, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(new_vendor_panelLayout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(fax))
                    .addGroup(new_vendor_panelLayout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(new_vendor_panelLayout.createSequentialGroup()
                        .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(address)
                            .addComponent(city)))
                    .addGroup(new_vendor_panelLayout.createSequentialGroup()
                        .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(companyName, javax.swing.GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
                            .addComponent(contact))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        new_vendor_panelLayout.setVerticalGroup(
            new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, new_vendor_panelLayout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(companyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(city, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(state, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(postalCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(fax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(terms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(new_vendor_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        saveSupplier.setText("Save");
        saveSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveSupplierActionPerformed(evt);
            }
        });

        Cancel.setText("Cancel");
        Cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout save_cancel_buttonpanelLayout = new javax.swing.GroupLayout(save_cancel_buttonpanel);
        save_cancel_buttonpanel.setLayout(save_cancel_buttonpanelLayout);
        save_cancel_buttonpanelLayout.setHorizontalGroup(
            save_cancel_buttonpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, save_cancel_buttonpanelLayout.createSequentialGroup()
                .addContainerGap(116, Short.MAX_VALUE)
                .addComponent(saveSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Cancel)
                .addGap(116, 116, 116))
        );
        save_cancel_buttonpanelLayout.setVerticalGroup(
            save_cancel_buttonpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(save_cancel_buttonpanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(save_cancel_buttonpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Cancel)
                    .addComponent(saveSupplier))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout new_supplier_inputLayout = new javax.swing.GroupLayout(new_supplier_input);
        new_supplier_input.setLayout(new_supplier_inputLayout);
        new_supplier_inputLayout.setHorizontalGroup(
            new_supplier_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(new_supplier_inputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(new_supplier_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(new_vendor_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(save_cancel_buttonpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        new_supplier_inputLayout.setVerticalGroup(
            new_supplier_inputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, new_supplier_inputLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(new_vendor_panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(save_cancel_buttonpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 10, Short.MAX_VALUE)
                .addComponent(new_supplier_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(new_supplier_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void companyNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_companyNameFocusGained
        if(companyName.getText().equals("Company Name"))
            companyName.setText("");
    }//GEN-LAST:event_companyNameFocusGained

    private void companyNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_companyNameFocusLost
        if(companyName.getText().equals(""))
            companyName.setText("Company Name");
    }//GEN-LAST:event_companyNameFocusLost

    private void contactFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_contactFocusGained
        if(contact.getText().equals("Contact"))
            contact.setText("");
    }//GEN-LAST:event_contactFocusGained

    private void contactFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_contactFocusLost
        if(contact.getText().equals(""))
            contact.setText("Contact");
    }//GEN-LAST:event_contactFocusLost

    private void addressFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addressFocusGained
        if(address.getText().equals("Address"))
            address.setText("");
    }//GEN-LAST:event_addressFocusGained

    private void addressFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_addressFocusLost
        if(address.getText().equals(""))
            address.setText("Address");
    }//GEN-LAST:event_addressFocusLost

    private void cityFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cityFocusGained
        if(city.getText().equals("City"))
            city.setText("");
    }//GEN-LAST:event_cityFocusGained

    private void cityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_cityFocusLost
        if(city.getText().equals(""))
            city.setText("City");
    }//GEN-LAST:event_cityFocusLost

    private void stateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stateFocusGained
        if(state.getText().equals("State"))
            state.setText("");
    }//GEN-LAST:event_stateFocusGained

    private void stateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stateFocusLost
        if(state.getText().equals(""))
            state.setText("State");
    }//GEN-LAST:event_stateFocusLost

    private void postalCodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_postalCodeFocusGained
        if(postalCode.getText().equals("Postal Code"))    
            postalCode.setText("");
    }//GEN-LAST:event_postalCodeFocusGained

    private void postalCodeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_postalCodeFocusLost
        if(postalCode.getText().equals(""))
            postalCode.setText("Postal Code");
    }//GEN-LAST:event_postalCodeFocusLost

    private void phoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_phoneFocusGained
        if(phone.getText().equals("Phone"))
            phone.setText("");
    }//GEN-LAST:event_phoneFocusGained

    private void phoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_phoneFocusLost
        if(phone.getText().equals(""))
            phone.setText("Phone");
    }//GEN-LAST:event_phoneFocusLost

    private void faxFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_faxFocusGained
        if(fax.getText().equals("Fax"))
            fax.setText("");
    }//GEN-LAST:event_faxFocusGained

    private void faxFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_faxFocusLost
        if(fax.getText().equals(""))
            fax.setText("Fax");
    }//GEN-LAST:event_faxFocusLost

    private void termsFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_termsFocusGained
        if(terms.getText().equals("Terms"))
            terms.setText("");
    }//GEN-LAST:event_termsFocusGained

    private void termsFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_termsFocusLost
        if(terms.getText().equals(""))
            terms.setText("Terms");
    }//GEN-LAST:event_termsFocusLost

    private void saveSupplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveSupplierActionPerformed
            I_U_Supplier();
            
    }//GEN-LAST:event_saveSupplierActionPerformed

    private void CancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_CancelActionPerformed

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
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NSupplier.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }   
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new NSupplier().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cancel;
    private javax.swing.JTextField address;
    private javax.swing.JTextField city;
    private javax.swing.JTextArea comments;
    private javax.swing.JTextField companyName;
    private javax.swing.JTextField contact;
    private javax.swing.JTextField fax;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel new_supplier_input;
    private javax.swing.JPanel new_vendor_panel;
    private javax.swing.JTextField phone;
    private javax.swing.JTextField postalCode;
    private javax.swing.JButton saveSupplier;
    private javax.swing.JPanel save_cancel_buttonpanel;
    private javax.swing.JTextField state;
    private javax.swing.JTextField terms;
    // End of variables declaration//GEN-END:variables
}
