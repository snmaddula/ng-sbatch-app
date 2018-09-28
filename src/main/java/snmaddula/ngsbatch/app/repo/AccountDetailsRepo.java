package snmaddula.ngsbatch.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import snmaddula.ngsbatch.app.model.AccountDetails;

/**
 * 
 * @author snmaddula
 *
 */
@Repository
public interface AccountDetailsRepo extends JpaRepository<AccountDetails, Integer> {

}
