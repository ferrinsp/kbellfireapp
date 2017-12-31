package kbapp.classes;

import java.util.ArrayList;
import java.util.List;
import kbapp.classes.*;

public class Supplier {
	
	public String companyID; 
	public String company;
        public String contact;
	public String address;
	public String city;
	public String state;
	public String postalCode;	
	public String phone;
        public String email;
        public String fax;
        public String terms;
        private static int columnLength = 30;
        
        
	public String getCompanyID(){
		return companyID;
	}
	public String getCompany(){
		return company;
	}
	public String getAddress(){
		return address;
	}
	public String getCity(){
		return city;
	}
	public String getState(){
		return state;
	}
	public String getPostalCode(){
		return postalCode;
	}
	public String getPhone() {
                return phone; 
        }
        public String getEmail(){
		return email;
	}
        public String getFax() {
                return fax; 
        }
        public String getTerms() {
                return terms; 
        }
        

	public void setCompanyId(String setCompanyID){
		companyID = setCompanyID;
	}
	public void setCompany(String setCompanyName){
		company = setCompanyName;
	}
	public void setAddress(String setAddress){
		address = setAddress;
	}
	public void setCity(String setCity){
		city = setCity;
	}
	public void setState(String setState){
		state = setState;
	}
	public void setPostalCode(String setPostalCode){
		postalCode = setPostalCode;
	}	
	public void setPhone(String setPhone){
                phone = setPhone;
        }
        public void setEmail(String setEmail){
		email = setEmail;
	}
        public void setFax(String setFax) {
                fax = setFax; 
        }
	public void setTerms(String setTerms) {
		terms = setTerms;
	}
}



























