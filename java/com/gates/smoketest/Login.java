package com.gates.smoketest;

import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.BeforeMethod;
import org.testng.internal.PropertiesFile;

import atu.testng.reports.ATUReports;
import atu.testng.reports.utils.Utils;

import com.gates.setup.utility;

public class Login extends utility{
	
	public static String applink;
	String username;
	String password;
		
	@BeforeMethod(groups ={"login"})
	public void testLogin(ITestContext context) throws Exception {
		
		ATUReports.currentRunDescription = this.getClass().getSimpleName();
		ATUReports.setWebDriver(driver);
		ATUReports.indexPageDescription="<h1><font color=\"blue\">Gates Automation</font></h1>";
		ATUReports.setAuthorInfoAtClassLevel("Encore Team", Utils.getCurrentTime() ,"1.0");
		utility gates_data = new utility();
		String Inputfile=System.getProperty("user.dir")+"\\InputFiles\\LoginDetails.xls";
		gates_data.setInputFile(Inputfile);
		File f = new File(System.getProperty("user.dir")+"\\InputFiles\\LoginDetails.xls");
		Sheet inp2 = Workbook.getWorkbook(f).getSheet("Login_Data");
		
		for(int row=1;row<inp2.getRows();row++){
			String environment = inp2.getCell(inp2.findCell("Environment").getColumn(), row).getContents();
		if (applink.equalsIgnoreCase(environment)) {
			username = inp2.getCell(inp2.findCell("Username").getColumn(), row).getContents();
			password = inp2.getCell(inp2.findCell("Password").getColumn(), row).getContents();
			applink = inp2.getCell(inp2.findCell("AppLink").getColumn(), row).getContents();
			}
		}
		driver.get(applink);
		Thread.sleep(2000);		
		getUiElement_xpath("login_username").clear();
		getUiElement_xpath("login_username").sendKeys(username);
		getUiElement_xpath("login_password").clear();
		getUiElement_xpath("login_password").sendKeys(password);
		getUiElement_xpath("login_submit").click();
		Thread.sleep(20000);
		Assert.assertEquals("GATES: Customer Profile", driver.getTitle(), "Gates Application not loaded properly");		
	}
//	@BeforeMethod(description="Getting Environment Details")
	 public static void run() throws BiffException, IOException{
	  File f = new File(System.getProperty("user.dir")+"\\InputFiles\\LoginDetails.xls");
	  Sheet inp = Workbook.getWorkbook(f).getSheet("Run");
	  PropertiesFile pf = new PropertiesFile("env.properties");
	  int row = Integer.valueOf(pf.getProperties().getProperty("row"));
	   String script = inp.getCell(inp.findCell("Script").getColumn(), row).getContents();
	   String exe = inp.getCell(inp.findCell("Execution").getColumn(), row).getContents();
	   String env = inp.getCell(inp.findCell("Environment").getColumn(), row).getContents();
	   System.out.println("Test Script to be Executed: " +script);
	   System.out.println("Test Script Execution Status: " +exe);	   
	   if(exe.equalsIgnoreCase("y") && !script.isEmpty()){
		   System.out.println("Test Environment: " +env);
		   applink = env;
		  
	    if(iteration==null){
	     iteration = inp.getCell(inp.findCell("Iteration").getColumn(), row).getContents();
	    }
	
	  }
	 }

}