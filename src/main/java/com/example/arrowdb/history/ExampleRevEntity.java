package com.example.arrowdb.history;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@RevisionEntity(ExampleListener.class)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "REVINFO", schema = "history")
public class ExampleRevEntity {

    @Id
    @RevisionNumber
    @GeneratedValue(generator = "CustomerAuditRevisionSeq")
    @SequenceGenerator(name = "CustomerAuditRevisionSeq",
            sequenceName = "customer_audit_revision_seq",
            schema = "history", allocationSize = 1)
    private Integer id;

    @Column(name = "time_stamp")
    @RevisionTimestamp
    private long timeStamp;

    @Column(name = "user_name")
    @LastModifiedBy
    private String userName;

    @Column(name = "local_date_time_modified")
    private LocalDateTime localDateTimeModified = LocalDateTime.now();

}