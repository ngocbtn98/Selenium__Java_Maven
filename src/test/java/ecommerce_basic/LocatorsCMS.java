package ecommerce_basic;

/**
 * Locators dùng chung cho BT1 -> BT4 (website https://cms.anhtester.com)
 */
public class LocatorsCMS {
    public static final String URL_LOGIN = "https://cms.anhtester.com/login";
    public static final String EMAIL = "admin@example.com";
    public static final String PASSWORD = "123456";

    // Login
    public static final String buttonAcceptCookie = "//button[normalize-space()='Ok. I Understood']";
    public static final String inputEmail = "//input[@id='email']";
    public static final String inputPassword = "//input[@id='password']";
    public static final String buttonLogin = "//button[normalize-space()='Login']";

    // Dashboard / Menu
    public static final String menuDashboard = "//span[@class='aiz-side-nav-text' and normalize-space()='Dashboard']";
    public static final String menuProducts = "//span[@class='aiz-side-nav-text' and normalize-space()='Products']";
    public static final String menuCategory = "//span[@class='aiz-side-nav-text' and normalize-space()='Category']";
    public static final String menuAddNewProduct = "//span[@class='aiz-side-nav-text' and normalize-space()='Add New Product']";
    public static final String menuAllProducts = "//span[@class='aiz-side-nav-text' and normalize-space()='All products']";

    // Common
    public static final String messageToast = "//span[@data-notify='message']";
    public static final String inputSearch = "//input[@id='search']";
    public static final String firstRowName = "//table//tbody/tr[1]/td[2]";
    public static final String firstRowEdit = "//table//tbody/tr[1]//a[@title='Edit']";
    public static final String firstRowDelete = "//table//tbody/tr[1]//a[@title='Delete']";
    public static final String headerDeleteModal = "//div[@id='delete-modal']//h4";
    public static final String messageDeleteModal = "//div[@id='delete-modal']//p";
    public static final String buttonConfirmDelete = "//a[@id='delete-link']";
    public static final String rowsTable = "//table//tbody/tr";

    // Category list
    public static final String headerAllCategories = "//h1[normalize-space()='All categories']";
    public static final String buttonAddNewCategory = "//span[normalize-space()='Add New category']";

    // Add/Edit category
    public static final String headerCategoryInformation = "//h5[normalize-space()='Category Information']";
    public static final String inputName = "//input[@id='name']";
    public static final String dropdownParentCategory = "//button[@data-id='parent_id']";
    public static final String inputOrderLevel = "//input[@id='order_level']";
    public static final String dropdownType = "//button[@data-id='digital']";
    public static final String buttonBrowseBanner = "//label[contains(normalize-space(),'Banner')]/following-sibling::div//div[normalize-space()='Browse']";
    public static final String buttonBrowseIcon = "//label[contains(normalize-space(),'Icon')]/following-sibling::div//div[normalize-space()='Browse']";
    public static final String inputSearchUploader = "//input[@name='aiz-uploader-search']";
    public static final String firstImageUploader = "(//div[contains(@class,'aiz-uploader-select')])[1]";
    public static final String buttonAddFiles = "//button[normalize-space()='Add Files']";
    public static final String inputMetaTitle = "//input[@name='meta_title']";
    public static final String textareaMetaDescription = "//textarea[@name='meta_description']";
    public static final String buttonSave = "//button[normalize-space()='Save']";

    // Product
    public static final String headerAddNewProduct = "//h5[normalize-space()='Add New Product']";
    public static final String inputProductName = "//input[@name='name']";
    public static final String dropdownCategory = "//button[@data-id='category_id']";
    public static final String inputSearchCategory = "//button[@data-id='category_id']/following-sibling::div//input[@type='search' or @type='text']";
    public static final String inputUnit = "//input[@name='unit']";
    public static final String inputMinQty = "//input[@name='min_qty']";
    public static final String inputTags = "//span[contains(@class,'tagify__input')]";
    public static final String toggleColors = "//input[@name='colors_active']/following-sibling::span";
    public static final String dropdownColors = "//button[@data-id='colors']";
    public static final String inputSearchColors = "//button[@data-id='colors']/following-sibling::div//input[@type='search' or @type='text']";
    public static final String textSelectedColors = "//button[@data-id='colors']//div[@class='filter-option-inner-inner']";
    public static final String selectColors = "//select[@id='colors']";
    public static final String inputUnitPrice = "//input[@name='unit_price']";
    public static final String inputDiscount = "//input[@name='discount']";
    public static final String inputQuantity = "//input[@name='current_stock']";
    public static final String inputSku = "//input[@name='sku']";
    public static final String buttonSavePublish = "//button[normalize-space()='Save & Publish']";
    public static final String headerAllProducts = "//h1[normalize-space()='All Products']";
}
