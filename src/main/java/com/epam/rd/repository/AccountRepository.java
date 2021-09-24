package com.epam.rd.repository;

import com.epam.rd.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface AccountRepository extends JpaRepository<Account, String> {
    boolean existsByUserName(String userName);

    Account findByUserName(String userName);
}
