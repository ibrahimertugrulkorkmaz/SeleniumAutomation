package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileWriter;
import java.util.List;


public class ProductsOnSaleCheckListRandW {

    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.n11.com/kampanyalar");
        driver.manage().window().maximize();

        //'Moda' Campaign Promotion webpage defined differently than rest of other categories, xpath was opening from span section
        WebElement Moda = driver.findElement(By.xpath("//*[@id=\"contentCampaignPromotion\"]/div/div[2]/div/div[2]/div/div/ul/li[2]/span"));
        Moda.click();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Moda page opening successful");


        //set variable for xpath link cause whole Campaign Promotion webpage xpaths were ordered by id. Pet 11 category is disabled right now as it seems like. So maybe can add skip the order on 10th page if needed on future.
        for (int i = 3; i < 12; i++) {
            System.out.println(i);
            WebElement Page = driver.findElement(By.xpath("//*[@id=\"contentCampaignPromotion\"]/div/div[2]/div/div[2]/div/div/div/ul/li[" + i + "]"));
            Page.click();
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("Page opening action is successful");

        }

        try {
            File silinecekDosya = new File("csv/CsvOutput.csv");

            //If the file does exist control
            if (!silinecekDosya.exists())
                throw new IllegalArgumentException("Cannot find csv file"
                        + silinecekDosya.getAbsolutePath());

            if (silinecekDosya.delete()) {
                System.out.println("Csv file cleared successfully");
            } else {
                System.out.println("Clearing action not successful");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        // section of category counter definition
        int d = 2;
        // loop which gives output of categories
        for (int e = 2; e < 11; e++) {
            WebElement Title = driver.findElement(By.xpath("//*[@id=\"contentCampaignPromotion\"]/div/div[2]/div/div[2]/div/section[" + e + "]/h2"));
            String name = Title.getText();
            System.out.println(name);
            // loop which give output of products of categories up to the number of items in the list
            for (int b = 2; b < 100; b++) {

                List<WebElement> elements = driver.findElements(By.xpath("//*[@id=\"contentCampaignPromotion\"]/div/div[2]/div/div[2]/div/section[" + d + "]/ul/li[" + b + "]/a"));
                for (int a = 0; a < elements.size(); a++) {
                    System.out.println(elements.get(a).getAttribute("href"));
                    System.out.println(name);
                    try {
                        FileWriter fwriter = new FileWriter("csv/CsvOutput.csv", true);
                        fwriter.write(name);
                        fwriter.write("\n");
                        fwriter.write(elements.get(a).getAttribute("href"));
                        fwriter.write("\n");
                        fwriter.close();
                    } catch (Exception ex) {
                        System.out.println("Error while exporting data to csv file");
                        System.out.println(ex.getMessage());
                    }


                }

            }
            //adds +1 to section category number so we keep getting items from next section
            d = d + 1;

        }
        //closing driver after all process is done, added for to keep memory/processor usage on low level
        driver.close();
    }

}

