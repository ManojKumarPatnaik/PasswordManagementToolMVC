package com.epam.rd.repository;
import com.epam.rd.entity.Account;
import com.epam.rd.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    Group findByNameAndAccount(String name, Account account);
    List<Group> findByAccount(Account account);
}
