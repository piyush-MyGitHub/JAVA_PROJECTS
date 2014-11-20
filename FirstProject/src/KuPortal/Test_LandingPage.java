package KuPortal;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.annotations.*;
import org.testng.Assert;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;


import UIMapKuPortal.LandingPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import commonfunctions.*;


public class Test_LandingPage {

	public WebDriver driver = new FirefoxDriver();
	public String sLandingPageHandleName = "Kaplan University | KU Campus - Login";
	public String Path_HREFFile = "C:\\Java Projects\\JAVA_PROJECTS\\FirstProject\\Resources\\UtilityFiles\\KuPortal_HREF_Data.properties";	
	
	Properties propKuPortalHREF = new Properties();
	
	@Parameters({ "Environment" })
	@BeforeClass
	public void BeforeClass(String Environment)
	{
		
		driver.get(EnvironmentVariables.sUrl_StudentPortal);
		
	}
	@AfterClass
	public void AfterClass()
	{
		if(driver != null)
		{
			driver.quit();
		}
	}
	
	@BeforeMethod
	public void BeforeMethod(Method method)
	{
		ReportExtn.MethodReportingLayout(method.getName());
	}
		
	 @Test(groups = {"Grp_Navigation"})
 	  public void Navigation_LandingPage() {
						
		int iFailCount = 0, iRow = 0, iCol = 0;
		String sCellValue = "";
		String sWorkSheetTitle = "HREF_Details";
		
		//driver.get(propEnvironment.getProperty("EnvironmentURL"));
		FileInputStream fileInputStream = null;
		HSSFWorkbook workbook = null;
		
		try {
			fileInputStream = new FileInputStream("C:\\Java Projects\\JAVA_PROJECTS\\FirstProject\\Resources\\UtilityFiles\\HREF_Details.xls");
		} catch (FileNotFoundException e1) {		
			e1.printStackTrace();
		}		
		try {
			workbook = new HSSFWorkbook(fileInputStream);
		} catch (IOException e) {

			e.printStackTrace();
		}
		HSSFSheet worksheet = workbook.getSheet(sWorkSheetTitle);
		List<WebElement> linksLandingPage = UserExtension.FindAllLinks(driver);
		String sExpectedHRefValue=null;
		boolean bLinkFound =  false;
		int iRowCount =  ExcelReader.GetRowCount(worksheet);
		
		for(int k=0;k<iRowCount;k++)
		{
			
			sCellValue =  ExcelReader.ReadCellValue(worksheet, iRow, iCol);
			bLinkFound = false;
			for(int i=0;i<linksLandingPage.size();i++)
			{				
				if(sCellValue.equalsIgnoreCase(linksLandingPage.get(i).getText()))
				{
					bLinkFound = true;
					sExpectedHRefValue = ExcelReader.ReadCellValue(worksheet, iRow, 1);
					if(sExpectedHRefValue.equalsIgnoreCase(linksLandingPage.get(i).getAttribute("href")))
					{
						ReportExtn.Pass(linksLandingPage.get(i).getText() + " link reference is correct");
					}
					else
					{
						ReportExtn.Fail(sExpectedHRefValue, linksLandingPage.get(i).getAttribute("href"), " link reference is INCORRECT");
						iFailCount++;
					}
					if(bLinkFound)
					{
						break;
					}
					
				}
			}
			if(!bLinkFound)
			{
				ReportExtn.Fail(sCellValue, null, " link not found on Landing Page.");
				iFailCount++;
			}
			iRow++;
		}		

		if(iFailCount>0)
		{
			Assert.assertEquals(iFailCount,0,"Failure Count is not 0. Please check the Reporter log for failed items");
		}
					 
	 }	  
		   	  
}


