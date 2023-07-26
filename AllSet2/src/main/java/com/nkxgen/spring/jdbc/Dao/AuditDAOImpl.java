package com.nkxgen.spring.jdbc.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nkxgen.spring.jdbc.DaoInterfaces.AuditLogDAO;
import com.nkxgen.spring.jdbc.Exception.NoRecordFoundException;
import com.nkxgen.spring.jdbc.model.AuditLogs;

@Repository
public class AuditDAOImpl implements AuditLogDAO {
	@PersistenceContext
	private EntityManager entityManager;
	Logger LOGGER = LoggerFactory.getLogger(AuditDAOImpl.class);

	@Transactional
	public void saveAudit(AuditLogs audits) {
		LOGGER.info("Saving audit: {}", audits);

		// Persist the audit log object in the database
		entityManager.persist(audits);
	}

	@Transactional
	public List<AuditLogs> getAllAuditLogs() {
		LOGGER.info("Retrieving all audit logs");

		// Create a typed query to retrieve all audit logs from the database, ordered by ID in descending order
		TypedQuery<AuditLogs> query = entityManager.createQuery("SELECT a FROM AuditLogs a ORDER BY a.id DESC",
				AuditLogs.class);

		// Execute the query and return the list of audit logs
		List<AuditLogs> auditLogsList = query.getResultList();

		LOGGER.info("Retrieved {} audit logs", auditLogsList.size());
		return auditLogsList;
	}

	@Transactional
	public AuditLogs lastLoggedIn(String userid) {
		LOGGER.info("Retrieving all last logged in logs");

		// Create a typed query to retrieve all audit logs from the database, ordered by ID in descending order
		TypedQuery<AuditLogs> query = entityManager.createQuery(
				"SELECT a FROM AuditLogs a WHERE a.username = :userid and a.event = :events ORDER BY a.id DESC ",
				AuditLogs.class);
		query.setParameter("userid", userid);
		query.setParameter("events", "Logged In");

		query.setMaxResults(1); // Set the maximum number of results to 1

		try {
			// Execute the query and return the single audit log (or null if no result is found)
			return query.getSingleResult();
		} catch (NoResultException ex) {
			// Handle the situation when there is no record fetched (e.g., log a message, return a default value, etc.)
			LOGGER.info("No last logged in log found for userid: {}", userid);
			throw new NoRecordFoundException("No record found for userid: " + userid);
		}
	}

}
