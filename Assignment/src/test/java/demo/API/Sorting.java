package demo.API;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class Sorting {

	public void waitForVisibility(WebDriver driver, By by, int time) {
		WebDriverWait wait = new WebDriverWait(driver, (time));
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public void impWait(WebDriver driver, int TimeOut) {
		driver.manage().timeouts().implicitlyWait(TimeOut, TimeUnit.SECONDS);
	}

	WebDriver driver = new ChromeDriver();

	@Test(priority = 0)
	public void OpenMakeMyTripURL() throws InterruptedException {
		try {
			String downloadPath = System.getProperty("user.dir");
			System.setProperty("webdriver.chrome.driver",
					downloadPath + "\\src\\main\\java\\resources\\chromedriver.exe");
			driver.get("https://www.makemytrip.com/");
			driver.manage().window().maximize();
			// driver.findElement(By.xpath("//*[@id=\"SW\"]/div[1]/div[2]/div[2]/div/section/div[4]/p/a[1]")).click();
			driver.navigate().refresh();
			impWait(driver, 50);
			driver.switchTo().frame(driver
					.findElement(By.xpath("//iframe[@id='webklipper-publisher-widget-container-notification-frame']")));
			// waitForVisibility(driver, By.xpath("//i[@class='wewidgeticon we_close']"),
			// 50);
			driver.findElement(By.xpath("//i[@class='wewidgeticon we_close']")).click();
			driver.switchTo().defaultContent();

		} catch (Exception e) {
			System.out.println("Not able to open URL " + e);
		}
		Reporter.log("We opened make my trip URL and maxixmize the window");
	}

	@Test(priority = 1)
	public void SearchCity() {

		try {
			waitForVisibility(driver, By.xpath("//input[@id='toCity']"), 30);
			driver.findElement(By.xpath("//input[@id='toCity']")).click();

			waitForVisibility(driver,
					By.xpath("//input[contains(@class,'react-autosuggest__input react-autosuggest__input--open')]"),
					30);
			driver.findElement(
					By.xpath("//input[contains(@class,'react-autosuggest__input react-autosuggest__input--open')]"))
					.sendKeys("Mumbai");
			waitForVisibility(driver, By.xpath("//p[@class='font14 appendBottom5 blackText']"), 30);

			driver.findElement(
					By.xpath("//input[contains(@class,'react-autosuggest__input react-autosuggest__input--open')]"))
					.sendKeys(Keys.DOWN);
			driver.findElement(
					By.xpath("//input[contains(@class,'react-autosuggest__input react-autosuggest__input--open')]"))
					.sendKeys(Keys.ENTER);
			waitForVisibility(driver, By.xpath("//span[text()='Book round trip for great savings']"), 30);
			driver.findElement(By.xpath("//div[@class='DayPicker-Day DayPicker-Day--selected']")).click();
			driver.findElement(By.xpath("//a[@class='primaryBtn font24 latoBold widgetSearchBtn ']")).click();
			waitForVisibility(driver,
					By.xpath("//button[@class='button buttonSecondry buttonBig fontSize12 relative']"), 40);
			driver.findElement(By.xpath("//button[@class='button buttonSecondry buttonBig fontSize12 relative']"))
					.click();

		} catch (Exception e) {
			System.out.println("Issue while searching flight " + e);

		}
		Reporter.log("We searched the flight from Delhi to Mumbai and got the search results.");
	}

	@Test(priority = 2)
	public void SortingDeparture() {
		waitForVisibility(driver, By.xpath("//span[text()='Departure']"), 30);
		driver.findElement(By.xpath("//span[text()='Departure']")).click();
		System.out.println("We have sorted the search results");
	}

	@Test(priority = 3)
	public void PrintSecondLowestPrice() throws InterruptedException {

		for (int p = 0; p <= 20; p++) {
			impWait(driver, 50);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,1000)", "");
		}

		String list = "";

		impWait(driver, 50);
		List<WebElement> lst = driver
				.findElements(By.xpath("//div[@class='blackText fontSize18 blackFont white-space-no-wrap']"));

		System.out.println("Amount Size  : " + lst.size());

		for (int d = 0; d <= lst.size() - 1; d++)

		{

			String Data = lst.get(d).getText();
			String DA = Data.replaceAll("\\n", " - ");
			String DA1[] = DA.split("-");
			String Data2 = DA1[0].substring(1);

			String Data3 = Data2.replaceAll(",", "").trim();

			list = Data3 + "," + list;

		}

		String[] string = list.replaceAll("\\[", "")

				.replaceAll("]", "")

				.split(",");

		int[] arr = new int[string.length];

		for (int i = 0; i < string.length; i++) {

			arr[i] = Integer.valueOf(string[i]);

		}

		System.out.print("String : " + list);

		System.out.print("\nInteger array : "

				+ Arrays.toString(arr));

		int n = arr.length;

		Arrays.sort(arr);

		System.out.println("smallest element is " + arr[0]);

		System.out.println("second smallest element is "

				+ arr[1]);

		int aa = arr[1];

		String AMM = String.valueOf(aa);

		int S = 0;

		for (int m = 0; m <= string.length - 1; m++) {
			String Am = string[m];
			if (AMM.equals(Am)) {

				S = m;
				System.out.println("Place of Second lowest price in Amount List:" + m);
			}

		}
		List<WebElement> Namelst = driver.findElements(By.xpath("//p[@class='boldFont blackText airlineName']"));
		String Name = Namelst.get(S).getText();
		System.out.println("Name Of Flight :" + Name);
		driver.quit();
		System.out.println(
				"We have printed second lowest price and name of that flight and also prices of all the flight");
	}

}