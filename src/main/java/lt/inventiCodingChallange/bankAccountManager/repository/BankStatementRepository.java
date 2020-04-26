package lt.inventiCodingChallange.bankAccountManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import lt.inventiCodingChallange.bankAccountManager.model.BankStatement;

@RepositoryRestResource
public interface BankStatementRepository extends JpaRepository<BankStatement, Long> {

}
