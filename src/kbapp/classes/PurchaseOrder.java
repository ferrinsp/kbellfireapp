/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kbapp.classes;

import java.util.ArrayList;
import java.util.List;
import kbapp.classes.*;
/**
 *
 * @author ferrinsp
 */
public class PurchaseOrder {
    
    public String purchaseOrderNum; 
    public String category;
    public String description;
    public String size;
    public String quanitity;
    public String jobSite;
    public String dateExpected; 
    public String deliveryContact;
    public String toAddress;
    public String shipToAddress;
    public String searchable;

    public String getPurchaseOrderNum() {
        return purchaseOrderNum;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public String getQuanitity() {
        return quanitity;
    }

    public String getJobSite() {
        return jobSite;
    }

    public String getDateExpected() {
        return dateExpected;
    }

    public String getDeliveryContact() {
        return deliveryContact;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getShipToAddress() {
        return shipToAddress;
    }

    public void setPurchaseOrderNum(String purchaseOrderNum) {
        this.purchaseOrderNum = purchaseOrderNum;
    }

    public void setCategory(String setCategory) {
        category = setCategory;
        updateSearch();
    }

    public void setDescription(String setDescription) {
        description = setDescription;
        updateSearch();
    }

    public void setSize(String setSize) {
        size = setSize;
        updateSearch();
    }

    public void setQuanitity(String setQuanitity) {
        quanitity = setQuanitity;
        updateSearch();
    }

    public void setJobSite(String jobSite) {
        this.jobSite = jobSite;
    }

    public void setDateExpected(String dateExpected) {
        this.dateExpected = dateExpected;
    }

    public void setDeliveryContact(String deliveryContact) {
        this.deliveryContact = deliveryContact;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public void setShipToAddress(String shipToAddress) {
        this.shipToAddress = shipToAddress;
    }
    
    public void updateSearch() {
        searchable = category + description + size + quanitity;
    }
            
}