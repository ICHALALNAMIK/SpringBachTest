package fr.in.bankspringbach.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BankTransaction {
    @Id
    private Long id;
    private  long accountID;
    private Date transactionDate;
    private String strTransactionDate;
    private String transactionType;
    private double amount;
}
