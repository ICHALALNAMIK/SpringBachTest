package fr.in.bankspringbach.dao;

import fr.in.bankspringbach.entites.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankTransactionRepository extends JpaRepository<BankTransaction,Long> {

}
