package gui;

import java.awt.Color;
import java.awt.Point;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class VCompletePOs extends javax.swing.JFrame {
    
    public Color genericColor = new Color(209, 220, 204);    
    private final AlternatingListCellRenderer cellRenderer;
    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;
    
    public VCompletePOs() {
        this.cellRenderer = new AlternatingListCellRenderer();
        initComponents();
        setDatePickers();
        selectCompletedPOs();
    }
    private void setDatePickers() {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DATE, -31);
        startDatePicker.setDate(startDate.getTime()); 
        endDatePicker.setDate(Calendar.getInstance().getTime());
    }
    private void print(int orderid){
        try {
            //Generate Report
            InputStream is = getClass().getResourceAsStream("/Reports/PO.jrxml");
            JasperDesign jd= JRXmlLoader.load(is);
            connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellPlumb?useSSL=false", "admin", "1qaz2wsx");

            //set parameters
            Map map = new HashMap();
            map.put("orderid", orderid);
            //compile report
            JasperReport jasperReport = JasperCompileManager.compileReport(jd);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connObj);
            //view report to UI
            JasperViewer.viewReport(jasperPrint, false);                   
        } catch (SQLException | JRException ex) {
            Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void selectCompletedPOs() {
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellPlumb?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            if(showHideStatus.isSelected()) {resultObj = stateObj.executeQuery("select t1.orderid, t1.status, t4.terms, t4.companyname, a.name, date_format(t1.expectedby, '%m/%d/%Y') as 'expectedby', t3.name, b.name, t1.total,t1.exported, t1.comments " +
"		from purchaseorder t1 inner join job a on t1.job = a.jobid inner join job b on t1.shipto =b.jobid" +
"		inner join kbellplumb.user t3 on t1.createdby = t3.userid inner join supplier t4 on t1.supplier = t4.supplierid where t1.status like '%Reconciled%'" +
"		and expectedby BETWEEN '" + new java.sql.Date(startDatePicker.getDate().getTime()) + "' AND '" + new java.sql.Date(endDatePicker.getDate().getTime()) + "' and t1.exported ='No';"); }
            else{
            resultObj = stateObj.executeQuery("select t1.orderid, t1.status, t4.terms, t4.companyname, a.name, date_format(t1.expectedby, '%m/%d/%Y') as 'expectedby', t3.name, b.name, t1.total,t1.exported, t1.comments " +
"		from purchaseorder t1 inner join job a on t1.job = a.jobid inner join job b on t1.shipto =b.jobid" +
"		inner join kbellplumb.user t3 on t1.createdby = t3.userid inner join supplier t4 on t1.supplier = t4.supplierid where t1.status like '%Reconciled%'" +
"		and expectedby BETWEEN '" + new java.sql.Date(startDatePicker.getDate().getTime()) + "' AND '" + new java.sql.Date(endDatePicker.getDate().getTime()) + "';"); }
            viewCompletedPOs.setModel(DbUtils.resultSetToTableModel(resultObj));
            viewCompletedPOs.getColumn("orderid").setHeaderValue("Purchase Order Number");
            viewCompletedPOs.getColumn("companyname").setHeaderValue("Company");
            viewCompletedPOs.getColumn("terms").setHeaderValue("Terms");
            viewCompletedPOs.getColumn("name").setHeaderValue("Job");
            viewCompletedPOs.getColumn("status").setHeaderValue("Status");
            viewCompletedPOs.getColumn("expectedby").setHeaderValue("Date");
            viewCompletedPOs.getColumn("name").setHeaderValue("Issued By");
            viewCompletedPOs.getColumn("name").setHeaderValue("Ship To");
            viewCompletedPOs.getColumn("total").setHeaderValue("Invoice Total");
            viewCompletedPOs.getColumn("exported").setHeaderValue("Exported");
            viewCompletedPOs.getColumn("comments").setHeaderValue("Comments");
            viewCompletedPOs.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
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

        jPanel1 = new javax.swing.JPanel();
        viewCompletedPOScrollPane = new javax.swing.JScrollPane();
        viewCompletedPOs = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        closePOCompleteButton = new javax.swing.JButton();
        exportButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        startDatePicker = new org.jdesktop.swingx.JXDatePicker();
        endDatePicker = new org.jdesktop.swingx.JXDatePicker();
        showHideStatus = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reconciled Purchase Orders");
        setResizable(false);

        viewCompletedPOs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Purchase Order", "Supplier ", "Terms", "Job", "Expected Date", "Issued By", "Ship To", "Invoice Total", "Exported", "Comments"
            }
        ));
        viewCompletedPOs.getTableHeader().setReorderingAllowed(false);
        viewCompletedPOScrollPane.setViewportView(viewCompletedPOs);
        viewCompletedPOs.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                JTable table =(JTable) me.getSource();
                Point p = me.getPoint();
                int row = table.rowAtPoint(p);
                if (me.getClickCount() ==2 ) {
                    print((int) viewCompletedPOs.getValueAt(row,0));
                }
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(viewCompletedPOScrollPane)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(viewCompletedPOScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        closePOCompleteButton.setText("Close");
        closePOCompleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closePOCompleteButtonActionPerformed(evt);
            }
        });

        exportButton.setText("Export");
        exportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exportButton)
                .addGap(18, 18, 18)
                .addComponent(closePOCompleteButton))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closePOCompleteButton)
                    .addComponent(exportButton))
                .addGap(12, 12, 12))
        );

        jLabel1.setText("Start Date:");

        jLabel2.setText("End Date:");

        startDatePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startDatePickerActionPerformed(evt);
            }
        });

        endDatePicker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endDatePickerActionPerformed(evt);
            }
        });

        showHideStatus.setText("Hide Exported");
        showHideStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showHideStatusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(endDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addGap(205, 205, 205)
                .addComponent(showHideStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(endDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(startDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(showHideStatus))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void closePOCompleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closePOCompleteButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closePOCompleteButtonActionPerformed

    private void startDatePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startDatePickerActionPerformed
        selectCompletedPOs();
    }//GEN-LAST:event_startDatePickerActionPerformed

    private void endDatePickerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endDatePickerActionPerformed
        selectCompletedPOs();
    }//GEN-LAST:event_endDatePickerActionPerformed

    private void showHideStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showHideStatusActionPerformed
        selectCompletedPOs();
    }//GEN-LAST:event_showHideStatusActionPerformed

    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtonActionPerformed
        int[] index = viewCompletedPOs.getSelectedRows();
        if(index.length <= 0){
            JOptionPane.showMessageDialog(null, "No items added for Export.");
        }  else {
            boolean error =false;
            String s; 
            for (int i=0;i<index.length;i++){
                s=viewCompletedPOs.getValueAt(index[i],9).toString();
                if(s.equals("Yes")){
                    JOptionPane.showMessageDialog(null, "One of the items selected has been exported.");
                    error=true;
                }
            }
            if(error)
                System.out.println("Error");
            else {
                BufferedWriter bfw = null;      
                try {
                    bfw = new BufferedWriter(new FileWriter("C:\\temp\\ItemsExported.csv"));
                    for (int i = 0 ; i < index.length; i++)
                    { 
                        for(int j = 0 ; j < viewCompletedPOs.getColumnCount();j++)
                        {
                            if(j!=8){
                            s =viewCompletedPOs.getValueAt(index[i],j).toString();
                            bfw.write(s.replaceAll("(?:\\n|\\r)", ""));
                            bfw.write(",");
                            }
                        }
                        bfw.newLine();
                    }
                    this.setVisible(false);
                } catch (IOException ex) {
                    Logger.getLogger(NPurchaseOrder.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        bfw.close();
                    } catch (IOException ex) {
                        Logger.getLogger(NPurchaseOrder.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try{
                    String query;
                    PreparedStatement preparedStmt;
                    connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellPlumb?useSSL=false", "admin", "1qaz2wsx");
                    for(int i=0;i<index.length;i++){
                        int orderid = (int) viewCompletedPOs.getValueAt(index[i],0);
                        System.out.println(orderid);
                        query = "update purchaseorder set exported=? where orderid ="+orderid+";";
                        preparedStmt=connObj.prepareStatement(query);
                        preparedStmt.setString (1, "Yes");
                        preparedStmt.executeUpdate();
                    }
                    connObj.close();
                }
                catch(SQLException ex){}
            }
            
        }
        
    }//GEN-LAST:event_exportButtonActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VCompletePOs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VCompletePOs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VCompletePOs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VCompletePOs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new VCompletePOs().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closePOCompleteButton;
    private org.jdesktop.swingx.JXDatePicker endDatePicker;
    private javax.swing.JButton exportButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JCheckBox showHideStatus;
    private org.jdesktop.swingx.JXDatePicker startDatePicker;
    private javax.swing.JScrollPane viewCompletedPOScrollPane;
    private javax.swing.JTable viewCompletedPOs;
    // End of variables declaration//GEN-END:variables
}
