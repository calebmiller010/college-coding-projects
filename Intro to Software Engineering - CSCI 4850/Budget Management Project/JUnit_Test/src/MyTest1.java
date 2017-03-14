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
public class MyTest1 {
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
  public void testMyTest1() throws Exception {
    driver.get(baseUrl + "budget");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("user1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("123");
    Thread.sleep(3000);
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    Thread.sleep(3000);
  }
  
  @Test
  public void testIncorrectUser() throws Exception {
    driver.get(baseUrl + "budget");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("testbaduser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("12345");
    Thread.sleep(5000);
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    Thread.sleep(5000);
  }
  
  @Test
  public void testCreatenewuser() throws Exception {
    driver.get(baseUrl + "budget");
    Thread.sleep(3000);
    driver.findElement(By.xpath("//input[@value='New User']")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("testnewuser1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("1234");
    driver.findElement(By.id("confirmpassword")).clear();
    driver.findElement(By.id("confirmpassword")).sendKeys("1234");
    Thread.sleep(5000);
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    Thread.sleep(5000);
  }
  
  @Test
  public void testPasswordmismatch() throws Exception {
    driver.get(baseUrl + "budget");
    Thread.sleep(5000);
    driver.findElement(By.xpath("//input[@value='New User']")).click();
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("testbaduser");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("hello");
    driver.findElement(By.id("confirmpassword")).clear();
    driver.findElement(By.id("confirmpassword")).sendKeys("hallo");
    Thread.sleep(5000);
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    Thread.sleep(5000);
  }

  @Test
  public void testAddNewIncome() throws Exception {
    driver.get(baseUrl + "budget");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("user1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("123");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.id("Transactiontab")).click();
    Thread.sleep(5000);
    driver.findElement(By.xpath("(//button[@type='button'])[5]")).click();
    Thread.sleep(5000);
    driver.findElement(By.id("description")).clear();
    driver.findElement(By.id("description")).sendKeys("New Weekend Job");
    driver.findElement(By.id("amount")).clear();
    driver.findElement(By.id("amount")).sendKeys("60");
    driver.findElement(By.id("comment")).clear();
    driver.findElement(By.id("comment")).sendKeys("Extra money!");
    Thread.sleep(5000);
    driver.findElement(By.cssSelector("input.btn.btn-default")).click();
    Thread.sleep(5000);
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

@Test
public void testAddNewExpenseDifferentCategory() throws Exception {
  driver.get(baseUrl + "budget");
  driver.findElement(By.id("username")).clear();
  driver.findElement(By.id("username")).sendKeys("user1");
  driver.findElement(By.id("password")).clear();
  driver.findElement(By.id("password")).sendKeys("123");
  driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
  Thread.sleep(2000);
  driver.findElement(By.id("Transactiontab")).click();
  Thread.sleep(3000);
  driver.findElement(By.xpath("(//button[@type='button'])[6]")).click();
  Thread.sleep(3000);
  driver.findElement(By.cssSelector("#ModalExpense > div.modal-dialog > div.modal-content > form > div.modal-body > p > #description")).clear();
  driver.findElement(By.cssSelector("#ModalExpense > div.modal-dialog > div.modal-content > form > div.modal-body > p > #description")).sendKeys("Birthday goodies");
  driver.findElement(By.name("expense")).clear();
  driver.findElement(By.name("expense")).sendKeys("40");
  new Select(driver.findElement(By.id("categoryexpense"))).selectByVisibleText("Add Custom Category");
  driver.findElement(By.id("newcategoryexpenseinput")).clear();
  driver.findElement(By.id("newcategoryexpenseinput")).sendKeys("Party Stuff");
  driver.findElement(By.xpath("(//input[@id='comment'])[2]")).clear();
  driver.findElement(By.xpath("(//input[@id='comment'])[2]")).sendKeys("We threw a great birthday party!");
  Thread.sleep(5000);
  driver.findElement(By.cssSelector("#ModalExpense > div.modal-dialog > div.modal-content > form > div.modal-footer > input.btn.btn-default")).click();
  Thread.sleep(5000);
}

@Test
public void testAddARecurringPayment() throws Exception {
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
  driver.findElement(By.cssSelector("#ModalExpense > div.modal-dialog > div.modal-content > form > div.modal-body > p > #description")).sendKeys("Phone Bill");
  driver.findElement(By.name("expense")).clear();
  driver.findElement(By.name("expense")).sendKeys("60");
  new Select(driver.findElement(By.id("recurring2"))).selectByVisibleText("Yes");
  new Select(driver.findElement(By.id("categoryexpense"))).selectByVisibleText("Utilities");
  driver.findElement(By.xpath("(//input[@id='comment'])[2]")).clear();
  driver.findElement(By.xpath("(//input[@id='comment'])[2]")).sendKeys("Verizon");
  driver.findElement(By.id("duefield")).clear();
  driver.findElement(By.id("duefield")).sendKeys("25");
  Thread.sleep(5000);
  driver.findElement(By.cssSelector("#ModalExpense > div.modal-dialog > div.modal-content > form > div.modal-footer > input.btn.btn-default")).click();
  Thread.sleep(5000);
}

@Test
public void testPayingOverdueBills() throws Exception {
  driver.get(baseUrl + "budget");
  driver.findElement(By.id("username")).clear();
  driver.findElement(By.id("username")).sendKeys("user1");
  driver.findElement(By.id("password")).clear();
  driver.findElement(By.id("password")).sendKeys("123");
  driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
  Thread.sleep(5000);
  driver.findElement(By.xpath("(//input[@id='submit'])[3]")).click();
  Thread.sleep(5000);
  assertEquals("Click OK when you've paid this bill for the month!", closeAlertAndGetItsText());
  Thread.sleep(5000);
}


@Test
public void testPayingUpcomingBills() throws Exception {
  driver.get(baseUrl + "budget");
  driver.findElement(By.id("username")).clear();
  driver.findElement(By.id("username")).sendKeys("user1");
  driver.findElement(By.id("password")).clear();
  driver.findElement(By.id("password")).sendKeys("123");
  driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
  Thread.sleep(3000);
  driver.findElement(By.id("submit")).click();
  Thread.sleep(3000);
  assertEquals("Click OK when you've paid this bill for the month!", closeAlertAndGetItsText());
  Thread.sleep(3000);
}
//remove 2nd expense
@Test
public void testRemoveExpense() throws Exception {
  driver.get(baseUrl + "budget");
  Thread.sleep(5000);
  driver.findElement(By.id("username")).clear();
  driver.findElement(By.id("username")).sendKeys("user1");
  driver.findElement(By.id("password")).clear();
  driver.findElement(By.id("password")).sendKeys("123");
  driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
  Thread.sleep(5000);
  driver.findElement(By.id("Transactiontab")).click();
  Thread.sleep(5000);
  driver.findElement(By.xpath("(//input[@id='submit'])[4]")).click();
  Thread.sleep(5000);
  assertTrue(closeAlertAndGetItsText().matches("^Are you sure you want to remove this user[\\s\\S]$"));
  Thread.sleep(5000);
}
//remove 1st income
@Test
  public void testRemoveIncome() throws Exception {
    driver.get(baseUrl + "budget");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("user1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("123");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    Thread.sleep(5000);
    driver.findElement(By.id("Transactiontab")).click();
    Thread.sleep(5000);
    driver.findElement(By.xpath("(//input[@id='submit'])[3]")).click();
    assertTrue(closeAlertAndGetItsText().matches("^Are you sure you want to remove this user[\\s\\S]$"));
    Thread.sleep(5000);
  }


@Test
  public void testBadBudgetDistribution() throws Exception {
    driver.get(baseUrl + "budget");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("user1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("123");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    driver.findElement(By.id("Budgettab")).click();
    Thread.sleep(5000);
    driver.findElement(By.xpath("(//button[@type='button'])[9]")).click();
    Thread.sleep(5000);
    driver.findElement(By.id("clothing")).clear();
    driver.findElement(By.id("clothing")).sendKeys("15");
    Thread.sleep(3000);
    driver.findElement(By.cssSelector("#ModalCreateBudget > div.modal-dialog > div.modal-content > form > div.modal-footer > input.btn.btn-default")).click();
    Thread.sleep(5000);
  }

@Test
  public void testUpdateBudget() throws Exception {
    driver.get(baseUrl + "budget");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("user1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("123");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("Budgettab")).click();
    Thread.sleep(3000);
    driver.findElement(By.xpath("(//button[@type='button'])[9]")).click();
    driver.findElement(By.id("personal")).clear();
    driver.findElement(By.id("personal")).sendKeys("5");
    driver.findElement(By.id("education")).clear();
    driver.findElement(By.id("education")).sendKeys("5");
    driver.findElement(By.id("clothing")).clear();
    driver.findElement(By.id("clothing")).sendKeys("5");
    Thread.sleep(5000);
    driver.findElement(By.cssSelector("#ModalCreateBudget > div.modal-dialog > div.modal-content > form > div.modal-footer > input.btn.btn-default")).click();
    Thread.sleep(5000);
  }

@Test
  public void testResetBudget() throws Exception {
    driver.get(baseUrl + "budget");
    driver.findElement(By.id("username")).clear();
    driver.findElement(By.id("username")).sendKeys("user1");
    driver.findElement(By.id("password")).clear();
    driver.findElement(By.id("password")).sendKeys("123");
    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
    Thread.sleep(2000);
    driver.findElement(By.id("Budgettab")).click();
    driver.findElement(By.xpath("(//button[@type='button'])[9]")).click();
    Thread.sleep(5000);
    driver.findElement(By.cssSelector("#ModalCreateBudget > div.modal-dialog > div.modal-content > form > div.modal-footer > button.btn.btn-default")).click();
    Thread.sleep(3000);
    driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
    Thread.sleep(5000);
    assertTrue(closeAlertAndGetItsText().matches("^Are you sure you want to remove the custom budget, and reset it to the default budget[\\s\\S]$"));
    Thread.sleep(3000);
    driver.findElement(By.xpath("(//button[@type='button'])[9]")).click();
    Thread.sleep(3000);
    driver.findElement(By.cssSelector("#ModalCreateBudget > div.modal-dialog > div.modal-content > form > div.modal-footer > button.btn.btn-default")).click();
    Thread.sleep(5000);
  }


@Test
public void testLogOut() throws Exception {
  driver.get(baseUrl + "budget");
  driver.findElement(By.id("username")).clear();
  driver.findElement(By.id("username")).sendKeys("user1");
  driver.findElement(By.id("password")).clear();
  driver.findElement(By.id("password")).sendKeys("123");
  driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
  Thread.sleep(3000);
  driver.findElement(By.cssSelector("button[type=\"submit\"]")).click();
  Thread.sleep(3000);
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
