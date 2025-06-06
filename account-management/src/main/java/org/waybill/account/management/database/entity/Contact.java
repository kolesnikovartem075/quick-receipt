package org.waybill.account.management.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@ToString(exclude = "account")
@EqualsAndHashCode(exclude = "account")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account account;

    private String firstName;
    private String lastName;
    private String middleName;
    private String phoneNumber;

    private String postOfficeRef;
    private String cityRef;

    @CreationTimestamp
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    private LocalDateTime dateUpdated;
}
