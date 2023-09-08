import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Test1
{
	public String username = "cig_phudgi";
    public String accesskey = "8J57qy9Oehdb0z56zHeod0W25ICdH5yZUP2ANjnDv28O3Zto2a";
    public static RemoteWebDriver driver = null;
    public String gridURL = "@hub.lambdatest.com/wd/hub";
    boolean status = false;
    @BeforeClass
    public void setUp() throws Exception {
       DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "chrome");
        capabilities.setCapability("version", "86.0");
        capabilities.setCapability("platform", "win10"); // If this cap isn't specified, it will just get the any available one
        capabilities.setCapability("build", "Hyperexecute_Demo");
        capabilities.setCapability("name", "MyTest01");
        capabilities.setCapability("visual", "true");
        capabilities.setCapability("video", "true");
        capabilities.setCapability("network", "true");
        capabilities.setCapability("console", "true");
        
        
        
        try {
            driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
        } catch (MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void testSimple() throws Exception {
    	driver.get("https://www.lambdatest.com");
    	   
    	 //Explicit wait 
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath( "//*[text()='Testing Cloud']")));
        
       //Scroll to an element "SEE ALL INTEGRATION"
      
        WebElement element = driver.findElement(By.xpath("//*[text()='See All Integrations']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		//waitForElementPresent(driver.findElement(By.xpath("//*[contains(text(),'See All Integrations')]")));
		

		String clicklnk = Keys.chord(Keys.CONTROL, Keys.ENTER);
		element.sendKeys(clicklnk);
        
		Thread.sleep(5000);
        //Saving in List from Set
        String parent=driver.getWindowHandle();
        Set<String> webpages= driver.getWindowHandles();
        List<String> webpagesIds=new ArrayList(webpages);
        String link_n=webpagesIds.get(0);
        Iterator<String> I1= webpages.iterator();

        while(I1.hasNext())
        {

        String child_window=I1.next();


        if(!parent.equals(child_window))
        {
        driver.switchTo().window(child_window);
        String currenturl= driver.getCurrentUrl();
        System.out.println("Opened Window URL : " + currenturl);
        System.out.println("Title of the opened window: " + driver.getTitle());
        
        for(String s:webpages)
        {
        System.out.println("Windows Opened " + s);
        }
        String expectedUrl="https://www.lambdatest.com/integrations";
        Assert.assertEquals(currenturl,expectedUrl,"Url doesn't match");
       
       
        }
        }
          
       }
    

    @AfterMethod
    public void tearDown()
    {
        if (driver != null)
        {
            ((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
            driver.quit();
        }
    }
}
