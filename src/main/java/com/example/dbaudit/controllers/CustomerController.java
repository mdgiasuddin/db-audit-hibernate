package com.example.dbaudit.controllers;

import com.example.dbaudit.model.dto.request.CustomerRequest;
import com.example.dbaudit.model.dto.response.CustomerAuditResponse;
import com.example.dbaudit.model.entity.AuditRevision;
import com.example.dbaudit.model.entity.Customer;
import com.example.dbaudit.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerRepository customerRepository;
    private final AuditReader auditReader;

    @PostMapping
    public String createNewCustomer(@Validated @RequestBody CustomerRequest request) {
        Customer customer = new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setCompany(request.company());
        customerRepository.save(customer);

        return "Customer created successfully!";
    }

    @PutMapping
    public String updateCustomer(@Validated @RequestBody CustomerRequest request) {
        Customer customer = customerRepository.findCustomerById(request.id()).orElseThrow();

        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setCompany(request.company());
        customerRepository.save(customer);

        return "Customer updated successfully!";
    }

    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable("id") Long id) {
        customerRepository.deleteById(id);

        return "Customer deleted successfully!";
    }

    @GetMapping("/audit/{id}")
    public List<CustomerAuditResponse> getRevisions(@PathVariable("id") Long id) {
        AuditQuery auditQuery = auditReader.createQuery()
            .forRevisionsOfEntityWithChanges(Customer.class, true);

        auditQuery.add(AuditEntity.id().eq(id));
        List histories = auditQuery.getResultList();

        List<CustomerAuditResponse> responses = new ArrayList<>();
        for (Object item : histories) {
            Customer customer = (Customer) ((Object[]) item)[0];
            AuditRevision auditRevision = (AuditRevision) ((Object[]) item)[1];
            RevisionType revisionType = (RevisionType) ((Object[]) item)[2];
            Object modifiedColumns = ((Object[]) item)[3];

            responses.add(
                CustomerAuditResponse
                    .builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .email(customer.getEmail())
                    .company(customer.getCompany())
                    .revisionType(revisionType)
                    .modifiedColumns(modifiedColumns)
                    .username(auditRevision.getUsername())
                    .revisionDate(auditRevision.getRevisionDate())
                    .build()
            );

        }

        return responses;
    }
}
