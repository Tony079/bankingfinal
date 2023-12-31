package com.nkxgen.spring.jdbc.Dao;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.nkxgen.spring.jdbc.DaoInterfaces.PermissionsDAOInterface;
import com.nkxgen.spring.jdbc.model.BankUser;
import com.nkxgen.spring.jdbc.model.Permission;

@Repository
public class PermissionsDAO implements PermissionsDAOInterface {

	@PersistenceContext
	private EntityManager entityManager;

	Logger LOGGER = LoggerFactory.getLogger(PermissionsDAO.class);

	@Transactional
	public void allUpdatePermissions(Permission permissions) {
		ArrayList<Permission> ar = (ArrayList<Permission>) entityManager
				.createQuery("select ps from Permission ps where ps.role=:x").setParameter("x", permissions.getRole())
				.getResultList();

		System.out.println("in dao" + permissions.getRole());
		System.out.println(permissions.toString());

		LOGGER.info("Updating permissions for role: {}", permissions.getRole());
		LOGGER.info("Found {} permissions for role: {}", ar.size(), permissions.getRole());

		for (Permission x : ar) {
			System.out.println(x.toString());
			x.setAccprocessing(permissions.isAccprocessing());
			x.setAccountactive(permissions.isAccountactive());
			x.setAccountedit(permissions.isAccountedit());
			x.setAccountdel(permissions.isAccountdel());
			x.setAppforms(permissions.isAppforms());
			x.setCusedit(permissions.isCusedit());
			x.setSecurity(permissions.isSecurity());
			x.setTransactions(permissions.isTransactions());
			x.setUsers(permissions.isUsers());
		}
	}

	public Permission getPermissions(String role) {
		String sql = "SELECT * FROM permissions WHERE role = :role";
		Permission permissions = (Permission) entityManager.createNativeQuery(sql, Permission.class)
				.setParameter("role", role).getSingleResult();

		LOGGER.info("Retrieving permissions for role: {}", role);
		return permissions;
	}
	
	@Transactional
	public void updatePermissions(Permission permissionData) {
		entityManager.merge(permissionData);
	}

	public BankUser getUserById(long id) {
		return entityManager.find(BankUser.class, id);
	}

}