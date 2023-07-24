package ttw.springbe.encryptionindatabase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ttw.springbe.encryptionindatabase.entities.TableA;

@Repository
public interface TableARepository extends JpaRepository<TableA, Integer> {
}
