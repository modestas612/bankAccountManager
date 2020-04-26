package lt.inventiCodingChallange.bankAccountManager.model;

import java.util.Date;
import javax.persistence.*;

import com.opencsv.bean.CsvBindByName;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
public class BankStatement {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;
    private Date operationDate;
    private String beneficiary;
    private String comment;
    private Double amount;
    private String currency;

    @CreationTimestamp
    private Date importedAt;
    
}
