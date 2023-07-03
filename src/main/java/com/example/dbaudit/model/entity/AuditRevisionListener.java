package com.example.dbaudit.model.entity;

import org.hibernate.envers.RevisionListener;

public class AuditRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevision auditRevision = (AuditRevision) revisionEntity;

        auditRevision.setUsername("Giash Uddin");
    }
}
