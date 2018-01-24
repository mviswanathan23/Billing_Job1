package com.gates.spx;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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
public class SPXBooking_Gems extends Login {
	String BookingN;
	//String cont2;
	String cont2;	
	String type2;
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "//atu.properties");
	}

	@Test(description = "SPX Booking", dataProvider = "iteration")
	public void booking(Object args) throws Exception {	
		
		resultStatus("SPX Booking", "******", " ", "Start", "");
		
	
		try {
			ATUReports.currentRunDescription = this.getClass().getSimpleName();
			ATUReports.setWebDriver(driver);
			ATUReports.indexPageDescription = "<h1><font color=\"blue\">Gates Automation </font></h1>";
			ATUReports.setAuthorInfoAtClassLevel("Encore Team", Utils.getCurrentTime(), "1.0");
			utility gates_data = new utility();
			String Inputfile = System.getProperty("user.dir") + "//InputFiles//Inputs.xls";
			//System.out.println(Inputfile);
			gates_data.setInputFile(Inputfile);
			Thread.sleep(15000);
			Assert.assertEquals(driver.getTitle(), "GATES: Customer Profile");
			Thread.sleep(5000);
		hoverAndClick(new WebElement[] { getelement("booking") }, getelement("maintain_booking"));	
			Thread.sleep(1500);
			selectFromDropDown(getUiElement_xpath("trade_value"), gates_data.readdata("23_Gems", "TRADE", Integer.valueOf(args.toString())));				     				 	     	
			selectFromDropDown(getUiElement_xpath("customer_groupId"), gates_data.readdata("23_Gems", "CUSTOMER", Integer.valueOf(args.toString())));
			getUiElement_xpath("expand_all").click();
			Thread.sleep(2000);
			selectvalue("Senddoc_organizationName",(gates_data.readdata("23_Gems", "SHIPPER", Integer.valueOf(args.toString()))));
			Thread.sleep(3000);
			switchToWindow("GATES: Quick Search");
			if (driver.getTitle().equals("GATES: Quick Search")) {
                 Thread.sleep(1500);
				// If all is displayed				
				if (isElementDisplayed(By.linkText("ALL"))) {
					clickFirstLink("casQuickSearch", "4", "1").click();					
					switchToWindow("GATES: Create booking from Template/Quote");
					if (driver.getTitle().equals("GATES: Create booking from Template/Quote")) {
						getUiElement_xpath("booking_shipper_template_window_exit").click();						
					}
					switchToWindow("GATES: Booking");								
				}
			}			
			else{
				getUiElement_xpath("origanization_address").click();
				switchToWindow("GATES: Quick Search");
				if (isElementDisplayed(By.linkText("ALL"))) {
					clickFirstLink("casQuickSearch", "4", "1").click();	
				}
				switchToWindow("GATES: Booking");
			}
			Thread.sleep(1500);
						
			if(getValue("booking_shipper_contact", "value").isEmpty()){
				getUiElement_xpath("booking_shipper_contact").sendKeys(gates_data.readdata("01_Shipper", "CONTACT_NAME", Integer.valueOf(args.toString())));
				}
			if (getValue("maintain_area_code", "value").isEmpty()) {
				getUiElement_xpath("maintain_country_code").sendKeys(gates_data.readdata("01_Shipper", "COUNTRY_CODE", Integer.valueOf(args.toString())));
				getUiElement_xpath("maintain_area_code").sendKeys(gates_data.readdata("01_Shipper", "AREA_CODE", Integer.valueOf(args.toString())));
				getUiElement_xpath("maintain_exchange_code").sendKeys(gates_data.readdata("01_Shipper", "EXCHANGE", Integer.valueOf(args.toString())));
				getUiElement_xpath("maintain_phonestation").sendKeys(gates_data.readdata("01_Shipper", "STATION_CODE", Integer.valueOf(args.toString())));

			}
			getUiElement_xpath("copy_shipper_address").click();
			selectFromDropDown(getUiElement_xpath("prepaidCollectCode"), "Prepaid");
			Thread.sleep(2000);			
			
			selectFromDropDown(getUiElement_xpath("load_service_code"), gates_data.readdata("23_Gems", "LS", Integer.valueOf(args.toString())));
			selectFromDropDown(getUiElement_xpath("dicharge_service_code"), gates_data.readdata("23_Gems", "DS", Integer.valueOf(args.toString())));
			Thread.sleep(5000);
			selectvalue("originPortCityCodeDescription", gates_data.readdata("23_Gems", "POL", Integer.valueOf(args.toString())));
			Thread.sleep(2000);			
			selectvalue("destinationPortCityCodeDescription", gates_data.readdata("23_Gems", "POD", Integer.valueOf(args.toString())));
			selectvalue("placeofdel", gates_data.readdata("23_Gems", "POD2", Integer.valueOf(args.toString())));
			getUiElement_xpath("divOfRoutingVVDlnk", true).click();		
			Thread.sleep(5000);
			getradioBtn("searchCriteria", "L").click();
			getUiElement_xpath("searchVVDButton").click();	
			Thread.sleep(3000);
			clickFirstLink("gbox_vvdResultGrid", "3", "2").click();
			int routingTable = tableCount("vvdRoutingGrid");
			System.out.println("Routing table count : " +routingTable);
			 boolean vvd = false;
			   for(int a=1;a<=routingTable;a++){	
				   int c=0;
				   c=driver.findElements(By.xpath("//*[@id='vvdRoutingGrid']//*[@id="+a+"]/td[5]")).size();
						 System.out.println(c);
			    String resultValue = gettextval("//*[@id='vvdRoutingGrid']//*[@id="+a+"]/td[5]");		    
			    vvd = resultValue.equals("HON");	
			    if(vvd){
			    	resultStatus("SPX_BK01.1_TS.01_TC.01", "Pass", "", "User should verify the POD displayed as HON for 1st VVD for SPX booking with PJT/PJT service.", "");
					break;
				}			
			   }					
			getUiElement_xpath("commodity_desc").sendKeys("test");			
			boolean el = isElementDisplayed("eq_grid");			
			if(el){
				//getUiElement_xpath("eq1").click();
			clearvalue("equipment_quantity");
			getUiElement_xpath("equipment_quantity").sendKeys(gates_data.readdata("22_GatesToDB", "EQUIP_QUANTITY", Integer.valueOf(args.toString())));
			clearvalue("equipment_type");
			getUiElement_xpath("equipment_type").sendKeys(gates_data.readdata("22_GatesToDB", "EQUIP_TYPE", Integer.valueOf(args.toString())));
			clearvalue("equipment_size");
			getUiElement_xpath("equipment_size").sendKeys(gates_data.readdata("22_GatesToDB", "EQUIP_SIZE", Integer.valueOf(args.toString())));			
			getUiElement_xpath("add1").click();
			}
			else{
				getUiElement_xpath("eq1").click();				
				clearvalue("equipment_quantity");
				getUiElement_xpath("equipment_quantity").sendKeys(gates_data.readdata("22_GatesToDB", "EQUIP_QUANTITY", Integer.valueOf(args.toString())));
				clearvalue("equipment_type");
				getUiElement_xpath("equipment_type").sendKeys(gates_data.readdata("22_GatesToDB", "EQUIP_TYPE", Integer.valueOf(args.toString())));
				clearvalue("equipment_size");
				getUiElement_xpath("equipment_size").sendKeys(gates_data.readdata("22_GatesToDB", "EQUIP_SIZE", Integer.valueOf(args.toString())));				
				getUiElement_xpath("add1").click();
			}		
			getUiElement_xpath("save").click();
			Thread.sleep(2500);			
			boolean erralert = isElementDisplayed("err_alertclose");				
		    if(erralert){
		    getUiElement_xpath("err_alertclose").click();
		    }
		    if(verifyMessagePresent("Place of Receipt required for this Load Discharge Service Pair").equals("true")){
		    	resultStatus("SPX_BK01.1_TS.01_TC.02", "Pass", "", "User should verify the error message “Place of Receipt required for this Load Discharge Service Pair“.", "");
		    }
		    selectvalue("placeofreceipt", gates_data.readdata("23_Gems", "POR", Integer.valueOf(args.toString())));			    
		    getUiElement_xpath("save").click();
		    Thread.sleep(5000);
		    getUiElement_xpath("expand_all").click();
			Thread.sleep(1500);
		    clearvalue("commodity_desc");
		    getUiElement_xpath("commodity_desc").sendKeys("test");
		    getUiElement_xpath("save").click();
			Thread.sleep(2500);	
			boolean erralert2 = isElementDisplayed("err_alertclose");				
		    if(erralert2){
		    getUiElement_xpath("err_alertclose").click();
		    }
		    if(verifyMessagePresent("Change Source required").equals("true")){
		    	resultStatus("SPX_BK01.1_TS.01_TC.03", "Pass", "", "User should verify the error message “Change source Required” after editing the commodity description.", "");
		    	getUiElement_xpath("divOfRoutingVVDlnk", true).click();		
				Thread.sleep(5000);
				getradioBtn("searchCriteria", "L").click();
				getUiElement_xpath("searchVVDButton").click();	
				Thread.sleep(3000);
				clickFirstLink("gbox_vvdResultGrid", "2", "2").click();
				selectFromDropDown(getUiElement_xpath("wiredown_rollvvd_change_source"), "Shipper");				
		    }
		    else{
		    	resultStatus("SPX_BK01.1_TS.01_TC.03", "fail", "", "User should verify the error message “Change source Required” after editing the commodity description.", "");
		    }
		    getUiElement_xpath("save").click();
			Thread.sleep(2500);	
			 if(verifyMessagePresent("Booking updated successfully.").equals("true")){
			resultStatus("SPX_BK01.1_TS.01_TC.04", "Pass", "", "User should verify the update message for SPX Booking.", "");
			 }
			 else{
				 resultStatus("SPX_BK01.1_TS.01_TC.04", "fail", "", "User should verify the update message for SPX Booking.", "");
			 }
			boolean unrlsdHold = isElementDisplayed("lcl_holdpop");
	    	String holdgrid = getUiElement_xpath("itnhold_grid").getText();
	    	String Booking_Status=getDropDownval(getUiElement_xpath("bookingStatusCode"));
		
			if (unrlsdHold && holdgrid.equals("ITNBKG") && Booking_Status.equals("APPROVED")){		  
				resultStatus("SPX_BK01.1_TS.01_TC.05", "Pass", "", "User should verify the unreleased HOLD for “ITNBKG” and save message for SPX booking.", "");
				 BookingN = getUiElement_xpath("bookingn").getAttribute("value");
					System.out.println(BookingN);
			}
			else{
				resultStatus("SPX_BK01.1_TS.01_TC.05", "fail", "", "User should verify the unreleased HOLD for “ITNBKG” and save message for SPX booking.", "");
			}
			getUiElement_xpath("expand_all").click();
			Thread.sleep(1500);
			getUiElement_xpath("hold_chk").click();
			getUiElement_xpath("rel_hold").click();
			Thread.sleep(2500);
			 getUiElement_xpath("save").click();
				Thread.sleep(2500);
				boolean unrlsdHold2 = isElementDisplayed("lcl_holdpop");
				if(unrlsdHold2){
					resultStatus("SPX_BK01.1_TS.01_TC.06", "Pass", "", "User should verify the unreleased HOLD for “ITNBKG” is removed and able to SAVE.", "");
				}
				else{
					resultStatus("SPX_BK01.1_TS.01_TC.06", "fail", "", "User should verify the unreleased HOLD for “ITNBKG” is removed and able to SAVE.", "");
				}
			String gems = gates_data.readdata("23_Gems", "GEMS", Integer.valueOf(args.toString()));
			driver.get(gems);
			Thread.sleep(2500);	
			Assert.assertEquals("Gems Online Login Page", driver.getTitle(), "Login Page not loaded");
			switchToWindow("GEMS - Equipment Log");
			getUiElement_xpath("un_gems").clear();
			getUiElement_xpath("un_gems").sendKeys(gates_data.readdata("23_Gems", "Username", Integer.valueOf(args.toString())));
			getUiElement_xpath("pw_gems").clear();
			getUiElement_xpath("pw_gems").sendKeys(gates_data.readdata("23_Gems", "Password", Integer.valueOf(args.toString())));
			getUiElement_xpath("login_submit").click();
			Thread.sleep(2000);
			hoverAndClick(new WebElement[] { getelement("Invent_ParentLink"), getelement("Invent_lnk") }, getelement("Invent_lnk"));
			Thread.sleep(1500);		
			System.out.println(BookingN);		
					
			getUiElement_xpath("port_city").sendKeys(gates_data.readdata("23_Gems", "POL", Integer.valueOf(args.toString())));
			getUiElement_xpath("cont_type").sendKeys(gates_data.readdata("23_Gems", "TYPE", Integer.valueOf(args.toString())));
			getUiElement_xpath("equip_type").sendKeys(gates_data.readdata("23_Gems", "EQ_TYPE4", Integer.valueOf(args.toString())));
			getUiElement_xpath("inv_search").click();
			Thread.sleep(2000);
			String emptyContainer2 = getUiElement_xpath("empty_cont").getText();
			System.out.println(emptyContainer2);
			 for (String cont: emptyContainer2.split(" ")){
		         System.out.println(cont);
			String[] container2 = emptyContainer2.split(" ");
			cont2 = container2[0]; 
			type2 = container2[1];
			break;
				}
			hoverAndClick(new WebElement[] { getelement("Gate_ParentLink"), getelement("Gate_lnk") }, getelement("Gate_lnk"));
			Thread.sleep(1500);
			getUiElement_xpath("gate_loc").sendKeys(gates_data.readdata("25_AlaskaBooking_PE", "POL", Integer.valueOf(args.toString())));
			getUiElement_xpath("gate_locT").sendKeys(gates_data.readdata("23_Gems", "TYPE", Integer.valueOf(args.toString())));
			getUiElement_xpath("gate_cont").sendKeys(cont2);
			getUiElement_xpath("gate_contChkD").sendKeys(type2);
			getUiElement_xpath("chasis").sendKeys(gates_data.readdata("23_Gems", "CHASIS", Integer.valueOf(args.toString())));
			getUiElement_xpath("truc_code").sendKeys(gates_data.readdata("23_Gems", "TRUC_CODE", Integer.valueOf(args.toString())));
			getUiElement_xpath("gate_bn").sendKeys(BookingN);
			selectFromDropDown(getUiElement_xpath("trans"), 2);
			getUiElement_xpath("cont_go").click();			 
			if(verifyAlertMessage("Equipment already in yard. Verify number and review its current status.").equals("true")){
				clearvalue("truc_code");
				getUiElement_xpath("truc_code").sendKeys(gates_data.readdata("23_Gems", "TRUC_CODE2", Integer.valueOf(args.toString())));
				clearvalue("gate_bn");
				selectFromDropDown(getUiElement_xpath("trans"), 5);
				getUiElement_xpath("cont_go").click();
				String date=add_Date(-2);
				/*clearvalue("date_field");
				setvalue("date_field",date);*/
				getUiElement_xpath("cont_save").click();
				Thread.sleep(2500);
			
				if(verifyAlertMessage("AA000-I: Requested process was successful").equals("true")){
					getUiElement_xpath("gate_cont").sendKeys(cont2);
					getUiElement_xpath("gate_contChkD").sendKeys(type2);
					getUiElement_xpath("chasis").sendKeys(gates_data.readdata("23_Gems", "CHASIS", Integer.valueOf(args.toString())));
					getUiElement_xpath("truc_code").sendKeys(gates_data.readdata("23_Gems", "TRUC_CODE", Integer.valueOf(args.toString())));
					getUiElement_xpath("gate_bn").sendKeys(BookingN);
					selectFromDropDown(getUiElement_xpath("trans"), 2);
					getUiElement_xpath("cont_go").click();
					Thread.sleep(1500);
					getUiElement_xpath("net_wt").sendKeys(gates_data.readdata("23_Gems", "WEIGHT", Integer.valueOf(args.toString())));
					getUiElement_xpath("cont_save").click();
					Thread.sleep(2500);
				}
					if(verifyAlertMessage("AA000-I: Requested process was successful").equals("true")){					
					clickLink("Logout").click();
					String gatesN = gates_data.readdata("22_GatesToDB", "GATES", Integer.valueOf(args.toString()));
					driver.get(gatesN);
					Thread.sleep(3500);		
					hoverAndClick(new WebElement[] { getelement("booking") }, getelement("maintain_booking"));	
					Thread.sleep(6500);
					getUiElement_xpath("book_bill").click();
					Thread.sleep(5000);
					resultStatus("SPX_BK01.1_TS.01_TC.07", "Pass", "", "User should verify the page navigates to B/L Setup page after IGT a container.", "");					
					getUiElement_xpath("chklink_book").click();
					getUiElement_xpath("billingSave").click();	
					Thread.sleep(3500);
					if(verifyAlertMessage("Please select the Debtor type.").equals("true")){
						resultStatus("SPX_BK01.1_TS.01_TC.08", "Pass", "", "User should verify the error message “Please select the Debtor type.”", "");
					}
					selectFromDropDown(getUiElement_xpath("responsiblePartyCode"),"Prepaid");
					getUiElement_xpath("billingSave").click();	
					Thread.sleep(3500);
					if(verifyAlertMessage("Successfully Loaded.").equals("true")){
						resultStatus("SPX_BK01.1_TS.01_TC.09", "Pass", "", "User should verify the link message displayed and bill created for SPX booking.", "");
					}
					else{
						resultStatus("SPX_BK01.1_TS.01_TC.09", "fail", "", "User should verify the link message displayed and bill created for SPX booking.", "");
					}
					getUiElement_xpath("bl_mbill").click();
					Thread.sleep(6000);
					}		
			}
			
					
			else{
			while (isAlertPresent()) {
				acceptAlert();
			}		
			Thread.sleep(2000);			
			getUiElement_xpath("net_wt").sendKeys(gates_data.readdata("23_Gems", "WEIGHT", Integer.valueOf(args.toString())));
			getUiElement_xpath("cont_save").click();	
			Thread.sleep(2500);
			if(verifyAlertMessage("AA000-I: Requested process was successful").equals("true")){	
			Thread.sleep(2000);
			clickLink("Logout").click();	
			Thread.sleep(2000);
			}
			String gatesN = gates_data.readdata("22_GatesToDB", "GATES", Integer.valueOf(args.toString()));
			driver.get(gatesN);
			Thread.sleep(3500);		
			hoverAndClick(new WebElement[] { getelement("booking") }, getelement("maintain_booking"));	
			Thread.sleep(6500);
			getUiElement_xpath("book_bill").click();
			Thread.sleep(5000);							
			getUiElement_xpath("chklink_book").click();
			resultStatus("SPX_BK01.1_TS.01_TC.07", "Pass", "", "User should verify the page navigates to B/L Setup page after IGT a container.", "");
			getUiElement_xpath("billingSave").click();	
			Thread.sleep(3500);
			if(verifyAlertMessage("Please select the Debtor type.").equals("true")){
				resultStatus("SPX_BK01.1_TS.01_TC.08", "Pass", "", "User should verify the error message “Please select the Debtor type.”", "");
			}
			selectFromDropDown(getUiElement_xpath("responsiblePartyCode"),"Prepaid");
			getUiElement_xpath("billingSave").click();	
			Thread.sleep(3500);
			if(verifyAlertMessage("Successfully Loaded.").equals("true")){
				resultStatus("SPX_BK01.1_TS.01_TC.09", "Pass", "", "User should verify the link message displayed and bill created for SPX booking.", "");
			}
			else{
				resultStatus("SPX_BK01.1_TS.01_TC.09", "fail", "", "User should verify the link message displayed and bill created for SPX booking.", "");
			}
			getUiElement_xpath("bl_mbill").click();
			Thread.sleep(6000);
			}
			 
				 String bill_status = getUiElement_xpath("bill_status").getText();
				 System.out.println(bill_status);
				 if(bill_status.equals("PENDING")){
					 resultStatus("SPX_BK01.1_TS.01_TC.10", "Pass", "", "User should verify the SPX bill created and status sets to PENDING in maintain bill page.", "");
				 }
				 else{
					 resultStatus("SPX_BK01.1_TS.01_TC.10", "fail", "", "User should verify the SPX bill created and status sets to PENDING in maintain bill page.", "");
				 }
				 
				 getUiElement_xpath("bl_charge").click();
				 Thread.sleep(2000);
				 selectFromDropDown(getUiElement_xpath("BL_shipmentChargeId"),gates_data.readdata("27_SPX", "CHARGE_CODE", Integer.valueOf(args.toString())));	
					setvalue("BL_rate",gates_data.readdata("27_SPX", "RATE_AMOUNT", Integer.valueOf(args.toString())));	
					selectFromDropDown(getUiElement_xpath("BL_RateBasis"),gates_data.readdata("27_SPX", "RATE_BASIS", Integer.valueOf(args.toString())));
					getUiElement_xpath("BL_addbtn").click();
					
					if(verifyAlertMessage("Manual Charge Addition not allowed for bill status PEND").equals("true")){
						resultStatus("SPX_BK01.1_TS.01_TC.11", "Pass", "", "User should verify the error message “Manual Charge Addition not allowed for bill status PEND”.", "");
					} else {
						resultStatus("SPX_BK01.1_TS.01_TC.11", "Fail", "", "User should verify the error message “Manual Charge Addition not allowed for bill status PEND”.", "");
					}	
					getUiElement_xpath("BL_mb").click();
		}
				 
			 		
		catch (Exception e) {
			System.out.println(e);
		}
		resultStatus("SPX Booking", "******", " ", "Ends", "");
		new SPXBilling().billing(args);
		
			 }
			 

		}