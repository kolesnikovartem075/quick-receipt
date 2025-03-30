package org.artem.servicemanagement.mapper;

import lombok.RequiredArgsConstructor;
import org.artem.servicemanagement.database.entity.Account;
import org.artem.servicemanagement.database.entity.Admin;
import org.artem.servicemanagement.database.repository.AccountRepository;
import org.artem.servicemanagement.dto.AdminCreateEditDto;
import org.artem.servicemanagement.dto.AdminRole;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminCreateEditMapper implements Mapper<AdminCreateEditDto, Admin> {


    AccountRepository accountRepository;

    @Override
    public Admin map(AdminCreateEditDto fromObject, Admin toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Admin map(AdminCreateEditDto object) {
        Admin admin = new Admin();
        copy(object, admin);

        return admin;
    }

    private void copy(AdminCreateEditDto object, Admin admin) {
        var account = getAccount(object);
        admin.setAccount(account);
        admin.setExternalUserId(object.getExternalUserId());
        admin.setRole(AdminRole.valueOf(object.getRole()));
    }

    private Account getAccount(AdminCreateEditDto object) {
        return accountRepository.findById(object.getAccountId()).orElseThrow();
    }
}