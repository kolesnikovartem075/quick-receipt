package org.artem.servicemanagement.database.repository;

import org.artem.servicemanagement.database.entity.ServiceSender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceSenderRepository extends JpaRepository<ServiceSender, Long>, JpaSpecificationExecutor<ServiceSender> {

}