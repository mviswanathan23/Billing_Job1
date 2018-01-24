package com.gates.smoketest;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestContext;
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
public class gatesTest extends Login {
	String BookingN;
	
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "//atu.properties");
	}

	@Test(description = "Gates Booking", dataProvider = "iteration")
	public void booking(Object args) throws Exception {	
		
		resultStatus("Gates Booking", "******", " ", "Start", "");
		
	
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
			if (verifycontenttitle("Maintain Booking").equals("true")) {
				resultStatus("BK01.4_TS.02_TC.01", "Pass", "", "To verify Maintain Booking Screen is displayed", "");
			} else {
				ATUReports.add("Fail step", LogAs.FAILED, new CaptureScreen(
		                ScreenshotOf.BROWSER_PAGE));
				resultStatus("BK01.4_TS.02_TC.01", "Fail", "", "To verify Maintain Booking Screen is displayed", "");
			}
			
			/*selectFromDropDown(getUiElement_xpath("trade_value"), gates_data.readdata("01_Shipper", "TRADE_GROUP", Integer.valueOf(args.toString())));
			selectFromDropDown(getUiElement_xpath("customer_groupId"), gates_data.readdata("01_Shipper", "CUSTOMER_GROUP", Integer.valueOf(args.toString())));
			getUiElement_xpath("expand_all").click();
			
			Thread.sleep(2000);
			selectvalue("Senddoc_organizationName",(gates_data.readdata("01_Shipper", "SHIPPER_CUSTOMER", Integer.valueOf(args.toString()))));
			Thread.sleep(3000);						
						
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
			
			selectFromDropDown(getUiElement_xpath("load_service_code"), gates_data.readdata("05_RoutingVVD", "LOAD_SERVICE", Integer.valueOf(args.toString())));
			selectFromDropDown(getUiElement_xpath("dicharge_service_code"), gates_data.readdata("05_RoutingVVD", "DISCHARGE_SERVICE", Integer.valueOf(args.toString())));
			Thread.sleep(5000);
			selectvalue("originPortCityCodeDescription", gates_data.readdata("05_RoutingVVD", "PORT_OF_LOADING_EDIT", Integer.valueOf(args.toString())));
			Thread.sleep(2000);			
			selectvalue("destinationPortCityCodeDescription", gates_data.readdata("05_RoutingVVD", "PORT_OF_DISCHARGE_EDIT1", Integer.valueOf(args.toString())));			
			getUiElement_xpath("divOfRoutingVVDlnk", true).click();		
			Thread.sleep(5000);
			getradioBtn("searchCriteria", "L").click();
			getUiElement_xpath("searchVVDButton").click();	
			Thread.sleep(3000);
			clickFirstLink("gbox_vvdResultGrid", "2", "2").click();
			
			//getUiElement_xpath("commodity_desc").sendKeys("test");			
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
			ATUReports.add("Fail step", LogAs.FAILED, new CaptureScreen(
	                ScreenshotOf.BROWSER_PAGE));
			getUiElement_xpath("commodity_desc").sendKeys("test");
			getUiElement_xpath("save").click();
			Thread.sleep(2500);		
			while (isAlertPresent())
			{
				closeAlertAndGetItsText();
			}
			String Booking_Status=getDropDownval(getUiElement_xpath("bookingStatusCode"));
			if(Booking_Status.equals("APPROVED")){
				resultStatus("BK01.4_TS.02_TC.02", "Pass", "", "User verifies booking status sets to APPROVED on successful transaction.", "");
			 BookingN = getUiElement_xpath("bookingn").getAttribute("value");
			System.out.println("Booking Created: " +BookingN);
			resultStatus("BK01.4_TS.02_TC.03", "Pass", "", "User verifies booking number is generated on successful transaction.", "");
			}
			
			*/
		}
				 
			 		
		catch (Exception e) {
			System.out.println(e);
		}
		resultStatus("Gates Booking", "******", " ", "Ends", "");		
		
			 }
			 

		}