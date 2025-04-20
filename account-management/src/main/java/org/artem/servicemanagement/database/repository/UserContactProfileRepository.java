package org.artem.servicemanagement.database.repository;


import org.artem.servicemanagement.database.entity.UserContactProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserContactProfileRepository extends JpaRepository<UserContactProfile, Long>, JpaSpecificationExecutor<UserContactProfile> {

}