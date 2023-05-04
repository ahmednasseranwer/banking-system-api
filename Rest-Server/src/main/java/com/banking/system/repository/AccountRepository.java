package com.banking.system.repository;

import com.banking.system.entity.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public
interface AccountRepository extends PagingAndSortingRepository<Account, Long> {

}
