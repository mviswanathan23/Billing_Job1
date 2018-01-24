package com.gates.spx;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.utils.Utils;

import com.gates.setup.utility;

@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class SPXBilling extends Login {
	String BookingN;
	//String cont2;
	String cont2;	
	String type2;
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "//atu.properties");
	}

	@Test(description = "SPX Billing", dataProvider = "iteration")
	public void billing(Object args) throws Exception {	
		
		resultStatus("SPX Billing", "******", " ", "Start", "");
		
	
		try {
			ATUReports.currentRunDescription = this.getClass().getSimpleName();
			ATUReports.setWebDriver(driver);
			ATUReports.indexPageDescription = "<h1><font color=\"blue\">Gates Automation </font></h1>";
			ATUReports.setAuthorInfoAtClassLevel("Encore Team", Utils.getCurrentTime(), "1.0");
			utility gates_data = new utility();
			String Inputfile = System.getProperty("user.dir") + "//InputFiles//Inputs.xls";
			//System.out.println(Inputfile);
			gates_data.setInputFile(Inputfile);
						
			Thread.sleep(5000);
			getUiElement_xpath("expand_all").click();
			selectvalue("mb_tariff",(gates_data.readdata("27_SPX", "TARIFF", Integer.valueOf(args.toString()))));
			selectvalue("mb_item",(gates_data.readdata("27_SPX", "ITEM", Integer.valueOf(args.toString()))));
			getUiElement_xpath("bill_save").click();
			Thread.sleep(3500);
			 if(verifyMessagePresent("Shipment updated successfully.").equals("true")){
					resultStatus("SPX_BK01.1_TS.01_TC.12", "Pass", "", "User should verify the update message for SPX billing after populating commodity grid.", "");
					 }
					 else{
						 resultStatus("SPX_BK01.1_TS.01_TC.12", "fail", "", "User should verify the update message for SPX billing after populating commodity grid.", "");
					 }
		getUiElement_xpath("mb_bill").click();
		Thread.sleep(3000);
		boolean unrlsdHold = isElementDisplayed("unrls_holdP");
    	String holdgrid = getUiElement_xpath("itnhold_grid").getText();    	
	
		if (unrlsdHold && holdgrid.equals("SEALM")){		  
			resultStatus("SPX_BK01.1_TS.01_TC.13", "Pass", "", "User should verify the unreleased HOLD for “SEALM”.", "");		
		}
		else{
			resultStatus("SPX_BK01.1_TS.01_TC.13", "fail", "", "User should verify the unreleased HOLD for “SEALM”.", "");
		}
		getUiElement_xpath("expand_all").click();			
		//driver.findElement(By.xpath("//*[@id='shipmentForm']/div[4]/div[5]/h3")).click();
		new WebDriverWait(driver,10).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("((//*[@id='commodityGrid']//*[@title='Edit selected row']/span)[last()])")))).click();
		clearvalue("seal_number");
		getUiElement_xpath("seal_number").sendKeys(gates_data.readdata("27_SPX", "SEAL", Integer.valueOf(args.toString())));
		new WebDriverWait(driver,10).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("((//*[@id='commodityGrid']//*[@title='OK']/span)[last()])")))).click();
		getUiElement_xpath("mb_bill").click();
		Thread.sleep(2000);
		if(isElementDisplayed("error_dialog")){
			getUiElement_xpath("err_exit").click();
			Thread.sleep(5000);
		}
		String bill_status = getUiElement_xpath("bill_status").getText();
		 System.out.println(bill_status);
		 if(bill_status.equals("DESCRIBED")){
			 resultStatus("SPX_BK01.1_TS.01_TC.14", "Pass", "", "User should verify the bill status sets to DESCRIBED on BILL.", "");
		 }
		 else{
			 resultStatus("SPX_BK01.1_TS.01_TC.14", "fail", "", "User should verify the bill status sets to DESCRIBED on BILL.", "");
		 } 
		 getUiElement_xpath("bl_charge").click();
		 Thread.sleep(2000);
		 selectFromDropDown(getUiElement_xpath("BL_shipmentChargeId"),gates_data.readdata("27_SPX", "CHARGE_CODE", Integer.valueOf(args.toString())));	
			setvalue("BL_rate",gates_data.readdata("27_SPX", "RATE_AMOUNT", Integer.valueOf(args.toString())));	
			selectFromDropDown(getUiElement_xpath("BL_RateBasis"),gates_data.readdata("27_SPX", "RATE_BASIS", Integer.valueOf(args.toString())));
			getUiElement_xpath("BL_addbtn").click();
			getUiElement_xpath("charge_bill").click();
			Thread.sleep(3500);
			if(verifyMessagePresent("Bill Manager request is successfully completed.").equals("true")){
				resultStatus("SPX_BK01.1_TS.01_TC.15", "Pass", "", "User should  verify the message “Bill Manager request is successfully completed.” after adding item charges in describe status.", "");
				 }
				 else{
					 resultStatus("SPX_BK01.1_TS.01_TC.15", "fail", "", "User should  verify the message “Bill Manager request is successfully completed.” after adding item charges in describe status.", "");
				 }
			if(isElementEnabled(getUiElement_xpath("reles_audit"))){
				getUiElement_xpath("reles_audit").click();
				Thread.sleep(2000);
			}
			String bill_status2=getUiElement_xpath("status_code").getText();
			 System.out.println(bill_status2);
			 if(bill_status2.equals("RATED")){
				 resultStatus("SPX_BK01.1_TS.01_TC.16", "Pass", "", "User should  verify the bill status sets to RATED after releasing audit. (DESCRIBED TO RATED)", "");
			 }
			 else{
				 resultStatus("SPX_BK01.1_TS.01_TC.16", "fail", "", "User should  verify the bill status sets to RATED after releasing audit. (DESCRIBED TO RATED)", "");
			 } 
			getUiElement_xpath("bl_payables").click();
			Thread.sleep(1500);
			selectFromDropDown(getUiElement_xpath("chargeCodeOverlay"),2);
			setvalue("unitsOverlay",gates_data.readdata("27_SPX", "UNITS", Integer.valueOf(args.toString())));
			setvalue("rateOverlay",gates_data.readdata("27_SPX", "RATE", Integer.valueOf(args.toString())));
			selectFromDropDown(getUiElement_xpath("27_SPX"),gates_data.readdata("04_BLPayables", "RATE_BASIS", Integer.valueOf(args.toString())));
			selectvalue("payeeOverlay",gates_data.readdata("27_SPX", "PAYEE", Integer.valueOf(args.toString())));
			
			Thread.sleep(1500);
			switchToWindow("GATES: Quick Search");
			if(driver.getTitle().equals("GATES: Quick Search")){
			switchToWindow("GATES: Quick Search");	
			Thread.sleep(2000);
			clickLink("ALL").click();
			}
			getUiElement_xpath("addCharge_Okbtn").click();
			Thread.sleep(1500);
			getUiElement_xpath("payabl_save").click();
			String bill_status3=getUiElement_xpath("billpayabl_status").getText();
			System.out.println(bill_status3);
			if(verifyMessagePresent("Shipment Saved Successfully").equals("true") && bill_status3.equals("DESCRIBED")){
				resultStatus("SPX_BK01.1_TS.01_TC.17", "Pass", "", "User should  verify the message “Shipment Saved Successfully” after adding payable and status sets to DESCRIBED.", "");
				 }
				 else{
					 resultStatus("SPX_BK01.1_TS.01_TC.17", "fail", "", "User should  verify the message “Shipment Saved Successfully” after adding payable and status sets to DESCRIBED.", "");
				 }
			
			getUiElement_xpath("paybl_bill").click();
			Thread.sleep(2500);
			String bill_status4=getUiElement_xpath("blcharge_status").getText();
			System.out.println(bill_status4);
			if (verifycontenttitle("B/L Charges").equals("true") && bill_status4.equals("IN AUDIT")) {
				resultStatus("SPX_BK01.1_TS.01_TC.18", "Pass", "", "User should  navigates to B/L Charge page and verify status sets to “IN AUDIT”.", "");
			} else {
				resultStatus("SPX_BK01.1_TS.01_TC.18", "fail", "", "User should  navigates to B/L Charge page and verify status sets to “IN AUDIT”.", "");
			}
			if(isElementEnabled(getUiElement_xpath("reles_audit"))){
				getUiElement_xpath("reles_audit").click();
				Thread.sleep(2000);
			}
			String bill_status5=getUiElement_xpath("status_code").getText();
			 System.out.println(bill_status5);
			 if(bill_status5.equals("RATED")){
				 resultStatus("SPX_BK01.1_TS.01_TC.19", "Pass", "", "User should  verify the bill status sets to RATED after adding payable and releasing audit. (DESCRIBED to RATED)", "");
			 }
			 else{
				 resultStatus("SPX_BK01.1_TS.01_TC.19", "fail", "", "User should  verify the bill status sets to RATED after adding payable and releasing audit. (DESCRIBED to RATED)", "");
			 } 
			 getUiElement_xpath("bl_payables").click();
			 Thread.sleep(2000);
			 getUiElement_xpath("payb_transmit").click();
			 if(verifyAlertMessage("Transmit Successful").equals("true")){
					resultStatus("SPX_BK01.1_TS.01_TC.20", "Pass", "", "Verify Payables have been transmitted successfully on clicking Transmit button.", "");
				} else {
					resultStatus("SPX_BK01.1_TS.01_TC.20", "Fail", "", "Verify Payables have been transmitted successfully on clicking Transmit button.", "");
				}
			 hoverAndClick(new WebElement[] { getelement("Billing_lnk"),getelement("SendBillDocument_lnk")}, getelement("SendBillDocument_lnk"));
				Thread.sleep(3500);
				getUiElement_xpath("sendself").click();
				if(verifyAlertMessage("Request submitted successfully").equals("true")){
					resultStatus("SPX_BK01.1_TS.01_TC.21", "Pass", "", "User should  verify the message “Request submitted successfully” for send to self.", "");
				}
				else {
					resultStatus("SPX_BK01.1_TS.01_TC.21", "Fail", "", "User should  verify the message “Request submitted successfully” for send to self.", "");
				}
				
				getUiElement_xpath("contact_chkbox2").click();
				getUiElement_xpath("contact_chkbox").click();
				getUiElement_xpath("saveandsend2").click();
				Thread.sleep(1500);
				
		}
				 
			 		
		catch (Exception e) {
			System.out.println(e);
		}
		resultStatus("SPX Billing", "******", " ", "Ends", "");
		
		
			 }
			 

		}