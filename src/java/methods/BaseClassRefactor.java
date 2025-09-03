package java.methods;

import com.google.common.base.CharMatcher;
import com.jcraft.jsch.*;
import config.GlobalConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import pages.AuthorisationPage;
import pages.map.sattings.SettingsPage;
import util.Language;
import util.Pause;

import java.util.concurrent.TimeUnit;


public class BaseClassRefactor {

    protected WebDriver driver;
    protected AuthorisationPage authPage;
    protected SettingsPage settingsPage;

    private static final int TIME_OUT_DEFAULT = 10;

    @BeforeClass
    protected void initDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox"); // надо комментить при запуске тестов локально
        //отключает защитный режим Chrome, что может понадобиться при запуске тестов на удаленной машине;
        //options.addArguments("--headless");  запускает браузер в безголовом режиме, т.е. без открытия окна браузера;
        options.addArguments("--disable-dev-shm-usage"); //отключает использование /dev/shm в Chrome, что может уменьшить потребление памяти;
        options.addArguments("--remote-allow-origins=*"); //позволяет удаленному серверу запустить Chrome и получать доступ к его контенту.
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        setTimeOut(TIME_OUT_DEFAULT); //ожидание появления элементов
        driver.manage().window().maximize(); //отрытие окна браузера на весь экран
        getServer();
        authPage = new AuthorisationPage(driver);
        authPage.login(GlobalConfig.SERVER_LOGIN,GlobalConfig.SERVER_PASS);


    }

    protected void setTimeOut(Integer seconds) {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(seconds, TimeUnit.SECONDS);
    }

    protected void setDefaultLang() {
        settingsPage = new SettingsPage(driver);
        if (!CharMatcher.ascii().matchesAllOf(authPage.getExit().getText())) {
            settingsPage.setLanguage(Language.ENG_RUS_TEXT);
        }
    }

    protected void getServer() {
        driver.get(GlobalConfig.GENERAL_URL);
        driver.findElement(By.id("details-button")).click();
        driver.findElement(By.id("proceed-link")).click();
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String[] spec_symbol  = new String[]
            {"/","|",
            "~","!","@","#","$","%","^","&",".","?",
            "(",")","{","}","[","]","<",">",
            "*","_","+","=","","   "};

    //перебор в поле и элемент недоступен
    public void validation_false(WebElement ElementValidation, String[] massive, WebElement Correct){

        for (int i=0;i<26;i++){
            ElementValidation.sendKeys(massive[i]);
            Pause.pause(2000);

            Pause.pause(100);
            Assert.assertFalse(Correct.isEnabled());
            ElementValidation.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        }
    }
    //перебор в поле и элемент доступен
    public void validation_true(WebElement ElementValidation, String[] massive, WebElement Correct){

        for (int i=0;i<26;i++){
            ElementValidation.sendKeys(massive[i]);
            Pause.pause(500);

            Assert.assertTrue(Correct.isEnabled());
            ElementValidation.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        }
    }

    public void UserAuthPubKey (String command, String user){

            try {
                JSch jsch = new JSch();
                int port = 22;
                String privateKey = "D:\\Desktop\\ssh_key.private";
                //String command = "sudo systemctl stop realtrac-server";

                jsch.addIdentity(privateKey);
                System.out.println("identity added ");

                Session session = jsch.getSession(user, GlobalConfig.HOST, port);
                System.out.println("session created.");

                // disabling StrictHostKeyChecking may help to make connection but makes it insecure
                // see http://stackoverflow.com/questions/30178936/jsch-sftp-security-with-session-setconfigstricthostkeychecking-no
                //
                // java.util.Properties config = new java.util.Properties();
                // config.put("StrictHostKeyChecking", "no");
                // session.setConfig(config);
                session.setConfig("StrictHostKeyChecking", "no");
                session.connect();
                Channel channel = session.openChannel("exec");
                ((ChannelExec) channel).setCommand(command);
                ((ChannelExec) channel).setPty(false);
                channel.connect();
                channel.disconnect();
                session.disconnect();
//                System.out.println("session connected.....");
//
//                Channel channel = session.openChannel("sftp");
//                channel.setInputStream(System.in);
//                channel.setOutputStream(System.out);
//                channel.connect();
//                System.out.println("shell channel connected....");
//
//                ChannelSftp c = (ChannelSftp) channel;
//                String fileName = "test.txt";
//                c.put(fileName, "./in/");
//                c.exit();
                System.out.println("done");

            } catch (Exception e) {
                System.err.println(e);
            }
        }


    //закрытие браузера с драйвером
    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}



