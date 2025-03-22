package org.artem.servicemanagement.database.repository;

import org.artem.servicemanagement.database.entity.AccountSender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountSenderRepository extends JpaRepository<AccountSender, Long>, JpaSpecificationExecutor<AccountSender> {

}