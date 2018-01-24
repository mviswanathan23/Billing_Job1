package com.gates.smoketest;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

import com.gates.setup.utility;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class gatesBookingTest extends Login {	
	String IssueQuote;
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "//atu.properties");
	}

	@Test(description = "Gates Booking", dataProvider = "iteration")
	public void booking(Object args) throws Exception {	
		System.out.println("##### Gates - Booking Test Starts ####");
		resultStatus("Gates Booking", "******", " ", "Starts", "");
		
	
		try {			
			ATUReports.currentRunDescription = this.getClass().getSimpleName();
			ATUReports.setWebDriver(driver);
			ATUReports.indexPageDescription = "<h1><font color=\"blue\">GatesAutomation_SmokeTest_Result</font></h1>";
			ATUReports.setAuthorInfoAtClassLevel("Encore Team", Utils.getCurrentTime(), "1.0");
			
			utility gates_data = new utility();
			String Inputfile = System.getProperty("user.dir") + "//InputFiles//Inputs.xls";			
			gates_data.setInputFile(Inputfile);
			Thread.sleep(15000);
			Assert.assertEquals(driver.getTitle(), "GATES: Customer Profile");
			Thread.sleep(5000);
			hoverAndClick(new WebElement[] { getelement_cssPath("quote_menu") }, getelement_cssPath("maintquote_link"));		
			Thread.sleep(5000);		
			System.out.println("Test Info: MaintainQuote Test Started");			
			boolean clearmsgcheck = verifyMessagePresent("Please add record.").equals("true");
			if(clearmsgcheck){
				resultStatus("SMT_BKTS.01_TC.01", "pass", "", "User should able to  verify the data are cleared and the message been displayed “Please add record.”", "");
			}
			else{
				resultStatus("SMT_BKTS.01_TC.01", "fail", "", "User should able to  verify the data are cleared and the message been displayed “Please add record.”", "");
			}/*
			System.out.println("Test Info: Verified the Maintain Quote default exception message");
			selectvalue("customerName", gates_data.readdata("21_Maintain_Quotes", "SHIPPER_CUSTOMER3", Integer.valueOf(args.toString())));
			switchToWindow("GATES: Quick Search");
			Thread.sleep(3000);
			if (driver.getTitle().equals("GATES: Quick Search")) {
	             Thread.sleep(5000);
				// If all is displayed				
				if (isElementDisplayed(By.linkText("ALL"))) {
					clickLink("ALL").click();
					switchToWindow("GATES: Booking");
					selectFromDropDown(getUiElement_xpath("quote_contact_dd"), 1);
					if (getValue("phoneAreaCode", "value").isEmpty()) {
						//getUiElement_xpath("maintain_country_code").sendKeys(gates_data.readdata("01_Shipper", "COUNTRY_CODE", Integer.valueOf(args.toString())));
						getUiElement_xpath("phoneAreaCode").sendKeys(gates_data.readdata("01_Shipper", "AREA_CODE", Integer.valueOf(args.toString())));
						getUiElement_xpath("phoneExchange").sendKeys(gates_data.readdata("01_Shipper", "EXCHANGE", Integer.valueOf(args.toString())));
						getUiElement_xpath("phoneStation").sendKeys(gates_data.readdata("01_Shipper", "STATION_CODE", Integer.valueOf(args.toString())));

					}
					if (getValue("customer_phone", "value").equals("P")) {
						getUiElement_xpath("customer_phone").click();						
					}
				} 
			}		
			else{
				getUiElement_xpath("popupSearchaddRolDesc").click();
				switchToWindow("GATES: Quick Search");
				WebElement link=clickFirstLink("casQuickSearch","1","1");
				link.click();
				switchToWindow("GATES: Booking");
				
				selectFromDropDown(getUiElement_xpath("quote_contact_dd"), 1);
				if (getValue("phoneAreaCode", "value").isEmpty()) {					
					getUiElement_xpath("phoneAreaCode").sendKeys(gates_data.readdata("01_Shipper", "AREA_CODE", Integer.valueOf(args.toString())));
					getUiElement_xpath("phoneExchange").sendKeys(gates_data.readdata("01_Shipper", "EXCHANGE", Integer.valueOf(args.toString())));
					getUiElement_xpath("phoneStation").sendKeys(gates_data.readdata("01_Shipper", "STATION_CODE", Integer.valueOf(args.toString())));

				}
				if (getValue("customer_phone", "value").equals("P")) {
					getUiElement_xpath("customer_phone").click();					
				}
			}
			selectFromDropDown(getUiElement_xpath("customer_group"), gates_data.readdata("21_Maintain_Quotes", "CUSTOMER_GROUP", Integer.valueOf(args.toString())));
			selectFromDropDown(getUiElement_xpath("load_service_code"), gates_data.readdata("21_Maintain_Quotes", "LOAD_SERVICE",Integer.valueOf(args.toString())));
	    	Thread.sleep(500);
	    	selectFromDropDown(getUiElement_xpath("dicharge_service_code"), gates_data.readdata("21_Maintain_Quotes", "DIS_SERVICE",Integer.valueOf(args.toString())));
	    	selectvalue("routing_pol", gates_data.readdata("05_RoutingVVD", "PORT_OF_LOADING_EDIT", Integer.valueOf(args.toString())));
	    	selectvalue("routing_pod", gates_data.readdata("05_RoutingVVD", "PORT_OF_DISCHARGE_EDIT1", Integer.valueOf(args.toString())));
	    	sendtab("routing_pod");
	    	Thread.sleep(1000);
	    	System.out.println("Test Info: Routing grid details are populated.");
	    	clickLink("Cmdy Line").click();    	
	    	boolean commline = isElementDisplayed("comm_line_overlay");
			if (commline == true){			 
			  setvalue("comm_equip_size", gates_data.readdata("21_Maintain_Quotes", "COMM_TYPE", Integer.valueOf(args.toString())));
			  setvalue("comm_equip_size2", gates_data.readdata("21_Maintain_Quotes", "COMM_LENGTH", Integer.valueOf(args.toString())));
			  setvalue("comm_equip_height", gates_data.readdata("21_Maintain_Quotes", "COMM_HEIGHT", Integer.valueOf(args.toString())));			 
			  selectvalue("comm_tariff", gates_data.readdata("21_Maintain_Quotes", "COMM_TARIFF3", Integer.valueOf(args.toString())));
			  selectvalue("comm_item", gates_data.readdata("21_Maintain_Quotes", "COMM_ITEM3", Integer.valueOf(args.toString())));
			  Thread.sleep(1500);			  
			  clearvalue("comm_weight");
			  setvalue("comm_weight", gates_data.readdata("21_Maintain_Quotes", "COMM_WEIGHT", Integer.valueOf(args.toString())));
			  Thread.sleep(1500);
			  getUiElement_xpath("ss_ok").click();			 
			  
			}
			System.out.println("Test Info: Commodity Line details are populated.");
			getUiElement_xpath("ss_add").click();		
	    	boolean specialservice = isElementDisplayed("special_service_overlay");
			if (specialservice == true){		  
			  selectvalue("ss_cl", gates_data.readdata("21_Maintain_Quotes", "SS_CL", Integer.valueOf(args.toString())));			 
			  setvalue("ss_amt", gates_data.readdata("21_Maintain_Quotes", "SS_AMT", Integer.valueOf(args.toString())));
			  selectFromDropDown(getUiElement_xpath("ss_ratebasis"), gates_data.readdata("21_Maintain_Quotes", "SS_RATE", Integer.valueOf(args.toString())));
			  selectvalue("ss_payee", gates_data.readdata("21_Maintain_Quotes", "SS_PAYEE", Integer.valueOf(args.toString())));
			  Thread.sleep(1500);
			  getUiElement_xpath("ss_ok1").click();
			}		 
			System.out.println("Test Info: Special service grid details are populated.");
			getUiElement_xpath("quotesave").click();
			Thread.sleep(3000);
			if(verifyMessagePresent("Quote saved successfully.").equals("true")){
				System.out.println("Test Info: Quote saved successfully.");
				resultStatus("SMT_BKTS.01_TC.02", "pass", "", "After clicking on Save, Verify that the message—“Quote saved successfully” is displayed.", "");
				 setvalue("cl_level", gates_data.readdata("21_Maintain_Quotes", "CHARGELINE_LEVEL", Integer.valueOf(args.toString())));
				  setvalue("cl_comm", gates_data.readdata("21_Maintain_Quotes", "CHARGELINE_COMMODITY", Integer.valueOf(args.toString())));
				  selectvalue("cl_code", gates_data.readdata("21_Maintain_Quotes", "CHARGELINE_CODE", Integer.valueOf(args.toString())));
				  setvalue("cl_unit", gates_data.readdata("21_Maintain_Quotes", "CHARGELINE_UNIT", Integer.valueOf(args.toString())));
				  setvalue("cl_rate", gates_data.readdata("21_Maintain_Quotes", "CHARGELINE_RATE", Integer.valueOf(args.toString())));
				  selectFromDropDown(getUiElement_xpath("cl_ratebasis"), gates_data.readdata("21_Maintain_Quotes", "SS_RATE1", Integer.valueOf(args.toString())));
				 getUiElement_xpath("Payment_Add").click();  					
			}
			else{
				resultStatus("SMT_BKTS.01_TC.02", "fail", "", "After clicking on Save, Verify that the message—“Quote saved successfully” is displayed.", "");
			}
			String Bill_Status=getUiElement_xpath("status_code").getAttribute("value");		
			if(Bill_Status.equals("PEND"))	{
				resultStatus("SMT_BKTS.01_TC.03", "pass", "", "Verify that the status is in PEND.", "");
			}
			else{
				resultStatus("SMT_BKTS.01_TC.03", "fail", "", "Verify that the status is in PEND.", "");
			}
			getUiElement_xpath("save_calculate").click();
			Thread.sleep(5000);
			String Bill_Status2=getUiElement_xpath("status_code").getAttribute("value");		
			if(Bill_Status2.equals("APPR"))		{
				System.out.println("Test Info: Quote calculated successfully.");
				resultStatus("SMT_BKTS.01_TC.04", "pass", "", "Verify the STATUS changes to APPROVED.", "");
			getUiElement_xpath("issue").click();
			Thread.sleep(5000);	
			String Bill_Status3=getUiElement_xpath("status_code").getAttribute("value");
			if(Bill_Status3.equals("ISSD")){
				System.out.println("Test Info: Quote issued successfully.");
				resultStatus("SMT_BKTS.01_TC.05", "pass", "", "Verify that the quote has gone to the ISSD status.", "");
			IssueQuote = getUiElement_xpath("quote").getAttribute("value");
			System.out.println("Quote No. Issued: " +IssueQuote);
			}
			else{
				resultStatus("SMT_BKTS.01_TC.05", "fail", "", "Verify that the quote has gone to the ISSD status.", "");
			}
			}
			else{
				resultStatus("SMT_BKTS.01_TC.04", "fail", "", "Verify the STATUS changes to APPROVED.", "");
			}
			
		
			hoverAndClick(new WebElement[] { getelement("booking") }, getelement("maintain_booking"));
			Thread.sleep(10000);
			System.out.println("Test Info: Maintain booking page test started.");
			selectFromDropDown(getUiElement_xpath("trade_value"), gates_data.readdata("01_Shipper", "TRADE_GROUP", Integer.valueOf(args.toString())));
			selectFromDropDown(getUiElement_xpath("customer_groupId"), gates_data.readdata("01_Shipper", "CUSTOMER_GROUP", Integer.valueOf(args.toString())));
			getUiElement_xpath("quotePopUpSearch").click();
			Thread.sleep(2500);
			switchToWindow("GATES: Search Quote");
			Thread.sleep(2500);
			String Search_Quote = verifycontenttitle_xpath("Search Quote");
			if (Search_Quote == "true")
			{
				resultStatus("SMT_BKTS.01_TC.06", "pass", "", "Popup title should be “Search Quote”", "");			
			}
			else
			{
				resultStatus("SMT_BKTS.01_TC.06", "fail", "", "Popup title should be “Search Quote”", "");
			}
						
			setvalue("quote_field", IssueQuote);
			getUiElement_xpath("quote_search").click();	
			Thread.sleep(1500);
			WebElement link=clickFirstLink("casQuickSearch","1","1");		
			link.click();
			switchToWindow("GATES: Booking");
			if(verifyAlertMessage("Quote data loaded successfully.").equals("true")){
				System.out.println("Test Info: Issued Quote data loaded successfully in booking page.");
				resultStatus("SMT_BKTS.01_TC.07", "pass", "", "User should verify the message “Quote data loaded successfully.”", "");			
			}
			else{
				resultStatus("SMT_BKTS.01_TC.07", "fail", "", "User should verify the message “Quote data loaded successfully.”", "");
			}
			boolean shipperOrgName = isElementEnabled(getUiElement_xpath("Senddoc_organizationName"));
			boolean consigneeOrgName = isElementEnabled(getUiElement_xpath("consigneeOrgName"));
			boolean copyButton = isElementEnabled(getUiElement_xpath("copy_shipper_address"));
			boolean routing_POL=isElementEnabled(getUiElement_xpath("originPortCityCodeDescription"));
			boolean routing_POD=isElementEnabled(getUiElement_xpath("destinationPortCityCodeDescription"));
			System.out.println("Test Info: Performing issued quote and booking related test.");
			if ((shipperOrgName == true) && (consigneeOrgName == true) && (copyButton == true) && (routing_POL == true) && (routing_POD == true)){
				resultStatus("SMT_BKTS.01_TC.08", "pass", "", "User should able to edit the booking details loaded from quote search.", "");			
			}
			else{
				resultStatus("SMT_BKTS.01_TC.08", "fail", "", "User should able to edit the booking details loaded from quote search.", "");
			}

			boolean quotelink =isElementEnabled(getUiElement_xpath("quoteSelected"));
			if(quotelink){
				resultStatus("SMT_BKTS.01_TC.09", "pass", "", "User should verify the quote field has the value hyperlinked.", "");
			}
			else{
				resultStatus("SMT_BKTS.01_TC.09", "fail", "", "User should verify the quote field has the value hyperlinked.", "");
			}				
			
			if(!getValue("Senddoc_organizationName","value").equals("") && !getValue("shipperaddress","value").equals("") && !getValue("shippercontact","value").equals("") && !getValue("originPortCityCodeDescription","value").equals("") && !getValue("destinationPortCityCodeDescription","value").equals("")){
				resultStatus("SMT_BKTS.01_TC.10", "pass", "", "User should verify the Create Booking From Quote detail is also filled in.", "");
			}
			else{
				resultStatus("SMT_BKTS.01_TC.10", "fail", "", "User should verify the Create Booking From Quote detail is also filled in.", "");
			}
			if(copyButton) {
				getUiElement_xpath("copy_shipper_address").click();
				if(!getValue("consigneeOrgName","value").equals("") && !getValue("consgineeaddress","value").equals("") && !getValue("consigneecontact","value").equals("")){
					resultStatus("SMT_BKTS.01_TC.11", "pass", "", "Shipper details should get copied over to the consignee grids.", "");
				}
				else{
					resultStatus("SMT_BKTS.01_TC.11", "fail", "", "Shipper details should get copied over to the consignee grids.", "");
				}
				getUiElement_xpath("save").click();
				boolean ErrorAlert1 = verifyErrorAlertPresent("Autobill option is required").equals("true");
				boolean ErrorAlert2 = verifyErrorAlertPresent("Autobill trigger event is required").equals("true");	
				
				boolean ErrorAlert1 = verifyAlertMessage("Autobill option is required").equals("true");
				boolean ErrorAlert2 = verifyAlertMessage("Autobill trigger event is required").equals("true");	
				if((ErrorAlert1) && (ErrorAlert2)){
					resultStatus("SMT_BKTS.01_TC.12", "pass", "", "User should verify the error message should displayed as “Autobill option is required” and “Autobill trigger event is required”.", "");
				}
				else{
					resultStatus("SMT_BKTS.01_TC.12", "fail", "", "User should verify the error message should displayed as “Autobill option is required” and “Autobill trigger event is required”.", "");
				}
				selectFromDropDown(getUiElement_xpath("option"), 1);
				selectFromDropDown(getUiElement_xpath("event"), 1);
				selectFromDropDown(getUiElement_xpath("prepaidCollectCode"), 1);
				getUiElement_xpath("save").click();
				while (isAlertPresent()) {// If alert present in the page
					closeAlertAndGetItsText();// closing the alert
				}
				if(verifyAlertMessage("Booking saved successfully.").equals("true")){
					System.out.println("Test Info: Booking saved successfully using issued quote and status sets to INCOMPLETE.");
					resultStatus("SMT_BKTS.01_TC.13", "pass", "", "User should verify the message “Booking saved successfully.”", "");			
				}
				else{
					resultStatus("SMT_BKTS.01_TC.13", "fail", "", "User should verify the message “Booking saved successfully.”", "");
				}
				
				boolean statuscode = getDropDownval(getUiElement_xpath("status")).equals("INCOMPLETE");
				if(statuscode){
					resultStatus("SMT_BKTS.01_TC.14", "pass", "", "Status should set to INCOMPLETE after initial save.", "");
				}
				else{
					resultStatus("SMT_BKTS.01_TC.14", "fail", "", "Status should set to INCOMPLETE after initial save.", "");
				}
				getUiElement_xpath("expand_all").click();
				int RoutingTb_Count = tableCount("vvdRoutingGrid");
				getUiElement_xpath("divOfRoutingVVDlnk").click();			
				WebElement link2=clickFirstLink("vvdResultGrid","2","2");		
				link2.click();
				Thread.sleep(1500);
				int RoutingTb_Count2 = tableCount("vvdRoutingGrid");
				if (RoutingTb_Count2 > RoutingTb_Count){
					resultStatus("SMT_BKTS.01_TC.15", "pass", "", "VVD detail should be added under the VVD Routing grid.", "");
				}
				else{
					resultStatus("SMT_BKTS.01_TC.15", "fail", "", "VVD detail should be added under the VVD Routing grid.", "");
				}
				getUiElement_xpath("save").click();
				Thread.sleep(1500);
				while (isAlertPresent()) {// If alert present in the page
					closeAlertAndGetItsText();// closing the alert
				}
				
				boolean statuscode2 = getDropDownval(getUiElement_xpath("status")).equals("APPROVED");
				if(statuscode2){
					System.out.println("Test Info: Booking saved successfully using issued quote and status sets to APPROVED.");
					resultStatus("SMT_BKTS.01_TC.16", "pass", "", "Status should set to APPROVED from INCOMPLETE after populating all required fields and click save.", "");
				}
				else{
					resultStatus("SMT_BKTS.01_TC.16", "fail", "", "Status should set to APPROVED from INCOMPLETE after populating all required fields and click save.", "");
				}
				String BookingNo = getUiElement_xpath("bookingn").getAttribute("value");
				String QuoteNo = getUiElement_xpath("quoteSelected").getText();				
				if(QuoteNo.contains(BookingNo)){
					resultStatus("SMT_BKTS.01_TC.17", "pass", "", "User should verify Booking No & Quote No are same.", "");
				}
				else{
					resultStatus("SMT_BKTS.01_TC.17", "fail", "", "User should verify Booking No & Quote No are same.", "");
				}
			
				clickLinkByText("Remove Quote").click();
				boolean assignquotelink = isElementEnabled(getUiElement_xpath("assignquote"));
				if(assignquotelink){
					resultStatus("SMT_BKTS.01_TC.18", "pass", "", "User should verify the quote been removed and assign quote link is enabled.", "");
				}
				else{
					resultStatus("SMT_BKTS.01_TC.18", "fail", "", "User should verify the quote been removed and assign quote link is enabled.", "");
				}				
				System.out.println("Test Info: Quotes has been removed successfully from the booking.");
				getUiElement_xpath("assignquote").click();
				Thread.sleep(2500);
				switchToWindow("GATES: Search Quote");
				Thread.sleep(2500);
				getUiElement_xpath("quote_clear").click();
				getUiElement_xpath("quote_field").sendKeys(BookingNo);
				getUiElement_xpath("quote_search").click();
				Thread.sleep(1500);
				String quotenotexist = getUiElement_xpath("quote_norecordmsg").getText();
				if((quotenotexist).equals("No Records Found")){
					resultStatus("SMT_BKTS.01_TC.19", "pass", "", "User should verify one quote can be used to create one booking only, by verifying the message “No Records Found” after issued quotes removed.", "");
				}
				else{
					resultStatus("SMT_BKTS.01_TC.19", "fail", "", "User should verify one quote can be used to create one booking only, by verifying the message “No Records Found” after  issued quotes removed.", "");
				}
				System.out.println("#### Gates - Booking Test Ends ####");
			}*/
		}
				 
			 		
		catch (Exception e) {
			System.out.println(e);
		}
		resultStatus("Gates Booking", "******", " ", "Ends", "");	
		//new gatesBillingTest().billing(args);
		
			 }
			 

		}