package com.gates.billing;

import java.util.List;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import atu.testng.reports.listeners.ATUReportsListener;
import atu.testng.reports.listeners.ConfigurationListener;
import atu.testng.reports.listeners.MethodListener;


import com.gates.setup.utility;


@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class SendBill_Document extends Login {
	/**/
	String BILL_RATED;
	public static int i;
	public static int Rcount;
	String billIssd;
	String BILL_RATED2;
	String SEQ;
	String SEQ2;
	public static int i2;
	public static int Rcount2;
	
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "//atu.properties");
	}
	@Test(description = "Booking Shipper", dataProvider = "iteration")
	public void Sendbilldoc(Object args) throws Exception {
		
				
		utility gates_data = new utility();
		String Inputfile=System.getProperty("user.dir")+"//InputFiles//Inputs.xls";
		gates_data.setInputFile(Inputfile);
		//test data
		System.out.println("Test Info - Working on getting test data for Send Bill Document.");
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
		     else if(verifyAlertMessage("Booking Found.").equals("true") || verifyAlertMessage("Booking located, but requested B/L not found.").equals("true")){
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
				Thread.sleep(2500);
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
				Thread.sleep(8000);					
				if(verifyErrorAlertPresent("*Tariff is mandatory").equals("true") || verifyErrorAlertPresent("Tariff did not load correctly, please re-enter").equals("true")){
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
					Thread.sleep(6500);	
				}
				if(verifyErrorAlertPresent("Tariff did not load correctly, please re-enter").equals("true")) {
					clearvalue("mb_tariff");
					selectvalue("mb_tariff", gates_data.readdata("19_Billing", "TARIFF",Integer.valueOf(args.toString())));	
					Thread.sleep(1500);
					getUiElement_xpath("mb_bill").click();					
					Thread.sleep(6500);	
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
					Thread.sleep(2500);	
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
					//getUiElement_xpath("billing_maintain_hold_menu_al_check").click();//clicking the all checkbox item in hold menu
					getUiElement_xpath("hold_grid1").click();
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
						Thread.sleep(2500);
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
						getUiElement_xpath("mb_bill").click();
						Thread.sleep(5000);							
						if(isElementDisplayed("unrls_holdP")) {
							String refhold2 = getUiElement_xpath("unrls_stop").getText();	
							if(refhold2.equals("HAZ")){
							getUiElement_xpath("billing_maintain_hold_menu").click();//clicking the hold menu
							//getUiElement_xpath("billing_maintain_hold_menu_al_check").click();//clicking the all checkbox item in hold menu
							getUiElement_xpath("hold_grid1").click();
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
				while(isElementDisplayed("choice_screen_overlay2")){
					clickLink("1").click();			
					Thread.sleep(10000);			
				}
				boolean errchoice11 = getelement("err_gr").isDisplayed();	
				boolean errchoice12 = getelement("err_gr2").isDisplayed();			
					if(errchoice11 || errchoice12){			
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
						//getUiElement_xpath("billing_maintain_hold_menu_al_check").click();//clicking the all checkbox item in hold menu
						getUiElement_xpath("hold_grid1").click();
						getUiElement_xpath("billing_maintain_billing_release_button_inholdmenu").click();//clickcing the release button
						getUiElement_xpath("billing_maintain_billing_save_button").click();//clicking the save button
						Thread.sleep(5000);	
						getUiElement_xpath("mb_bill").click();
						Thread.sleep(5000);						
						}
				}
				 boolean errchoice22 = getelement("err_gr").isDisplayed();	
					boolean errchoice23 = getelement("err_gr2").isDisplayed();			
						if(errchoice22 || errchoice23){			
							getUiElement_xpath("err_exit").click();
							Thread.sleep(2500);
							String Bill_Status=gettextval("//*[@id='statusCode']");
							if(Bill_Status.equals("DESCRIBED")){
								getUiElement_xpath("bl_charge").click();
							}
						}		
				boolean holdchk4 = isElementDisplayed("unrls_holdP");
				if(holdchk4) {
					System.out.println("Test Info: Working on un-released HOLDS");		
					String refhold = getUiElement_xpath("unrls_stop").getText();	
					if(!refhold.equals("REF") && !refhold.equals("POSTSETU") && !refhold.equals("RATE")){							
						getUiElement_xpath("billing_maintain_hold_menu").click();//clicking the hold menu
						//getUiElement_xpath("billing_maintain_hold_menu_al_check").click();//clicking the all checkbox item in hold menu
						getUiElement_xpath("hold_grid1").click();
						getUiElement_xpath("billing_maintain_billing_release_button_inholdmenu").click();//clickcing the release button
						getUiElement_xpath("billing_maintain_billing_save_button").click();//clicking the save button
						Thread.sleep(5000);	
						getUiElement_xpath("mb_bill").click();
						Thread.sleep(5000);						
						}
				}
				boolean errchoice24 = getelement("err_gr").isDisplayed();	
				boolean errchoice25 = getelement("err_gr2").isDisplayed();			
					if(errchoice24 || errchoice25){			
						getUiElement_xpath("err_exit").click();
						Thread.sleep(2500);
						String Bill_Status=gettextval("//*[@id='statusCode']");
						if(Bill_Status.equals("DESCRIBED")){
							getUiElement_xpath("bl_charge").click();
						}
					}	
				boolean holdchk5 = isElementDisplayed("unrls_holdP");
				if(holdchk5) {
					System.out.println("Test Info: Working on un-released HOLDS");		
					String refhold = getUiElement_xpath("unrls_stop").getText();	
					if(!refhold.equals("REF") && !refhold.equals("POSTSETU") && !refhold.equals("RATE")){							
						getUiElement_xpath("billing_maintain_hold_menu").click();//clicking the hold menu
						//getUiElement_xpath("billing_maintain_hold_menu_al_check").click();//clicking the all checkbox item in hold menu
						getUiElement_xpath("hold_grid1").click();
						getUiElement_xpath("billing_maintain_billing_release_button_inholdmenu").click();//clickcing the release button
						getUiElement_xpath("billing_maintain_billing_save_button").click();//clicking the save button
						Thread.sleep(5000);	
						getUiElement_xpath("mb_bill").click();
						Thread.sleep(5000);						
						}
				}
				boolean errchoice26 = getelement("err_gr").isDisplayed();	
				boolean errchoice27 = getelement("err_gr2").isDisplayed();			
					if(errchoice26 || errchoice27){			
						getUiElement_xpath("err_exit").click();
						Thread.sleep(2500);
						String Bill_Status=gettextval("//*[@id='statusCode']");
						if(Bill_Status.equals("DESCRIBED")){
							getUiElement_xpath("bl_charge").click();
						}
					}	
				boolean holdchk7 = isElementDisplayed("unrls_holdP");
				if(holdchk7) {
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
				/*boolean holdchk8 = isElementDisplayed("unrls_holdP");
				if(holdchk8) {
					System.out.println("Test Info: Working on un-released HOLDS");		
					getUiElement_xpath("billing_maintain_hold_menu").click();//clicking the hold menu
					getUiElement_xpath("billing_maintain_hold_menu_al_check").click();//clicking the all checkbox item in hold menu
					getUiElement_xpath("billing_maintain_billing_release_button_inholdmenu").click();//clickcing the release button
					getUiElement_xpath("mb_bill").click();
					Thread.sleep(5000);
					}*/
					//boolean errchoice3 = getelement("choice_screen_overlay2").isDisplayed();
				     while(isElementDisplayed("choice_screen_overlay2")){
					clickLink("1").click();			
					Thread.sleep(2500);
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
					Thread.sleep(5000);	
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
					Thread.sleep(3500);
					break;
				}
				String billstatus=gettextval("//*[@id='statusCode']");
				System.out.println(billstatus);
				if(billstatus.equals("RATED")){
					BILL_RATED=getUiElement_xpath("BLCharge_shipmentNumber").getAttribute("value");
					 SEQ = getUiElement_xpath("bl_seq").getAttribute("value"); 
					System.out.println("Rated Bill: " +BILL_RATED +SEQ);
				}			
				
				WebElement hoveKey2[] = {getelement("click_billing"),getelement("BillingSearch_lnk")};
				hoverAndClick(hoveKey2, getelement("BillingSearch_lnk"));	
				Thread.sleep(5000);
				getUiElement_xpath("maintain_billing_clear").click();
				selectFromDropDown(getUiElement_xpath("maintain_billing_shipment_status"), "ISSUED");		
				selectFromDropDown(getUiElement_xpath("bs_trade"), "H-HAWAII");
				selectFromDropDown(getUiElement_xpath("bs_ls"), "CY");
				selectFromDropDown(getUiElement_xpath("bs_ds"), "CY");
				clearvalue("billing_shipment_tracking_date");
				getUiElement_xpath("billing_shipment_tracking_date").sendKeys("01-01-2011");
				String toDate2 = add_Date(-60);
				System.out.println(toDate2);
				clearvalue("billing_shipment_tracking_date2");
				getUiElement_xpath("billing_shipment_tracking_date2").sendKeys(toDate);
				getUiElement_xpath("maintain_billing_search").click();
				Thread.sleep(2500);
				getUiElement_xpath("billingsearch_date_link").click();
				Thread.sleep(5000);
				getUiElement_xpath("billingsearch_date_link").click();
				Thread.sleep(5000);
				 int tablecount3 = tableCount("casQuickSearch");
				 for(int a=1;a<tablecount3;a++){			 
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
					int tablecount4 = tableCount("casQuickSearch");
					System.out.println("# of Billing Records: " +tablecount4);					
				   getUiElement_xpath("maintain_billing_first_checkbox").click();
				   getUiElement_xpath("maintain_billing_delete_QA").click();
				  boolean  isDialogPresent4 = isElementDisplayed(By.xpath(".//*[@id='ui-dialog-title-shipment_deleteQA']"));
					if(isDialogPresent4){
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
				     else if(verifyAlertMessage("Booking Found.").equals("true") || verifyAlertMessage("Booking located, but requested B/L not found.").equals("true")){
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
						Thread.sleep(2500);
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
						String commwt4 = getUiElement_xpath("comm_wgt").getAttribute("value");					
						if(verifyTextPresent2("comm_wgt") || commwt4.equals("0") ){
							getUiElement_xpath("comm_wgt").clear();
							getUiElement_xpath("comm_wgt").sendKeys(gates_data.readdata("19_Billing", "WEIGHT", Integer.valueOf(args.toString())));
						}
						getUiElement_xpath("mb_bill").click();					
						Thread.sleep(8000);					
						if(verifyErrorAlertPresent("*Tariff is mandatory").equals("true") || verifyErrorAlertPresent("Tariff did not load correctly, please re-enter").equals("true")){
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
							String commwt5 = getUiElement_xpath("comm_wgt").getAttribute("value");					
							if(verifyTextPresent2("comm_wgt") || commwt5.equals("0") ){
								getUiElement_xpath("comm_wgt").clear();
								getUiElement_xpath("comm_wgt").sendKeys(gates_data.readdata("19_Billing", "WEIGHT", Integer.valueOf(args.toString())));
							}		
							getUiElement_xpath("mb_bill").click();					
							Thread.sleep(6500);	
						}
						if(verifyErrorAlertPresent("Tariff did not load correctly, please re-enter").equals("true")) {
							clearvalue("mb_tariff");
							selectvalue("mb_tariff", gates_data.readdata("19_Billing", "TARIFF",Integer.valueOf(args.toString())));	
							Thread.sleep(1500);
							getUiElement_xpath("mb_bill").click();					
							Thread.sleep(6500);	
						}
						System.out.println("Test Info: Bill submission in progress.");				
				
						boolean errchoice7 = getelement("err_gr").isDisplayed();	
						boolean errchoice8 = getelement("err_gr2").isDisplayed();			
							if(errchoice7 || errchoice8){			
								getUiElement_xpath("err_exit").click();
								Thread.sleep(2500);
								String Bill_Status=gettextval("//*[@id='statusCode']");
								if(Bill_Status.equals("DESCRIBED")){
									getUiElement_xpath("bl_charge").click();
								}
							}					
							Thread.sleep(2500);	
							while(isElementDisplayed("choice_screen_overlay2")){
								clickLink("1").click();			
								Thread.sleep(10000);				
							}	
		boolean errchoice9 = getelement("err_gr").isDisplayed();	
		boolean errchoice10 = getelement("err_gr2").isDisplayed();			
			if(errchoice9 || errchoice10){			
				getUiElement_xpath("err_exit").click();
				Thread.sleep(2500);
				String Bill_Status=gettextval("//*[@id='statusCode']");
				if(Bill_Status.equals("DESCRIBED")){
					getUiElement_xpath("bl_charge").click();
				}
			}		
						boolean holdchk3 = isElementDisplayed("unrls_holdP");
						if(holdchk3) {
							System.out.println("Test Info: Working on un-released HOLDS");		
							String refhold = getUiElement_xpath("unrls_stop").getText();	
							
							if(!refhold.equals("REF") && !refhold.equals("POSTSETU") && !refhold.equals("RATE")){							
							getUiElement_xpath("billing_maintain_hold_menu").click();//clicking the hold menu
							//getUiElement_xpath("billing_maintain_hold_menu_al_check").click();//clicking the all checkbox item in hold menu
							getUiElement_xpath("hold_grid1").click();
							getUiElement_xpath("billing_maintain_billing_release_button_inholdmenu").click();//clickcing the release button
							getUiElement_xpath("billing_maintain_billing_save_button").click();//clicking the save button
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
								Thread.sleep(2500);
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
								getUiElement_xpath("mb_bill").click();
								Thread.sleep(5000);							
								if(isElementDisplayed("unrls_holdP")) {
									String refhold2 = getUiElement_xpath("unrls_stop").getText();	
									if(refhold2.equals("HAZ")){
									getUiElement_xpath("billing_maintain_hold_menu").click();//clicking the hold menu
									//getUiElement_xpath("billing_maintain_hold_menu_al_check").click();//clicking the all checkbox item in hold menu
									getUiElement_xpath("hold_grid1").click();
									getUiElement_xpath("billing_maintain_billing_release_button_inholdmenu").click();//clickcing the release button
									getUiElement_xpath("billing_maintain_billing_save_button").click();//clicking the save button
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
						while(isElementDisplayed("choice_screen_overlay2")){
							clickLink("1").click();			
							Thread.sleep(10000);				
						}	
						boolean holdchk6 = isElementDisplayed("unrls_holdP");
						if(holdchk6) {
							System.out.println("Test Info: Working on un-released HOLDS");		
							String refhold = getUiElement_xpath("unrls_stop").getText();	
							if(!refhold.equals("REF") && !refhold.equals("POSTSETU") && !refhold.equals("RATE")){							
								getUiElement_xpath("billing_maintain_hold_menu").click();//clicking the hold menu
								//getUiElement_xpath("billing_maintain_hold_menu_al_check").click();//clicking the all checkbox item in hold menu
								getUiElement_xpath("hold_grid1").click();
								getUiElement_xpath("billing_maintain_billing_release_button_inholdmenu").click();//clickcing the release button
								getUiElement_xpath("billing_maintain_billing_save_button").click();//clicking the save button
								Thread.sleep(5000);	
								getUiElement_xpath("mb_bill").click();
								Thread.sleep(5000);						
								}
						}
						boolean errchoice28 = getelement("err_gr").isDisplayed();	
						boolean errchoice29 = getelement("err_gr2").isDisplayed();			
							if(errchoice28 || errchoice29){			
								getUiElement_xpath("err_exit").click();
								Thread.sleep(2500);
								String Bill_Status=gettextval("//*[@id='statusCode']");
								if(Bill_Status.equals("DESCRIBED")){
									getUiElement_xpath("bl_charge").click();
								}
							}	
						boolean holdchk9 = isElementDisplayed("unrls_holdP");
						if(holdchk9) {
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
						/*boolean holdchk10 = isElementDisplayed("unrls_holdP");
						if(holdchk10) {
							System.out.println("Test Info: Working on un-released HOLDS");		
							getUiElement_xpath("billing_maintain_hold_menu").click();//clicking the hold menu
							getUiElement_xpath("billing_maintain_hold_menu_al_check").click();//clicking the all checkbox item in hold menu
							getUiElement_xpath("billing_maintain_billing_release_button_inholdmenu").click();//clickcing the release button
							getUiElement_xpath("mb_bill").click();
							Thread.sleep(5000);
							}*/
							//boolean errchoice3 = getelement("choice_screen_overlay2").isDisplayed();
						     while(isElementDisplayed("choice_screen_overlay2")){
							clickLink("1").click();			
							Thread.sleep(2500);
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
									Thread.sleep(2500);			
						String Bill_Status3=gettextval("//*[@id='statusCode']");
						if(Bill_Status3.equals("RATED")){
							System.out.println("Test Info: BILL RATED successfully.");
							//resultStatus("SMT_BLTS.01_TC.08A", "Pass", "", "User should be able to verify Bill Status for the loaded shipment on BL Charges Screen", "");
						} else if(Bill_Status3.equals("IN AUDIT")){
							getUiElement_xpath("BL_ReleaseAudit").click();
							//System.out.println("Test Info: IN AUDIT BILL Released successfully.");
							//resultStatus("SMT_BLTS.01_TC.08B", "Pass", "", "To verify that if the bill status was In AUDIT and by clicking RELEASE AUDIT button status changes to RATED .", "");
						}	
						else if(Bill_Status3.equals("DESCRIBED")){
							selectFromDropDown(getUiElement_xpath("BL_shipmentChargeId"), 1);
							clearvalue("BL_rate");
							setvalue("BL_rate",gates_data.readdata("19_Billing", "WEIGHT", Integer.valueOf(args.toString())));
							selectFromDropDown(getUiElement_xpath("BL_RateBasis"),gates_data.readdata("27_SPX", "RATE_BASIS", Integer.valueOf(args.toString())));
							getUiElement_xpath("BL_addbtn").click();
							getUiElement_xpath("charge_bill").click();	
							Thread.sleep(3000);
							String Bill_Status4=gettextval("//*[@id='statusCode']");
							if(Bill_Status4.equals("IN AUDIT")){
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
						String billstatus5=gettextval("//*[@id='statusCode']");
						System.out.println(billstatus5);
						if(billstatus.equals("RATED")){
							BILL_RATED2=getUiElement_xpath("BLCharge_shipmentNumber").getAttribute("value");
							SEQ2 = getUiElement_xpath("bl_seq").getAttribute("value"); 
							System.out.println("Rated Bill: " +BILL_RATED2 +SEQ2);
						}		
						
				System.out.println("Test Info - Test data retrieved for Send Bill Document.");
				
	hoverAndClick(new WebElement[] { getelement("Billing_lnk"),getelement("SendBillDocument_lnk")}, getelement("SendBillDocument_lnk"));
	Thread.sleep(5000);
	System.out.println("Test Info: Billng -Send Bill Document Started");
	if (verifycontenttitle("Send Bill Document").equals("true")) {
		resultStatus("BL06.1_TS_01_TC.01", "Pass", "", "To verify  user  to be able  to navigate Send Bill Document screen", "");
	} else {
		resultStatus("BL06.1_TS_01_TC.01", "Fail", "", "To verify  user  to be able  to navigate Send Bill Document screen", "");
	}
	//selectvalue("Sndbill_shipmentNumber", "1001430");
	//getUiElement_xpath("sndbill_gobtn").click();
	/*String Status_Label1=gettextval(".//*[@id='status']");
	if(Status_Label1.equals("IN AUDIT") && isElementEnabled(getUiElement_xpath("saveandsend"))==false){
		resultStatus("BL06.1_TS_01_TC.01a", "Pass", "", "To verify  Save & Send button is disabled on the Print Bill Screen by default for Audit Billings", "");
	}
	else if(!Status_Label1.equals("IN AUDIT")){
		clearvalue("Sndbill_shipmentNumber");
		selectvalue("Sndbill_shipmentNumber",gates_data.readdata("05_SendDoc", "BILLING_NUMBER_AUDIT", Integer.valueOf(args.toString())));
		getUiElement_xpath("sndbill_gobtn").click();
		Thread.sleep(1500);
		while(isElementEnabled(getUiElement_xpath("saveandsend"))==false){
			resultStatus("BL06.1_TS_01_TC.01a", "Pass", "", "To verify  Save & Send button is disabled on the Print Bill Screen by default for Audit Billings", "");
			break;
		}		
	}
	else{
		resultStatus("BL06.1_TS_01_TC.01a", "Fail", "", "To verify  Save & Send button is disabled on the Print Bill Screen by default for Audit Billings", "");
	}	*/
	clearvalue("Sndbill_shipmentNumber");
	sendtab("Sndbill_shipmentNumber");	
	Thread.sleep(3000);
	if(verifyAlertMessage("* This field is required").equals("true")){
		resultStatus("BL06.1_TS_01_TC.02", "Pass", "", "To verify  mandatory validations are checked for Billing Number on Send Bill Document screen.", "");
	} else {
		resultStatus("BL06.1_TS_01_TC.02", "Fail", "", "To verify  mandatory validations are checked for Billing Number on Send Bill Document screen.", "");
	}
	
	clearvalue("Sndbill_shipmentNumber");
	selectvalue("Sndbill_shipmentNumber", BILL_RATED);
	clearvalue("bl_seq");
	selectvalue("bl_seq", SEQ);
			//gates_data.readdata("05_SendDoc", "BILLING_NUMBER", Integer.valueOf(args.toString())));
	getUiElement_xpath("sndbill_gobtn").click();
	Thread.sleep(2000);
	if(verifyAlertMessage("Bill successfully loaded").equals("true")){
		resultStatus("BL06.1_TS_01_TC.03", "Pass", "", "To verify  Shipment has been successfully loaded on Send Bill Document Screen.", "");
	} else {
		resultStatus("BL06.1_TS_01_TC.03", "Fail", "", "To verify  Shipment has been successfully loaded on Send Bill Document Screen.", "");
	}
	
	String Trade=gettextval("//*[@id='trade']");
	if(!Trade.isEmpty()){
		resultStatus("BL06.1_TS_01_TC.04", "Pass", "", "To verify trade field wrt the shipment is loaded.", "");
	} else {
		resultStatus("BL06.1_TS_01_TC.04", "Fail", "", "To verify trade field wrt the shipment is loaded.", "");
	}
	
	String Status_Label=gettextval(".//*[@id='status']");
	if(Status_Label.equals("RATED")){
		resultStatus("BL06.1_TS_01_TC.05", "Pass", "", "To verify Status is RATED for Status field wrt the shipment is loaded.", "");
	} else {
		resultStatus("BL06.1_TS_01_TC.05", "Fail", "", "To verify Status is RATED for Status field wrt the shipment is loaded.", "");
	}
	int Table_Data1=tableCount_xpath(".//*[@id='contactGrid']");
	System.out.println(Table_Data1);
	if(Table_Data1>1){
		getUiElement_xpath("contact_chkbox").click();
		boolean saveen = isElementEnabled(getUiElement_xpath("saveandsend"));
		if(saveen){
		resultStatus("BL06.1_TS_01_TC.06", "Pass", "", "To verify Save & Send button is enabled by selecting check box from contact grid.", "");
		}		
	}
	else if(Table_Data1<=1){
		getUiElement_xpath("contact_cust").sendKeys(gates_data.readdata("05_SendDoc", "CUST_NAME", Integer.valueOf(args.toString())));
		getUiElement_xpath("contact_name").sendKeys(gates_data.readdata("05_SendDoc", "CONT_NAME", Integer.valueOf(args.toString())));
		selectFromDropDown(getUiElement_xpath("primary_met"),gates_data.readdata("05_SendDoc", "PRIMARY_MET", Integer.valueOf(args.toString())));
		getUiElement_xpath("prim_value").sendKeys(gates_data.readdata("05_SendDoc", "VALUE", Integer.valueOf(args.toString())));
		getUiElement_xpath("cont_add").click();
		getUiElement_xpath("contact_chkbox").click();
		boolean saveen = isElementEnabled(getUiElement_xpath("saveandsend"));
		if(saveen){
		resultStatus("BL06.1_TS_01_TC.06", "Pass", "", "To verify Save & Send button is enabled by selecting check box from contact grid.", "");
		}		
	}
	else {
		resultStatus("BL06.1_TS_01_TC.06", "Fail", "", "To verify Save & Send button is enabled by selecting check box from contact grid.", "");
	}
	if(isElementEnabled(getUiElement_xpath("sendself"))==true){
		resultStatus("BL06.1_TS_01_TC.06a", "Pass", "", "To verify Send to Self button is enabled on the Print Bill Screen by default", "");
	}
	else {
		resultStatus("BL06.1_TS_01_TC.06a", "Fail", "", "To verify Send to Self button is enabled on the Print Bill Screen by default", "");
	}
	while(isElementEnabled(getUiElement_xpath("sendself"))==true){
		getUiElement_xpath("copies").sendKeys("1");
		getUiElement_xpath("sendself").click();
		Thread.sleep(1500);
		break;
	}
	while (isAlertPresent()) {// If alert present in the page
		closeAlertAndGetItsText();// closing the alert
	}
	if(verifyAlertMessage("Request submitted successfully").equals("true")){
		resultStatus("BL06.1_TS_01_TC.06b", "Pass", "", "To verify Send to Self request submitted successfully.", "");
	}
	else {
		resultStatus("BL06.1_TS_01_TC.06b", "Fail", "", "To verify Send to Self request submitted successfully.", "");
	}
	if(isElementEnabled(getUiElement_xpath("print"))==true){
		resultStatus("BL06.1_TS_01_TC.07", "Pass", "", "To verify Print button is enabled on the Print Bill Screen by default", "");
	}
	else {
		resultStatus("BL06.1_TS_01_TC.07", "Fail", "", "To verify Print button is enabled on the Print Bill Screen by default", "");
	}
	while(isElementEnabled(getUiElement_xpath("print"))==true){		
		getUiElement_xpath("printreq").click();
		switchToWindow("GATES: Quick Search");
		Thread.sleep(4000);
		getUiElement_xpath("print_search").click();
		Thread.sleep(3000);
		clickFirstLink3("casQuickSearch", 1, 1).click();
		switchToWindow("GATES: Print Bill");
		Thread.sleep(1500);
		getUiElement_xpath("print").click();
		break;
	}
	while (isAlertPresent()) {// If alert present in the page
		closeAlertAndGetItsText();// closing the alert
	}
	if(verifyAlertMessage("Print request submitted successfully.").equals("true")){
		resultStatus("BL06.1_TS_01_TC.07a", "Pass", "", "To verify Print request submitted successfully.", "");
	}
	else {
		resultStatus("BL06.1_TS_01_TC.07a", "Fail", "", "To verify Print request submitted successfully.", "");
	}
	
	if(isElementEnabled(getUiElement_xpath("ediButton"))==false){
		resultStatus("BL06.1_TS_01_TC.08", "Pass", "", "To verify EDI button is disabled on the Print Bill Screen by default", "");
	}
	else {
		resultStatus("BL06.1_TS_01_TC.08", "Fail", "", "To verify EDI button is disabled on the Print Bill Screen by default", "");
	}
		
	if(isElementEnabled(getUiElement_xpath("web"))==false){
		resultStatus("BL06.1_TS_01_TC.09", "Pass", "", "To verify Web button is disabled on the Print Bill Screen if the B/L Status is NOT ISSD/CORR.", "");
	}
	else {
		resultStatus("BL06.1_TS_01_TC.09", "Fail", "", "To verify Web button is disabled on the Print Bill Screen if the B/L Status is NOT ISSD/CORR.", "");
	}
	
	if(isElementEnabled(getUiElement_xpath("shipmentHoldReleaseBtn"))==false){
		resultStatus("BL06.1_TS_01_TC.10", "Pass", "", "To verify Hold Release button is disabled on the Print Bill Screen by default", "");
	}
	else {
		resultStatus("BL06.1_TS_01_TC.10", "Fail", "", "To verify Hold Release button is disabled on the Print Bill Screen by default", "");
	}
	
	if(isElementEnabled(getUiElement_xpath("snd_billbtn"))==true){
		resultStatus("BL06.1_TS_01_TC.11", "Pass", "", "To verify Bill  button is Enabled on the Print Bill Screen by default", "");
	}
	else {
		resultStatus("BL06.1_TS_01_TC.11", "Fail", "", "To verify Bill  button is Enabled on the Print Bill Screen by default", "");
	}
	
	if(isElementEnabled(getUiElement_xpath("statusButton"))==true){
		resultStatus("BL06.1_TS_01_TC.12", "Pass", "", "To verify Status  button is Enabled on the Print Bill Screen by default", "");
	}
	else {
		resultStatus("BL06.1_TS_01_TC.12", "Fail", "", "To verify Status  button is Enabled on the Print Bill Screen by default", "");
	}
	
	if(isElementEnabled(getUiElement_xpath("issueBill"))==true){
		resultStatus("BL06.1_TS_01_TC.13", "Pass", "", "To verify Issue Bill  button is Enabled on the Print Bill Screen by default", "");
	}
	else {
		resultStatus("BL06.1_TS_01_TC.13", "Fail", "", "To verify Issue Bill  button is Enabled on the Print Bill Screen by default", "");
	}
	
	//'Select Document Type : 
	
	selectFromDropDown(getUiElement_xpath("selectedDocType"),1);
	
	String Party_Type=getDropDownval(getUiElement_xpath("selectedPartyType"));
	if(!Party_Type.isEmpty()){
		resultStatus("BL06.1_TS_01_TC.14", "Pass", "", "To verify on selecting Document Type Party Type is loaded by default.", "");
	}
	else {
		resultStatus("BL06.1_TS_01_TC.14", "Fail", "", "To verify on selecting Document Type Party Type is loaded by default.", "");
	}
	
	String contactid=getDropDownval(getUiElement_xpath("selectedContactId"));
	if(!contactid.isEmpty()){
		resultStatus("BL06.1_TS_01_TC.15", "Pass", "", "To verify on selecting Document TypeContact ID is loaded by default.", "");
	}
	else {
		resultStatus("BL06.1_TS_01_TC.15", "Fail", "", "To verify on selecting Document TypeContact ID is loaded by default.", "");
	}
	
	//'Check Print button is enabled now
	
	if(isElementEnabled(getUiElement_xpath("print"))==true){
		resultStatus("BL06.1_TS_01_TC.16", "Pass", "", "To verify Print  button is Enabled on the Print Bill Screen after selecting the Document Type", "");
	}
	else {
		resultStatus("BL06.1_TS_01_TC.16", "Fail", "", "To verify Print  button is Enabled on the Print Bill Screen after selecting the Document Type", "");
	}
	
	getUiElement_xpath("issueBill").click();
	Thread.sleep(2000);	
	if(verifyAlertMessage("Bill is Successfully issued").equals("true") || verifyAlertMessage("Bill successfully loaded").equals("true")){
		resultStatus("BL06.1_TS_01_TC.17", "Pass", "", "To verify  Bill has been issued successfully.", "");
	} else if(verifyAlertMessage("Issue of bill with HOLDs attempted - Not Allowed.").equals("true")){
		hoverAndClick(new WebElement[] { getelement("Billing_lnk")}, getelement("Billing_lnk"));
		Thread.sleep(1500);
		//getUiElement_xpath("hold_grid1").click();
		getUiElement_xpath("expand_all").click();
		getUiElement_xpath("billing_maintain_billing_release_button_inholdmenu").click();			
		getUiElement_xpath("shipmentSaveBtn").click();
		Thread.sleep(1500);
		getUiElement_xpath("shipmentSendDocBtn").click();
		selectFromDropDown(getUiElement_xpath("selectedDocType"), 2);
		getUiElement_xpath("issueBill").click();
		Thread.sleep(2000);
		while(verifyAlertMessage("Bill is Successfully issued").equals("true") || verifyAlertMessage("Bill successfully loaded").equals("true")){
		resultStatus("BL06.1_TS_01_TC.17", "Pass", "", "To verify  Bill has been issued successfully.", "");
			break;
		}
	} else {
		resultStatus("BL06.1_TS_01_TC.17", "Fail", "", "To verify  Bill has been issued successfully.", "");
	}
	
//	selectFromDropDown(getUiElement_xpath("selectedDocType"),"Please Select");
	getUiElement_xpath("statusButton").click();
	Thread.sleep(2500);
	if (verifycontenttitle("Bill Status History").equals("true")) {
		resultStatus("BL06.1_TS_01_TC.18", "Pass", "", "To verify  Bill Status History Screen is displayed on clicking Status button", "");
	} else {
		resultStatus("BL06.1_TS_01_TC.18", "Fail", "", "To verify  Bill Status History Screen is displayed on clicking Status button", "");
	}
	
	if(verifyAlertMessage("Bill successfully loaded").equals("true")){
		resultStatus("BL06.1_TS_01_TC.19", "Pass", "", "To verify  Shipment Successfully Loaded on Bill Status History Screen.", "");
	} else {
		resultStatus("BL06.1_TS_01_TC.19", "Fail", "", "To verify  Shipment Successfully Loaded on Bill Status History Screen.", "");
	}
	
	getUiElement_xpath("history_exit").click();
	Thread.sleep(1500);
	clearvalue("Sndbill_shipmentNumber");
	selectvalue("Sndbill_shipmentNumber", BILL_RATED2);
	clearvalue("bl_seq");
	selectvalue("bl_seq", SEQ2);
			//gates_data.readdata("05_SendDoc", "BILLING_NUMBER_NEW", Integer.valueOf(args.toString())));
	getUiElement_xpath("sndbill_gobtn").click();
	Thread.sleep(2000);
	//Added the below line to click Issue Bill button - Magesh
	selectFromDropDown(getUiElement_xpath("selectedDocType"),"Please Select");
	getUiElement_xpath("issueBill").click();
	Thread.sleep(3500);
	if(verifyAlertMessage("Issue of bill with HOLDs attempted - Not Allowed.").equals("true")){
		hoverAndClick(new WebElement[] { getelement("Billing_lnk")}, getelement("Billing_lnk"));
		Thread.sleep(1500);
		getUiElement_xpath("hold_grid1").click();	
		getUiElement_xpath("billing_maintain_billing_release_button_inholdmenu").click();
		getUiElement_xpath("shipmentSaveBtn").click();
		Thread.sleep(1500);
		getUiElement_xpath("shipmentSendDocBtn").click();
		selectFromDropDown(getUiElement_xpath("selectedDocType"),"Please Select");
		getUiElement_xpath("issueBill").click();
			
	}
	
	if(verifyAlertMessage("Bill is Successfully issued").equals("true") || verifyAlertMessage("Bill successfully loaded").equals("true")){
		resultStatus("BL06.1_TS_01_TC.20", "Pass", "", "To verify  Bill has been issued successfully after forth back fromt the page Bill Status History. ", "");
	} else {
		resultStatus("BL06.1_TS_01_TC.20", "Fail", "", "To verify  Bill has been issued successfully after forth back fromt the page Bill Status History.", "");
	}
	
	Status_Label=gettextval(".//*[@id='status']");
	if(Status_Label.equals("ISSUED")){
		resultStatus("BL06.1_TS_01_TC.21", "Pass", "", "To verify Status  has changed to  ISSUED after successful issuing of Bill.", "");
	} else {
		resultStatus("BL06.1_TS_01_TC.21", "Fail", "", "To verify Status  has changed to  ISSUED after successful issuing of Bill.", "");
	}
	System.out.println("Test Info: Billng -Send Bill Document Ends");
	clickLink("Logout").click();
		
  }
}	