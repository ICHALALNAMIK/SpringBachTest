package fr.in.bankspringbach.entites;


import fr.in.bankspringbach.dao.BankTransactionRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
@Component
public class BankTransactionItemWriter implements ItemWriter<BankTransaction> {
    @Autowired
private BankTransactionRepository bankTransactionRepository;
    @Override
    public void write(List<? extends BankTransaction> list) throws Exception {
  bankTransactionRepository.saveAll(list);
    }
}
