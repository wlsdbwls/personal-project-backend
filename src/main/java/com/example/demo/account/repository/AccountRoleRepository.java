package com.example.demo.account.repository;

import com.example.demo.account.entity.Account;
import com.example.demo.account.entity.AccountRole;
import com.example.demo.account.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {
    Optional<AccountRole> findByBusinessNumber(String businessNumber);
    @Query("select ar.role from AccountRole ar join fetch Role r where ar.account = :account")
    Role findRoleByAccount(@Param("account") Account account);
    @Query("SELECT ar FROM AccountRole ar JOIN FETCH ar.role WHERE ar.account.id = :accountId")
    Optional<AccountRole> findByAccountIdWithRole(@Param("accountId") Long accountId);
    @Transactional
    void deleteAllByAccountId(Long id);
}
