package com.anhtester.pages;

import com.anhtester.keywords.WebUI;
import org.openqa.selenium.By;

/** Các thành phần chung của các trang admin: menu, toast message, search, table */
public class BasePage {
    protected By messageToast = By.xpath("//span[@data-notify='message']");
    protected By inputSearch = By.xpath("//input[@id='search']");
    protected By tableRows = By.xpath("//table//tbody/tr");
    protected By firstRowName = By.xpath("//table//tbody/tr[1]/td[2]");
    protected By firstRowEdit = By.xpath("//table//tbody/tr[1]//a[@title='Edit']");
    protected By firstRowDelete = By.xpath("//table//tbody/tr[1]//a[@title='Delete']");
    protected By headerDeleteModal = By.xpath("//div[@id='delete-modal']//h4");
    protected By messageDeleteModal = By.xpath("//div[@id='delete-modal']//p");
    protected By buttonConfirmDelete = By.xpath("//a[@id='delete-link']");

    protected By menuDashboard = By.xpath("//span[@class='aiz-side-nav-text' and normalize-space()='Dashboard']");
    protected By menuProducts = By.xpath("//span[@class='aiz-side-nav-text' and normalize-space()='Products']");
    protected By menuAddNewProduct = By.xpath("//span[@class='aiz-side-nav-text' and normalize-space()='Add New Product']");
    protected By menuAllProducts = By.xpath("//span[@class='aiz-side-nav-text' and normalize-space()='All products']");
    protected By menuCategory = By.xpath("//span[@class='aiz-side-nav-text' and normalize-space()='Category']");
    protected By menuBrand = By.xpath("//span[@class='aiz-side-nav-text' and normalize-space()='Brand']");

    public String getToastMessage() {
        return WebUI.getElementText(messageToast);
    }

    public void verifyToastMessage(String expected) {
        WebUI.verifyEquals(getToastMessage(), expected, "Toast message không đúng");
    }

    protected void expandProductsMenu(By subMenu) {
        if (!WebUI.isElementDisplayed(subMenu)) {
            WebUI.clickElement(menuProducts);
        }
        WebUI.clickElement(subMenu);
    }

    /** Search trên trang list và chờ dòng đầu tiên chứa giá trị */
    protected void search(String value) {
        WebUI.setTextAndKey(inputSearch, value, org.openqa.selenium.Keys.ENTER);
        WebUI.waitForTextPresent(firstRowName, value);
    }

    /** BT9: Search và kiểm tra tất cả các dòng trong table ở cột Name đều chứa giá trị */
    public void searchDataTable(String value, int columnIndex) {
        search(value);
        WebUI.checkContainsSearchTableByColumn(tableRows, columnIndex, value);
    }

    public boolean isItemInTable(String value) {
        return WebUI.checkElementExist(By.xpath("//table//tbody//td[normalize-space()='" + value + "']"));
    }
}
