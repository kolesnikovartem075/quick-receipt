package org.artem.servicemanagement.database.repository;

import org.artem.servicemanagement.database.entity.Account;
import org.artem.servicemanagement.database.entity.AccountContactProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountContactProfileRepository extends JpaRepository<AccountContactProfile, Long>, JpaSpecificationExecutor<Account> {
}