package sim.tv2.no.webDriver;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class OpenOpta {

	private static String osVersion = System.getProperty("os.name").toLowerCase();
	
	public OpenOpta() {
		// TODO Auto-generated constructor stub
	}
	
	public void openOptaTabs() {
		String user = "TV2";
		String passwd = "goal5LM3";
		String url = "http://lm3.cloud.opta.net/index.html";
		WebDriver driver = new FirefoxDriver();
		
		driver.get(url);
		driver.manage().window().maximize();
		waitForPageLoad(1000);
		
		WebElement loginMail = driver.findElement(By.id("lm3-email-lm3_0"));
		WebElement loginPwd = driver.findElement(By.id("lm3-password-lm3_0"));
		
		loginMail.sendKeys(user);
		loginPwd.sendKeys(passwd);
		
		WebElement submitButton = driver.findElement(By.id("lm3-signin-lm3_0"));
		submitButton.click();
		
		waitForPageLoad(1000);
		
		List<WebElement> matches = driver.findElements(By.className("opta-event-link"));
		List<String> urls = new ArrayList<String>();
		
		for(WebElement elem : matches) {
			urls.add(elem.getAttribute("href"));
		}
		
		for(String matchUrl : urls) {
			if(isMac()) {
				driver.findElement(By.cssSelector("body")).sendKeys(Keys.COMMAND + "t");				
			} else if(isWindows()) {
				driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "t");
			}
			driver.navigate().to(matchUrl);
			waitForPageLoad(1000);
			driver.findElement(By.xpath("//*[@id=\"opta-widget-idx-lm3_6\"]/div/ul[1]/li[3]")).click();
			
			waitForPageLoad(500);
			
		}
		

//		driver.close();
		
	}
	
	public static boolean isWindows() {

		return (osVersion.indexOf("win") >= 0);

	}

	public static boolean isMac() {

		return (osVersion.indexOf("mac") >= 0);

	}

	private void waitForPageLoad(long amountToWait) {
		Long end = System.currentTimeMillis() + amountToWait;

		while(System.currentTimeMillis() < end) {

		}
	}

}
