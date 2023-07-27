package com.nkxgen.spring.jdbc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nkxgen.spring.jdbc.DaoInterfaces.BankUserInterface;
import com.nkxgen.spring.jdbc.DaoInterfaces.PermissionsDAOInterface;
import com.nkxgen.spring.jdbc.model.BankUser;
import com.nkxgen.spring.jdbc.model.Permission;

@Controller

public class PermissionController {

	Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);

	private final PermissionsDAOInterface permissionsDAO;
	private BankUserInterface bankUserService;
	private BankUser bankUser;

	@Autowired
	public PermissionController(PermissionsDAOInterface permissionsDAO, BankUserInterface bankUserService,
			BankUser bankUser) {
		this.permissionsDAO = permissionsDAO;
		this.bankUserService = bankUserService;
		this.bankUser = bankUser;
	}

	@RequestMapping(value = "/permission", method = RequestMethod.GET)
	public String permission(Model model, HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Handling GET request for /permission");
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		bankUser = permissionsDAO.getUserById(Long.parseLong(username));
		Permission p = permissionsDAO.getPermissions(bankUser.getBusr_desg());
		BankUser b = bankUserService.getBankUserById(Long.parseLong(username));

		model.addAttribute("permissions", p);
		return "finalpermissions";
		// } else {
		// return "not-permitted";
		// }
	}

	@RequestMapping(value = "/savePermissionData", method = RequestMethod.POST)
	public ResponseEntity<String> savePermissionData(Permission permissionData) {
		try {
			// Save the permissionData object to the database using the repository
			permissionsDAO.updatePermissions(permissionData);
			System.out.println(permissionData);
			return ResponseEntity.ok("Data saved successfully!");
		} catch (Exception e) {
			return ResponseEntity.status(500).body("Error saving data: " + e.getMessage());
		}
	}

	@RequestMapping(value = "/allpermissionurl", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> allpermission(Permission permissions) {
		LOGGER.info("Handling POST request for /allpermissionurl");
		System.out.println("alllll" + permissions);
		permissionsDAO.allUpdatePermissions(permissions);
		return ResponseEntity.ok("Customer data updated successfully");
	}

	@PostMapping("/getPermissions")
	public ResponseEntity<Permission> getPermissions(@RequestParam("role") String role) {
		try {
			// Fetch the permissions for the selected role
			Permission permissions = permissionsDAO.getPermissions(role);

			if (permissions != null) {
				// Return the permissions as JSON response
				return ResponseEntity.ok(permissions);
			} else {
				// Return a 404 Not Found response if permissions are not found for the given role
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			// Handle any errors that occur during data retrieval
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}