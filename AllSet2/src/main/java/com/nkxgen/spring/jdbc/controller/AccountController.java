package com.nkxgen.spring.jdbc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nkxgen.spring.jdbc.Bal.ViewInterface;
import com.nkxgen.spring.jdbc.DaoInterfaces.AccountApplicationDaoInterface;
import com.nkxgen.spring.jdbc.DaoInterfaces.CustomerDaoInterface;
import com.nkxgen.spring.jdbc.DaoInterfaces.PermissionsDAOInterface;
import com.nkxgen.spring.jdbc.Exception.AccountNotFound;
import com.nkxgen.spring.jdbc.Exception.ApplicationNotFound;
import com.nkxgen.spring.jdbc.InputModels.AccountApplicationInput;
import com.nkxgen.spring.jdbc.InputModels.AccountDocumentInput;
import com.nkxgen.spring.jdbc.InputModels.AccountInput;
import com.nkxgen.spring.jdbc.ViewModels.AccountApplicationViewModel;
import com.nkxgen.spring.jdbc.ViewModels.AccountTypeView;
import com.nkxgen.spring.jdbc.ViewModels.AccountViewModel;
import com.nkxgen.spring.jdbc.events.AccountAppApprovalEvent;
import com.nkxgen.spring.jdbc.events.AccountAppRequestEvent;
import com.nkxgen.spring.jdbc.model.Account;
import com.nkxgen.spring.jdbc.model.AccountApplication;
import com.nkxgen.spring.jdbc.model.Accountdocument;
import com.nkxgen.spring.jdbc.model.BankUser;
import com.nkxgen.spring.jdbc.model.Customertrail;
import com.nkxgen.spring.jdbc.model.Permission;

@Controller
// The @Controller annotation indicates that this class is a controller in the Spring MVC framework.
public class AccountController {
	Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	private final AccountApplicationDaoInterface ac;
	private final ViewInterface v;
	private final PermissionsDAOInterface permissionsDAO;
	private final CustomerDaoInterface cd;
	private BankUser bankUser;

	@Autowired
	public AccountController(AccountApplicationDaoInterface ac, ViewInterface v, PermissionsDAOInterface permissionsDAO,
			CustomerDaoInterface cd, BankUser bankUser) {
		this.ac = ac;
		this.v = v;
		this.permissionsDAO = permissionsDAO;
		this.cd = cd;
		this.bankUser = bankUser;
	}

	// The @RequestMapping annotation maps the /New_account_application URL to the getAccountApplicationByType method,
	// which accepts a Types object and a Model object as parameters
	@RequestMapping(value = "/newAccountApplication", method = RequestMethod.POST)
	public String getAccountApplicationByType(@RequestParam("Typevalue") String accountType, Model model,
			HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Getting account application by type: {}", accountType);

		String value = accountType; // get the account type value

		List<AccountApplicationViewModel> list1 = v.getAccountsappByType(value);

		List<AccountApplicationViewModel> list = new ArrayList<>();
		HttpSession session = request.getSession();
		// Get the username attribute from the session
		String username = (String) session.getAttribute("username");
		bankUser = permissionsDAO.getUserById(Long.parseLong(username));
		Permission p = permissionsDAO.getPermissions(bankUser.getBusr_desg());

		// Check if the list is not empty before accessing the first object
		// if (p.isApplication()) {
		for (AccountApplicationViewModel l : list1) {
			if (l.getProcessedBy() == Integer.parseInt(username)) {
				list.add(l);
			}
		}
		model.addAttribute("listOfAccountApplications", list);
		return "new-account-application";
		// } else {
		// return "not-permitted";
		// }
	}

	@RequestMapping(value = "/anyTypeAccountInfo", method = RequestMethod.POST)
	public String viewAccounts(@RequestParam("Typevalue") String accountType, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.info("Viewing accounts for account type: {}", accountType);

		// Retrieve the value from the validated 'Types' object
		String value = accountType;

		// Print the retrieved value to the console
		LOGGER.info("Retrieved account type: {}", value);

		// Retrieve a list of AccountViewModel objects based on the value
		List<AccountViewModel> list1 = v.getAccountsByType(value);
		HttpSession session = request.getSession();

		// Get the username attribute from the session
		String username = (String) session.getAttribute("username");
		bankUser = permissionsDAO.getUserById(Long.parseLong(username));
		Permission p = permissionsDAO.getPermissions(bankUser.getBusr_desg());
		// Create an empty list to store Customertrail objects
		List<Customertrail> list2 = new ArrayList<>();

		// Iterate through each AccountViewModel object in the list
		for (AccountViewModel account : list1) {
			// Retrieve the Customertrail object based on the customerId of the account
			Customertrail customer = cd.getRealCustomerById(account.getCustomerId());

			// Add the retrieved customer to the list of Customertrail objects
			list2.add(customer);
		}
		for (AccountViewModel a : list1) {
			LOGGER.info("Account balance: {}", a.getBalance());
		}
		// if (p.isAccounts()) {
		// Add the list of AccountViewModel objects and the list of Customertrail objects to the model
		model.addAttribute("list_of_account", list1);
		model.addAttribute("list_of_customer", list2);
		model.addAttribute("permissions", p);

		// Return the view name "Any_Type_account_info" to render the page
		return "any-type-account-info";
		// } else {
		// return "not-permitted";
		// }
	}

	@RequestMapping("/result")
	public String ne(Model model) {
		LOGGER.info("Rendering new-account-application view");
		return "new-account-application";
	}

	@RequestMapping("/accountNewApplicationForm")
	public String accountNewApplicationForm(Model model) {
		LOGGER.info("Rendering account-new-application-form view");
		List<AccountTypeView> list = v.set11();

		// Add the list of AccountTypeView objects to the model attribute "accountTypes"
		model.addAttribute("accountTyped", list);
		return "account-new-application-form";
	}

	@RequestMapping("/anyTypeAccountInfo")
	public String anyTypeAccountInfo(Model model) {
		LOGGER.info("Rendering any-type-account-info view");
		return "any-type-account-info";
	}

	// ===========================================================================================

	@RequestMapping(value = "/accountApplicationSave", method = RequestMethod.POST)
	public String accountApplicationSaveToDb(@Validated AccountApplicationInput accountApplication,
			HttpServletRequest request, Model model) {
		try {
			LOGGER.info("Saving account application to the Database");

			// Create a new instance of AccountApplication
			AccountApplication account = new AccountApplication();

			// Set the input model values of the account using the accountApplication object
			account.setInputModelValues(accountApplication);

			// Save the account to the database using the accountApplicationDaoInterface
			ac.save(account);

			// Retrieve the HttpSession object from the request
			HttpSession session = request.getSession();

			// Retrieve the username attribute from the session
			String username = (String) session.getAttribute("username");

			// Publish an AccountAppRequestEvent with the event message and username
			applicationEventPublisher.publishEvent(new AccountAppRequestEvent("New Application Form Filled", username));

			// Return the view name "account-new-application-form" to render the page
			return "account-new-application-form";
		} catch (Exception e) {
			LOGGER.error("An error occurred while saving the account application.", e);
			model.addAttribute("error", "An error occurred while saving the account application.");
			return "error-view";
		}
	}

	// ===========================================================================================

	// ===================================================================================================
	@RequestMapping(value = "/saveToAccountDatabase", method = RequestMethod.POST)
	public String saveToAccountDatabase(@Validated AccountInput account, Model model, HttpServletRequest request) {
		try {
			LOGGER.info("Saving account to the database");

			// Create a new instance of Account
			Account a = new Account();

			// Set the input model values of the account using the accountInput object
			a.setInputModelValues(account);

			// Save the account to the database using the accountApplicationDaoInterface
			ac.saveAccount(a);

			// Retrieve the HttpSession object from the request
			HttpSession session = request.getSession();

			// Retrieve the username attribute from the session
			String username = (String) session.getAttribute("username");

			// Publish an AccountAppApprovalEvent with the event message and username
			applicationEventPublisher
					.publishEvent(new AccountAppApprovalEvent("Account Application Approved", username));

			AccountApplication accountap = ac.getAccountApplicationById(account.getApplicationId());
			LOGGER.info("The status of the application is: {}", accountap.getStatus());
			accountap.setStatus("approved");
			LOGGER.info("The status of the application is now: {}", accountap.getStatus());
			ac.savetheAccountapp(accountap);

			// Change the return to the view name "account-new-application-form" to render the page
			return "account-new-application-form";
		} catch (Exception e) {
			LOGGER.error("An error occurred while saving the account to the database.", e);
			model.addAttribute("error", "An error occurred while saving the account to the database.");
			return "error-view";
		}
	}

	@RequestMapping(value = "/saveToAccountDocumentsDatabase", method = RequestMethod.POST)
	public String saveToAccountDocumentsDatabase(@Validated AccountDocumentInput accountdocument, Model model) {
		try {
			LOGGER.info("Saving account document to the database");

			// Create a new instance of Accountdocument
			Accountdocument ad = new Accountdocument();

			// Set the input model values of the accountdocument using the accountdocumentInput object
			ad.setInputModelValues(accountdocument);

			// Save the accountdocument to the database using the accountApplicationDaoInterface
			ac.saveAccountdocument(ad);

			// Change the return to the view name "account-new-application-form" to render the page
			return "account-new-application-form";
		} catch (Exception e) {
			LOGGER.error("An error occurred while saving the account document to the database.", e);
			model.addAttribute("error", "An error occurred while saving the account document to the database.");
			return "error-view";
		}
	}

	// // ======================================================================================================

	@RequestMapping(value = "/chnageTheStatusOfAccount", method = RequestMethod.POST)
	public String chnageTheStatusOfAccount(@RequestParam("num") Long num) {
		LOGGER.info("Changing status of account with number: {} to 'Inactive'", num);
		Account account = ac.getAccountById(num);
		account.setAccountStatus("Inactive");
		ac.mergeAccount(account);
		return "any-type-account-info";
	}

	@RequestMapping(value = "/chnageTheStatusOfAccountToActive", method = RequestMethod.POST)
	public String chnageTheStatusOfAccountToActive(@RequestParam("num") Long num) {
		LOGGER.info("Changing status of account with number: {} to 'Active'", num);
		Account account = ac.getAccountById(num);
		account.setAccountStatus("Active");
		ac.mergeAccount(account);
		return "any-type-account-info";
	}

	@RequestMapping(value = "/getAccountById", method = RequestMethod.POST)
	public String getAccountById(@RequestParam("Data") int accountId, Model model, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			AccountViewModel account = v.getAccountById(accountId);
			List<AccountViewModel> list1 = new ArrayList<AccountViewModel>();
			// Retrieve the Customertrail object based on the customerId of the account
			Customertrail customer = cd.getRealCustomerById(account.getCustomerId());
			List<Customertrail> list2 = new ArrayList<Customertrail>();
			// Add the retrieved customer to the list of Customertrail objects
			list1.add(account);
			list2.add(customer);
			HttpSession session = request.getSession();

			// Get the username attribute from the session
			String username = (String) session.getAttribute("username");
			bankUser = permissionsDAO.getUserById(Long.parseLong(username));
			Permission p = permissionsDAO.getPermissions(bankUser.getBusr_desg());
			LOGGER.info("Account found");
			model.addAttribute("list_of_account", list1);
			model.addAttribute("list_of_customer", list2);
			model.addAttribute("permissions", p);
			return "any-type-account-info";
		} catch (AccountNotFound e) {
			LOGGER.warn(e.getMessage());
			return "AccountNotFound";
		}
	}

	@RequestMapping(value = "/getApplicationById", method = RequestMethod.POST)
	public String getAccountApplicationById(@RequestParam("Data") int accountId, Model model) {
		try {
			AccountApplicationViewModel application = v.getAccountsappById(accountId);
			List<AccountApplicationViewModel> list1 = new ArrayList<AccountApplicationViewModel>();
			list1.add(application);
			model.addAttribute("listOfAccountApplications", list1);
			LOGGER.info("Account application found for ID: {}", accountId);
			return "new-account-application";
		} catch (ApplicationNotFound e) {
			LOGGER.warn("Failed to retrieve account application for ID: {}. Application not found.", accountId);
			return "ApplicationNotFound";
		}
	}

}
