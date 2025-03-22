package org.artem.servicemanagement.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"admin", "accountSender"})
@EqualsAndHashCode(exclude = {"admin", "accountSender"})
@Entity
@Table(name = "account")
public class Account implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nickname;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Admin> admin;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<AccountSender> accountSender;
}