package pageObjects;

import org.openqa.selenium.By;

public class HomePage {

	public By userIcon = By.className("avatar");
	public By logOutLink = By.xpath("//li[text()='Logout']");
	public By okButton = By.name("ok");
}
