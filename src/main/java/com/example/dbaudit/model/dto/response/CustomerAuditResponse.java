package com.example.dbaudit.model.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.envers.RevisionType;

import java.util.Date;
import java.util.Set;

@Builder
@Getter
public class CustomerAuditResponse {
    private Long id;
    private String name;
    private String email;
    private String company;
    private RevisionType revisionType;
    private Object modifiedColumns;
    private String username;
    private Date revisionDate;
}
