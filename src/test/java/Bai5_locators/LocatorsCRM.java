package Bai5_locators;

public class LocatorsCRM {
    public static String url = "https://crm.anhtester.com/admin/authentication";

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
    public static String buttonNewCustomer = "//a[normalize-space()='New Customer'";
    public static String buttonImportCustomers = "//a[normalize-space()='Import Customers']";
    public static String buttonContacts = "//a[normalize-space()='Contacts' and contains(@href,'all_contacts')]";
    public static String headerCustomersPage = "//span[normalize-space()='Customers Summary']";
    public static String labelTotalCustomers = "//span[normalize-space()='Total Customers']";
    public static String labelActiveCustomers = "//span[normalize-space()='Active Customers']";
    public static String labelInactiveCustomers = "//span[normalize-space()='Inactive Customers']";
    public static String labelActiveContacts = "//span[normalize-space()='Active Contacts']";
    public static String labelInactiveContacts = "//span[normalize-space()='Inactive Contacts']";
    public static String labelContactsLoginInToday = "//span[normalize-space()='Contacts Logged In Today'] //span[normalize-space()='Contacts Logged In Today']";
    public static String inputSearchCustomer = "//div[@id='clients_filter']//input[@type='search']";

//Locator for New Customer page


}


