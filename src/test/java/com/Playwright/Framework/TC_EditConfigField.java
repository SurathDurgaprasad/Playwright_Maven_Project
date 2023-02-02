package com.Playwright.Framework;
import org.testng.annotations.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.LoadState;

public class TC_EditConfigField {
	@Test
	public static void editConfig() throws InterruptedException {
		try (Playwright playwright = Playwright.create()) {
			Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
			BrowserContext context = browser.newContext(new Browser.NewContextOptions().setViewportSize(1500, 800));
			Page page = context.newPage();
			page.navigate("https://smartops-qa04.eastus.cloudapp.azure.com/paas/itops/aiopsui/smartops/ustglobal/");
			page.waitForLoadState(LoadState.NETWORKIDLE);

			String title = page.title();
			System.out.println("Page title is "+title);

			page.locator("xpath=//input[@id = 'username']").fill("itops_admin_new");
			page.locator("xpath=//input[@id = 'password']").fill("qa123");
			page.locator("xpath=//input[@id='kc-login']").click();

			page.waitForLoadState(LoadState.NETWORKIDLE);
			page.locator("xpath=//input[@id='project-list-search-input']").click();

			Locator element = page.locator("xpath=//input[@id='project-list-search-input']");
			element.type("Pru_views");
			element.press("Enter");
			Thread.sleep(11000);
			page.locator("xpath=//span[@class='context-menu']").click();
			page.locator("xpath=//span[normalize-space()='Edit Project']").click();

			page.locator("xpath=//span[normalize-space()='Project Configuration']").click();

			page.locator("xpath=//span[normalize-space()='General Configuration']//preceding::span[@class='accordion-header-content header-icon cursor-pointer ng-tns-c111-20 ai ai-chevron-down-12']").click(new Locator.ClickOptions().setTimeout(100000));

			Locator groupName = page.locator("xpath=//input[@id='priority-worker-group-name']");
			groupName.fill("");
			groupName.fill("Demo");

			Locator thresholdCount = page.locator("xpath=//input[@id='ITOps_responseSLA_threshold']");
			thresholdCount.fill("");
			thresholdCount.fill("8");
			page.locator("//span[@class='p-button-label ng-star-inserted']").click();
			Locator heading = page.locator("xpath=//input[@id='priority-worker-group-name']");

			page.waitForSelector("//div[contains(@class,\"p-toast-detail\")]", new Page.WaitForSelectorOptions().setTimeout(90000));
			if (heading.textContent().contains("Project Configurations Updated")){
				System.out.println("Project Configurations Updated Success!");
			}

			System.out.println("Project Configurations Updated message is displayed");
			browser.close();
			playwright.close();	

		}
	}
}