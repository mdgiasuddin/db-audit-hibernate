package com.example.dbaudit.model.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@RevisionEntity(AuditRevisionListener.class)
@Getter
@Setter
public class AuditRevision extends DefaultRevisionEntity {
    private String username;
}
