package org.waybill.account.management.database.repository;


import org.waybill.account.management.database.entity.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserContactProfileRepository extends JpaRepository<UserContact, Long>, JpaSpecificationExecutor<UserContact> {

}