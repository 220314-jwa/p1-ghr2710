package test.dev.roberts;


import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;


public class LoginPage {
	WebDriver driver;
	
	// PageFactory allows you to use annotations to have Selenium instantiate your
	// WebElement fields for you
	@FindBy(id="uName")
	WebElement usernameInput;
	@FindBy(id="pword")
	WebElement passwordInput;
	@FindBy(id="login")
	WebElement logInBtn;
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		
		// tells Selenium to find the elements based on the annotations above
		PageFactory.initElements(driver, this);
	}
	
	public void navigateTo() {
		driver.get("../../../../../main/resources/html/Login-Page.html");
	}

	public void inputUsername(String username) {
		usernameInput.sendKeys(username);
	}
	
	public void inputPassword(String password) {
		passwordInput.sendKeys(password);
	}
	
	public void submitLogin() {
		logInBtn.click();
	}

	public String getAlertText() {
		Alert alert = driver.switchTo().alert();
		String result = alert.getText();
		alert.accept();
		return result;
	}

	public String getUrl() {
		String result = driver.getCurrentUrl();
		return result;
	}
}
