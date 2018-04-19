
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import net.proteanit.sql.DbUtils;

public class UPurchaseOrder extends javax.swing.JFrame {

    public Color genericColor = new Color(209, 220, 204);    
    private final AlternatingListCellRenderer cellRenderer;
    Connection connObj = null;
    Statement stateObj = null;
    ResultSet resultObj = null;
    String [][] job = null;
    int id; //Set this later
    
    public UPurchaseOrder(int index) {
        this.cellRenderer = new AlternatingListCellRenderer();
        initComponents();
        getShipTo();
        this.id=index;
        selectpo();
    }
    public UPurchaseOrder() {
        this.cellRenderer = new AlternatingListCellRenderer();
        initComponents();
        getShipTo();
        selectpo();
    }
    private void selectpo()    {
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellPlumb?useSSL=false", "admin", "1qaz2wsx");
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
            resultObj = stateObj.executeQuery("select po.orderid, j.name, po.expectedby,u.name as 'user',s.companyname,j2.name as 'shipto',po.currentTax, po.created, po.status, po.comments,po.subtotal, po.bldg\n" +
"                from purchaseorder po inner join purchaseorderdetails pod on pod.orderid=po.orderid\n" +
"                inner join supplier s on s.supplierid=po.supplier inner join user u on u.userid=po.createdby\n" +
"                inner join job j on j.jobid=po.job inner join job j2 on j2.jobid=po.shipto where po.orderid="+id+";");//change to passed in variable
            Date d;
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            while (resultObj.next()){
                purchaseOrderNum.setText(Integer.toString(resultObj.getInt("orderid")));
                jobName.setText(resultObj.getString("name"));
                expectedDate.setDate(resultObj.getDate("expectedby"));
                createdBy.setText(resultObj.getString("user"));
                supplierName.setText(resultObj.getString("companyname"));
                taxRate.setText(Double.toString(resultObj.getDouble("currentTax")));
                shipToCombo.setSelectedItem(resultObj.getString("shipto"));
                d= resultObj.getDate("created");
                dateCreated.setText(df.format(d));
                commentsArea.setText(resultObj.getString("comments"));
                bldgTextField.setText(resultObj.getString("bldg"));
                subTotal.setText(resultObj.getString("subtotal"));
                switch (resultObj.getString("status")) {
                    case "Back Order":
                        rdbBackOrder.setSelected(true);
                        break;
                    case "Completed":
                        rdbCompleted.setSelected(true);
                        break;
                        case "Pending":
                        rdbPending.setSelected(true);
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
            connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellPlumb?useSSL=false", "admin", "1qaz2wsx");
            //Update 
            int detailID;
            int value;
            double total=0;
            double product=0;
            for (int i=0;i<itemTable.getRowCount();i++){
                detailID=Integer.parseInt(itemTable.getValueAt(i, 0).toString());
                value=Integer.parseInt(itemTable.getValueAt(i, 3).toString());
                product=Double.parseDouble(itemTable.getValueAt(i, 2).toString()) * Double.parseDouble(itemTable.getValueAt(i, 4).toString());
                total +=product;
                query="Update purchaseorderdetails set receivedqty=?, cost =?, total=?, orderqty =?  where detailsid="+detailID+";";
                //Get Values to insert
                preparedStmt =connObj.prepareStatement(query);
                preparedStmt.setInt (1, value);
                preparedStmt.setDouble(2,Double.parseDouble(itemTable.getValueAt(i, 4).toString()));
                preparedStmt.setDouble(3,product);
                preparedStmt.setInt(4,Integer.parseInt(itemTable.getValueAt(i, 2).toString())); 
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
            query = "Update purchaseorder set status=?, comments=? , expectedby =? , subtotal= ?, shipto=?, bldg=?, tax =?, total =?  where orderid="+id+";";
            //Get Values to update
            int ship=-1;
            for (String[] job1 : job) {
                        if (shipToCombo.getSelectedItem().equals(job1[1])) {
                            ship = Integer.parseInt(job1[0]);
                        }
                    }
            preparedStmt =connObj.prepareStatement(query);
            preparedStmt.setString (1, poStatus);
            preparedStmt.setString (2, commentsArea.getText());
            preparedStmt.setDate(3, new java.sql.Date(expectedDate.getDate().getTime()));
            preparedStmt.setDouble(4,total);
            preparedStmt.setInt(5,ship);
            preparedStmt.setString (6,bldgTextField.getText());
            double invTotal=(total* Double.parseDouble(taxRate.getText())/100)+total;
            preparedStmt.setDouble(7,(total* Double.parseDouble(taxRate.getText())/100));
            preparedStmt.setDouble(8,invTotal);
            preparedStmt.executeUpdate();
            connObj.close();
            this.id= -1;
            this.dispose();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }
    
    private void getShipTo() {
        try {
            //use your own username and login for the second and third parameters..I'll change this in the future to be dynamic
            connObj = DriverManager.getConnection("jdbc:mysql://192.168.1.10:3306/kbellPlumb?useSSL=false", "admin", "1qaz2wsx");
            stateObj = connObj.createStatement();
            resultObj = stateObj.executeQuery("select jobid, name from job ORDER BY name;");
            //Dynamically set job list size
            resultObj.last();
            job = new String[resultObj.getRow()][2];
            resultObj.beforeFirst();
            int i=0;
            while (resultObj.next()){
                job[i][0] =Integer.toString(resultObj.getInt("jobid"));
                job[i][1]=resultObj.getString("name");
                i++;
                shipToCombo.addItem(resultObj.getString("name"));
            }
            connObj.close();
        } catch (SQLException e) {
            Logger.getLogger(PreviewPurchaseOrder.class.getName()).log(Level.SEVERE, null, e);
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
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        expectedBy = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        taxRate = new javax.swing.JTextField();
        shipToCombo = new javax.swing.JComboBox<>();
        dateCreated = new javax.swing.JTextField();
        createdBy = new javax.swing.JTextField();
        supplierName = new javax.swing.JTextField();
        expectedDate = new org.jdesktop.swingx.JXDatePicker();
        jobName = new javax.swing.JTextField();
        purchaseOrderNum = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        subTotal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        bldgTextField = new javax.swing.JTextField();
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
        rdbPending = new javax.swing.JRadioButton();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update Purchase Order");

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

        jLabel8.setText("Tax Rate:");

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

        shipToCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ship To" }));

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

        Date date = new Date();
        expectedDate.setDate(date);

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

        jLabel1.setText("Purchase Order #");

        jLabel2.setText("Job:");

        jLabel3.setText("Expected Date:");

        jLabel4.setText("Supplier:");

        jLabel5.setText("Created By:");

        jLabel6.setText("Created Date:");

        jLabel7.setText("Ship To:");

        jLabel11.setText("Sub Total:");

        subTotal.setEditable(false);
        subTotal.setText("SubTotal");
        subTotal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                subTotalFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                subTotalFocusLost(evt);
            }
        });

        jLabel12.setText("Bldg:");

        bldgTextField.setText(" ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(148, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jobName)
                    .addComponent(purchaseOrderNum)
                    .addComponent(supplierName)
                    .addComponent(createdBy)
                    .addComponent(dateCreated)
                    .addComponent(taxRate)
                    .addComponent(expectedDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(shipToCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(subTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bldgTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(purchaseOrderNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jobName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(expectedDate, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(supplierName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(createdBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(dateCreated, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(shipToCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(bldgTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(taxRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(subTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jButton2)
                .addGap(254, 254, 254))
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

        statusGroup.add(rdbPending);
        rdbPending.setSelected(true);
        rdbPending.setText("Pending");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(141, 141, 141)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(rdbPending, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(rdbIssued, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rdbBackOrder)
                        .addGap(18, 18, 18)
                        .addComponent(rdbCompleted)
                        .addGap(18, 18, 18)))
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdbIssued)
                        .addComponent(rdbCompleted)
                        .addComponent(rdbBackOrder)
                        .addComponent(rdbPending)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel10))
                .addContainerGap(21, Short.MAX_VALUE))
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

    private void taxRateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_taxRateFocusLost
        if(taxRate.getText().equals(""))
            taxRate.setText("Tax Rate");
    }//GEN-LAST:event_taxRateFocusLost

    private void taxRateFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_taxRateFocusGained
        if(taxRate.getText().equals("Tax Rate"))
            taxRate.setText("");
    }//GEN-LAST:event_taxRateFocusGained

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void subTotalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_subTotalFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_subTotalFocusGained

    private void subTotalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_subTotalFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_subTotalFocusLost

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
    private javax.swing.JTextField bldgTextField;
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
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jobName;
    private javax.swing.JTextField purchaseOrderNum;
    private javax.swing.JRadioButton rdbBackOrder;
    private javax.swing.JRadioButton rdbCompleted;
    private javax.swing.JRadioButton rdbIssued;
    private javax.swing.JRadioButton rdbPending;
    private javax.swing.JComboBox<String> shipToCombo;
    private javax.swing.ButtonGroup statusGroup;
    private javax.swing.JTextField subTotal;
    private javax.swing.JTextField supplierName;
    private javax.swing.JTextField taxRate;
    // End of variables declaration//GEN-END:variables
}
