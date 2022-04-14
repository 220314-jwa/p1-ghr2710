package test.dev.roberts;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import test.dev.roberts.LoginPage;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class LoginTest {
	static WebDriver driver;
	static LoginPage newLogIn;
	
	@BeforeAll
	public static void setUp() {
		File file = new File("src/test/resources/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		
		driver = new ChromeDriver();
		newLogIn = new LoginPage(driver);
	}
	
	@AfterAll
	public static void closeDriver() {
		driver.quit();
	}
	
	@Given("the user is on the login page")
	public void the_user_is_on_the_homepage() {
		newLogIn.navigateTo();
	}

	@When("the user enters the correct username")
	public void the_user_enters_the_correct_username() {
		newLogIn.inputUsername("TestAuthor");
	}

	@When("the user enters the correct password")
	public void the_user_enters_the_correct_password() {
		newLogIn.inputPassword("AuthPass");
	}

	@When("the user clicks the login button")
	public void the_user_clicks_the_login_button() {
		newLogIn.submitLogin();
	}

	@Then("the page will redirect")
	public void the_nav_will_show_the_user_s_first_name() {
		String expected = "file:///C:/Users/Hunter/Desktop/Classwork/revature_assignments/project1/p1-ghr2710/src/main/resources/html/Author-Page.html";
		String url = newLogIn.getUrl();
		assertTrue(url.equals(expected));
	}

	@When("the user enters an incorrect username")
	public void the_user_enters_an_incorrect_username() {
		newLogIn.inputUsername("asdfghjkl");
	}

	@Then("an incorrect credentials message will be displayed")
	public void an_incorrect_credentials_message_will_be_displayed() {
		String message = newLogIn.getAlertText();
	    assertTrue(message.contains("Username or Password is incorrect"));
	}

	@When("the user enters the incorrect password")
	public void the_user_enters_the_incorrect_password() {
		newLogIn.inputPassword("12345678987654321");
	}

}
