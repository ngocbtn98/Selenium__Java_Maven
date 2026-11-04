package com.anhtester.pages;

import com.anhtester.constants.ConfigData;
import com.anhtester.keywords.WebUI;
import org.openqa.selenium.By;

public class BrandPage extends BasePage {
    private final By headerAllBrands = By.xpath("//h1[normalize-space()='All Brands']");
    private final By headerAddNewBrand = By.xpath("//h5[normalize-space()='Add New Brand']");
    private final By inputName = By.xpath("//input[@id='name'] | //input[@placeholder='Name']");
    private final By buttonBrowseLogo = By.xpath("//label[contains(normalize-space(),'Logo')]/following-sibling::div//div[normalize-space()='Browse'] | //div[normalize-space()='Browse']");
    private final By inputMetaTitle = By.xpath("//input[@name='meta_title']");
    private final By textareaMetaDescription = By.xpath("//textarea[@name='meta_description']");
    private final By buttonSave = By.xpath("//button[normalize-space()='Save']");

    public void verifyBrandPage() {
        WebUI.verifyElementVisible(headerAllBrands, "Trang Brand không hiển thị");
        WebUI.verifyEquals(WebUI.getElementText(headerAddNewBrand), "Add New Brand", "Header form Add New Brand không đúng");
        WebUI.takeScreenshotPage("BrandPage");
    }

    public void addBrand(String name, String metaTitle, String metaDescription) {
        WebUI.setText(inputName, name);
        WebUI.selectImageFromUploader(buttonBrowseLogo, ConfigData.IMAGE_NAME());
        WebUI.setText(inputMetaTitle, metaTitle);
        WebUI.setText(textareaMetaDescription, metaDescription);
        WebUI.clickElement(buttonSave);
        verifyToastMessage("Brand has been inserted successfully");
    }

    public void verifyBrandInTable(String name) {
        search(name);
        WebUI.verifyEquals(WebUI.getElementText(firstRowName), name, "Brand trong table không đúng");
    }
}
