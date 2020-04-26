package lt.inventiCodingChallange.bankAccountManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import lt.inventiCodingChallange.bankAccountManager.model.BankStatement;

import java.util.List;

@RepositoryRestResource
public interface BankStatementRepository extends JpaRepository<BankStatement, Long> {

    List<BankStatement> findAllByAccountNumber(String accountNumber);
}
