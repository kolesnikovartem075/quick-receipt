package org.waybill.account.management.database.repository;

import org.waybill.account.management.database.entity.AccountContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountContactProfileRepository extends JpaRepository<AccountContact, Long>, JpaSpecificationExecutor<AccountContact> {
}