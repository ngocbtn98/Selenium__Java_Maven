package com.anhtester.pages;

import com.anhtester.helpers.CaptureHelper;
import com.anhtester.keywords.WebUI;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

/**
 * Phần dùng chung cho các trang sau khi Login: menu bên trái, thông báo, popup chọn hình.
 * BÀI TẬP 5: liên kết trang Dashboard => Category, Dashboard => Product, Dashboard => Brand.
 */
public class BasePage {

    // Menu
    protected By menuDashboard = By.xpath("//span[normalize-space()='Dashboard']");
    protected By menuProducts = By.xpath("//span[normalize-space()='Products']");
    private final String productsSubMenu = "(//span[normalize-space()='Products']/ancestor::li[1])//span[normalize-space()='%s']";
    protected By menuAddNewProduct = By.xpath(String.format(productsSubMenu, "Add New Product"));
    protected By menuAllProducts = By.xpath("(//span[normalize-space()='Products']/ancestor::li[1])"
            + "//span[normalize-space()='All products' or normalize-space()='All Products']");
    protected By menuCategory = By.xpath(String.format(productsSubMenu, "Category"));
    protected By menuBrand = By.xpath(String.format(productsSubMenu, "Brand"));

    // Thông báo (toast)
    protected By messageAlert = By.xpath("//span[@data-notify='message']");

    // Popup chọn hình (Uploader)
    protected By inputSearchImage = By.xpath("//input[@name='aiz-uploader-search']");
    protected By firstImageItem = By.xpath("(//div[contains(@class,'aiz-file-box')]//div[contains(@class,'card-file')])[1]");
    protected By buttonAddFiles = By.xpath("//button[normalize-space()='Add Files']");

    /** Click menu con của Products, chỉ mở menu Products khi menu con đang bị ẩn */
    protected void openProductsSubMenu(By subMenu) {
        if (!WebUI.checkElementExist(subMenu)) {
            WebUI.clickElement(menuProducts);
        }
        WebUI.clickElement(subMenu);
        WebUI.waitForPageLoaded();
    }

    @Step("Open Category page")
    public CategoryPage openCategoryPage() {
        openProductsSubMenu(menuCategory);
        CaptureHelper.takeScreenshotPage("Category");
        return new CategoryPage();
    }

    @Step("Open All Products page")
    public ProductPage openProductPage() {
        openProductsSubMenu(menuAllProducts);
        CaptureHelper.takeScreenshotPage("AllProducts");
        return new ProductPage();
    }

    @Step("Open Add New Product page")
    public ProductPage openAddNewProductPage() {
        openProductsSubMenu(menuAddNewProduct);
        CaptureHelper.takeScreenshotPage("AddNewProduct");
        return new ProductPage();
    }

    @Step("Open Brand page")
    public BrandPage openBrandPage() {
        openProductsSubMenu(menuBrand);
        CaptureHelper.takeScreenshotPage("Brand");
        return new BrandPage();
    }

    public String getMessage() {
        return WebUI.getElementText(messageAlert);
    }

    @Step("Verify message: {0}")
    public void verifyMessage(String expected) {
        WebUI.verifyEquals(getMessage(), expected, "Message thông báo sai");
    }

    /**
     * Click Browse -> search tên hình đã add bằng tay -> click hình -> Add Files.
     * Danh sách hình load lại sau khi search nên chờ đúng hình có title chứa tên rồi mới click.
     */
    @Step("Choose image {1}")
    protected void chooseImage(By browseButton, String imageName) {
        WebUI.clickElement(browseButton);
        WebUI.waitForElementVisible(firstImageItem);
        WebUI.setText(inputSearchImage, imageName);
        By imageItem = By.xpath("//div[@id='aizUploaderModal']//div[contains(@class,'aiz-uploader-select')"
                + " and contains(@title,'" + imageName + "')]");
        WebUI.clickElement(imageItem);
        WebUI.clickElement(buttonAddFiles);
        WebUI.waitForElementInvisible(buttonAddFiles);
    }
}
