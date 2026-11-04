package Bai5_locators;

/**
 * BT-Locators: https://crm.anhtester.com/admin/authentication (admin@example.com/123456)
 * - Login, menu, Customers, New Customer form: XPath tương đối cách 1-5
 *   (thuộc tính, text(), contains(), starts-with(), and/or)
 * - Projects, New Project form: XPath tương đối cách 6-10
 *   (axes: parent, ancestor, following-sibling, preceding-sibling, following/descendant)
 */
public class LocatorsCRM {
    public static String url = "https://crm.anhtester.com/admin/authentication";
    public static String email = "admin@example.com";
    public static String password = "123456";

    //Locator for login page
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

    //Locator for Dashboard page
    public static String menuDashboard = "//span[normalize-space()='Dashboard' and @class='menu-text']";
    public static String menuCustomers = "//span[normalize-space()='Customers' and @class='menu-text']";
    public static String menuSales = "//span[normalize-space()='Sales' and @class='menu-text']";
    public static String menuSubscription = "//span[normalize-space()='Subscription' and @class='menu-text']";
    public static String menuExpenses = "//span[normalize-space()='Expenses' and @class='menu-text']";
    public static String menuContacts = "//span[normalize-space()='Contacts' and @class='menu-text']";
    public static String menuProjects = "//span[normalize-space()='Projects' and @class='menu-text']";
    public static String menuTasks = "//span[normalize-space()='Tasks' and @class='menu-text']";
    public static String menuSupport = "//span[normalize-space()='Support' and @class='menu-text']";
    public static String menuLeads = "//span[normalize-space()='Leads' and @class='menu-text']";
    public static String menuEstimatesRequest = "//span[normalize-space()='Estimates & Requests' and @class='menu-text']";
    public static String menuKnowledgeBase = "//span[normalize-space()='Knowledge Base' and @class='menu-text']";
    public static String menuUtilities = "//span[normalize-space()='Utilities' and @class='menu-text']";
    public static String menuReposts = "//span[normalize-space()='Reports' and @class='menu-text']";

    //Locator for Customers page
    public static String buttonNewCustomer = "//a[normalize-space()='New Customer']"; // Fix: thiếu dấu ]
    public static String buttonImportCustomers = "//a[normalize-space()='Import Customers']";
    public static String buttonContacts = "//a[normalize-space()='Contacts' and contains(@href,'all_contacts')]";
    public static String headerCustomersPage = "//span[normalize-space()='Customers Summary']";
    public static String labelTotalCustomers = "//span[normalize-space()='Total Customers']";
    public static String labelActiveCustomers = "//span[normalize-space()='Active Customers']";
    public static String labelInactiveCustomers = "//span[normalize-space()='Inactive Customers']";
    public static String labelActiveContacts = "//span[normalize-space()='Active Contacts']";
    public static String labelInactiveContacts = "//span[normalize-space()='Inactive Contacts']";
    public static String labelContactsLoginInToday = "//span[normalize-space()='Contacts Logged In Today']"; // Fix: bị lặp xpath 2 lần
    public static String inputSearchCustomer = "//div[@id='clients_filter']//input[@type='search']";
    public static String firstRowCustomerName = "//table[@id='clients']//tbody/tr[1]/td[3]/a";

    //Locator for New Customer page
    public static String tabCustomerDetails = "//a[@aria-controls='contact_info']";
    public static String tabBillingShipping = "//a[@aria-controls='billing_and_shipping']";
    public static String inputCompany = "//input[@id='company']";
    public static String inputVatNumber = "//input[@id='vat']";
    public static String inputPhone = "//input[@id='phonenumber']";
    public static String inputWebsite = "//input[@id='website']";
    public static String dropdownGroups = "//button[@data-id='groups_in[]']";
    public static String inputSearchGroups = "//button[@data-id='groups_in[]']/following-sibling::div//input[@type='search']";
    public static String buttonAddNewGroup = "//div[@id='contact_info']//a[contains(@onclick,'customer_group')]";
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
    public static String buttonSaveCustomer = "//div[@id='profile-save-section']//button[normalize-space()='Save']";
    public static String alertSuccess = "//span[@class='alert-title' or contains(@class,'alert-success')] | //div[contains(@class,'alert-success')]";

    /** Option trong dropdown bootstrap-select theo text (dùng cho Groups, Currency, Language, Country...) */
    public static String optionDropdown(String text) {
        return "//div[contains(@class,'bootstrap-select') and (contains(@class,'open') or contains(@class,'show'))]//span[normalize-space()='" + text + "']";
    }

    //Locator for Projects page (XPath cách 6-10: axes)
    public static String buttonNewProject = "//a[contains(@href,'projects/project')]/parent::div[contains(@class,'_buttons')]/a[1] | //a[normalize-space()='New Project']";
    public static String headerProjectsSummary = "//span[normalize-space()='Projects Summary']";
    public static String inputSearchProject = "//div[@id='projects_filter']/descendant::input[@type='search']";
    public static String firstRowProjectName = "//table[@id='projects']/descendant::tbody/tr[1]/td[2]/a";

    //Locator for New Project page (XPath cách 6-10: axes)
    public static String tabProject = "//a[@aria-controls='tab_project']";
    public static String tabProjectSettings = "//a[@aria-controls='tab_settings']";
    public static String inputProjectName = "//label[@for='name']/following-sibling::input[@id='name']";
    public static String dropdownCustomer = "//label[@for='clientid']/following-sibling::div/button[@data-id='clientid']";
    public static String inputSearchCustomerInProject = "//button[@data-id='clientid']/following-sibling::div/descendant::input[@type='search']";
    public static String checkboxCalculateProgress = "//label[@for='progress_from_tasks']/preceding-sibling::input[@id='progress_from_tasks']";
    public static String sliderProgress = "//div[@id='project_progress_slider']/child::span";
    public static String dropdownBillingType = "//label[@for='billing_type']/following-sibling::div/button[@data-id='billing_type']";
    public static String dropdownStatus = "//label[@for='status']/following-sibling::div/button[@data-id='status']";
    public static String inputTotalRate = "//label[@for='project_cost']/following-sibling::div/input[@id='project_cost'] | //input[@id='project_cost']";
    public static String inputEstimatedHours = "//label[@for='estimated_hours']/following-sibling::input[@id='estimated_hours']";
    public static String dropdownMembers = "//label[@for='project_members[]']/following-sibling::div/button[@data-id='project_members[]'] | //button[@data-id='project_members[]']";
    public static String inputStartDate = "//label[@for='start_date']/following-sibling::div/input[@id='start_date']";
    public static String inputDeadline = "//label[@for='deadline']/following-sibling::div/input[@id='deadline']";
    public static String inputTags = "//label[@for='tags']/following-sibling::ul//input[contains(@class,'ui-autocomplete-input')] | //ul[contains(@class,'tagit')]//input";
    public static String iframeDescription = "//p[normalize-space()='Description']/following::iframe[1] | //iframe[@id='description_ifr']";
    public static String bodyDescription = "//body[@id='tinymce']";
    public static String checkboxSendEmail = "//label[@for='send_created_email']/preceding-sibling::input[@id='send_created_email']";
    public static String buttonSaveProject = "//form[@id='project_form']/descendant::button[@type='submit'][last()]";
}
