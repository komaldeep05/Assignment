package demo.API;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class Email {

	public WebDriverWait wait;
	public String emailId="komal12345@aol.com";
	public JavascriptExecutor executor;
	
	
	@Test
	public void EmailValidation() throws InterruptedException, AWTException {
		 String downloadPath=System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", downloadPath+"\\src\\main\\java\\resources\\chromedriver.exe");
		ChromeOptions options=new ChromeOptions();
		  HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
		  chromePrefs.put("profile.default_content_settings.popups", 0);
		  chromePrefs.put("download.default_directory", downloadPath); 
		  options.setExperimentalOption("prefs", chromePrefs); 
		WebDriver driver = new ChromeDriver(options);
		driver.get("https://mail.aol.com/");
		//komal12345@a0l.com Password@12345
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//*[@class='login']")).click();
		driver.findElement(By.xpath("//*[@class='phone-no ']")).sendKeys(emailId);
		driver.findElement(By.xpath("//*[@id='login-signin']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@id='login-passwd']")).sendKeys("Password@12345");
		driver.findElement(By.xpath("//*[@id='login-signin']")).click();
		System.out.println("Login Successfully");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@href='/d/compose/']")));
		driver.findElement(By.xpath("//*[@href='/d/compose/']")).click();
		driver.findElement(By.xpath("//*[@data-test-id='compose-subject']")).sendKeys("Damco");
		driver.findElement(By.xpath("//*[@data-test-id='icon-btn-list_unordered']")).click();
		driver.findElement(By.xpath("//*[@data-test-id='icon-btn-list_unordered']//span")).click();
		driver.findElement(By.xpath("//*[@data-test-id='rte']")).sendKeys("Line one");
		driver.switchTo().activeElement().sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//*[@data-test-id='rte']")).sendKeys("Line two");
		driver.switchTo().activeElement().sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//*[@data-test-id='rte']")).sendKeys("Line three");
		
		  driver.findElement(By.xpath("//*[@title='Attach files']")).click(); 
		  String path = System.getProperty("user.dir"); 
		  Robot robot = new Robot();
		  robot.setAutoDelay(5000); StringSelection file = new
		  StringSelection(path+"\\src\\main\\java\\resources\\Screenshot.png");
		  Toolkit.getDefaultToolkit().getSystemClipboard().setContents(file, null);
		  robot.setAutoDelay(5000); robot.keyPress(KeyEvent.VK_CONTROL);
		  robot.keyPress(KeyEvent.VK_V); robot.keyRelease(KeyEvent.VK_CONTROL);
		  robot.keyRelease(KeyEvent.VK_V); robot.setAutoDelay(5000);
		  robot.keyPress(KeyEvent.VK_ENTER); robot.keyRelease(KeyEvent.VK_ENTER);
		  System.out.println("File Uploaded");
		driver.findElement(By.xpath("//*[@id='message-to-field']")).sendKeys(emailId);
		driver.findElement(By.xpath("//*[@data-test-id='compose-send-button']")).click();
		System.out.println("Email send successfully");
		try{
		} catch (Exception e) {
		if(e.toString().contains("org.openqa.selenium.UnhandledAlertException"))
		 {
		    Alert alert = driver.switchTo().alert();
		    alert.accept();
		 }
		}
		driver.findElement(By.xpath("//*[text()='Back']")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@data-test-id='folder-label']")).click();
		List<WebElement> values = driver.findElements(By.xpath("//*[@data-test-id='message-subject']"));
		for(int i=0;i<values.size();i++) {
			if(values.get(i).getText().equalsIgnoreCase("Damco")) {
				values.get(i).click();
				break;	
			}
		}
	  Assert.assertTrue(driver.getPageSource().contains("Damco"));
	  Assert.assertTrue(driver.getPageSource().contains("Line one"));
	  Assert.assertTrue(driver.getPageSource().contains("Line two"));
	  Assert.assertTrue(driver.getPageSource().contains("Line three"));
	  System.out.println("Received Email Validated");
	  driver.findElement(By.xpath("//*[@data-test-id='attachment-thumbnail']")).click();
	  driver.findElement(By.xpath("//*[@data-test-id='previewr-download']")).click();
	  System.out.println("Attachment downloaded");
	  driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	  
	  File f = new File(downloadPath +"\\Screenshot.png") ;
		  if(f.exists()) {
			long size =f.length();
			long expectedSize=4990;
			Assert.assertEquals(expectedSize, size);
			  System.out.println("File downloaded");
	  }else {
		  System.out.println("File not downloaded");
	  }
		}
		
	}