package lt.inventiCodingChallange.bankAccountManager.repository;

import lt.inventiCodingChallange.bankAccountManager.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BalanceRepository extends JpaRepository<Balance, Long> { }
