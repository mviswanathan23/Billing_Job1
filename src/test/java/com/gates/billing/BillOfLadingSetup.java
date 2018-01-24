package com.gates.billing;

import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
public class BillOfLadingSetup extends Login {
	String billPending;
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "//atu.properties");
	}
	public static int i;
	public static int Rcount;
	@Test(description = "Bill Of Lading Setup", dataProvider = "iteration")
	public void BillofladingSetup(Object args) throws Exception {
				
			utility gates_data = new utility();
			String Inputfile = System.getProperty("user.dir") + "//InputFiles//Inputs.xls";
			gates_data.setInputFile(Inputfile);
			Thread.sleep(2000);
			//System.out.println("Test Info - Working on getting test data for BL setup.");
			WebElement hoveKey[] = {getelement("click_billing"),getelement("BillingSearch_lnk")};
			hoverAndClick(hoveKey, getelement("BillingSearch_lnk"));	
			Thread.sleep(5000);			
			System.out.println("Test Info: Working on getting test data for -B/L Setup.");
			getUiElement_xpath("maintain_billing_clear").click();
			Thread.sleep(2500);			
			getUiElement_xpath("maintain_billing_clear").click();
			Thread.sleep(2500);	
			selectFromDropDown(getUiElement_xpath("maintain_billing_shipment_status"), "PENDING");		
			selectFromDropDown(getUiElement_xpath("bs_trade"), "H-HAWAII");
			selectFromDropDown(getUiElement_xpath("bs_ls"), "CY");
			selectFromDropDown(getUiElement_xpath("bs_ds"), "CY");
			clearvalue("billing_shipment_tracking_date");
			getUiElement_xpath("billing_shipment_tracking_date").sendKeys("01-01-2011");

			getUiElement_xpath("maintain_billing_search").click();
			Thread.sleep(2500);
			 int tablecount = tableCount("casQuickSearch");
			   for(int a=1;a<tablecount;a++){
			    boolean seq = Pattern.matches("000", (gettextval(".//*[@id='casQuickSearch']/tbody/tr["+a+"]/td[3]")));
			    System.out.println("seq#  : " +seq);
			    String billnumber = gettextval(".//*[@id='casQuickSearch']/tbody/tr["+a+"]/td[2]");	    
			    boolean checkingTheValue = seq;
			    System.out.println("checkingTheValue: " +checkingTheValue);
			    if(checkingTheValue){
			    	billPending = billnumber;
			    break;
			    }
			    }
			   System.out.println("Test Info: Test data retrieved.");
			hoverAndClick(new WebElement[] { getelement("Billing_lnk"),getelement("BOLSetup_lnk") }, getelement("BOLSetup_lnk"));
			Thread.sleep(2000);
			System.out.println("Test Info: Billng -B/L Setup Started");
			if (verifycontenttitle("Bill Of Lading Setup").equals("true")) {
				resultStatus("BL01.1_TS_01_TC.01", "Pass", "", "To verify user to be able to navigate Bill of lading setup screen", "");
			} else {
				resultStatus("BL01.1_TS_01_TC.01", "Fail", "", "To verify user to be able to navigate Bill of lading setup screen", "");
				
			}
			
			
			clearvalue("shipmentNumber");
			getUiElement_xpath("billSetupGoBtn").click();
			Thread.sleep(2000);
			if(verifyAlertMessage("* This field is required").equals("true")) {
				selectvalue("shipmentNumber",billPending);
						//gates_data.readdata("02_BOLSetUp", "SHIPMENT_NUMBER", Integer.valueOf(args.toString())));					
				resultStatus("BL01.1_TS_01_TC.02", "Pass", "", "To verify data is provided in all  mandatory fields of BLSU Screen", "");
			} else {
				resultStatus("BL01.1_TS_01_TC.02", "Fail", "", "To verify data is provided in all  mandatory fields of BLSU Screen", "");
			}
			selectvalue("BillingSequenceNo",gates_data.readdata("02_BOLSetUp", "SEQ_Number", Integer.valueOf(args.toString())));
			getUiElement_xpath("billSetupGoBtn").click();	
			Thread.sleep(2500);
			//'Check the LDSP 	
			if(!gettextval(".//*[@id='ldServiceCode']").isEmpty()){
				
				resultStatus("BL01.1_TS_01_TC.03", "Pass", "", "To verify Load Discharge Service pair is reflected for the respective booking in BLSU Screen", "");
			} else {
				resultStatus("BL01.1_TS_01_TC.03", "Fail", "", "To verify Load Discharge Service pair is reflected for the respective booking in BLSU Screen", "");
			}
			
			//'Shipment should be loaded
			
			if(verifyAlertMessage("Booking Found.").equals("true") || verifyAlertMessage("Successfully Loaded.").equals("true")){
			//if(verifyAlertMessage("Successfully Loaded.").equals("true")){
				resultStatus("BL01.1_TS_01_TC.04", "Pass", "", "To verify Shipment is loaded successfully using predictive search", "");
			} else {
				resultStatus("BL01.1_TS_01_TC.04", "Fail", "", "To verify Shipment is loaded successfully using predictive search", "");
			}
			
			//'Check the checkbox for which SEQ number is not present and link the container to the Booking
			Thread.sleep(1500);
			Rcount=tableCount("containerGrid");
			System.out.println(Rcount);
			for (i=2;i<=Rcount;i++)
			{
				System.out.println(i);
				//if(gettextval("//tr["+i+"]//td[4]").isEmpty())				
				if(gettextvalid("manualSeqNo").isEmpty())
				{
					//selectFromDropDown(getUiElement_xpath("responsiblePartyCode"),"");
					selectcheckbox("//*[@id='containerGrid']/tbody/tr["+i+"]/td[1]/input");
					resultStatus("BL01.1_TS_01_TC.05", "Pass", "", "To Verify that when Manual sequence number field is blank ,then while linking system should link the freight with the displayed bill sequence Number.", "");
					//getUiElement_xpath("billingSave").click();
					break;
				}
				else
				{
					resultStatus("BL01.1_TS_01_TC.05", "Fail", "", "To Verify that when Manual sequence number field is blank ,then while linking system should link the freight with the displayed bill sequence Number.", "");
				}
			}
			
			//'This continue button is displayed when Booking is an AutoBill Booking
			Thread.sleep(2000);
			System.out.println("welcome");			
			
			Thread.sleep(1500);
			//'Please select Debtor Type
			selectFromDropDown(getUiElement_xpath("responsiblePartyCode"),"");
			//selectcheckbox("//*[@id='containerGrid']/tbody/tr["+i+"]/td[1]/input");
			getUiElement_xpath("billingSave").click();
			Thread.sleep(3500);
			if(driver.findElement(By.xpath("//*[@id='billingSetUpWarningDialog']")).isDisplayed()){
				//if(driver.findElement(By.xpath("//*[@id='autoBillingWarning']")).isDisplayed()){
					getUiElement_xpath("Continuebtn").click();
					resultStatus("BL01.1_TS_01_TC.06", "Pass", "", "To Verify that the billing is an AutoBill Booking", "");
			}
			if(verifyAlertMessage("Please select the Debtor type.").equals("true")){
				resultStatus("BL01.1_TS_01_TC.07", "Pass", "", "To Verify that the debtor type is selected validation is checked before linking.", "");
				System.out.println("welcome");								
				selectFromDropDown(getUiElement_xpath("responsiblePartyCode"),"Prepaid");
				getUiElement_xpath("billingSave").click();	
				Thread.sleep(3500);
				if(driver.findElement(By.xpath("//*[@id='billingSetUpWarningDialog']")).isDisplayed()){
					//if(driver.findElement(By.xpath("//*[@id='autoBillingWarning']")).isDisplayed()){
						getUiElement_xpath("Continuebtn").click();
						resultStatus("BL01.1_TS_01_TC.08", "Pass", "", "To Verify that the billing is an AutoBill Booking", "");
				}
			} else {
				resultStatus("BL01.1_TS_01_TC.07", "Fail", "", "To Verify that the debtor type is selected validation is checked before linking.", "");
			}
			
				
			Thread.sleep(3500);	
			if(verifyAlertMessage("Successfully Loaded.").equals("true"))
			{
				resultStatus("BL01.1_TS_01_TC.09", "Pass", "", "To Verify shipment is successfully linked to the container", "");
				if(!gettextval("//tr["+i+"]//td[4]").isEmpty()){					
					resultStatus("BL01.1_TS_01_TC.10", "Pass", "", "To Verify that when Manual sequence number field contains data while container is linked to the shipment", "");
				} 
				} else if(verifyAlertMessage("Container not part of pro-rated Shipment; cannot link to another bill.").equals("true") || verifyAlertMessage("Selected item already linked, cannot update.").equals("true")){
					if(!gettextval("//tr["+i+"]//td[4]").isEmpty()){
						resultStatus("BL01.1_TS_01_TC.09", "Pass", "", "To Verify shipment is successfully linked to the container", "");
						resultStatus("BL01.1_TS_01_TC.10", "Pass", "", "To Verify that when Manual sequence number field contains data while container is linked to the shipment", "");
					} 
					getUiElement_xpath("billSetupGoBtn").click();
					Thread.sleep(2500);
					if(getUiElement_xpath("setupBillBtn").isEnabled()){
					getUiElement_xpath("setupBillBtn").click();					
					//driver.findElement(By.xpath("/html/body/div[22]/div[1]/a/span")).click();
					while(isAlertPresent())
					{
						closeAlertAndGetItsText();
					}
					Thread.sleep(5000);	
					boolean errchoice3 = getelement("err_gr").isDisplayed();	
					boolean errchoice4 = getelement("err_gr2").isDisplayed();			
						if(errchoice3 || errchoice4){			
							getUiElement_xpath("err_exit").click();
							Thread.sleep(2500);
						}
					
					if (verifycontenttitle_xpath("Maintain Bill").equals("true")) {
					//if(st){
						resultStatus("BL01.1_TS_01_TC.13", "Pass", "", "To Verify that on clicking Bill Button Maintain Bill  Screen is displayed for Unsuccessfull Bill", "");
						hoverAndClick(new WebElement[] { getelement("Billing_lnk"),getelement("BOLSetup_lnk") }, getelement("BOLSetup_lnk"));	
						Thread.sleep(5000);
						selectvalue("pod",gates_data.readdata("02_BOLSetUp", "POD_CHANGE", Integer.valueOf(args.toString())));
						getUiElement_xpath("billSetupGoBtn").click();
						Thread.sleep(2500);
						int PODRowCount = tableCount("containerGrid");
						if(PODRowCount<Rcount){	
							resultStatus("BL01.1_TS_01_TC.11", "Pass", "", "To Verify that on changing POD the Container Grid does not contains value.", "");
						} else {
							resultStatus("BL01.1_TS_01_TC.11", "Fail", "", "To Verify that on changing POD the Container Grid does not contains value.", "");
						}
					} 	
					else if (verifycontenttitle_xpath("B/L Charges").equals("true")) {
							resultStatus("BL01.1_TS_01_TC.13", "Pass", "", "To Verify that on clicking Bill Button BL Charges Screen is displayed for successfull Bill", "");
						}	
					
					}
				}
			System.out.println("Test Info: Billng -B/L Setup Ends");
		//new BL_Charges().BLCharges(args);
	}
}