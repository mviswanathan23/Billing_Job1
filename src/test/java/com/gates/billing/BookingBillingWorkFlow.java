package com.gates.billing;


import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.utils.Utils;

import com.gates.setup.utility;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class BookingBillingWorkFlow extends Login {
	int a;
	 String Eqnumber_billing;
	 String Eqnumber_booking;
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "//atu.properties");
	}
	
	@Test(description = "Container Linkage", dataProvider = "iteration")
	public void ContainerLinkage(Object args) throws Exception {
		
		ATUReports.currentRunDescription = this.getClass().getSimpleName();
		ATUReports.setWebDriver(driver);
		ATUReports.indexPageDescription="<h1><font color=\"blue\">Gates Automation </font></h1>";
		ATUReports.setAuthorInfoAtClassLevel("Encore Team", Utils.getCurrentTime() ,"1.0");
		utility gates_data = new utility();
		String Inputfile=System.getProperty("user.dir")+"//InputFiles//Inputs.xls";
		gates_data.setInputFile(Inputfile);
		Assert.assertEquals("GATES: Customer Profile", driver.getTitle());
		Thread.sleep(1500);
		
		  hoverAndClick(new WebElement[]{getelement("Billing_lnk")}, getelement("billing_workQueue"));
		  Thread.sleep(10000);
		 // getUiElement_xpath("billing_workqueue_clear").click();
		  System.out.println("Test Info: Working on getting test data for Container Linkage.");
		  selectFromDropDown(getUiElement_xpath("trade_group2"), "H-HAWAII");
		  selectFromDropDown(getUiElement_xpath("bill_ls"), "CY");
			selectFromDropDown(getUiElement_xpath("bill_ds"), "CY");
		  selectFromDropDown(getUiElement_xpath("billing_source"), "Billing");		  		 
			selectFromDropDown(getUiElement_xpath("billing_status"), "PENDING");
		  Thread.sleep(2000);
		  getUiElement_xpath("billing_user_id").clear();
		  getUiElement_xpath("billing_workqueue_search").click();
		  Thread.sleep(5000);
		  getUiElement_xpath("billing_equip_link").click();//for ascending
		  Thread.sleep(5000);
		 // getUiElement_xpath("billing_equip_link").click();//for decending
		  Thread.sleep(5000);
		  int tablecount = tableCount("casQuickSearch");
		   for(int a=1;a<tablecount;a++){
		    boolean bookAndReceived = Pattern.matches("([1-9])([1-9]?)/([1-9])([1-9]?)(/[1-9])([1-9]?)", (gettextval(".//*[@id='casQuickSearch']/tbody/tr["+a+"]/td[8]")));
		    System.out.println("bookAndReceived  : " +bookAndReceived);		  
		    String Eqnumber = gettextval(".//*[@id='casQuickSearch']/tbody/tr["+a+"]/td[9]");
		    System.out.println("unitValue   : " +Eqnumber);
		    boolean checkingTheValue = bookAndReceived && !Eqnumber.equals("1 UNIT") && !Eqnumber.equals("No Equp/Pc") && !Eqnumber.contains("Multi");
		    System.out.println("checkingTheValue    : " +checkingTheValue);
		    if(checkingTheValue){
		    	Eqnumber_billing = Eqnumber;
		    	System.out.println(Eqnumber_billing);
		    	break;
		    }
		    
	       }
		   hoverAndClick(new WebElement[]{getelement("Billing_lnk")}, getelement("billing_workQueue"));
			  Thread.sleep(5000);
		   getUiElement_xpath("search_filter").click();
			  getUiElement_xpath("billing_workqueue_clear").click();
			  Thread.sleep(5000);
			  selectFromDropDown(getUiElement_xpath("trade_group2"), "H-HAWAII");
			  selectFromDropDown(getUiElement_xpath("billing_source"), "Booking");
			  selectFromDropDown(getUiElement_xpath("billing_status"), "APPROVED");
			  Thread.sleep(2000);
			  getUiElement_xpath("billing_user_id").clear();
			  getUiElement_xpath("billing_workqueue_search").click();
			  Thread.sleep(5000);
			  getUiElement_xpath("billing_reason_link").click();//for ascending
			  Thread.sleep(5000);
			  getUiElement_xpath("billing_reason_link").click();//for decending
			  Thread.sleep(5000);
			  int tablecount2 = tableCount("casQuickSearch");
			   for(int a=1;a<tablecount2;a++){
			    boolean bookAndReceived = Pattern.matches("([1-9])([1-9]?)/(1)(/0)", (gettextval(".//*[@id='casQuickSearch']/tbody/tr["+a+"]/td[8]")));
			    System.out.println("bookAndReceived  : " +bookAndReceived);		  
			    String Eqnumber = gettextval(".//*[@id='casQuickSearch']/tbody/tr["+a+"]/td[9]");
			    System.out.println("unitValue   : " +Eqnumber);
			    boolean checkingTheValue = bookAndReceived && !Eqnumber.equals("1 UNIT") && !Eqnumber.equals("No Equp/Pc") && !Eqnumber.contains("Multi");
			    System.out.println("checkingTheValue    : " +checkingTheValue);
			    if(checkingTheValue){
			    	Eqnumber_booking = Eqnumber;
			    	System.out.println(Eqnumber_booking);
			    	break;
			    }
			    
		       }
			   //getUiElement_xpath("search_filter").click();
			   //driver.navigate().
			   System.out.println("Test Info:Test data retrieved.");
		hoverAndClick(new WebElement[] { getelement("Booking_Parentlnk"), getelement("Container_lnk"),getelement("Container_Linkage_lnk2") }, getelement("Container_Linkage_lnk2"));
		Thread.sleep(2000);
		System.out.println("Test Info: Billng - Container Linkage Started");
	//'Click on Container Linkage to link container to the Approved Booking
		
			if (verifycontenttitle("Container Linkage").equals("true")) {
		resultStatus("BK.04.05_TS_01_TC.01", "Pass", "", "To verify user able to navigate Container Linkage Screen", "");
	} else {
		resultStatus("BK.04.05_TS_01_TC.01", "Fail", "", "To verify user able to navigate Container Linkage Screen", "");
	}
	
	selectvalue("containerHead", Eqnumber_billing);
			//gates_data.readdata("01_ContainerLinkage", "CONTAINER_NUMBER", Integer.valueOf(args.toString())));	
	 if(verifyAlertMessage("SuccessFully Displayed.").equals("true")){
		resultStatus("BK.04.05_TS_01_TC.02", "Pass", "", "To verify Container loaded Sucessfully", "");
	} else {
		resultStatus("BK.04.05_TS_01_TC.02", "Fail", "", "To verify Container loaded Sucessfully", "");
	}
	 
		while(clickLinkByText("Billing Started").isDisplayed())
	{
		resultStatus("BK.04.05_TS_01_TC.05", "Pass", "", "To verify  Billing has already started for the Container", "");
		String Current_Booking=getValue("currentBookingForDisplay","value");
		hoverAndClick(new WebElement[] { getelement("Billing_lnk"), getelement("BillingSearch_lnk")}, getelement("BillingSearch_lnk"));		
		Thread.sleep(2500);
		selectvalue("in_shipment_number", Current_Booking);		
		getUiElement_xpath("RemittAdvice_Search").click();
		Thread.sleep(3000);		
		if(getUiElement_xpath("BillingSrh_grid").isDisplayed())			
		{
			 int rcount = tableCount("casQuickSearch");
			   for(int a=1;a<rcount;a++){			   
			    String resultValue = gettextval(".//*[@id='casQuickSearch']/tbody/tr["+a+"]/td[9]");			   
			    System.out.println("Bill Status   : " +resultValue);
			    boolean checkingTheValue = !resultValue.equals("CORRECTED") && !resultValue.equals("F/C PENDING") && !resultValue.equals("F/C DESCRIBED") && !resultValue.equals("F/C RATED") && !resultValue.equals("F/C AUDIT");
			   
			    if(checkingTheValue){		
			selectcheckbox("//*[@id='casQuickSearch']/tbody/tr["+a+"]/td[1]/input");
			getUiElement_xpath("deleteQA").click();
			break;
			   }			  
			   }
			if(getUiElement_xpath("ui-dialog-title-shipment_deleteQA").isDisplayed())
			{
				getUiElement_xpath("BiilingSrh_Yesbtn").click();
			}
					
			Thread.sleep(5000);
			boolean msgexst = isElementDisplayed("msgchk");	
			if(msgexst){
			String Msg = driver.findElement(By.id("resultdiv")).getText();
			 if(Msg.equals("No Records Found")){					
					Assert.assertEquals(Msg, "No Records Found");
					resultStatus("BK.04.05_TS_01_TC.06", "Pass", "", "To verify that a shipment entry deleted successfully.", "");
				}
			}						
			else if(getUiElement_xpath("BillingSrh_grid").isDisplayed())			
			{
			int rcount2=tableCount("casQuickSearch");
			System.out.println(rcount2);
			if(rcount2<rcount){
				resultStatus("BK.04.05_TS_01_TC.06", "Pass", "", "To verify that a shipment entry deleted successfully.", "");
			}
			}
			   
			else {
				resultStatus("BK.04.05_TS_01_TC.06", "Fail", "", "To verify that a shipment entry deleted successfully.", "");
			}
		
		break;
	}		 	
	}
	hoverAndClick(new WebElement[] { getelement("Booking_Parentlnk"), getelement("Container_lnk"),getelement("Container_Linkage_lnk2") }, getelement("Container_Linkage_lnk2"));
	//selectvalue("moveToBooking",gates_data.readdata("01_ContainerLinkage", "MOVE_TO_BOOKING", Integer.valueOf(args.toString())));
	//selectvalue("containerHead",gates_data.readdata("01_ContainerLinkage", "CONTAINER_NUMBER2", Integer.valueOf(args.toString())));
	/*if(!clickLinkByText("Billing Started").isDisplayed())
	{*/
	clearvalue("containerHead");
	selectvalue("containerHead", Eqnumber_booking);
	selectvalue("moveToBooking",gates_data.readdata("01_ContainerLinkage", "MOVE_TO_BOOKING", Integer.valueOf(args.toString())));
	if(getValue("moveToAssignedDate","value").equals(""))
	{
		//clearvalue("moveToAssignedDate");
		setvalue("moveToAssignedDate",gates_data.readdata("01_ContainerLinkage", "ASSIGNED_DATE", Integer.valueOf(args.toString())));
	}
	
	if(getValue("moveToAssignedTime","value").equals(""))
	{
		setvalue("moveToAssignedTime",gates_data.readdata("01_ContainerLinkage", "ASSIGNED_TIME", Integer.valueOf(args.toString())));
	}
	
	if(getValue("moveToReleasedDate","value").equals(""))
	{
		setvalue("moveToReleasedDate",gates_data.readdata("01_ContainerLinkage", "RELEASE_DATE", Integer.valueOf(args.toString())));
	}
	
	if(getValue("moveToReleasedTime","value").equals(""))
	{
		setvalue("moveToReleasedTime",gates_data.readdata("01_ContainerLinkage", "RELEASE_TIME", Integer.valueOf(args.toString())));
	}
	
	if(isElementEnabled(getUiElement_xpath("transferBtn"))==true){
			resultStatus("BK.04.05_TS_01_TC.03", "Pass", "", "To verify  Transfer button is enabled if the booking is already present", "");
	} else {
			resultStatus("BK.04.05_TS_01_TC.03", "Fail", "", "To verify  Transfer button is enabled if the booking is already present", "");
	}
				
	getUiElement_xpath("transferBtn").click();
	Thread.sleep(2000);
	if(verifyAlertMessage("Successfully Transferred.").equals("true")){
		resultStatus("BK.04.05_TS_01_TC.04", "Pass", "", "To verify booking has been successfully transferred", "");
	} else {
		resultStatus("BK.04.05_TS_01_TC.04", "Fail", "", "To verify booking has been successfully transferred", "");
	}
	
	if(isElementEnabled(getUiElement_xpath("unlinkBtn"))==true){
		resultStatus("BK.04.05_TS_01_TC.08", "Pass", "", "To verify  Unlink button is enabled if the booking is already present", "");
	} else {
		resultStatus("BK.04.05_TS_01_TC.08", "Fail", "", "To verify  Unlink button is enabled if the booking is already present", "");
	}
	
	getUiElement_xpath("unlinkBtn").click();
	Thread.sleep(2000);
	if(verifyMessagePresent("Successfully Unlinked.").equals("true")){
		resultStatus("BK.04.05_TS_01_TC.09", "Pass", "", "To verify booking has been successfully unliked", "");
	} else {
		resultStatus("BK.04.05_TS_01_TC.09", "Fail", "", "To verify booking has been successfully unliked", "");
	}
	System.out.println("Test Info: Billng - Container Linkage Ends");
	new BillOfLadingSetup().BillofladingSetup(args);	
	//driver.quit();
	}
	
	
}
	