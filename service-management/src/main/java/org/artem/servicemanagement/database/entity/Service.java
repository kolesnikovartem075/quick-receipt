package org.artem.servicemanagement.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"admin", "serviceSender"})
@EqualsAndHashCode(exclude = {"admin", "serviceSender"})
@Entity
@Table(name = "service")
public class Service implements BaseEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String botNickname;

    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private List<Admin> admin;

    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private List<ServiceSender> serviceSender;
}