package org.artem.servicemanagement.database.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.artem.servicemanagement.dto.AdminRole;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "admin")
public class Admin implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long externalUserId;

    @ManyToOne
    private Account account;

    @Enumerated(EnumType.STRING)
    private AdminRole role;
}