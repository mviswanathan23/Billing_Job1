package com.gates.setup;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;







//import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.gates.spx.Login;

import atu.testng.reports.ATUReports;
import atu.testng.reports.exceptions.ATUReporterStepFailedException;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;


public class utility {


	public static WebDriver driver;
	private boolean acceptNextAlert = true;
	private String inputFile;
	protected static Properties ui;
	protected StringBuffer verificationErrors = new StringBuffer();
	public static String iteration;
	public static String excelfile;
	
	@BeforeTest
	public static void setUp(ITestContext context) throws Exception {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//chromedriver.exe");		
		System.setProperty("atu.reporter.config", System.getProperty("user.dir")+"//atu.properties");
		ATUReports.setWebDriver(driver);
		ATUReports.indexPageDescription = "GATES";
		ATUReports.setAuthorInfo("Encore Team", Utils.getCurrentTime(), "1.0");
		ChromeOptions pref = new ChromeOptions();
		pref.addArguments("test-type");
		/*pref.addArguments("-incognito");
		pref.addArguments("--disable-popup-blocking");*/
		driver = new ChromeDriver(pref);			
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		Login.run();		
	}
	@DataProvider(name="iteration")
	   public  Object[][] input_iterate() {
	    String name = iteration;
	    if(name.contains("-")){
	    Object dataobj [][] = new Object[(Integer.valueOf(name.split("-")[1])+1)-Integer.valueOf(name.split("-")[0])][1];
	    for(int a=Integer.valueOf(name.split("-")[0]);a<=(Integer.valueOf(name.split("-")[1]));a++){
	     dataobj[a-(Integer.valueOf(name.split("-")[0]))][0] = a; 
	     
	    }
	    return dataobj;
	    }else if(name.contains(",")){
	     return new Object[][]{{name.split(",")[0]},{name.split(",")[1]}};
	    }else{
	     return new Object[][]{{name}};
	    }
	   }
	public int getRowNumber(String sheetName, String content) throws BiffException, IOException{
		File inputWorkbook = new File(excelfile);
		Workbook  workbook = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = workbook.getSheet(sheetName);
			int rowno =0;
			int a = 1;
			while(a<sheet.getRows()){
			Cell envorn = sheet.getCell(sheet.findCell("Environment").getColumn(), a);
			if(envorn.getContents().equalsIgnoreCase(content)){
			rowno = envorn.getRow();
			break;
			}
			}
			return rowno;
			
	}

	public boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public boolean isAlertPresent() {
		try {
			new WebDriverWait(driver, 7).until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException e) {
			return false;
		}
	}

	public String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}
    public void dismissAlert(){
    	Alert alert=driver.switchTo().alert();
		System.out.println(alert.getText());
		alert.dismiss();
    }
    public void acceptAlert(){
    	Alert alert=driver.switchTo().alert();
		System.out.println(alert.getText());
		alert.accept();
    }
    
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
		excelfile=inputFile;
	}

	public String readdata(String sheetName, String columnName, int rowNumber) throws IOException {

		String data = null;
		File inputWorkbook = new File(inputFile);
		Workbook workbook;
		try {

			workbook = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = workbook.getSheet(sheetName);

			int row = sheet.getRows();
			if (rowNumber <= row) {
				for (int col = 0; col < sheet.getColumns(); col++) {

					if (sheet.getCell(col, 0).getContents()
							.equalsIgnoreCase(columnName)) {

						data = sheet.getCell(col, rowNumber).getContents();
					}
				}
			}
		} catch (BiffException e) {
			e.printStackTrace();
		}

		return data;
	}

	public WebElement getUiElement_xpath(String key) throws InterruptedException
	{
		loadUi();
		scrolltoElement(key);
		return new WebDriverWait(driver, 90).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ui.getProperty(key))));
	}
	public WebElement getUiElement_xpath_byname(String key) throws InterruptedException
	{
		loadUi();
		scrolltoElement(key);
		return new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.name(ui.getProperty(key))));
	}
	public WebElement getUiElement_id(String key) throws InterruptedException
	{
		loadUi();
		scrolltoElement2(key);
		return new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.id(ui.getProperty(key))));
	}
	public WebElement getUiElement_xpath(String key,boolean args) throws InterruptedException
	{
		loadUi();
		scrolltoElement(key);
		if(args){
		return new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath(ui.getProperty(key))));}
	else{
		return  new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ui.getProperty(key))));}
	}

	public WebElement getUiElement_css(String key) throws InterruptedException
	{
		/*loadUi();
		scrolltoElement(key);*/
		return new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ui.getProperty(key))));

	}

	public static void loadUi() {
		ui = new Properties();
		String fileName = "";
		fileName = "ObjectRepository//Ui_Object_Repository.properties";
		InputStream is;
		try {
			is = new FileInputStream(fileName);
			ui.load(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail("File not found: " + fileName);
		}
	}

	public void switchToWindow(String windowTitle) throws InterruptedException {

		Thread.sleep(3000);

		for (String winHandle : driver.getWindowHandles()) {


			driver.switchTo().window(winHandle); // switch focus of WebDriver to
			// the next found window
			// handle (that's your newly
			// opened windew+ow)
			// System.out.println(driver.getTitle());

			if (driver.getTitle().equalsIgnoreCase(windowTitle)) {
				driver.switchTo().window(winHandle);
				System.out.println("Window-Name: "+ driver.getTitle());
				driver.manage().window().maximize();
				break;
			}
		}

	}
	public void download(String input)
	  {
		  
			String exePath = "D:\\chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", exePath);
								
		  HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			chromePrefs.put("download.default_directory", input);
			chromePrefs.put("download.extensions_to_open", "xls");
			//Save Chrome Opions
			ChromeOptions options = new ChromeOptions();
			HashMap<String, Object> chromeOptionsMap = new HashMap<String, Object>();
			options.setExperimentalOption("prefs", chromePrefs);
			options.addArguments("--test-type");
			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability(ChromeOptions.CAPABILITY, chromeOptionsMap);
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);
	  }

	public void hoverAndClick(WebElement[] hoverer, WebElement element) {
		// declare new Action
		Actions actions = new Actions(driver);
		// Iterate through the WebElements from the Array
		for (WebElement we : hoverer) {
			// hover each of them
			Action hovering = actions.moveToElement(we).build();
			hovering.perform();
		}
		// finally click the WebElement to click at
		element.click();

	}


	public WebElement getelement(String key)
	{
		return driver.findElement(By.xpath(ui.getProperty(key)));		
	}

	public WebElement getelement_cssPath(String key)
	{
		return driver.findElement(By.cssSelector(key));		
	}

	// to Verify Content-Title

	public String verifycontenttitle(String alertmsg) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement elementContent = null;

		try {

			elementContent = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("h3.content-title"))));

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Assert.assertEquals(alertmsg, elementContent.getText());
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}


	public String verifycontenttitle_xpath(String alertmsg) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement elementContentXpath = null;

		try {

			elementContentXpath = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//h3[contains(text(),'" + alertmsg + "')]")));




		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Assert.assertEquals(alertmsg, elementContentXpath.getText());
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}



	public String verifyErrorAlertPresent(String alertmsg) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement alertElement = null;
		try {


			alertElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div.formErrorContent")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Assert.assertEquals(alertmsg, alertElement.getText());
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}

	public String verifyMessagePresent(String message) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement messageElement = null;
		try {
			messageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='msgDiv']/div[contains(text(),'"+message+"')]")));

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Assert.assertEquals(message, messageElement.getText());
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}

	public String verifyMessagePresent(String idval,String message) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement messageElement = null;
		try {
			messageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='"+idval+"']/div[contains(text(),'"+message+"')]")));


			} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Assert.assertEquals(message, messageElement.getText());
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}
	
	public String verifyMsgDialogPresent(String message) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement messageElement = null;
		try {
			messageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@id='msgDivDialog']/div[contains(text(),'" + message
					+ "')]")));

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Assert.assertEquals(message, messageElement.getText());
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}

	public String verifyAlertMessage(String message) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement alertMessage = null;
		String val=null;
		/*try {
			
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}*/
		try {
			Thread.sleep(10000);
			alertMessage =driver.findElement(By.xpath(".//*[contains(text(),'" + message + "')]"));
			String msg=alertMessage.getText();
			if (msg.contains(message))
			
				//Assert.assertEquals(alertMessage.getText(), message);
			val= "true";
		} catch (Exception e) {
			val= "false";
		}
		return val;
	}
	 
	public String verifyElementValue(String key, String expectedvalue) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement verifyelementVal = null;
		try {
			verifyelementVal = wait.until(ExpectedConditions.visibilityOf(getUiElement_xpath(key)));

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Assert.assertEquals(expectedvalue, verifyelementVal.getText());
			return "true";
		} catch (Exception e) {
			return "false";
		}
	}

	public boolean verifyTextPresent(String key) throws InterruptedException {
		
		return !getUiElement_xpath(key).getAttribute("value").isEmpty() ? true : false;
	}
	
public boolean verifyTextPresent2(String key) throws InterruptedException {
		
		return getUiElement_xpath(key).getAttribute("value").isEmpty() ? true : false;
	}

	public boolean isReadyOnly(String key) throws InterruptedException {
		return getUiElement_xpath(key).getAttribute("readonly").equals("readonly") ? true : false;
	}

	public String getValue(String key, String attributeName) throws InterruptedException {

		return getUiElement_xpath(key).getAttribute(attributeName);
	}


	public String gettextval(String xpath) throws InterruptedException {
		scrolltoElement_xpath(xpath);
		WebElement elementValue = new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(xpath))));
		return elementValue.getText();
	}
public void selectmultiblecheckbox(String tableid,int dividval,int rowcount) throws InterruptedException {
		
		scrolltoElement_xpath("//*[@id='"+tableid+"']");
		for (int i=1;i<rowcount;i++)
		{
			if (!driver.findElement(By.xpath("//*[@id='"+tableid+"']//div["+dividval+"]//tr[@id='"+i+"']//input[@type='checkbox']")).isSelected())
			{
				driver.findElement(By.xpath("//*[@id='"+tableid+"']//div["+dividval+"]//tr[@id='"+i+"']//input[@type='checkbox']")).click();
			}
		}		
	}


	public void selectcheckbox(String xpath) throws InterruptedException {
		scrolltoElement_xpath(xpath);
		Thread.sleep(1500);
		WebElement elementValue = new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath(xpath))));

		elementValue.click();
	}

	public void scrolltoElement(String key) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(ui.getProperty(key))));
		highlightElement(driver.findElement(By.xpath(ui.getProperty(key))));
		Thread.sleep(5000);
	}
	public void scrolltoElement2(String key) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.id(ui.getProperty(key))));
		highlightElement(driver.findElement(By.id(ui.getProperty(key))));
		Thread.sleep(2000);
	}

	public void highlightElement(WebElement element) {

		for (int i = 0; i < 2; i++) {

			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("arguments[0].setAttribute('style', arguments[1]);",

					element, "color: yellow; border: 2px solid yellow;");

			js.executeScript("arguments[0].setAttribute('style', arguments[1]);",

					element, "");

		}

	}

	public void scrolltoElement_xpath(String xpath) throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath(xpath)));
		highlightElement(driver.findElement(By.xpath(xpath)));
		Thread.sleep(1500);

	}

	public void scrolltoElement_linkText(String linkText) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.linkText(linkText)));
		highlightElement(driver.findElement(By.linkText(linkText)));
		Thread.sleep(2000);
	}


	public void selectvalue(String objectname, String objectvalue) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 100);
		Object Oobject = new Object();
		Oobject = getUiElement_xpath(objectname);
		wait.until(ExpectedConditions.visibilityOf(((WebElement) Oobject)));
		highlightElement((WebElement) Oobject);
		((WebElement) Oobject).sendKeys(objectvalue);
		Thread.sleep(5000);
		((WebElement) Oobject).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(5000);
		((WebElement) Oobject).sendKeys(Keys.ENTER);

		Thread.sleep(5000);
	}

	public void selectvalue2(String objectname, String objectvalue) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		Object Oobject = new Object();
		Oobject = getUiElement_xpath(objectname);
		wait.until(ExpectedConditions.visibilityOf(((WebElement) Oobject)));
		highlightElement((WebElement) Oobject);
		((WebElement) Oobject).sendKeys(objectvalue);
		Thread.sleep(5000);
		((WebElement) Oobject).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(5000);
		((WebElement) Oobject).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(5000);
		((WebElement) Oobject).sendKeys(Keys.ENTER);
		Thread.sleep(5000);
	}

	public String verrifygridtitle(String gridTitle, String alertmsg) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		WebElement elementGrid = null;
		try {

			scrolltoElement_xpath("//span[starts-with(text(),'"+gridTitle+"')]"); 
			elementGrid = wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//span[starts-with(text(),'" + gridTitle + "')]"))));
			highlightElement(driver.findElement(By.xpath("//span[starts-with(text(),'" + gridTitle + "')]")));

		} catch (Error e) {
			e.printStackTrace();
		}
		try {


			Assert.assertEquals(alertmsg, elementGrid.getText());
			return "true";
		} catch (Error e) {
			return "false";
		}
	}

	public String verifyGridAlertMsg(String alertmsg) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		try {
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".ui-state-error")));
			highlightElement(element);
			if (element.getText().equals(alertmsg)) {
				return "true";

			} else {
				return "false";
			}
		} catch (NoSuchElementException e) {
			Reporter.log("Element not found");
		}
		return null;
	}
	

	public String verifyGridAlertMsg_xpath(String alertmsg) {
		WebDriverWait wait = new WebDriverWait(driver, 60);
		try {
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//*[@class='ui-state-error' and contains(text(),'"+alertmsg+"')]")));

			highlightElement(element);
			if (element.getText().equals(alertmsg)) {
				return "true";

			} else {
				return "false";
			}
		} catch (NoSuchElementException e) {
			Reporter.log("Element not found");
		}
		return null;
	}

	public boolean isElementEnabled(WebElement args) {
		highlightElement(args);

		return args.isEnabled() ? true : false;
	}
	public boolean isElementDisabled(WebElement args) {
		highlightElement(args);

		return args.isEnabled() ? true : false;
	}

	public boolean isElementDisplayed(String key) {


		return driver.findElements(By.xpath(ui.getProperty(key))).size() > 0 ? true : false;
	}	
	public boolean isElementDisplayed(By args) {

		return new WebDriverWait(driver, 30).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(args)).size()>0?true:false;
	}
	
	public void selectFromDropDown(WebElement args, String value) {


		new Select(args).selectByVisibleText(value);
	}	


	public void selectFromDropDown(WebElement args, int index) {

		new Select(args).selectByIndex(index);
	}

	public String getDropDownval(WebElement args) {

		return new Select(args).getFirstSelectedOption().getText();
	}
	
	/*public String getDropDownval2(String xpath) {
		WebElement selectElement = getUiElement_xpath(xpath);
		Select select = new Select(selectElement);
		//select.getOptions();
		List<WebElement> allOptions = select.getOptions();
		for (WebElement party : allOptions)
		{
		System.out.println(party.getText());
		}	
		return allOptions();
	}*/


	public WebElement clickLink(String linkText) throws InterruptedException {
		scrolltoElement_linkText(linkText);
		return new WebDriverWait(driver, 60).until(ExpectedConditions.visibilityOf(driver.findElement(By.linkText(linkText))));

	}

	public WebElement clickLinkByText(String text) {


		return new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(text(),'" + text + "')]"))));
	}
	public WebElement clickLinkByN(int text) {


		return new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[contains(text(),'" + text + "')]"))));
	}



	public WebElement clickFirstLink(String table_ID, String row, String col) throws InterruptedException {
		/*scrolltoElement_xpath("//*[@id='" + table_ID + "']//tr[" + row + "]/td[" + col + "]/a");
		return new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='" + table_ID + "']//tr[" + row
				+ "]/td[" + col + "]/a"))));*/
		if(row!=""){
			  scrolltoElement_xpath("//*[@id='" + table_ID + "']//tr[" + row + "]/td[" + col + "]/a");
			  return new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='" + table_ID + "']//tr[" + row
			    + "]/td[" + col + "]/a"))));
			}
			else{

			scrolltoElement_xpath("//*[@id='" + table_ID + "']//tr/td[" + col + "]/a");
			  return new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='" + table_ID + "']//tr/td[" + col + "]/a"))));

			}
	}
	
	public WebElement clickFirstLink2(String table_ID, String col) throws InterruptedException {
		/*scrolltoElement_xpath("//*[@id='" + table_ID + "']//tr[" + row + "]/td[" + col + "]/a");
		return new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='" + table_ID + "']//tr[" + row
				+ "]/td[" + col + "]/a"))));*/
		if(col!=""){
			  scrolltoElement_xpath("//*[@id='" + table_ID + "']//td[" + col + "]/a");
			  return new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='" + table_ID + "']//td[" + col + "]/a"))));
			}
			else{

			scrolltoElement_xpath("//*[@id='" + table_ID + "']//td[" + col + "]/a");
			  return new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id='" + table_ID + "']//td[" + col + "]/a"))));

			}
	}
	public void uploadfile() {
		try{
	WebElement fileInput = driver.findElement(By.xpath("//*[@id='dropzone-form']"));
	  	fileInput.sendKeys("D:\\1.pdf");
		}catch( Exception e){
			e.printStackTrace();
		}
	  	
	}
	public int tableCount(String table_ID) throws InterruptedException {
		scrolltoElement_xpath(".//*[@id='" + table_ID + "']//tr[*]");
		return new WebDriverWait(driver, 25).until(
				ExpectedConditions.presenceOfAllElementsLocatedBy((By.xpath(".//*[@id='" + table_ID + "']//tr[*]")))).size();

	}

	public int tableCount_xpath(String xpath) throws InterruptedException {
		scrolltoElement_xpath(xpath);
		return new WebDriverWait(driver, 25).until(
				ExpectedConditions.presenceOfAllElementsLocatedBy((By.xpath(xpath+"//tr[*]")))).size();



	}





	public WebElement getradioBtn(String idval,String elementval) throws InterruptedException {
		return new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@id='"+idval+"' and @value='" + elementval + "']"))));
	}
	

	public WebElement getradioBtn_ind(String idval,String indexval) throws InterruptedException {
		return new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("(.//*[@id='"+idval+"'])["+indexval+"]"))));

	}






	public void resultStatus(String stepdesc, String status, String input, String exp, String act) {
		LogAs pass;
		if (status.equalsIgnoreCase("pass")) {
			pass = LogAs.PASSED;
			act=status.toUpperCase( );
		}
		else if (status.equalsIgnoreCase("fail")) {
			pass = LogAs.FAILED;
			act=status.toUpperCase( );
		}
		else if (status.equalsIgnoreCase("warning")) {
			pass = LogAs.WARNING;
			act=status.toUpperCase( );
		}
		else {
			pass = LogAs.INFO;
			act=status.toUpperCase( );
		}
		try {
			ATUReports.add(stepdesc, input, exp, act, pass, null);
		} catch (ATUReporterStepFailedException e) {

		}

	}

	public WebElement getTableElement(String table_ID, String attributeValue, int searchIndex, int LinkIndex) throws InterruptedException {
		for (int a = 1; a < tableCount(table_ID); a++) {
			if (driver.findElement(By.xpath(".//*[@id=" + table_ID + "']//tr[" + a + "]/td[" + searchIndex + "]")).getText().equalsIgnoreCase(attributeValue)) {
				return driver.findElement(By.xpath(".//*[@id=" + table_ID + "']//tr[" + a + "]/td[" + LinkIndex + "]/a"));
			}

		}
		return null;
	}

	/*
	 * public static void add_Date(String id,int noofDays){
	 * DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	 * Calendar cal = Calendar.getInstance();
	 * cal.setTime(new Date());
	 * cal.add(Calendar.DATE,1);
	 * System.out.println(dateFormat.format(cal.getTime()));
	 * String date =
	 * "return document.getElementById('"+id+"').setAttribute('value','"
	 * +cal.toString()+"')";
	 * JavascriptExecutor js = (JavascriptExecutor)driver;
	 * js.executeScript(date, "");
	 * 
	 * }
	 */
	public static String add_Date(int noofDays) {

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, noofDays);
		String date = dateFormat.format(cal.getTime()).toString();
		System.out.println("My date: " + date);
		return date;
	}

	public static String add_DateTime(int noofDays) {

		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, noofDays);
		String date = dateFormat.format(cal.getTime()).toString();
		System.out.println("My date: " + date);
		return date;
	}

	public void setvalue(String objectname, String objectvalue) throws InterruptedException {
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, 60);
			Object Oobject = new Object();
			Oobject = getUiElement_xpath(objectname);
			wait.until(ExpectedConditions.visibilityOf(((WebElement) Oobject)));
			highlightElement((WebElement) Oobject);
			Thread.sleep(1500);
			((WebElement) Oobject).sendKeys(objectvalue);
			Thread.sleep(1500);
			((WebElement) Oobject).sendKeys(Keys.TAB);

		} catch (InterruptedException e)
		{
			System.out.println(e.toString());
		}
	}
	public void setvalueById(String objectname, String objectvalue) throws InterruptedException {
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, 60);
			Object Oobject = new Object();
			Oobject = getUiElement_id(objectname);
			wait.until(ExpectedConditions.visibilityOf(((WebElement) Oobject)));
			highlightElement((WebElement) Oobject);
			Thread.sleep(1500);
			((WebElement) Oobject).sendKeys(objectvalue);
			Thread.sleep(1500);
			((WebElement) Oobject).sendKeys(Keys.TAB);

		} catch (InterruptedException e)
		{
			System.out.println(e.toString());
		}
	}

	public void clearvalue(String key) throws InterruptedException {
		try {
			getUiElement_xpath(key).clear();

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	public void sendtab(String objectname) throws InterruptedException
	{
		try
		{
			WebDriverWait wait = new WebDriverWait(driver, 60);
			Object Oobject = new Object();
			Oobject = getUiElement_xpath(objectname);
			wait.until(ExpectedConditions.visibilityOf(((WebElement) Oobject)));

			((WebElement) Oobject).sendKeys(Keys.TAB);
			Thread.sleep(1500);

		} catch (InterruptedException e)
		{
			System.out.println(e.toString());
		}
	}
	
	public boolean isAlertMsgPresent() 
	{ 
	    try 
	    { 
	        driver.switchTo().alert(); 
	        return true; 
	    }   // try 
	    catch (NoAlertPresentException Ex) 
	    { 
	        return false; 
	    }   // catch 
	} 

	public WebElement geticon(int idval,int elementid) throws InterruptedException

	{
	
		scrolltoElement_xpath(".//*[@id='"+idval+"']/td//div/div["+elementid+"]/span");
		new WebDriverWait(driver, 25).until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath(".//*[@id='"+idval+"']/td//div/div["+elementid+"]/span"))));
		return (WebElement) driver.findElement(By.xpath(".//*[@id='"+idval+"']/td//div/div["+elementid+"]/span"));
}	

	public WebElement geticon(int idval,int elementid,String classval) throws InterruptedException

	{
	
		scrolltoElement_xpath(".//*[@id='"+idval+"']/td//div/div["+elementid+"]/span[@class='"+classval+"']");
		new WebDriverWait(driver, 50).until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath(".//*[@id='"+idval+"']/td//div/div["+elementid+"]/span[@class='"+classval+"']"))));
		return (WebElement) driver.findElement(By.xpath(".//*[@id='"+idval+"']/td//div/div["+elementid+"]/span[@class='"+classval+"']"));
}	
	
	public String htmltemplate()
	{

		String htmltemplate = "<html><head><body><img src='../seleniumframework.jpg'/></body></html> ";
		return htmltemplate;
	}
	 public static <T> List getDuplicate(Collection<T> list) {

	        final List duplicatedObjects = (List) new ArrayList<T>();
	        Set<T> set = new HashSet<T>() {
	        @Override
	        public boolean add(T e) {
	            if (contains(e)) {
	                ((Set<String>) duplicatedObjects).add((String) e);
	            }
	            return super.add(e);
	        }
	        };
	       for (T t : list) {
	            set.add(t);
	        }
	        return duplicatedObjects;
	    }
	  
	 public void clickLinkByHref(String href, int position) {
		    List<WebElement> anchors = driver.findElements(By.tagName("a"));
		    Iterator<WebElement> i = anchors.iterator();
		 
		    int j = 0;
		    while(i.hasNext()) {
		        WebElement anchor = i.next();
		 
		        if(anchor.getAttribute("href").contains(href)) {
		            j++;
		        }
		 
		        if(anchor.getAttribute("href").contains(href)
		                && j == position) {
		            anchor.click();
		            break;
		        }
		    }
		}
	
	@AfterTest
	public void tearDown() throws Exception {
	//	driver.quit();
		
	}
}


