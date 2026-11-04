package Bai5_locators;

/**
 * BT-Locators: https://crm.anhtester.com/admin/authentication (admin@example.com / 123456)
 * - Login, menu Customers, form New Customer: Xpath tương đối cách 1-5
 *   (thuộc tính, text(), contains(), and/or, starts-with / index)
 * - Menu Projects, form New Project: Xpath tương đối cách 6-10
 *   (following-sibling, preceding-sibling, parent, ancestor, following / descendant)
 */
public class LocatorsCRM {
    public static String url = "https://crm.anhtester.com/admin/authentication";
    public static String email = "admin@example.com";
    public static String password = "123456";

    //================ Login page ================
    public static String headerLoginPage = "//h1[normalize-space()='Login']";
    public static String labelEmail = "//label[@for='email']";
    public static String inputEmail = "//input[@id='email']";
    public static String inputPassword = "//input[@id='password']";
    public static String labelPassword = "//label[@for='password']";
    public static String checkboxRememberMe = "//input[@id='remember']";
    public static String labelRememberMe = "//label[@for='remember']";
    public static String buttonLogin = "//button[normalize-space()='Login']";
    public static String linkForgotPassword = "//a[normalize-space()='Forgot Password?']";
    public static String alertErrorMessage = "//div[contains(@class, 'alert-danger')]";
    public static String getAlertErrorPasswordRequired = "//div[normalize-space()='The Password field is required.']";
    public static String getAlertErrorEmailRequired = "//div[normalize-space()='The Email Address field is required.']";

    //================ Dashboard - Menu ================
    public static String menuDashboard = "//span[normalize-space()='Dashboard' and @class='menu-text']";
    public static String menuCustomers = "//span[normalize-space()='Customers' and @class='menu-text']";
    public static String menuSales = "//span[normalize-space()='Sales' and @class='menu-text']";
    public static String menuSubscription = "//span[normalize-space()='Subscriptions' and @class='menu-text']";
    public static String menuExpenses = "//span[normalize-space()='Expenses' and @class='menu-text']";
    public static String menuContracts = "//span[normalize-space()='Contracts' and @class='menu-text']";
    public static String menuProjects = "//span[normalize-space()='Projects' and @class='menu-text']";
    public static String menuTasks = "//span[normalize-space()='Tasks' and @class='menu-text']";
    public static String menuSupport = "//span[normalize-space()='Support' and @class='menu-text']";
    public static String menuLeads = "//span[normalize-space()='Leads' and @class='menu-text']";
    public static String menuEstimatesRequest = "//span[normalize-space()='Estimate Request' and @class='menu-text']";
    public static String menuKnowledgeBase = "//span[normalize-space()='Knowledge Base' and @class='menu-text']";
    public static String menuUtilities = "//span[normalize-space()='Utilities' and @class='menu-text']";
    public static String menuReports = "//span[normalize-space()='Reports' and @class='menu-text']";

    //================ Customers page ================
    public static String buttonNewCustomer = "//a[normalize-space()='New Customer']";
    public static String buttonImportCustomers = "//a[normalize-space()='Import Customers']";
    public static String buttonContacts = "//a[normalize-space()='Contacts' and contains(@href,'all_contacts')]";
    public static String headerCustomersPage = "//span[normalize-space()='Customers Summary']";
    public static String labelTotalCustomers = "//span[normalize-space()='Total Customers']";
    public static String labelActiveCustomers = "//span[normalize-space()='Active Customers']";
    public static String labelInactiveCustomers = "//span[normalize-space()='Inactive Customers']";
    public static String labelActiveContacts = "//span[normalize-space()='Active Contacts']";
    public static String labelInactiveContacts = "//span[normalize-space()='Inactive Contacts']";
    public static String labelContactsLoginInToday = "//span[normalize-space()='Contacts Logged In Today']";
    public static String inputSearchCustomer = "//div[@id='clients_filter']//input[@type='search']";
    public static String firstRowCustomerName = "//table[@id='clients']//tbody/tr[1]/td[3]/a";

    //================ New Customer page (cách 1-5) ================
    public static String tabCustomerDetails = "//a[@aria-controls='contact_info']";
    public static String tabBillingShipping = "//a[@aria-controls='billing_and_shipping']";
    public static String inputCompany = "//input[@id='company']";
    public static String inputVatNumber = "//input[@id='vat']";
    public static String inputPhone = "//input[@id='phonenumber']";
    public static String inputWebsite = "//input[@id='website']";
    public static String dropdownGroups = "//button[@data-id='groups_in[]']";
    public static String inputSearchGroups = "//button[@data-id='groups_in[]']/following-sibling::div//input[@type='search']";
    public static String dropdownCurrency = "//button[@data-id='default_currency']";
    public static String inputSearchCurrency = "//button[@data-id='default_currency']/following-sibling::div//input[@type='search']";
    public static String dropdownLanguage = "//button[@data-id='default_language']";
    public static String textareaAddress = "//textarea[@id='address']";
    public static String inputCity = "//input[@id='city']";
    public static String inputState = "//input[@id='state']";
    public static String inputZipCode = "//input[@id='zip']";
    public static String dropdownCountry = "//button[@data-id='country']";
    public static String inputSearchCountry = "//button[@data-id='country']/following-sibling::div//input[@type='search']";
    public static String buttonSaveAndCreateContact = "//button[contains(@class,'save-and-add-contact')]";
    public static String buttonSaveCustomer = "//div[@id='profile-save-section']//button[normalize-space()='Save' or contains(@class,'only-save')]";
    public static String alertSuccess = "//span[@class='alert-title' or contains(@class,'alert-title')]";

    /** Option của 1 dropdown bootstrap-select theo text: xpath button dropdown + text option */
    public static String optionOfDropdown(String dropdownButtonXpath, String text) {
        return dropdownButtonXpath + "/following-sibling::div//span[@class='text' and normalize-space()='" + text + "']";
    }

    //================ Projects page (cách 6-10) ================
    public static String headerProjectsSummary = "//span[normalize-space()='Projects Summary']";
    public static String buttonNewProject = "//a[normalize-space()='New Project']";
    public static String inputSearchProject = "//div[@id='projects_filter']//input[@type='search']";
    public static String firstRowProjectName = "//table[@id='projects']//tbody/tr[1]/td[2]/a";

    //================ New Project page (cách 6-10) ================
    // following-sibling: label -> input
    public static String inputProjectName = "//label[@for='name']/following-sibling::input";
    // parent / following-sibling
    public static String dropdownCustomer = "//select[@id='clientid']/following-sibling::button";
    public static String inputSearchCustomerProject = "//select[@id='clientid']/following-sibling::div//input[@type='search']";
    // preceding-sibling: label đứng sau input checkbox
    public static String checkboxCalculateProgress = "//label[@for='progress_from_tasks']/preceding-sibling::input";
    public static String dropdownBillingType = "//select[@id='billing_type']/following-sibling::button";
    public static String dropdownStatus = "//select[@id='status']/following-sibling::button";
    // ancestor
    public static String inputTotalRate = "//input[@id='project_cost']/ancestor::div[contains(@class,'form-group')]//input[@id='project_cost']";
    public static String inputRatePerHour = "//input[@id='project_rate_per_hour']";
    public static String inputEstimatedHours = "//label[@for='estimated_hours']/following-sibling::input";
    public static String dropdownMembers = "//select[@id='project_members[]' or @name='project_members[]']/following-sibling::button";
    // descendant
    public static String inputStartDate = "//label[@for='start_date']/following-sibling::div/descendant::input";
    public static String inputDeadline = "//label[@for='deadline']/following-sibling::div/descendant::input";
    public static String inputTags = "//input[@id='tags']/following-sibling::ul//input";
    public static String iframeDescription = "//iframe[@id='description_ifr']";
    public static String bodyDescription = "//body[@id='tinymce']";
    public static String checkboxSendEmail = "//label[@for='send_created_email']/preceding-sibling::input";
    public static String tabProjectSettings = "//a[@aria-controls='tab_settings']";
    public static String buttonSaveProject = "//div[contains(@class,'btn-bottom-toolbar')]/descendant::button[normalize-space()='Save']";
}
