package fr.in.bankspringbach;

import fr.in.bankspringbach.entities.BankTransaction;
import lombok.Getter;
import org.springframework.batch.item.ItemProcessor;

//@Component
public class BankTransactionAnalyticsItemProcessor implements ItemProcessor<BankTransaction,BankTransaction> {
   @Getter
   private double totalDebit;
    @Getter
    private double totalCredit;

            @Override
            public BankTransaction process(BankTransaction bankTransaction) throws Exception {
            if(bankTransaction.getTransactionType().equals("D")) totalDebit+=bankTransaction.getAmount();
            else if(bankTransaction.getTransactionType().equals("C")) totalCredit+=bankTransaction.getAmount();
                return bankTransaction;
            }
}
