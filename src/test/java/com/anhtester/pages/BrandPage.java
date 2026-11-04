package com.anhtester.pages;

import com.anhtester.helpers.CaptureHelper;
import com.anhtester.keywords.WebUI;
import com.anhtester.utils.LogUtils;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.util.Hashtable;

/**
 * Page Brand (BT10 có dùng data Excel cho Brand). Form Add nằm bên phải trang list.
 */
public class BrandPage extends BasePage {

    private final By inputName = By.xpath("//input[@name='name']");
    private final By buttonBrowseLogo = By.xpath("//label[contains(normalize-space(),'Logo')]/following-sibling::div//div[normalize-space()='Browse']");
    private final By inputMetaTitle = By.xpath("//input[@name='meta_title']");
    private final By textareaMetaDescription = By.xpath("//textarea[@name='meta_description']");
    private final By buttonSave = By.xpath("//button[normalize-space()='Save']");
    private final By inputSearch = By.xpath("//input[@id='search']");
    private final By firstRowName = By.xpath("//table//tbody/tr[1]/td[2]");

    @Step("Verify Brand page")
    public void verifyBrandPage() {
        WebUI.verifyContains(WebUI.getCurrentUrl(), "/admin/brands", "Không vào được trang Brand");
    }

    @Step("Add new Brand")
    public String addBrand(Hashtable<String, String> data) {
        String name = data.get("NAME") + " " + System.currentTimeMillis();
        LogUtils.info("Add brand: " + name);

        verifyBrandPage();
        WebUI.setText(inputName, name);
        chooseImage(buttonBrowseLogo, data.get("IMAGE"));
        WebUI.setText(inputMetaTitle, data.get("META_TITLE"));
        WebUI.setText(textareaMetaDescription, data.get("META_DESCRIPTION"));
        CaptureHelper.takeScreenshotPage("AddBrandForm");
        WebUI.clickElement(buttonSave);
        verifyMessage("Brand has been inserted successfully");
        return name;
    }

    @Step("Verify Brand {0} in table")
    public void verifyBrandInTable(String name) {
        openBrandPage();
        WebUI.setTextAndKey(inputSearch, name, Keys.ENTER);
        WebUI.waitForPageLoaded();
        WebUI.waitForTextInElement(firstRowName, name);
        WebUI.verifyEquals(WebUI.getElementText(firstRowName), name, "Tên brand trong bảng không đúng");
    }
}
