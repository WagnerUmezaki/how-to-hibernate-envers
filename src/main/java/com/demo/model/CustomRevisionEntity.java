package com.demo.model;

import jakarta.persistence.*;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

@Entity
@Table(name="REVINFO")
@RevisionEntity
public class CustomRevisionEntity {

    @Id
    @SequenceGenerator(name = "sq_revinfo_idt", sequenceName = "sq_revinfo_idt", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_revinfo_idt")
    @RevisionNumber
    @Column(name = "rev")
    private long rev;

    @RevisionTimestamp
    @Column(name = "revtstmp")
    private long timestamp;

}
