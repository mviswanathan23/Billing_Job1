package com.gates.billing;


import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
public class BL_Charges extends Login {
	
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "//atu.properties");
	}
	String billDesc;
	String Bill;
	String BILL_RATED;
	String SEQ;
	String billIssd;
	public static int i;
	public static int Rcount;
	@Test(description = "BLCharges", dataProvider = "iteration")
	public void BLCharges(Object args) throws Exception {
		
			
		utility gates_data = new utility();
		String Inputfile=System.getProperty("user.dir")+"//InputFiles//Inputs.xls";
		gates_data.setInputFile(Inputfile);
		hoverAndClick(new WebElement[] { getelement("Billing_lnk"),getelement("billing_maintain_billing"),getelement("BLCharge_lnk") }, getelement("BLCharge_lnk"));
		Thread.sleep(5000);
	if (verifycontenttitle("B/L Charges").equals("true")) {
		System.out.println("Test Info: Billng -BL Charge Started");
		resultStatus("BL04.1_TS_02_TC.01", "Pass", "", "To verify  user  to be able  to navigate BL Charges screen", "");
	} else {
		resultStatus("BL04.1_TS_02_TC.01", "Fail", "", "To verify  user  to be able  to navigate BL Charges screen", "");
	}
	
	/*getUiElement_xpath("search_filter").click();
	Thread.sleep(3500);	*/
	clearvalue("BLCharge_shipmentNumber");
	//Thread.sleep(2500);
	
	getUiElement_xpath("BLCharge_shipmentGoBtn").click();
	Thread.sleep(5000);
	if(verifyAlertMessage("* This field is required").equals("true") || verifyAlertMessage("Bill Number is mandatory").equals("true")){
		resultStatus("BL04.1_TS_02_TC.02", "Pass", "", "User should be able to verify Bill number is a mandatory field in BL Charges Screen", "");
	} else {
		resultStatus("BL04.1_TS_02_TC.02", "Fail", "", "User should be able to verify Bill number is a mandatory field in BL Charges Screen", "");
	}
	//test data
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
	clearvalue("billing_shipment_tracking_date2");
	getUiElement_xpath("billing_shipment_tracking_date2").sendKeys("01-01-2016");
	String toDate = add_Date(-60);
	System.out.println(toDate);
	clearvalue("billing_shipment_tracking_date2");
	getUiElement_xpath("billing_shipment_tracking_date2").sendKeys(toDate);
	getUiElement_xpath("maintain_billing_search").click();
	Thread.sleep(2500);
	getUiElement_xpath("billingsearch_date_link").click();
	Thread.sleep(5000);
	getUiElement_xpath("billingsearch_date_link").click();
	Thread.sleep(5000);
	 int tablecount = tableCount("casQuickSearch");
	   for(int a=1;a<tablecount;a++){			 
	    String billnumber = gettextval(".//*[@id='casQuickSearch']/tbody/tr["+a+"]/td[2]");	
	    String checkingTheValue = billnumber;
	    //boolean checkingTheValue = seq;
	    System.out.println("Bill #: " +checkingTheValue);
	    if(checkingTheValue.equals(billnumber)){
	    	billIssd = billnumber;
	    break;
	    }
	    }
	   getUiElement_xpath("maintain_billing_clear").click();
	   Thread.sleep(3500);
	   selectvalue("in_shipment_number", billIssd);
	   Thread.sleep(5000);
	   getUiElement_xpath("maintain_billing_search").click();
		Thread.sleep(5000);
		int tablecount2 = tableCount("casQuickSearch");
		System.out.println("# of Billing Records: " +tablecount2);					
	   getUiElement_xpath("maintain_billing_first_checkbox").click();
	   getUiElement_xpath("maintain_billing_delete_QA").click();
	  boolean  isDialogPresent = isElementDisplayed(By.xpath(".//*[@id='ui-dialog-title-shipment_deleteQA']"));
		if(isDialogPresent){
		getUiElement_xpath("maintain_billing_click_popUp").click();
		Thread.sleep(5000);
		}				
		hoverAndClick(new WebElement[] { getelement("Billing_lnk"),getelement("BOLSetup_lnk") }, getelement("BOLSetup_lnk"));
		Thread.sleep(5000);
		clearvalue("bookingn");
		selectvalue("bookingn",billIssd);
		Thread.sleep(2000);
		getUiElement_xpath("bl_go").click();
		Thread.sleep(2500);				
	     if(verifyAlertMessage("Booking Assignment Maintenance required").equals("true")) {
				hoverAndClick(new WebElement[] { getelement("booking") }, getelement("maintain_booking"));	
				Thread.sleep(5000);
				clickLink("Click Here").click();
				Thread.sleep(3500);
				getUiElement_xpath("ass_cb1").click();
				getUiElement_xpath("ass_cb2").click();
				getUiElement_xpath("acc_assg").click();
				Thread.sleep(2000);
				if(verifyAlertMessage("Successfully Accepted.").equals("true")){
					hoverAndClick(new WebElement[] { getelement("Billing_lnk"),getelement("BOLSetup_lnk") }, getelement("BOLSetup_lnk"));
					Thread.sleep(5000);
					clickLinkByText("000").click();
					Thread.sleep(3500);
				}
	     }	     		    			  
	     else if(verifyAlertMessage("Booking Found.").equals("true")){	    	
	    	 Rcount=tableCount("containerGrid");
	 		System.out.println("B/L Page Total Record: " +Rcount);
	 		for (i=2;i<=Rcount;i++){
	 			System.out.println(i);
				//if(gettextval("//tr["+i+"]//td[4]").isEmpty())
				String seqchk = driver.findElement(By.xpath("//*[@id='containerGrid']/tbody/tr["+i+"]/td[4]")).getText();
				System.out.println(seqchk);
				if(seqchk.equals(""))
				{						
					selectcheckbox("//*[@id='containerGrid']/tbody/tr["+i+"]/td[1]/input");
					selectFromDropDown(getUiElement_xpath("responsiblePartyCode"),gates_data.readdata("19_Billing", "DEBTOR", Integer.valueOf(args.toString())));									
					break;
				}			
	 		}
				getUiElement_xpath("billing_link").click();	
				Thread.sleep(3500);
				if(driver.findElement(By.xpath("//*[@id='billingSetUpWarningDialog']")).isDisplayed()){					
						getUiElement_xpath("Continuebtn").click();
				}
				Thread.sleep(5000);									
	    
	      if(verifyAlertMessage("Selected equipment has an unapproved variance; no B/L created.").equals("true")){
	    	 hoverAndClick(new WebElement[] { getelement("booking"), getelement("variance") }, getelement("variance_searchbybooking"));
				Thread.sleep(10000);	
				//driver.navigate().refresh();
				if(!isElementDisplayed("variance_resultgrid")){
					clearvalue("IN_BOOKING");
					selectvalue("IN_BOOKING", billIssd);
				}
				else{
					getUiElement_xpath("variance_chkbox").click();
					getUiElement_xpath("cont_variance").click();
					Thread.sleep(3000);
					getUiElement_xpath("shipper_rad").click();	
					 getUiElement_xpath("acc_gems").click();
	            	   while (isAlertPresent()) {
	           			acceptAlert();
	           		}
	            	   hoverAndClick(new WebElement[] { getelement("Billing_lnk"),getelement("BOLSetup_lnk") }, getelement("BOLSetup_lnk"));
	           		Thread.sleep(5000);
	           		clearvalue("bookingn");
	           		selectvalue("bookingn",billIssd);
	           		Thread.sleep(2000);
	           		getUiElement_xpath("bl_go").click();
	           		Thread.sleep(2500);			           		
	           	 Rcount=tableCount("containerGrid");
	 	 		System.out.println("B/L Page Total Record: " +Rcount);
	 	 		for (i=2;i<=Rcount;i++){
	 	 			System.out.println(i);
	 				//if(gettextval("//tr["+i+"]//td[4]").isEmpty())
	 				String seqchk = driver.findElement(By.xpath("//*[@id='containerGrid']/tbody/tr["+i+"]/td[4]")).getText();
	 				System.out.println(seqchk);
	 				if(seqchk.equals(""))
	 				{						
	 					selectcheckbox("//*[@id='containerGrid']/tbody/tr["+i+"]/td[1]/input");
	 					selectFromDropDown(getUiElement_xpath("responsiblePartyCode"),gates_data.readdata("19_Billing", "DEBTOR", Integer.valueOf(args.toString())));									
	 					break;
	 				}			
	 	 		}
	   	 	getUiElement_xpath("billing_link").click();	
			Thread.sleep(3500);
			if(driver.findElement(By.xpath("//*[@id='billingSetUpWarningDialog']")).isDisplayed()){					
					getUiElement_xpath("Continuebtn").click();
			}
			Thread.sleep(5000);
			getUiElement_xpath("billing_mb").click();
			Thread.sleep(5000);	
				}
	     }
	      else{
	    	  getUiElement_xpath("billing_mb").click();
				Thread.sleep(5000);	
	      }
	     }
			getUiElement_xpath("expand_all").click();
			Thread.sleep(5000);
			if(verifyTextPresent2("mb_tariff") && verifyTextPresent2("mb_item")){
			selectvalue("mb_tariff", gates_data.readdata("19_Billing", "TARIFF",Integer.valueOf(args.toString())));	
			Thread.sleep(3500);
			getUiElement_xpath("item_pop").click();
			switchToWindow("GATES: Quick Search");
			if (driver.getTitle().equals("GATES: Quick Search")) {
				getUiElement_xpath("clear_fields").click();
				clearvalue("rate_item");
				getUiElement_xpath("rate_item").sendKeys(gates_data.readdata("19_Billing", "ITEM",Integer.valueOf(args.toString())));
				getUiElement_xpath("gn_search").click();
				Thread.sleep(2500);
				clickLinkByText("3000").click();
				switchToWindow("GATES: Maintain Bill");
				Thread.sleep(2500);
			}
			String commwt = getUiElement_xpath("comm_wgt").getAttribute("value");					
			if(verifyTextPresent2("comm_wgt") || commwt.equals("0") ){
				getUiElement_xpath("comm_wgt").clear();
				getUiElement_xpath("comm_wgt").sendKeys(gates_data.readdata("19_Billing", "WEIGHT", Integer.valueOf(args.toString())));
			}					
			}	
			String commwt2 = getUiElement_xpath("comm_wgt").getAttribute("value");					
			if(verifyTextPresent2("comm_wgt") || commwt2.equals("0") ){
				getUiElement_xpath("comm_wgt").clear();
				getUiElement_xpath("comm_wgt").sendKeys(gates_data.readdata("19_Billing", "WEIGHT", Integer.valueOf(args.toString())));
			}	
			getUiElement_xpath("mb_bill").click();					
			Thread.sleep(10000);					
			if(verifyErrorAlertPresent("*Tariff is mandatory").equals("true") || verifyErrorAlertPresent("Tariff did not load correctly, please re-enter").equals("true")){
				clearvalue("mb_tariff");
				selectvalue("mb_tariff", gates_data.readdata("19_Billing", "TARIFF",Integer.valueOf(args.toString())));	
				Thread.sleep(3500);
				getUiElement_xpath("item_pop").click();
				switchToWindow("GATES: Quick Search");
				if (driver.getTitle().equals("GATES: Quick Search")) {
					getUiElement_xpath("clear_fields").click();
					clearvalue("rate_item");
					getUiElement_xpath("rate_item").sendKeys(gates_data.readdata("19_Billing", "ITEM",Integer.valueOf(args.toString())));
					getUiElement_xpath("gn_search").click();
					Thread.sleep(2500);
					clickLinkByText("3000").click();
					switchToWindow("GATES: Maintain Bill");
					Thread.sleep(2500);
				}
				String commwt3 = getUiElement_xpath("comm_wgt").getAttribute("value");					
				if(verifyTextPresent2("comm_wgt") || commwt3.equals("0") ){
					getUiElement_xpath("comm_wgt").clear();
					getUiElement_xpath("comm_wgt").sendKeys(gates_data.readdata("19_Billing", "WEIGHT", Integer.valueOf(args.toString())));
				}		
				getUiElement_xpath("mb_bill").click();					
				Thread.sleep(10000);	
			}
			if(verifyErrorAlertPresent("Tariff did not load correctly, please re-enter").equals("true")) {
				clearvalue("mb_tariff");
				selectvalue("mb_tariff", gates_data.readdata("19_Billing", "TARIFF",Integer.valueOf(args.toString())));	
				Thread.sleep(1500);
				getUiElement_xpath("mb_bill").click();					
				Thread.sleep(10000);	
			}
			System.out.println("Test Info: Bill submission in progress.");				
	
			boolean errchoice = getelement("err_gr").isDisplayed();	
			boolean errchoice2 = getelement("err_gr2").isDisplayed();			
				if(errchoice || errchoice2){			
					getUiElement_xpath("err_exit").click();
					Thread.sleep(2500);
					String Bill_Status=gettextval("//*[@id='statusCode']");
					if(Bill_Status.equals("DESCRIBED")){
						getUiElement_xpath("bl_charge").click();
					}
				}					
				Thread.sleep(5000);	
				while(isElementDisplayed("choice_screen_overlay2")){
					clickLink("1").click();			
					Thread.sleep(10000);				
				}		
			boolean holdchk = isElementDisplayed("unrls_holdP");
			if(holdchk) {
				System.out.println("Test Info: Working on un-released HOLDS");		
				String refhold = getUiElement_xpath("unrls_stop").getText();	
				
				if(!refhold.equals("REF") && !refhold.equals("POSTSETU") && !refhold.equals("RATE")){							
				getUiElement_xpath("billing_maintain_hold_menu").click();//clicking the hold menu
				Thread.sleep(2000);
				getUiElement_xpath("billing_maintain_hold_menu_al_check").click();//clicking the all checkbox item in hold menu
				getUiElement_xpath("billing_maintain_billing_release_button_inholdmenu").click();//clickcing the release button
				getUiElement_xpath("billing_maintain_billing_save_button").click();//clicking the save button
				Thread.sleep(5000);
				getUiElement_xpath("mb_bill").click();
				Thread.sleep(5000);						
				}
				else if(refhold.equals("REF")){									
					getUiElement_xpath("expand_all").click();
					selectFromDropDown(getUiElement_xpath("maintain_reference_type"),gates_data.readdata("04_ReferenceNumbers", "REF_TYPE", Integer.valueOf(args.toString())));
					setvalue("maintain_reference_number", gates_data.readdata("04_ReferenceNumbers", "REF_NUMBER", Integer.valueOf(args.toString())));
					getUiElement_xpath("billref_add").click();
					Thread.sleep(1500);
					setvalue("maintain_marksnumbers", gates_data.readdata("04_ReferenceNumbers", "MARKS_NUMBERS", Integer.valueOf(args.toString())));
					getUiElement_xpath("billing_maintain_billing_save_button").click();//clicking the save button
					Thread.sleep(5000);
					getUiElement_xpath("mb_bill").click();
					Thread.sleep(5000);							
				}
				else if(refhold.equals("POSTSETU")){								
					getUiElement_xpath("expand_all").click();
					getUiElement_xpath("editvvd", true).click();		
					Thread.sleep(5000);
					getradioBtn("searchCriteria", "L").click();
					getUiElement_xpath("searchVVDButton").click();	
					Thread.sleep(3000);
					clickFirstLink("gbox_vvdResultGrid", "4", "2").click();
					getUiElement_xpath("billing_maintain_billing_save_button").click();//clicking the save button
					Thread.sleep(5000);
					getUiElement_xpath("mb_bill").click();
					Thread.sleep(5000);							
					if(isElementDisplayed("unrls_holdP")) {
						String refhold2 = getUiElement_xpath("unrls_stop").getText();	
						if(refhold2.equals("HAZ")){
						getUiElement_xpath("billing_maintain_hold_menu").click();//clicking the hold menu
						getUiElement_xpath("billing_maintain_hold_menu_al_check").click();//clicking the all checkbox item in hold menu
						getUiElement_xpath("billing_maintain_billing_release_button_inholdmenu").click();//clickcing the release button
						getUiElement_xpath("billing_maintain_billing_save_button").click();//clicking the save button
						Thread.sleep(5000);
						getUiElement_xpath("mb_bill").click();
						Thread.sleep(5000);
						}
					}
				}
			}		
			if(isElementDisplayed("book_alert2")){
			escapeKey();
			getUiElement_xpath("mb_bill").click();
			Thread.sleep(5000);
			}
			 /*while(isElementDisplayed("choice_screen_overlay")){
					clickLink("1").click();			
					Thread.sleep(10000);				
				}*/
			 while(isElementDisplayed("choice_screen_overlay2")){
					clickLink("1").click();			
					Thread.sleep(10000);			
				}	
			 boolean errchoice5 = getelement("err_gr").isDisplayed();	
				boolean errchoice6 = getelement("err_gr2").isDisplayed();			
					if(errchoice5 || errchoice6){			
						getUiElement_xpath("err_exit").click();
						Thread.sleep(2500);
						String Bill_Status=gettextval("//*[@id='statusCode']");
						if(Bill_Status.equals("DESCRIBED")){
							getUiElement_xpath("bl_charge").click();
						}
					}
			boolean holdchk2 = isElementDisplayed("unrls_holdP");
			if(holdchk2) {
				System.out.println("Test Info: Working on un-released HOLDS");		
				String refhold = getUiElement_xpath("unrls_stop").getText();	
				if(!refhold.equals("REF") && !refhold.equals("POSTSETU") && !refhold.equals("RATE")){							
					getUiElement_xpath("billing_maintain_hold_menu").click();//clicking the hold menu
					Thread.sleep(2000);
					//getUiElement_xpath("billing_maintain_hold_menu_al_check").click();//clicking the all checkbox item in hold menu
					getUiElement_xpath("hold_grid1").click();
					getUiElement_xpath("billing_maintain_billing_release_button_inholdmenu").click();//clickcing the release button
					getUiElement_xpath("billing_maintain_billing_save_button").click();//clicking the save button
					Thread.sleep(5000);	
					getUiElement_xpath("mb_bill").click();
					Thread.sleep(5000);						
					}
				else if(refhold.equals("FRCVD")){
					clearvalue("billing_maintain_billing_rate_field");
					getUiElement_xpath("billing_maintain_billing_rate_field").sendKeys(add_Date(0));
					getUiElement_xpath("mb_bill").click();
					Thread.sleep(5000);
					
				}
				else if(refhold.equals("RATE")){
					selectFromDropDown(getUiElement_xpath("BL_shipmentChargeId"), 1);
					clearvalue("BL_rate");
					setvalue("BL_rate",gates_data.readdata("19_Billing", "WEIGHT", Integer.valueOf(args.toString())));
					selectFromDropDown(getUiElement_xpath("BL_RateBasis"),gates_data.readdata("27_SPX", "RATE_BASIS", Integer.valueOf(args.toString())));
					getUiElement_xpath("BL_addbtn").click();
					getUiElement_xpath("charge_bill").click();						
					Thread.sleep(5000);
					
				}
			}
			/*boolean holdchk3 = isElementDisplayed("unrls_holdP");
			if(holdchk3) {
				System.out.println("Test Info: Working on un-released HOLDS");		
				getUiElement_xpath("billing_maintain_hold_menu").click();//clicking the hold menu
				getUiElement_xpath("billing_maintain_hold_menu_al_check").click();//clicking the all checkbox item in hold menu
				getUiElement_xpath("billing_maintain_billing_release_button_inholdmenu").click();//clickcing the release button
				getUiElement_xpath("mb_bill").click();
				Thread.sleep(5000);
				}*/
							
			     while(isElementDisplayed("choice_screen_overlay2")){
				clickLink("1").click();			
				Thread.sleep(10000);				
			}				
			     boolean errchoice3 = getelement("err_gr").isDisplayed();	
					boolean errchoice4 = getelement("err_gr2").isDisplayed();			
						if(errchoice3 || errchoice4){			
							getUiElement_xpath("err_exit").click();
							Thread.sleep(2500);
							String Bill_Status=gettextval("//*[@id='statusCode']");
							if(Bill_Status.equals("DESCRIBED")){
								getUiElement_xpath("bl_charge").click();
							}
						}					
						Thread.sleep(2500);			
			String Bill_Status2=gettextval("//*[@id='statusCode']");
			if(Bill_Status2.equals("RATED")){
				System.out.println("Test Info: BILL RATED successfully.");
				//resultStatus("SMT_BLTS.01_TC.08A", "Pass", "", "User should be able to verify Bill Status for the loaded shipment on BL Charges Screen", "");
			} else if(Bill_Status2.equals("IN AUDIT")){
				getUiElement_xpath("BL_ReleaseAudit").click();
				//System.out.println("Test Info: IN AUDIT BILL Released successfully.");
				//resultStatus("SMT_BLTS.01_TC.08B", "Pass", "", "To verify that if the bill status was In AUDIT and by clicking RELEASE AUDIT button status changes to RATED .", "");
			}	
			else if(Bill_Status2.equals("DESCRIBED")){
				selectFromDropDown(getUiElement_xpath("BL_shipmentChargeId"), 1);
				clearvalue("BL_rate");
				setvalue("BL_rate",gates_data.readdata("19_Billing", "WEIGHT", Integer.valueOf(args.toString())));
				selectFromDropDown(getUiElement_xpath("BL_RateBasis"),gates_data.readdata("27_SPX", "RATE_BASIS", Integer.valueOf(args.toString())));
				getUiElement_xpath("BL_addbtn").click();
				getUiElement_xpath("charge_bill").click();	
				Thread.sleep(3000);
				String Bill_Status3=gettextval("//*[@id='statusCode']");
				if(Bill_Status3.equals("IN AUDIT")){
					getUiElement_xpath("BL_ReleaseAudit").click();	
					Thread.sleep(3000);
				}
			}				
			while(isElementEnabled(getUiElement_xpath("BL_ReleaseAudit"))){		
				getUiElement_xpath("BL_ReleaseAudit").click();
				System.out.println("Test Info: IN AUDIT BILL Released successfully.");
				Thread.sleep(2000);
				break;
			}
			String billstatus=gettextval("//*[@id='statusCode']");
			System.out.println(billstatus);
			if(billstatus.equals("RATED")){
				BILL_RATED=getUiElement_xpath("BLCharge_shipmentNumber").getAttribute("value");
				 SEQ = getUiElement_xpath("bl_seq").getAttribute("value"); 
				System.out.println("Rated Bill: " +BILL_RATED +SEQ);
			}			
			System.out.println("Test Info - Test data retrieved for BL Charges");
			
	
	//'Enter Bill which is in Pending Status:
	clearvalue("BLCharge_shipmentNumber");
	selectvalue("BLCharge_shipmentNumber",gates_data.readdata("03_BLCharges", "SHIPMENT_NUMBER_PENDING", Integer.valueOf(args.toString())));
	Thread.sleep(6000);
	selectvalue("BLCharge_shipmentSequenceNumber",gates_data.readdata("03_BLCharges", "SEQUENCE_NUMBER", Integer.valueOf(args.toString())));
	Thread.sleep(6000);
	getUiElement_xpath("BLCharge_shipmentGoBtn").click();
	Thread.sleep(25000);
	System.out.println("Test Info: Billng -BL Charges Started");
	if(verifyAlertMessage("Bill successfully loaded").equals("true")){
		resultStatus("BL04.1_TS_02_TC.03", "Pass", "", "User should be able to verify on adding Bill number Shipment loaded successfully", "");
	} else {
		resultStatus("BL04.1_TS_02_TC.03", "Fail", "", "User should be able to verify on adding Bill number Shipment loaded successfully", "");
	}
	
	String Bill_Status=gettextval("//*[@id='statusCode']");
	if(Bill_Status.equals("PENDING")){
		resultStatus("BL04.1_TS_02_TC.06", "Pass", "", "User should be able to verify Bill Status for the loaded shipment on BL Charges Screen", "");
	} else {
		resultStatus("BL04.1_TS_02_TC.06", "Fail", "", "User should be able to verify Bill Status for the loaded shipment on BL Charges Screen", "");
	}
	
	//'If Bill status is Pending user should not be able to add Charges:
	Thread.sleep(2000);	
	Select test = new Select(driver.findElement(By.xpath("//*[@id='chargeCode']")));
	test.selectByVisibleText("ACC : ACCESSORIAL CHARGE");
	//selectFromDropDown(getUiElement_xpath("BL_shipmentChargeId"),gates_data.readdata("03_BLCharges", "CHARGE_CODE", Integer.valueOf(args.toString())));	
	setvalue("BL_rate",gates_data.readdata("03_BLCharges", "RATE_AMOUNT", Integer.valueOf(args.toString())));	
	selectFromDropDown(getUiElement_xpath("BL_RateBasis"),gates_data.readdata("03_BLCharges", "RATE_BASIS", Integer.valueOf(args.toString())));
	getUiElement_xpath("BL_addbtn").click();
	
	if(verifyAlertMessage("Manual Charge Addition not allowed for bill status PEND").equals("true")){
		resultStatus("BL04.1_TS_02_TC.07", "Pass", "", "User should be able to verify the validation Manual Charge Addition not allowed for bill status PEND", "");
	} else {
		resultStatus("BL04.1_TS_02_TC.07", "Fail", "", "User should be able to verify the validation Manual Charge Addition not allowed for bill status PEND", "");
	}	
		
	
	if(isElementEnabled(getUiElement_xpath("tariffNumber"))==false && isElementEnabled(getUiElement_xpath("itemNumber"))==false){
		resultStatus("BL04.1_TS_02_TC.08", "Pass", "", "To verify that B/L Charges screen for tariff and item fields no longer being editable for all the shipments.", "");
	} else {
		resultStatus("BL04.1_TS_02_TC.08", "Fail", "", "To verify that B/L Charges screen for tariff and item fields no longer being editable for all the shipments.", "");
	}
	
	getUiElement_xpath("BLCharge_shipmentGoBtn").click();
	Thread.sleep(2000);
	while(isAlertPresent())
	{
		closeAlertAndGetItsText();
	}
	
	//'Clear the shipment so that new issued shipment can be added 
	clearvalue("BLCharge_shipmentNumber");
	clearvalue("BLCharge_shipmentSequenceNumber");
	//'Check the charges are disabled if status is ISSD or CORR
	
	//'Enter an Issued Shipment : 
	
	selectvalue("BLCharge_shipmentNumber",gates_data.readdata("03_BLCharges", "SHIPMENT_NUMBER_ISSD", Integer.valueOf(args.toString())));
	Thread.sleep(2500);
	selectvalue("BLCharge_shipmentSequenceNumber",gates_data.readdata("03_BLCharges", "SEQUENCE_NUMBER_ISSD", Integer.valueOf(args.toString())));
	Thread.sleep(3500);	
	getUiElement_xpath("BLCharge_shipmentGoBtn").click();
	Thread.sleep(2500);
	Bill_Status=gettextval("//*[@id='statusCode']");
	
	if(Bill_Status.equals("ISSUED")){
		resultStatus("BL04.1_TS_02_TC.09", "Pass", "", "User should be able to verify Bill Status for Issued Bill on BL Charges Screen", "");
		if(isElementEnabled(getUiElement_xpath("tariffNumber"))==false){
			resultStatus("BL04.1_TS_02_TC.10", "Pass", "", "User should be able to verify Tariff field is disabled for Issued Status", "");
		}
		else {
			resultStatus("BL04.1_TS_02_TC.10", "Fail", "", "User should be able to verify Tariff field is disabled for Issued Status", "");
		}
				
	} else {
		resultStatus("BL04.1_TS_02_TC.09", "Fail", "", "User should be able to verify Bill Status for Issued Bill on BL Charges Screen", "");
	}
	
	//Check that if the Bill status is Rated then on Changing Commodity Charges are removed.
	Thread.sleep(5000);
	clearvalue("BLCharge_shipmentNumber");
	clearvalue("BLCharge_shipmentSequenceNumber");
		
	//'Enter an Rated Shipment : 
	clearvalue("BLCharge_shipmentNumber");
	selectvalue("BLCharge_shipmentNumber", BILL_RATED);
	clearvalue("bl_seq");
	selectvalue("bl_seq", SEQ);
			//gates_data.readdata("03_BLCharges", "SHIPMENT_NUMBER_RATED", Integer.valueOf(args.toString())));
	//selectvalue("BLCharge_shipmentNumber", bill);
			//gates_data.readdata("03_BLCharges", "SHIPMENT_NUMBER_RATED", Integer.valueOf(args.toString())));	
	//selectvalue("BLCharge_shipmentSequenceNumber",gates_data.readdata("03_BLCharges", "SEQUENCE_NUMBER_RATED", Integer.valueOf(args.toString())));
	Thread.sleep(3500);
	getUiElement_xpath("BLCharge_shipmentGoBtn").click();
	Thread.sleep(3500);
	Bill_Status=gettextval("//*[@id='statusCode']");
	
	if(Bill_Status.equals("RATED")){
		resultStatus("BL04.1_TS_02_TC.11", "Pass", "", "User should be able to verify Bill Status for Rated Bill on BL Charges Screen", "");
		int Table_Count1=tableCount_xpath("//*[@id='gview_chargeGrid']/div[3]/div");
		Thread.sleep(1500);
		selectFromDropDown(getUiElement_xpath("BL_shipmentChargeId"), 1);
				//gates_data.readdata("03_BLCharges", "CHARGE_CODE", Integer.valueOf(args.toString())));
		clearvalue("BL_rate");
		setvalue("BL_rate",gates_data.readdata("03_BLCharges", "RATE_AMOUNT", Integer.valueOf(args.toString())));
		selectFromDropDown(getUiElement_xpath("BL_RateBasis"),gates_data.readdata("03_BLCharges", "RATE_BASIS", Integer.valueOf(args.toString())));
		getUiElement_xpath("BL_addbtn").click();
        System.out.println(Table_Count1);
		int Table_Count2=tableCount_xpath("//*[@id='gview_chargeGrid']/div[3]/div");
		 System.out.println(Table_Count2);
		if(Table_Count1<Table_Count2){
			resultStatus("BL04.1_TS_02_TC.12", "Pass", "", "To verify that user should be able to add charge line manually.", "");
		}
		else {
			resultStatus("BL04.1_TS_02_TC.12", "Fail", "", "To verify that user should be able to add charge line manually.", "");
		}
			
	}
	//'Click Save to check the Bill Status : 
	Webbtnclick("shpChargeSaveBtn",120);
	Thread.sleep(5000);
	Webbtnclick("BLCharge_shipmentGoBtn",120);
	Thread.sleep(5000);
	Bill_Status=getUiElement_xpath("BL_statusCode").getText();
	//Bill_Status=gettextval("//*[@id='statusCode']");
	
	System.out.println(Bill_Status);
	while(isAlertPresent())
	{
		closeAlertAndGetItsText();
	}
	if(Bill_Status.equals("DESCRIBED")){
		resultStatus("BL04.1_TS_02_TC.13", "Pass", "", "To verify that if the bill status was in RATD/AUDT and charges have been added/updated/deleted then the status of the bill should drop to DESC.", "");
	}
	else if(Bill_Status.equals("IN AUDIT")){
		Webbtnclick("BL_ReleaseAudit",120);
		resultStatus("BL04.1_TS_02_TC.25", "Pass", "", "To verify that if the bill status was In AUDIT and by clicking RELEASE AUDIT button status changes to RATED .", "");
	}
	else{
		resultStatus("BL04.1_TS_02_TC.13", "fail", "", "To verify that if the bill status was in RATD/AUDT and charges have been added/updated/deleted then the status of the bill should drop to DESC.", "");
	}
	Thread.sleep(10000);
	//'Click Bill Buton and check the status again changes to RATED: 
	//driver.findElement(By.xpath("/html/body/div[22]/div[1]/a/span"));
	//if(Bill_Status.equals("RATED")){
	//Bill_Status=gettextval("//*[@id='statusCode']");	
	Webbtnclick("shpChargeBillBtn",120);
	Thread.sleep(5000);
	Bill_Status=gettextval("//*[@id='statusCode']");
	if(Bill_Status.equals("RATED")){
		resultStatus("BL04.1_TS_02_TC.15", "Pass", "", "To verify that if the bill status was changed to DESC on manual addition ,then on pressing Bill button it changes back to RATED", "");
	}
	else if(Bill_Status.equals("IN AUDIT")){
		Webbtnclick("BL_ReleaseAudit",120);
		Thread.sleep(10000);
		Bill_Status=gettextval("//*[@id='statusCode']");
		while(Bill_Status.equals("RATED")){
			//Webbtnclick("BL_ReleaseAudit",120);
			resultStatus("BL04.1_TS_02_TC.15", "Pass", "", "To verify that if the bill status was changed to DESC on manual addition ,then on pressing Bill button it changes back to RATED", "");
			break;
		}
		/*Webbtnclick("shpChargeBillBtn",120);
		Thread.sleep(5000);
	//	Bill_Status=gettextval("//*[@id='statusCode']");
		if(Bill_Status.equals("RATED")){
			resultStatus("BL04.1_TS_02_TC.15", "Pass", "", "To verify that if the bill status was changed to DESC on manual addition ,then on pressing Bill button it changes back to RATED", "");
		}*/
	}
	else {
		resultStatus("BL04.1_TS_02_TC.15", "Fail", "", "To verify that if the bill status was changed to DESC on manual addition ,then on pressing Bill button it changes back to RATED", "");
	}
	Webbtnclick("shpChargeBillBtn",120);
	//}
	Thread.sleep(6000);	
	 if(verifyAlertMessage("Bill Manager request is successfully completed").equals("true")){
		resultStatus("BL04.1_TS_02_TC.14", "Pass", "", "To verify Bill Manager request is successfully completed", "");
	} else if(isElementEnabled(getUiElement_xpath("BL_ReleaseAudit"))==true){
		Webbtnclick("BL_ReleaseAudit",120);
		while(Bill_Status.equals("IN AUDIT")){
			Webbtnclick("BL_ReleaseAudit",120);
			break;
		}
		Webbtnclick("shpChargeBillBtn",120);
		if(verifyAlertMessage("Bill Manager request is successfully completed").equals("true")){
			resultStatus("BL04.1_TS_02_TC.14", "Pass", "", "To verify Bill Manager request is successfully completed", "");
		} 	
	}		
		else { resultStatus("BL04.1_TS_02_TC.14", "Fail", "", "To verify Bill Manager request is successfully completed", "");
	}	
	
	
	
	//'Check the validation if rate basis is no charges,user should not be able to add charge: 
	    Thread.sleep(1500);
		selectFromDropDown(getUiElement_xpath("BL_shipmentChargeId"),1);
		Thread.sleep(1500);					
		clearvalue("BL_rate");
		Thread.sleep(1500);		
		getUiElement_xpath("BL_rate").sendKeys("145");
	//	setvalue("BL_rate",gates_data.readdata("03_BLChargess", "RATE_AMOUNT", Integer.valueOf(args.toString())));
		Thread.sleep(2000);
		selectFromDropDown(getUiElement_xpath("BL_RateBasis"),gates_data.readdata("03_BLCharges", "RATE_BASIS_NOCHARGE", Integer.valueOf(args.toString())));
		getUiElement_xpath("BL_addbtn").click();
		
		if(verifyAlertMessage("Rate must be zero").equals("true")){
			resultStatus("BL04.1_TS_02_TC.16", "Pass", "", "To verify that while adding charge manually, system should display validation message if user attempts to user Rate Basis for which Rate Basis Type is NIL.", "");
		} else {
			resultStatus("BL04.1_TS_02_TC.16", "Fail", "", "To verify that while adding charge manually, system should display validation message if user attempts to user Rate Basis for which Rate Basis Type is NIL.", "");
		}	
		clearvalue("BL_rate");
		setvalue("BL_rate",gates_data.readdata("03_BLCharges", "RATE_AMOUNT_ZERO", Integer.valueOf(args.toString())));
		selectFromDropDown(getUiElement_xpath("BL_RateBasis"),gates_data.readdata("03_BLCharges", "RATE_BASIS", Integer.valueOf(args.toString())));
		getUiElement_xpath("BL_addbtn").click();
		
		if(verifyAlertMessage("Charge Rate required").equals("true")){
			resultStatus("BL04.1_TS_02_TC.17", "Pass", "", "To verify that while entering charge manually, system should not allow to enter rate amount greater than 0", "");
		} else {
			resultStatus("BL04.1_TS_02_TC.17", "Fail", "", "To verify that while entering charge manually, system should not allow to enter rate amount greater than 0", "");
		}

		//'Click Corrections for Freight Correction

		getUiElement_xpath("shpChargeCorrectionsBtn").click();	
		Thread.sleep(3500);
		if (verifycontenttitle("Freight Correction Maintenance").equals("true")) {
			resultStatus("BK.04.05_TS_01_TC.18", "Pass", "", "To verify that Freight Correction Maintenance screen is displayed on clicking Corrections button.", "");
		} else {
			resultStatus("BK.04.05_TS_01_TC.18", "Fail", "", "To verify that Freight Correction Maintenance screen is displayed on clicking Corrections button.", "");
		}
		
		getUiElement_xpath("BL_exitbtn").click();	
		Thread.sleep(5000);
		
		getUiElement_xpath("shpChargePayablesBtn").click();	
		Thread.sleep(6000);
		if (verifycontenttitle("B/L Payables").equals("true")) {
			resultStatus("BK.04.05_TS_01_TC.19", "Pass", "", "To verify that B/L Payables Screen is displayed on clicking Payables button", "");
		} else {
			resultStatus("BK.04.05_TS_01_TC.19", "Fail", "", "To verify that B/L Payables Screen is displayed on clicking Payables button", "");
		}
		
		getUiElement_xpath("Payable_exitbtn").click();	
		
		Thread.sleep(5000);
		
		getUiElement_xpath("shpChargeStatusBtn").click();
		Thread.sleep(3500);
		if (verifycontenttitle("Bill Status History").equals("true")) {
			resultStatus("BK.04.05_TS_01_TC.20", "Pass", "", "To verify that Bill Status History Screen is displayed on clicking Status button", "");
		} else {
			resultStatus("BK.04.05_TS_01_TC.20", "Fail", "", "To verify that Bill Status History Screen is displayed on clicking Status button", "");
		}
		Thread.sleep(2000);
		getUiElement_xpath("Payable_exitbtn").click();	
		
		Thread.sleep(3500);
		getUiElement_xpath("maintainBill").click();
		Thread.sleep(3500);
		if (verifycontenttitle("Maintain Bill").equals("true")) {
			resultStatus("BK.04.05_TS_01_TC.21", "Pass", "", "To verify that Maintain Bill Screen is displayed on clicking Maintain Bill button.", "");
		} else {
			resultStatus("BK.04.05_TS_01_TC.21", "Fail", "", "To verify that Maintain Bill Screen is displayed on clicking Maintain Bill button.", "");
		}
		
		//'Add Accesorial Charges
		getUiElement_xpath("shipmentChargesBtn").click();	
		Thread.sleep(4000);
		
		int Table_Data1=tableCount_xpath("//*[@id='chargeGrid']");
		getUiElement_xpath("accChargesId").click();	
		Thread.sleep(2000);
		if(getUiElement_xpath("commodityDescPage").isDisplayed()){
			getUiElement_xpath("BL_Search").click();
			Thread.sleep(7000);
			getUiElement_xpath("jqg_accesrialChargeGrid_1").click();
			getUiElement_xpath("OKBtn").click();
			Thread.sleep(1500);								
		}
		
		int Table_Data2=tableCount_xpath("//*[@id='chargeGrid']");
		
		if(Table_Data1<Table_Data2){
			resultStatus("BL04.1_TS_02_TC.22", "Pass", "", "To verify that user should be able to add charge line using additional charge.", "");
		}
		else {
			resultStatus("BL04.1_TS_02_TC.22", "Fail", "", "To verify that user should be able to add charge line using additional charge.", "");
		}
		
		if (verifycontenttitle("B/L Charges").equals("true")) {
			resultStatus("BK.04.05_TS_01_TC.23", "Pass", "", "To verify that Maintain Rate Screen is successfully displayed.", "");
		} else {
			resultStatus("BK.04.05_TS_01_TC.23", "Fail", "", "To verify that Maintain Rate Screen is successfully displayed.", "");
		}
		
		getUiElement_xpath("shpChargeSaveBtn").click();
		Thread.sleep(2500);
		System.out.println("click Btn");
		
		
		//getElement("shpChargeBillBtn",120,driver).click();
		Thread.sleep(2000);
		System.out.println("After Thread");
		//new WebDriverWait(driver, 50).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='shpChargeBillBtn']"))).click();
		while(true){
		try
		{
		getUiElement_xpath("shpChargeBillBtn").click();
		break;
		}
		catch(Exception e)
		{
			Thread.sleep(3000);			
		}
		}
		Thread.sleep(10000);
		if(isElementEnabled(getUiElement_xpath("BL_ReleaseAudit"))==true){
			Webbtnclick("BL_ReleaseAudit",120);
			Thread.sleep(3500);
			resultStatus("BL04.1_TS_02_TC.25", "Pass", "", "To verify that if the bill status was In AUDIT and by clicking RELEASE AUDIT button status changes to RATED .", "");
			getUiElement_xpath("shpChargeSaveBtn").click();
			Thread.sleep(6000);
		}	
		getUiElement_xpath("shpChargeBillBtn").click();
		Thread.sleep(2500);
		if(verifyAlertMessage("Bill Manager request is successfully completed").equals("true") || verifyAlertMessage("Bill successfully loaded").equals("true")){
			resultStatus("BL04.1_TS_02_TC.24", "Pass", "", "To verify Bill Manager request is successfully completed", "");
		} else {
			resultStatus("BL04.1_TS_02_TC.24", "Fail", "", "To verify Bill Manager request is successfully completed", "");
		}
		Thread.sleep(5000);
		Bill_Status=gettextval("//*[@id='statusCode']");
		if(Bill_Status.equals("IN AUDIT")){
			getUiElement_xpath("BL_ReleaseAudit").click();
			Thread.sleep(1500);
		}
		if(isElementEnabled(getUiElement_xpath("BL_ReleaseAudit"))==true){
			Webbtnclick("BL_ReleaseAudit",120);
			Thread.sleep(3500);
		}
		getUiElement_xpath("shpChargePayablesBtn").click();		
		Thread.sleep(1500);
		System.out.println("Test Info: Billng -BL Charges Ends");
		new BL_Payables().BLPayables(args);
	
	}
	

}
