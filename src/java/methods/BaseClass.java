package java.methods;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class BaseClass {

	public WebDriver driver;
	public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<>();


	public WebDriver initialize_driver() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--no-sandbox");
		options.addArguments("headless");
		options.addArguments("--disable-dev-shm-usage");
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		tdriver.set(driver);
		return getDriver();
	}


	public static synchronized WebDriver getDriver() {
		return tdriver.get();
	}

	@Step("Очистка поля и последующий ввод текста {text}")
	public boolean input_Text(@NotNull WebElement loc, String text) {
		try {
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
			loc.clear();
			loc.sendKeys(text);
			return true;
		} catch (NoSuchElementException | ElementNotInteractableException e) {
			return false;
		}
	}

	public boolean input_Text(@NotNull WebElement loc, String text, int t) {
		try {
			driver.manage().timeouts().implicitlyWait(t, TimeUnit.SECONDS);
			loc.clear();
			loc.sendKeys(text);
			return true;
		} catch (NoSuchElementException | ElementNotInteractableException e) {
			return false;
		}
	}

	@Step("Ввод текста {text}")
	public boolean input_Text_Not_Clear(@NotNull WebElement loc, String text) {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			loc.sendKeys(text);
			return true;
		} catch (NoSuchElementException | ElementNotInteractableException e) {
			return false;
		}
	}

	@Step("Осуществляет клик по элементу")
	public boolean click_Element(@NotNull WebElement loc) {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			loc.click();
			return true;
		} catch (NoSuchElementException | ElementNotInteractableException e) {
			return false;
		}
	}


	@Step("Осуществляет клик по элементу")
	public boolean click_Element(@NotNull WebElement loc, int t) {
		try {
			driver.manage().timeouts().implicitlyWait(t, TimeUnit.SECONDS);
			loc.click();
			return true;
		} catch (NoSuchElementException | ElementNotInteractableException e) {
			return false;
		}
	}

//	@Step("Ожидание доступности элемента")
//	public boolean wait_visibility_of_all_elements(@NotNull WebElement loc, int t)
//	{
//		try {
//			WebDriverWait wait = new WebDriverWait(driver,t);
//			wait.until(ExpectedConditions.visibilityOfAllElements(loc));
//			return true;
//		}
//		catch (NoSuchElementException | ElementNotInteractableException e)
//		{
//			return false;
//		}
//	}

//	@Step("Ожидание кликабельности элемента")
//	public boolean wait_element_to_be_clickable(@NotNull WebElement loc, int t)
//	{
//		try {
//			WebDriverWait wait = new WebDriverWait(driver,t);
//			wait.until(ExpectedConditions.elementToBeClickable(loc));
//			return true;
//		}
//		catch (NoSuchElementException | ElementNotInteractableException e)
//		{
//			return false;
//		}
//	}

	@Step("Проверяет включен ли объект")
	public boolean isDisabled(@NotNull WebElement loc) {
		return ("true".equals(loc.getAttribute("disabled")));
	}

	@Step("Выбор элемента из выпадающего списка по тексту")
	//loc - элемент верхнего уровня, после нажатия на который можно выбрать элемент второго уровня
	//loc2 - элемент который появляется после клика на loc
	//text - текст по которому ищем элемент из выпадающего списка
	//tagname - наименование тега в котором осуществляется поиск по тексту
	public boolean select_element_in_dropdown2(@NotNull WebElement loc, WebElement loc2, String text, String tagname) {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			loc.click();
			List<WebElement> options = loc2.findElements(By.tagName(tagname));
			for (WebElement option : options) {
				if (text.equals(option.getText())) {
					option.click();
					return true;
				}
			}

		} catch (NoSuchElementException | ElementNotInteractableException e) {
			return false;
		}
		return false;
	}

	@Step("Выбор элемента из выпадающего списка по тексту")
	//loc - сам элемент
	//text - текст по которому ищем элемент из выпадающего списка
	//tagname - наименование тега в котором осуществляется поиск по тексту
	public boolean select_element_in_dropdown(@NotNull WebElement loc, String text, String tagname) {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			//loc.click();
			List<WebElement> options = loc.findElements(By.tagName(tagname));
			for (WebElement option : options) {
				if (text.equals(option.getText())) {
					option.click();
					return true;
				}
			}

		} catch (NoSuchElementException | ElementNotInteractableException e) {
			return false;
		}
		return false;
	}

	@Step("Поиск элемента из выпадающего списка по тексту")
	//loc - сам элемент
	//text - текст по которому ищем элемент из выпадающего списка
	//tagname - наименование тега в котором осуществляется поиск по тексту
	public boolean search_element_in_dropdown(@NotNull WebElement loc, String text, String tagname) {
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			List<WebElement> options = loc.findElements(By.tagName(tagname));
			for (WebElement option : options) {
				if (text.equals(option.getText())) {
					return true;
				}
			}
		} catch (NoSuchElementException | ElementNotInteractableException e) {
			return false;
		}
		return false;
	}

//	@Step("Переключение интерфейса на русский язык")
//	public void rus_lang() {
//		mp = PageFactory.initElements(driver, Settings.class);
//		al = PageFactory.initElements(driver, AllureListener.class);
//		auth = PageFactory.initElements(driver, Authtorization.class);
//		if (Objects.equals(mp.carriers.getText(), "Carriers")) {
//			Assert.assertTrue(click_Element(mp.settings));
//			Assert.assertTrue(click_Element(mp.common_settings));
//			Assert.assertTrue(select_element_in_dropdown(mp.lang, "Russian", "option"));
//			Assert.assertTrue(click_Element(mp.save));
//			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//			Assert.assertEquals(mp.carriers.getText(), "Носители");
//		}
//	}

	@Step("Сравнивает по высоте размеры двух элеменов")
	public boolean CompareHeightSize(Dimension A, Dimension B) {
		if (A.height == B.height) return true;
		return false;
	}

	@Step("Взять значение элемента из списка, по порядковому номеру его тега")
	//loc - список
	//tagname - наименование тега в котором осуществляется поиск
	//n - Номер строки по которой ведется поиск
	public String get_element_in_list(@NotNull WebElement loc, String tagname, int n) {
		int j = 0;
		String abc = "";
		List<WebElement> parametrs = loc.findElements(By.tagName(tagname));
		for (WebElement parametr : parametrs) {
			if (j == n) abc = parametr.getText();
			j++;
		}
		return abc;
	}

	@Step("Взять значение элемента из таблицы, по порядковым номерам его тегов")
	//loc - таблица
	//tagname_1 - наименование тега в котором осуществляется поиск по строке
	//tagname_2 - наименование тега в котором осуществляется поиск по столбцу
	//n - Номер строки по которой ведется поиск
	//k - Номер столцбца по которому ведется поиск
	public String get_element_in_table(@NotNull WebElement loc, String tagname_1, String tagname_2, int n, int k) {
		int i = 0, j = 0;
		String abc = "";
		List<WebElement> orders = loc.findElements(By.tagName(tagname_1));
		for (WebElement order : orders) {
			if (j == n) {
				List<WebElement> parametrs = order.findElements(By.tagName(tagname_2));
				for (WebElement parametr : parametrs) {
					if (i == k) abc = parametr.getText();
					i++;
				}
			}
			j++;
		}
		return abc;
	}

	@Step("Проверка существования элемента с определенным значением в списке")
	//loc - список
	//text - значение, по которому ведеся поиск
	//tagname - наименование тега в котором осуществляется поиск по строке
	public boolean search_element_in_list(@NotNull WebElement loc, String text, String tagname) {
		List<WebElement> parametrs = loc.findElements(By.tagName(tagname));
		for (WebElement parametr : parametrs) {
			if (parametr.getText().contains(text)) return true;
		}
		return false;
	}

	@Step("Проверка существования элемента с определенным значением в таблице")
	//loc - таблица
	//text - значение, по которому ведеся поиск
	//tagname_1 - наименование тега в котором осуществляется поиск по строке
	//tagname_2 - наименование тега в котором осуществляется поиск по столбцу
	public boolean search_element_in_table(@NotNull WebElement loc, String text, String tagname_1, String tagname_2) {
		List<WebElement> orders = loc.findElements(By.tagName(tagname_1));
		for (WebElement order : orders) {
			List<WebElement> parametrs = order.findElements(By.tagName(tagname_2));
			for (WebElement parametr : parametrs) {
				if (parametr.getText().contains(text)) return true;
			}
		}
		return false;
	}

	@Step("Конвертация переменых string в int и их сравнение")
	public boolean comparison_of_variables(String a, String b, String z) {
		switch (z) {
			case ("<"):
				if ((Integer.parseInt(a)) < (Integer.parseInt(b)))
					return true;
				else return false;
			case (">"):
				if ((Integer.parseInt(a)) > (Integer.parseInt(b)))
					return true;
				else return false;
			case ("="):
				if ((Integer.parseInt(a)) == (Integer.parseInt(b)))
					return true;
				else return false;
			case ("<="):
				if ((Integer.parseInt(a)) <= (Integer.parseInt(b)))
					return true;
				else return false;
			case (">="):
				if ((Integer.parseInt(a)) >= (Integer.parseInt(b)))
					return true;
				else return false;
			default: return false;
		}
	}

	@Step("Наведение курсора мыши на выбранный элемент")
	//loc - элемент на который требуется навести курсор
	public void cursor_hover(@NotNull WebElement loc) {
		Actions action = new Actions(driver);
		action.moveToElement(loc);
		action.perform();
	}

	@Step("Возвращает полный путь к файлу, лежащему внутри проекта(в папке data), по его имени")
	//element - имя файла (например "Office.png")
	public String get_path(@NotNull String element) {
		return System.getProperty("user.dir")+"/data/"+element;
	}

//	@Step("Создать новую карту")
//	public void create_map() {
//		tp = PageFactory.initElements(driver, TopPanel.class);
//		tm = PageFactory.initElements(driver, ToolsMap.class);
//		Assert.assertTrue(click_Element(tp.Tools));
//		Assert.assertTrue(click_Element(tp.T_Map));
//		Assert.assertTrue(click_Element(tm.ToolsMap_Create));
//		tm.MapCreation_SelectFile.sendKeys(get_path("Office_Map.png"));
//		Assert.assertTrue(click_Element(tm.MapCreation_CreateMap));
//		Assert.assertTrue(click_Element(tm.ToolsMap_Exit));
//	}
//
//	@Step("Создать новую карту с выбором подложки")
//	public void create_map(String background) {
//		tp = PageFactory.initElements(driver, TopPanel.class);
//		tm = PageFactory.initElements(driver, ToolsMap.class);
//		Assert.assertTrue(click_Element(tp.Tools));
//		Assert.assertTrue(click_Element(tp.T_Map));
//		Assert.assertTrue(click_Element(tm.ToolsMap_Create));
//		tm.MapCreation_SelectFile.sendKeys(get_path(background));
//		Assert.assertTrue(click_Element(tm.MapCreation_CreateMap));
//		Assert.assertTrue(click_Element(tm.ToolsMap_Exit));
//	}
//
//	@Step("Удалить текущую карту")
//	public void delete_map() {
//		tp = PageFactory.initElements(driver, TopPanel.class);
//		tm = PageFactory.initElements(driver, ToolsMap.class);
//		Assert.assertTrue(click_Element(tp.Tools));
//		Assert.assertTrue(click_Element(tp.T_Map));
//		Assert.assertTrue(click_Element(tm.ToolsMap_Delete));
//		Assert.assertTrue(click_Element(tm.ConfirmationDeleting_DeliteMap));
//		Assert.assertTrue(click_Element(tm.ToolsMap_Exit));
//	}
//
//	@Step("Удалить карту по названию")
//	public void delete_map(String mapname) {
//		tp = PageFactory.initElements(driver, TopPanel.class);
//		tm = PageFactory.initElements(driver, ToolsMap.class);
//		Assert.assertTrue(select_element_in_dropdown2(tp.SelectMapDrDn, tp.SelectMapDropdown,mapname,"div"));
//		Assert.assertTrue(click_Element(tp.Tools));
//		Assert.assertTrue(click_Element(tp.T_Map));
//		Assert.assertTrue(click_Element(tm.ToolsMap_Delete));
//		Assert.assertTrue(click_Element(tm.ConfirmationDeleting_DeliteMap));
//		Assert.assertTrue(click_Element(tm.ToolsMap_Exit));
//		}
//
//	@Step("Удалить новую карту по номеру")
//	// n - номер вновь созданной карты (например "Карта 1")
//	public void delete_map(int n) {
//		tp = PageFactory.initElements(driver, TopPanel.class);
//		tm = PageFactory.initElements(driver, ToolsMap.class);
//		Assert.assertTrue(select_element_in_dropdown2(tp.SelectMapDrDn, tp.SelectMapDropdown,"Карта " + n,"div"));
//		Assert.assertTrue(click_Element(tp.Tools));
//		Assert.assertTrue(click_Element(tp.T_Map));
//		Assert.assertTrue(click_Element(tm.ToolsMap_Delete, 10));
//		Assert.assertTrue(click_Element(tm.ConfirmationDeleting_DeliteMap, 10));
//		Assert.assertTrue(click_Element(tm.ToolsMap_Exit, 10));
//	}

	@Step("Проверка существования элемента")
	public boolean existence_Element(@NotNull WebElement loc) {
		try {
			loc.isEnabled();
			return true;
		} catch (NoSuchElementException | ElementNotInteractableException e) {
			return false;
		}
	}

	@Step("Проверка существования элемента")
	private boolean existsElement(String id) {
		try {
			driver.findElement(By.id(id));
		} catch (NoSuchElementException e) {
			return false;
		}
		return true;
	}

}



