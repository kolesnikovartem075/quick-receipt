package org.waybill.account.management.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"contactProfiles"})
@EqualsAndHashCode(exclude = {"contactProfiles"})
@Entity
@Table
public class Account implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @OneToMany(mappedBy = "account")
    private List<AccountContact> contactProfiles;

    @OneToMany(mappedBy = "account")
    private List<User> users;
}