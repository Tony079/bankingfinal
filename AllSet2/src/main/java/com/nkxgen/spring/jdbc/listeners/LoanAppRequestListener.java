package com.nkxgen.spring.jdbc.listeners;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import com.nkxgen.spring.jdbc.DaoInterfaces.AuditLogDAO;
import com.nkxgen.spring.jdbc.events.LoanAppRequestEvent;
import com.nkxgen.spring.jdbc.model.AuditLogs;

@Controller
public class LoanAppRequestListener {

	@Autowired
	private AuditLogDAO auditLogDAO;

	@EventListener
	public void onEvent(LoanAppRequestEvent event) {
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
