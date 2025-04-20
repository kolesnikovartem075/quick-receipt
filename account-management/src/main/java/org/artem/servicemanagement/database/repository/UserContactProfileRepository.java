package org.artem.servicemanagement.database.repository;


import org.artem.servicemanagement.database.entity.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserContactProfileRepository extends JpaRepository<UserContact, Long>, JpaSpecificationExecutor<UserContact> {

}