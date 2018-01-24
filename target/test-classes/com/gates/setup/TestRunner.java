package com.gates.setup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;





import org.apache.commons.io.IOUtils;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class TestRunner {

		
public static void main(String args[]) throws BiffException, IOException{
	 File f = new File(System.getProperty("user.dir")+"\\InputFiles\\LoginDetails.xls");
	 Sheet inp = Workbook.getWorkbook(f).getSheet("Run");
	 
	 for(int row=1;row<inp.getRows();row++){
		 
	  String script = inp.getCell(inp.findCell("Script").getColumn(), row).getContents()+".xml";
	  String exe = inp.getCell(inp.findCell("Execution").getColumn(), row).getContents();
	 
	  if(exe.equalsIgnoreCase("y") && !script.isEmpty()){
		  FileWriter fw = new FileWriter("env.properties");
		  fw.write("row="+row);
		  fw.close();
		  IOUtils.copy(Runtime.getRuntime().exec("cmd /c java -cp \"bin;..\\Lib\\*\" org.testng.TestNG TestNG-Suite-Files\\"+script).getInputStream(),System.out);
		  new File("env.properties").delete();
	  }
	  
	 }
}
}