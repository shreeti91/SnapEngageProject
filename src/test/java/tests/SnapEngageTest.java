package tests;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import utils.BaseUtils;
import utils.Commons;

public class SnapEngageTest {
	LoginPage objLP;
	HomePage objHP;
	BaseUtils objBU;

	@BeforeTest
	public void SetUp() {
		System.setProperty("webdriver.chrome.driver", Commons.chromeDriverPath + "chromedriver.exe");
		Commons.objDriver = new ChromeDriver();
		Commons.objWait = new WebDriverWait(Commons.objDriver, 10);
		objLP = new LoginPage();
		objHP = new HomePage();
		objBU = new BaseUtils();
	}

	@Test(dataProvider = "data-provider", priority = 1)
	public void loginSteps(String url, String userName, String pwd) {

		Commons.objDriver.get(url);
		Commons.objDriver.manage().window().maximize();
		Commons.objDriver.findElement(objLP.emailText).sendKeys(userName);
		Commons.objDriver.findElement(objLP.pwdText).sendKeys(pwd);
		Commons.objDriver.findElement(objLP.SignInButton).click();
		objBU.wait_presenceOfElementLocated(objHP.userIcon);
		Assert.assertTrue((Commons.objDriver.findElements(objHP.userIcon).size() != 0), "User couldn't login.");

	}

	@Test(priority = 2)
	public void TakeScreenShot() {

		try {
			objBU.takeSnapShot(Commons.objDriver, "..\\SnapEngage\\TestScreenShot\\screenshot.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@DataProvider(name = "data-provider")
	public Object[][] getData() {
		return new Object[][] {
				{ "https://snapengage-qa.appspot.com/signin?to=hub", "pedroalmodovar@test.com", "1q2w3e" } };

	}

	@AfterTest
	public void TearDown() {
		Commons.objDriver.findElement(objHP.userIcon).click();
		objBU.wait_elementToBeClickable(objHP.logOutLink);
		Commons.objDriver.findElement(objHP.logOutLink).click();
		Commons.objDriver.findElement(objHP.okButton).click();
		Assert.assertTrue((Commons.objDriver.findElements(objLP.SignInButton).size() != 0), "User couldn't log out.");
		Commons.objDriver.close();
	}

}
