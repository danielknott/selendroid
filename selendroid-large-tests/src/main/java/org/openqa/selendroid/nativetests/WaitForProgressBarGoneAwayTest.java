package org.openqa.selendroid.nativetests;

import static org.openqa.selendroid.waiter.TestWaiter.waitFor;

import java.util.concurrent.TimeUnit;

import org.openqa.selendroid.tests.internal.BaseAndroidTest;
import org.openqa.selendroid.waiter.TestWaiter;
import org.openqa.selendroid.waiter.WaitingConditions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class WaitForProgressBarGoneAwayTest extends BaseAndroidTest {
  public static final String ACTIVITY_CLASS = "org.openqa.selendroid.testapp."
      + "HomeScreenActivity";

  protected void precondition() {
    driver.switchTo().window(NATIVE_APP);
    driver.get("and-activity://" + ACTIVITY_CLASS);
    waitFor(WaitingConditions.driverUrlToBe(driver, "and-activity://HomeScreenActivity"));
  }

  @Test
  public void testShouldBeAbleToPassWithCorrectTimeout() {
    precondition();
    int timeout = 25;
    driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
    startTimeOutTest(timeout);
  }

  @Test(enabled = true)
  public void testShouldNotBeAbleToPassWithTooShortTimeout() {
    precondition();
    int timeout = 5;
    driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
    try {
      startTimeOutTest(timeout);
      Assert.fail("This should not happen.");
    } catch (Exception e) {
      // expected
    }
  }

  private void startTimeOutTest(int timeout) {
    driver.findElement(By.id("waitingButtonTest")).click();
    // TODO should work also without wait
    WebElement nameInput = TestWaiter.waitForElement(By.id("inputName"), timeout, driver);
    Assert.assertEquals(nameInput.getText(), "Mr. Burns");
  }
}
