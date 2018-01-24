package com.gates.billing;


import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;
import atu.testng.reports.utils.Utils;

import com.gates.setup.utility;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class BL_Payables extends Login {
	String billIssd;
	@Test(description = "BLCharges", dataProvider = "iteration")
	public void BLPayables(Object args) throws Exception {
		
		
		
		utility gates_data = new utility();
		String Inputfile=System.getProperty("user.dir")+"//InputFiles//Inputs.xls";
		gates_data.setInputFile(Inputfile);
		String Shipment_NumberPayable=null;
		
		//hoverAndClick(new WebElement[] { getelement("Billing_lnk"),getelement("billing_maintain_billing"),getelement("BLPay_Link") }, getelement("BLPay_Link"));
		Thread.sleep(2500);
		System.out.println("Test Info: Billng -BL Payables Started");
	if (verifycontenttitle("B/L Payables").equals("true")) {
		resultStatus("BL04.2_TS_01_TC.01", "Pass", "", "To verify  user  to be able  to navigate BL Payable screen", "");
	} else {
		resultStatus("BL04.2_TS_01_TC.01", "Fail", "", "To verify  user  to be able  to navigate BL Payable screen", "");
	}
	//selectvalue("shipmentNumber", "2075559");
	Thread.sleep(5000);
	Shipment_NumberPayable=getValue("shipmentNumber","value");
	System.out.println(Shipment_NumberPayable);
	String Seq_Number=getValue("BLCharge_shipmentSequenceNumber","value");
	System.out.println(Seq_Number);
	clearvalue("BLPayables_shipmentNumber");
	//sendtab("BLPayables_shipmentNumber");
	getUiElement_xpath("BLPayables_gobtn").click();	
	Thread.sleep(1500);
	boolean billingNumberValidation = verifyAlertMessage("* This field is required").equals("true");	
	boolean billingNumberValidation2 = verifyAlertMessage("*This field is mandatory").equals("true");
	if(billingNumberValidation || billingNumberValidation2){
		resultStatus("BL04.2_TS_01_TC.02", "Pass", "", "User should be able to verify all mandatory fields of BLpayables screen.", "");
	} else {
		resultStatus("BL04.2_TS_01_TC.02", "Fail", "", "User should be able to verify all mandatory fields of BLpayables screen.", "");
	}
	Thread.sleep(1500);	

	selectvalue("BLPayables_shipmentNumber",Shipment_NumberPayable);
	selectvalue("BLPayables_shipmentSequenceNumber",Seq_Number);		

	//selectvalue("shipmentNumber", "5696372");
	getUiElement_xpath("BLPayables_gobtn").click();
	
	if(verifyAlertMessage("Bill successfully loaded").equals("true")){
		resultStatus("BL04.2_TS_01_TC.03", "Pass", "", "User should be able to verify Shipment is successfully Loaded on BLpayables screen.", "");
	} else {
		resultStatus("BL04.2_TS_01_TC.03", "Fail", "", "User should be able to verify Shipment is successfully Loaded on BLpayables screen.", "");
	}
	
	/*String Bill_Status=gettextval("//*[@id='billStatus']");
	 if(Bill_Status.equals("IN AUDIT")){
		 clearvalue("BLPayables_shipmentNumber");
		 selectvalue("BLPayables_shipmentNumber",gates_data.readdata("04_BLPayables", "SHIPMENT_NUMBER_RATED", Integer.valueOf(args.toString())));
		 getUiElement_xpath("BLPayables_gobtn").click();
		 Thread.sleep(2000);
	 }*/
	//'Add Payables 
	
	int Table_Data1=tableCount_xpath(".//*[@id='payableChargeGrid']");
	System.out.println(Table_Data1);
	getUiElement_xpath("payableClauseAdd").click();
	Thread.sleep(1500);
	
	//selectFromDropDown(getUiElement_xpath("chargeCodeOverlay"),gates_data.readdata("04_BLPayables", "CHARGE_CODE", Integer.valueOf(args.toString())));
	selectFromDropDown(getUiElement_xpath("chargeCodeOverlay"),2);
	setvalue("unitsOverlay",gates_data.readdata("04_BLPayables", "UNITS", Integer.valueOf(args.toString())));
	String Equipment_Value=getValue("equipmentNumberOverlay","value");
	if (Equipment_Value.isEmpty()){
		getUiElement_xpath("equipmentNumberOverlayImg").click();
		Thread.sleep(1500);
		switchToWindow("GATES: Quick Search");
		Thread.sleep(5000);
		getUiElement_xpath("BLCharge_search").click();
		Thread.sleep(3000);
		boolean link = isElementDisplayed("link_1");
		if(link){
		clickFirstLink("casQuickSearch","1","3").click();
		}		
		else{
			clickFirstLink("casQuickSearch","1","2").click();
		}
		switchToWindow("GATES: Bill of Lading Payable");
		//getUiElement_xpath("equipmentNumberOverlay").sendKeys(gates_data.readdata("04_BLPayables", "EQUIPMENT", Integer.valueOf(args.toString())));
		
	}
	
	setvalue("rateOverlay",gates_data.readdata("04_BLPayables", "RATE", Integer.valueOf(args.toString())));
	Thread.sleep(1500);
	selectFromDropDown(getUiElement_xpath("rateBasisOverlay"),gates_data.readdata("04_BLPayables", "RATE_BASIS", Integer.valueOf(args.toString())));
	Thread.sleep(1500);
	selectvalue("payeeOverlay",gates_data.readdata("04_BLPayables", "PAYEE", Integer.valueOf(args.toString())));	
	Thread.sleep(15000);
	switchToWindow("GATES: Quick Search");
	if(driver.getTitle().equals("GATES: Quick Search")){
	//switchToWindow("GATES: Quick Search");	
	Thread.sleep(2000);
	clickLinkByText("ALL").click();
	}
	else{
		getUiElement_xpath("popupSearchaddressLookUpImage").click();
		switchToWindow("GATES: Quick Search");
		Thread.sleep(2000);
		clickLinkByText("ALL").click();
	}
	switchToWindow("GATES: Bill of Lading Payable");
	getUiElement_xpath("addCharge_Okbtn").click();
	Thread.sleep(1500);
	//escapeKey();
	int Table_Data2=tableCount_xpath(".//*[@id='payableChargeGrid']");
	System.out.println(Table_Data2);
	if(Table_Data1<Table_Data2){
		resultStatus("BL04.2_TS_01_TC.04", "Pass", "", "To verify that the payable has been added manually.", "");
	}
	else {
		resultStatus("BL04.2_TS_01_TC.04", "Fail", "", "To verify that the payable has been added manually.", "");
	}
	
	//'Edit the Payable added  
	//int row=-(Table_Data1-1);
	//System.out.println(row);
	Thread.sleep(2500);
	new WebDriverWait(driver,10).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("((.//*[@title='Edit selected row']/span)[last()])")))).click();
	Thread.sleep(2000);
	driver.findElement(By.xpath(".//*[@id='rateOverlay']")).sendKeys(Keys.HOME,Keys.chord(Keys.SHIFT,Keys.END),gates_data.readdata("04_BLPayables", "RATE_EDIT", Integer.valueOf(args.toString())));
	//driver.findElement(By.xpath(".//*[@id='rateOverlay']")).clear();
	//setvalue("rateOverlay",gates_data.readdata("04_BLPayables", "RATE_EDIT", Integer.valueOf(args.toString())));
	
	//'Check Add & New Button is disabled for Edit Condition
	Thread.sleep(1500);
	//if(driver.findElement(By.xpath("(.//span[contains(text(),'Add & New')]/..[@disabled=''])")).isDisplayed())
	
	
//	if(getValue("edtcharge_Addnewbtn","disabled").equals("true"))
	boolean edit = !isElementEnabled(getUiElement_xpath("edtcharge_Addnewbtn"));
	if(edit)
	{
		System.out.println("welcome at disabled");
		resultStatus("BL04.2_TS_01_TC.05", "Pass", "", "To verify that while editing Payable Add &New button is disabled.", "");
	}
	else {
		resultStatus("BL04.2_TS_01_TC.05", "Fail", "", "To verify that while editing Payable Add &New button is disabled.", "");
	}
	
	getUiElement_xpath("addCharge_Okbtn").click();
	Thread.sleep(1500);
	getUiElement_xpath("Payables_savebtn").click();
	Thread.sleep(1500);
	if(verifyAlertMessage("Shipment Saved Successfully").equals("true")){
		resultStatus("BL04.2_TS_01_TC.06", "Pass", "", "Verify on adding payables Shipment is saved successfully.", "");
	} else {
		resultStatus("BL04.2_TS_01_TC.06", "Fail", "", "Verify on adding payables Shipment is saved successfully.", "");
	}
	Thread.sleep(1500);
	
	//'Click on Transmit
	Webbtnclick("Payables_transmitbtn",120);
	Thread.sleep(1500);
	if(verifyAlertMessage("Transmit Successful").equals("true")){
		resultStatus("BL04.2_TS_01_TC.07", "Pass", "", "Verify Payables have been transmitted successfully on clicking Transmit button.", "");
	} else {
		resultStatus("BL04.2_TS_01_TC.07", "Fail", "", "Verify Payables have been transmitted successfully on clicking Transmit button.", "");
	}
	
	//'Click on Charges Button to check navigation to Charges Screen
	
	getUiElement_xpath("payable_chargebtn").click();
	Thread.sleep(2500);
	if (verifycontenttitle("B/L Charges").equals("true")) {
		resultStatus("BL04.2_TS_01_TC.08", "Pass", "", "Verify BLCharges screen is displayed on clicking Charges Button", "");
	} else {
		resultStatus("BL04.2_TS_01_TC.08", "Fail", "", "Verify BLCharges screen is displayed on clicking Charges Button", "");
	}
	
	getUiElement_xpath("shpChargePayablesBtn").click();
	Thread.sleep(5000);
	if (verifycontenttitle("B/L Payables").equals("true")) {
		resultStatus("BL04.2_TS_01_TC.09", "Pass", "", "Verify BLPayables screen is displayed on clicking Payables Button", "");
	} else {
		resultStatus("BL04.2_TS_01_TC.09", "Fail", "", "Verify BLCharges screen is displayed on clicking Payables Button", "");
	}
	
	
	//testdata
	System.out.println("Test Info - Working on getting test data for BL Payables.");
	WebElement hoveKey[] = {getelement("click_billing"),getelement("BillingSearch_lnk")};
	hoverAndClick(hoveKey, getelement("BillingSearch_lnk"));	
	Thread.sleep(5000);
	getUiElement_xpath("maintain_billing_clear").click();
	selectFromDropDown(getUiElement_xpath("maintain_billing_shipment_status"), "ISSUED");		
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
	    	billIssd = billnumber;
	    break;
	    }
	    }
		System.out.println("Test Info - Test data retrieved for BL Payables.");
	 //'Check if the BIll is ISSUed and Corrected then Save and Bill Button is disabled.
	   hoverAndClick(new WebElement[] { getelement("Billing_lnk"),getelement("billing_maintain_billing"),getelement("BLPay_Link") }, getelement("BLPay_Link"));
	   Thread.sleep(2500);
	clearvalue("BLPayables_shipmentNumber");
	clearvalue("BLPayables_shipmentSequenceNumber");	
	selectvalue("BLPayables_shipmentNumber",billIssd);
			//gates_data.readdata("04_BLPayables", "SHIPMENT_NUMBER_ISSD", Integer.valueOf(args.toString())));
	selectvalue("BLPayables_shipmentSequenceNumber",gates_data.readdata("04_BLPayables", "SEQUENCE_NUMBER_ISSD", Integer.valueOf(args.toString())));
	getUiElement_xpath("BLPayables_gobtn").click();
	Thread.sleep(5000);
	if(isElementEnabled(getUiElement_xpath("Payables_savebtn"))==false && isElementEnabled(getUiElement_xpath("Payables_billbtn"))==false){
		resultStatus("BL04.2_TS_01_TC.09", "Pass", "", "Verify Save and Bill buttons are disabled for Issued and corrected Bill.", "");
	}
	else {
		resultStatus("BL04.2_TS_01_TC.09", "Fail", "", "Verify Save and Bill buttons are disabled for Issued and corrected Bill.", "");
	}
	
	//'To Verify Transmit is disabled for bill  status other than PEND,DESC,RATED
	
	if(isElementEnabled(getUiElement_xpath("Payables_transmitbtn"))==false){
		resultStatus("BL04.2_TS_01_TC.10", "Pass", "", "Verify Transmit button is disabled for Issued and corrected Bil.", "");
	}
	else {
		resultStatus("BL04.2_TS_01_TC.10", "Fail", "", "Verify Transmit button is disabled for Issued and corrected Bil.", "");
	}
	System.out.println("Test Info: Billng -BL Payables Ends");
	Thread.sleep(2500);
	
	new SendBill_Document().Sendbilldoc(args);
 }
}
 