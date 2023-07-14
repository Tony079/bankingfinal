package com.nkxgen.spring.jdbc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nkxgen.spring.jdbc.Bal.CustomerSetter;
import com.nkxgen.spring.jdbc.Bal.ViewInterface;
import com.nkxgen.spring.jdbc.DaoInterfaces.CustomerDaoInterface;
import com.nkxgen.spring.jdbc.DaoInterfaces.PermissionsDAOInterface;
import com.nkxgen.spring.jdbc.ViewModels.CustomerViewModel;
import com.nkxgen.spring.jdbc.model.Customer;
import com.nkxgen.spring.jdbc.model.CustomerSub;
import com.nkxgen.spring.jdbc.model.Customertrail;
import com.nkxgen.spring.jdbc.model.Permission;

@Controller
public class customercontroller {

	private PermissionsDAOInterface permissionsDAO;

	@Autowired
	private CustomerDaoInterface cd;
	@Autowired
	ViewInterface v;

	public customercontroller(ViewInterface v, CustomerDaoInterface cd, PermissionsDAOInterface permissionsDAO) {
		this.cd = cd;
		this.v = v;
		this.permissionsDAO = permissionsDAO;
	}

	Logger LOGGER = LoggerFactory.getLogger(customercontroller.class);

	@RequestMapping(value = "/customer_data_trail_save", method = RequestMethod.POST)
	public String customerDataSaveToDb(@Validated CustomerViewModel customer, Model model) {
		// Print the new customer ID
		LOGGER.info("New customer ID: {}", customer.getId());

		// Perform service operations on the customer view model to generate the customer trail
		Customertrail cus = CustomerSetter.dotheservice2(customer);

		// Check if the customer trail has an ID
		if (cus.getId() != null) {
			// Update the existing customer trail in the database
			LOGGER.info("Updating existing customer trail in the database.");
			cd.saveCustomer(cus);
		} else {
			// Save the new customer trail to the database
			LOGGER.info("Saving new customer trail to the database.");
			cd.saveCustomer(cus);
		}

		// Return the view name "Account_new_application_form" to render the page
		return "account-new-application-form";
	}

	// =================================================
	// real saving
	@RequestMapping(value = "/saveToCustomerDatabase", method = RequestMethod.POST)
	public String saveToCustomerDatabase(@RequestParam("CustomerId") Long customerId) {
		// Convert the customerId to a long value
		long customerid = customerId;

		// Print the customerid value
		LOGGER.info("Print the custoemr id value: {}", customerid);

		// Retrieve the Customertrail object from the database using the customerid
		LOGGER.info("Retrieving Customertrail object with ID: {}", customerid);
		Customertrail cus = cd.getCustomerById(customerid);

		// Print the ID of the retrieved Customertrail object
		LOGGER.info("Retrieved Customertrail object with ID: {}", cus.getId());

		// Perform service operations on the Customertrail object to generate the Customer object
		LOGGER.info("Performing service operations on the Customertrail object to generate the Customer object.");
		Customer customer = CustomerSetter.dotheservice(cus);

		// Save the Customer object to the database
		LOGGER.info("Saving Customer object to the database.");
		cd.saveCustomertoDb(customer);

		// Change the return to the view name "Account_new_application_form" to render the page
		return "account-new-application-form";
	}

	// =========================================================
	@RequestMapping(value = "/customers", method = RequestMethod.POST)
	public String getAllCustomers(Model model, HttpServletRequest request, HttpServletResponse response) {
		// Retrieve a list of all customers
		LOGGER.info("Retrieving a list of all customers.");
		List<Customer> customerList = v.getAllCustomers();
		HttpSession session = request.getSession();
		// Get the username attribute from the session
		String username = (String) session.getAttribute("username");
		// Add the customerList as an attribute to the model
		model.addAttribute("customerList", customerList);

		Permission p = permissionsDAO.getPermissions(Long.parseLong(username));

		model.addAttribute("permissions", p);
		// Return the view name "customer_edit_details_form" to render the page
		return "customer-edit-details-form";
	}

	// ===============================================================
	// editing the existing data
	// =========================================================
	@RequestMapping(value = "/customerDataUpdation", method = RequestMethod.POST)
	public String CustomerDataUpdation(Customertrail updatedCustomer) {
		// Update the customer data based on the provided updatedCustomer object
		LOGGER.info("Updating customer data based on the provided updatedCustomer object.");
		cd.updateCustomerDataById(updatedCustomer);

		// Return the view name "customer_edit_details_form" to render the page
		return "customer-edit-details-form";
	}

	// =========================================================================================
	@RequestMapping(value = "/saveToCustomersubDatabase", method = RequestMethod.POST)
	public String saveToCustomersubDatabase(@Validated CustomerSub CustomerSub, Model model) {
		// Get the real customer by the provided CustomerId from the CustomerSub object
		LOGGER.info("Getting the real customer by the provided CustomerId: {}", CustomerSub.getCustomerId());
		Customertrail customer2 = cd.getRealCustomerById(CustomerSub.getCustomerId());

		// Change the properties of customer2 based on the values from the CustomerSub object
		LOGGER.info("Changing the properties of customer2 based on the values from the CustomerSub object.");
		cd.changethese(customer2, CustomerSub);

		// Return the view name "Any_Type_account_info" to render the page
		return "any-type-account-info";
	}

}