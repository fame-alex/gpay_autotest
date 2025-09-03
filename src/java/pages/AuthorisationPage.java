package java.pages;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.Setter;
import methods.AllureListenerRefactor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
@Setter
public class AuthorisationPage {

    private WebDriver driver;
    AllureListenerRefactor allureListenerRefactor = new AllureListenerRefactor();

    public AuthorisationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

        //new

//    @FindBy(id = ":r1:")
//    private WebElement login;
//
//    @FindBy(id = ":r2:")
//    private WebElement password;
//
//    @FindBy(xpath = ("//*[@test-id='header-fullnameSpan']"))
//    private WebElement username;
//
//    @FindBy(xpath = "//button[text() = 'Выход']")
//    private WebElement exit;
//    @FindBy(xpath =("//*[@test-id='authorization-submitButton']"))
//    private WebElement submit;

    //old

    @FindBy(xpath = ("//*[@test-id='authorization-loginInput']"))
    private WebElement login;

    @FindBy(xpath = ("//*[@test-id='authorization-passwordInput']"))
    private WebElement password;

    @FindBy(xpath = "//*[@id=\"topPanel\"]/div/section/div[3]/div[1]/p")
    private WebElement username;

    @FindBy(xpath = "//button[@class='common-Header-___style__buttonLogOut___uSnYa btn btn-outline-secondary']")
    private WebElement exit;

    @FindBy(css = "button[type=submit]")
    private WebElement submit;


    @Step("Авторизация во вкладке 'Карта'")
    public void login(String Login, String Pass) {
        login.clear();
        login.sendKeys(Login);
        password.clear();
        password.sendKeys(Pass);
        submit.click();
        allureListenerRefactor.saveScreenshot(driver);
    }




    }

