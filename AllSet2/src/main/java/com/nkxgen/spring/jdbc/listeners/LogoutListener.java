package com.nkxgen.spring.jdbc.listeners;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.nkxgen.spring.jdbc.DaoInterfaces.AuditLogDAO;
import com.nkxgen.spring.jdbc.events.LogoutEvent;
import com.nkxgen.spring.jdbc.model.AuditLogs;

@Component
public class LogoutListener {
	@Autowired
	AuditLogDAO auditLogDAO;

	@EventListener
	public void logout(LogoutEvent event) {
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        String currentTimestampstr = formatTimestamp(currentTimestamp);
        AuditLogs auditLog = new AuditLogs(event.getEvent(), currentTimestampstr, event.getUsername());
        auditLogDAO.saveAudit(auditLog);
	}
	 private String formatTimestamp(Timestamp timestamp) {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	        return dateFormat.format(timestamp);

	    }
	
}
