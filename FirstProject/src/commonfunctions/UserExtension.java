package commonfunctions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.WebElement;

import java.util.*;



public class UserExtension {
	
	public static List<WebElement> FindAllLinks(WebDriver driver){
		
		List<WebElement> links = driver.findElements(By.tagName("a"));
		return links;
		
		
		}
	public static boolean IsElementPresent(WebDriver driver, By by)
	{
		
		boolean present = driver.findElements(by).size() != 0;
		return present;
		
	}
	public static boolean IsElementPresent(WebDriver driver, By by,   int seconds)
	{
		boolean present = driver.findElements(by).size() != 0;
		int i = 0;
		do
		{
			present = driver.findElements(by).size() != 0;
			i++;
		}while(present == false || i <= seconds);
				
		return present;
		
	} 
	
	public static void ClickSafe(WebDriver driver, By by)
	{
		try
		{
			driver.findElement(by).click();
		}
		catch(Exception e)
		{
			ReportExtn.Fail("Unable to Click. " +  e.getMessage());
		}
		
	}
	
	public static String GetAttributeSafe(WebDriver driver, By by, String attribute)
	{
		String sStr="null";
		try
		{
			sStr = driver.findElement(by).getAttribute(attribute);
		}
		catch(Exception e)
		{
			ReportExtn.Fail("Unable to get the Attribute. " +  e.getMessage());
		}
		return sStr;
	}
	
}

