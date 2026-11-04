package ecommerce;

/**
 * Locator dùng chung cho BT1 -> BT4: https://cms.anhtester.com (admin@example.com / 123456)
 */
public class LocatorsCMS {
    public static String urlLogin = "https://cms.anhtester.com/login";
    public static String urlAdmin = "https://cms.anhtester.com/admin";
    public static String email = "admin@example.com";
    public static String password = "123456";

    // Login
    public static String inputEmail = "//input[@id='email']";
    public static String inputPassword = "//input[@id='password']";
    public static String buttonLogin = "//button[normalize-space()='Login']";

    // Dashboard - menu
    public static String menuDashboard = "//span[normalize-space()='Dashboard']";
    public static String menuProducts = "//span[normalize-space()='Products']";
    // Menu con nằm trong menu Products
    public static String menuAddNewProduct = "(//span[normalize-space()='Products']/ancestor::li[1])//span[normalize-space()='Add New Product']";
    public static String menuAllProducts = "(//span[normalize-space()='Products']/ancestor::li[1])//span[normalize-space()='All products' or normalize-space()='All Products']";
    public static String menuCategory = "(//span[normalize-space()='Products']/ancestor::li[1])//span[normalize-space()='Category']";
    public static String menuBrand = "(//span[normalize-space()='Products']/ancestor::li[1])//span[normalize-space()='Brand']";

    // Thông báo (toast)
    public static String messageAlert = "//span[@data-notify='message']";

    // Category list
    public static String headerAllCategories = "//h1[normalize-space()='All categories' or normalize-space()='All Categories']";
    public static String buttonAddNewCategory = "//span[normalize-space()='Add New category' or normalize-space()='Add New Category']";
    public static String inputSearch = "//input[@id='search']";
    public static String firstRowName = "//table//tbody/tr[1]/td[2]";
    public static String firstRowEdit = "//table//tbody/tr[1]//a[@title='Edit']";
    public static String firstRowDelete = "//table//tbody/tr[1]//a[@title='Delete']";
    public static String headerDeleteConfirm = "//h4[normalize-space()='Delete Confirmation']";
    public static String messageDeleteConfirm = "//p[contains(normalize-space(),'Are you sure to delete this')]";
    public static String buttonConfirmDelete = "//a[@id='delete-link']";
    public static String nothingFound = "//td[normalize-space()='Nothing found']";

    // Category form (Add / Edit)
    public static String headerCategoryInformation = "//h5[normalize-space()='Category Information']";
    public static String inputCategoryName = "//input[@id='name']";
    public static String dropdownParentCategory = "//select[@name='parent_id']/following-sibling::button";
    public static String inputOrderingNumber = "//input[@id='order_level']";
    public static String dropdownType = "//select[@name='digital']/following-sibling::button";
    public static String buttonBrowseBanner = "//label[contains(normalize-space(),'Banner')]/following-sibling::div//div[normalize-space()='Browse']";
    public static String buttonBrowseIcon = "//label[contains(normalize-space(),'Icon')]/following-sibling::div//div[normalize-space()='Browse']";
    public static String inputMetaTitle = "//input[@name='meta_title']";
    public static String textareaMetaDescription = "//textarea[@name='meta_description']";
    public static String buttonSave = "//button[normalize-space()='Save']";

    // Popup chọn hình (Uploader)
    public static String inputSearchImage = "//input[@name='aiz-uploader-search']";
    public static String firstImageItem = "(//div[contains(@class,'aiz-file-box')]//div[contains(@class,'card-file')])[1]";
    public static String buttonAddFiles = "//button[normalize-space()='Add Files']";

    // Product form
    public static String headerAddNewProduct = "//h5[normalize-space()='Add New Product'] | //h1[normalize-space()='Add New Product']";
    public static String inputProductName = "//input[@name='name']";
    public static String dropdownCategory = "//select[@name='category_id']/following-sibling::button";
    public static String inputSearchCategory = "//select[@name='category_id']/following-sibling::div//input[@type='search']";
    public static String dropdownBrand = "//select[@name='brand_id']/following-sibling::button";
    public static String inputSearchBrand = "//select[@name='brand_id']/following-sibling::div//input[@type='search']";
    public static String inputUnit = "//input[@name='unit']";
    public static String inputWeight = "//input[@name='weight']";
    public static String inputMinQty = "//input[@name='min_qty']";
    public static String inputTags = "//tags[contains(@class,'tagify')]//span[contains(@class,'tagify__input')]";
    public static String switchColors = "//input[@name='colors_active']/following-sibling::span";
    public static String dropdownColors = "//select[@name='colors[]']/following-sibling::button";
    public static String inputSearchColors = "//select[@name='colors[]']/following-sibling::div//input[@type='search']";
    public static String selectColors = "//select[@name='colors[]']";
    public static String inputUnitPrice = "//input[@name='unit_price']";
    public static String inputDiscount = "//input[@name='discount']";
    public static String inputQuantity = "//input[@name='current_stock']";
    public static String inputSku = "//input[@name='sku']";
    public static String editorDescription = "//div[contains(@class,'note-editable')]";
    public static String buttonSaveAndPublish = "//button[normalize-space()='Save & Publish']";
    public static String buttonUpdateProduct = "//button[normalize-space()='Update Product']";

    // Product list
    public static String headerAllProducts = "//h1[normalize-space()='All Products' or normalize-space()='All products']";
    public static String firstRowProductName = "//table//tbody/tr[1]/td[2]";
}
