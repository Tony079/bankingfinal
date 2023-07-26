package com.nkxgen.spring.jdbc.DaoInterfaces;

import com.nkxgen.spring.jdbc.model.BankUser;
import com.nkxgen.spring.jdbc.model.Permission;

public interface PermissionsDAOInterface {

	void allUpdatePermissions(Permission permissions);

	Permission getPermissions(String role);

	BankUser getUserById(long id);

	void updatePermissions(Permission permissionData);
}
