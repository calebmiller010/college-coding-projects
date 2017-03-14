	//package com.example.tests;

	import java.util.regex.Pattern;
	import java.util.concurrent.TimeUnit;
	import org.junit.*;
	import static org.junit.Assert.*;
	import static org.hamcrest.CoreMatchers.*;
	import org.openqa.selenium.*;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.openqa.selenium.firefox.FirefoxDriver;
	import org.openqa.selenium.support.ui.Select;
	import org.junit.AfterClass;
	import org.junit.BeforeClass;
	import org.junit.Test;
	import org.junit.runners.Parameterized.Parameters;
	public class AddNewExpenseNew {
	  private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @Before
	  public void setUp() throws Exception {
	  //  driver = new FirefoxDriver();
		  System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
	    driver = new ChromeDriver();
	    baseUrl = "http://ec2budgetmanager.ddns.net:8080/BudgetManagement_V2/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }
	  
	  @Test
	  public void testAddNewExpenseNew() throws Exception {
	    driver.get(baseUrl + "budget");
	    driver.findElement(By.id("username")).clear();
	    driver.findElement(By.id("username")).sendKeys("user1");
	    driver.findElement(By.id("password")).clear();
	    driver.findElement(By.id("password")).sendKeys("123");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.id("Transactiontab")).click();
	    Thread.sleep(5000);
	    driver.findElement(By.xpath("(//button[@type='button'])[6]")).click();
	    Thread.sleep(5000);
	    driver.findElement(By.cssSelector("#ModalExpense > div.modal-dialog > div.modal-content > form > div.modal-body > p > #description")).clear();
	    driver.findElement(By.cssSelector("#ModalExpense > div.modal-dialog > div.modal-content > form > div.modal-body > p > #description")).sendKeys("New Shoes");
	    driver.findElement(By.name("expense")).clear();
	    driver.findElement(By.name("expense")).sendKeys("80");
	    new Select(driver.findElement(By.id("categoryexpense"))).selectByVisibleText("Clothing");
	    driver.findElement(By.xpath("(//input[@id='comment'])[2]")).clear();
	    driver.findElement(By.xpath("(//input[@id='comment'])[2]")).sendKeys("Great running shoes!");
	    Thread.sleep(5000);
	    driver.findElement(By.cssSelector("#ModalExpense > div.modal-dialog > div.modal-content > form > div.modal-footer > input.btn.btn-default")).click();
	    Thread.sleep(7000);
	  }
	  
	  @After
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
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
	}


