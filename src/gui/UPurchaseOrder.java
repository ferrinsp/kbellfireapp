
package gui;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import net.proteanit.sql.DbUtils;

public class UPurchaseOrder extends javax.swing.JFrame {

    public Color genericColor = new Color(209, 220, 204);    
    private final AlternatingListCellRenderer cellRenderer;
    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;
    int id; //Set this later
    
    public UPurchaseOrder(int index) {
        this.cellRenderer = new AlternatingListCellRenderer();
        initComponents();
        this.id=index;
        selectpo();
    }
    public UPurchaseOrder() {
        this.cellRenderer = new AlternatingListCellRenderer();
        initComponents();
        selectpo();
    }
    private void selectpo()    {
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
           //Populate the table
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select pod.detailsid,pd.productDescription, pod.orderqty, pod.receivedqty,pod.cost,pod.total from purchaseorder po\n" +
                "inner join purchaseorderdetails pod on pod.orderid=po.orderid\n" +
                "inner join product p on p.id=pod.product\n" +
                "inner join productdescription pd on pd.pdescID=p.description\n" +
                "where po.orderid="+id+";"); //Add id number instead of 6 after test
            itemTable.setModel(DbUtils.resultSetToTableModel(resultObj));
            itemTable.getColumn("detailsid").setHeaderValue("ID");
            itemTable.getColumn("productDescription").setHeaderValue("Description");
            itemTable.getColumn("orderqty").setHeaderValue("Order Qty");
            itemTable.getColumn("receivedqty").setHeaderValue("Recieved Qty");
            itemTable.getColumn("cost").setHeaderValue("Unit Price");
            itemTable.getColumn("total").setHeaderValue("Total");
            itemTable.repaint();
            //Populate the textfields
            resultObj = stateObj.executeQuery("select po.orderid, j.name, po.expectedby,u.name as 'user',s.companyname,j2.name as 'shipto',po.currentTax, po.created, po.status, po.comments\n" +
                "from purchaseorder po inner join purchaseorderdetails pod on pod.orderid=po.orderid\n" +
                "inner join supplier s on s.supplierid=po.supplier inner join user u on u.userid=po.createdby\n" +
                "inner join job j on j.jobid=po.job inner join job j2 on j2.jobid=po.shipto where po.orderid="+id+";");//change to passed in variable
            Date d;
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            while (resultObj.next()){
                purchaseOrderNum.setText(Integer.toString(resultObj.getInt("orderid")));
                jobName.setText(resultObj.getString("name"));
                expectedDate.setDate(resultObj.getDate("expectedby"));
                createdBy.setText(resultObj.getString("user"));
                supplierName.setText(resultObj.getString("companyname"));
                shipTo.setText(resultObj.getString("shipto"));
                taxRate.setText(Integer.toString(resultObj.getInt("currentTax")));
                d= resultObj.getDate("created");
                dateCreated.setText(df.format(d));
                commentsArea.setText("comments");
                switch (resultObj.getString("status")) {
                    case "Back Order":
                        rdbBackOrder.setSelected(true);
                        break;
                    case "Completed":
                        rdbCompleted.setSelected(true);
                        break;
                    default:
                        rdbIssued.setSelected(true);
                        break;
                }
            }
            connObj.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void updatePO(){
        try{
            String query;
            PreparedStatement preparedStmt;
            connObj = DriverManager.getConnection("jdbc:mysql://localhost:3306/kbell?useSSL=false", "admin", "1qaz2wsx");
            //Update 
            int detailID=0;
            int value=0;
            for (int i=0;i<itemTable.getRowCount();i++){
                detailID=Integer.parseInt(itemTable.getValueAt(i, 0).toString());
                value=Integer.parseInt(itemTable.getValueAt(i, 3).toString());
                query="Update purchaseorderdetails set receivedqty=? where detailsid="+detailID+";";
                //Get Values to insert
                preparedStmt =connObj.prepareStatement(query);
                preparedStmt.setInt (1, value);
                preparedStmt.executeUpdate();
                
            }
            //Update purchase order
            String poStatus= null;
            for (Enumeration<AbstractButton> buttons = statusGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
                if (button.isSelected()) {
                    poStatus = button.getText();
                }
            }
            query = "Update purchaseorder set status=?, comments=? where orderid="+id+";";
            //Get Values to update
            preparedStmt =connObj.prepareStatement(query);
            preparedStmt.setString (1, poStatus);
            preparedStmt.setString (2, commentsArea.getText());
            preparedStmt.executeUpdate();
            connObj.close();
            this.id= -1;
            this.dispose();
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

        statusGroup = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        purchaseOrderNum = new javax.swing.JTextField();
        jobName = new javax.swing.JTextField();
        expectedBy = new javax.swing.JTextField();
        supplierName = new javax.swing.JTextField();
        createdBy = new javax.swing.JTextField();
        dateCreated = new javax.swing.JTextField();
        shipTo = new javax.swing.JTextField();
        taxRate = new javax.swing.JTextField();
        expectedDate = new org.jdesktop.swingx.JXDatePicker();
        jScrollPane2 = new javax.swing.JScrollPane();
        itemTable = new javax.swing.JTable();
        rdbIssued = new javax.swing.JRadioButton();
        rdbBackOrder = new javax.swing.JRadioButton();
        rdbCompleted = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentsArea = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update Purchase Order");

        jLabel1.setText("Purchase Order #");

        jLabel2.setText("Job:");

        jLabel3.setText("Expected Date:");

        jLabel4.setText("Supplier:");

        jLabel5.setText("Created By:");

        jLabel6.setText("Created Date:");

        jLabel7.setText("Ship To:");

        jLabel8.setText("Tax Rate:");

        purchaseOrderNum.setEditable(false);
        purchaseOrderNum.setText("Purchase Order #");
        purchaseOrderNum.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                purchaseOrderNumFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                purchaseOrderNumFocusLost(evt);
            }
        });

        jobName.setEditable(false);
        jobName.setText("Job");
        jobName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jobNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jobNameFocusLost(evt);
            }
        });

        expectedBy.setEditable(false);
        expectedBy.setText("Expected Date");
        expectedBy.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                expectedByFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                expectedByFocusLost(evt);
            }
        });

        supplierName.setEditable(false);
        supplierName.setText("Supplier");
        supplierName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                supplierNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                supplierNameFocusLost(evt);
            }
        });

        createdBy.setEditable(false);
        createdBy.setText("Created By");
        createdBy.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                createdByFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                createdByFocusLost(evt);
            }
        });

        dateCreated.setEditable(false);
        dateCreated.setText("Created Date");
        dateCreated.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                dateCreatedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                dateCreatedFocusLost(evt);
            }
        });

        shipTo.setEditable(false);
        shipTo.setText("Ship To");
        shipTo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                shipToFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                shipToFocusLost(evt);
            }
        });

        taxRate.setEditable(false);
        taxRate.setText("Tax Rate");
        taxRate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                taxRateFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                taxRateFocusLost(evt);
            }
        });

        Date date = new Date();
        expectedDate.setDate(date);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(219, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jobName)
                    .addComponent(purchaseOrderNum)
                    .addComponent(supplierName)
                    .addComponent(createdBy)
                    .addComponent(dateCreated)
                    .addComponent(shipTo)
                    .addComponent(taxRate, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addComponent(expectedDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(196, 196, 196))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(purchaseOrderNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jobName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(expectedDate, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(supplierName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(createdBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(dateCreated, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(shipTo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(taxRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(57, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Update Purchase Order", jPanel1);

        itemTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Description", "Ordered Qty", "Received Qty", "Unit Price", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, true, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        itemTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(itemTable);

        statusGroup.add(rdbIssued);
        rdbIssued.setSelected(true);
        rdbIssued.setText("Issued");

        statusGroup.add(rdbBackOrder);
        rdbBackOrder.setText("Back Order");
        rdbBackOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbBackOrderActionPerformed(evt);
            }
        });

        statusGroup.add(rdbCompleted);
        rdbCompleted.setText("Completed");

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Close");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(271, 271, 271)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        jLabel9.setText("Purchase Order Status:");

        commentsArea.setColumns(20);
        commentsArea.setRows(5);
        jScrollPane1.setViewportView(commentsArea);

        jLabel10.setText("Comments:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1)
                            .addComponent(jScrollPane2)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel10)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rdbIssued, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rdbBackOrder)
                .addGap(18, 18, 18)
                .addComponent(rdbCompleted)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbIssued)
                    .addComponent(rdbBackOrder)
                    .addComponent(rdbCompleted)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rdbBackOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbBackOrderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdbBackOrderActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        updatePO();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void purchaseOrderNumFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_purchaseOrderNumFocusGained
        if(purchaseOrderNum.getText().equals("Purchase Order #"))
            purchaseOrderNum.setText("");
    }//GEN-LAST:event_purchaseOrderNumFocusGained

    private void purchaseOrderNumFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_purchaseOrderNumFocusLost
        if(purchaseOrderNum.getText().equals(""))
            purchaseOrderNum.setText("Purchase Order #");
    }//GEN-LAST:event_purchaseOrderNumFocusLost

    private void jobNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jobNameFocusGained
        if(jobName.getText().equals("Job"))
            jobName.setText("");
    }//GEN-LAST:event_jobNameFocusGained

    private void jobNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jobNameFocusLost
        if(jobName.getText().equals(""))
            jobName.setText("Job");
    }//GEN-LAST:event_jobNameFocusLost

    private void expectedByFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_expectedByFocusGained

    }//GEN-LAST:event_expectedByFocusGained

    private void expectedByFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_expectedByFocusLost

    }//GEN-LAST:event_expectedByFocusLost

    private void supplierNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_supplierNameFocusGained
        if(supplierName.getText().equals("Supplier"))
            supplierName.setText("");
    }//GEN-LAST:event_supplierNameFocusGained

    private void supplierNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_supplierNameFocusLost
        if(supplierName.getText().equals(""))
            supplierName.setText("Supplier");
    }//GEN-LAST:event_supplierNameFocusLost

    private void createdByFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_createdByFocusGained
        if(createdBy.getText().equals("Created By"))
            createdBy.setText("");
    }//GEN-LAST:event_createdByFocusGained

    private void createdByFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_createdByFocusLost
        if(createdBy.getText().equals(""))
            createdBy.setText("Created By");
    }//GEN-LAST:event_createdByFocusLost

    private void dateCreatedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dateCreatedFocusGained
        if(dateCreated.getText().equals("Created Date"))
            dateCreated.setText("");
    }//GEN-LAST:event_dateCreatedFocusGained

    private void dateCreatedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_dateCreatedFocusLost
        if(dateCreated.getText().equals(""))
            dateCreated.setText("Created Date");
    }//GEN-LAST:event_dateCreatedFocusLost

    private void shipToFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_shipToFocusGained
        if(shipTo.getText().equals("Ship To"))
            shipTo.setText("");
    }//GEN-LAST:event_shipToFocusGained

    private void shipToFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_shipToFocusLost
        if(shipTo.getText().equals(""))
            shipTo.setText("Ship To");
    }//GEN-LAST:event_shipToFocusLost

    private void taxRateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_taxRateFocusLost
        if(taxRate.getText().equals(""))
            taxRate.setText("Tax Rate");
    }//GEN-LAST:event_taxRateFocusLost

    private void taxRateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_taxRateFocusGained
        if(taxRate.getText().equals("Tax Rate"))
            taxRate.setText("");
    }//GEN-LAST:event_taxRateFocusGained

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
            java.util.logging.Logger.getLogger(UPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UPurchaseOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea commentsArea;
    private javax.swing.JTextField createdBy;
    private javax.swing.JTextField dateCreated;
    private javax.swing.JTextField expectedBy;
    private org.jdesktop.swingx.JXDatePicker expectedDate;
    private javax.swing.JTable itemTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jobName;
    private javax.swing.JTextField purchaseOrderNum;
    private javax.swing.JRadioButton rdbBackOrder;
    private javax.swing.JRadioButton rdbCompleted;
    private javax.swing.JRadioButton rdbIssued;
    private javax.swing.JTextField shipTo;
    private javax.swing.ButtonGroup statusGroup;
    private javax.swing.JTextField supplierName;
    private javax.swing.JTextField taxRate;
    // End of variables declaration//GEN-END:variables
}
