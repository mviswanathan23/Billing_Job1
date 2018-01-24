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
public class gatesBillingTest extends Login {	
	String IssueQuote;
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "//atu.properties");
	}

	@Test(description = "Gates Billing", dataProvider = "iteration")
	public void billing(Object args) throws Exception {	
		System.out.println("#### Gates - Billing Test Starts ####");
		resultStatus("Gates Billing", "******", " ", "Start", "");
		
	
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
				resultStatus("SMT_BLTS.01_TC.01", "pass", "", "User should able to  verify the data are cleared and the message been displayed “Please add record.”", "");
			}
			else{
				resultStatus("SMT_BLTS.01_TC.01", "fail", "", "User should able to  verify the data are cleared and the message been displayed “Please add record.”", "");
			}
			System.out.println("#### Gates - Billing Test Ends ####");
		}
				 
			 		
		catch (Exception e) {
			System.out.println(e);
		}
		resultStatus("Gates Billing", "******", " ", "Ends", "");		
		
			 }
			 

		}