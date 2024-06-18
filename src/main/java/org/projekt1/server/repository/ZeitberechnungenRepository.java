import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ZeitberechnungenRepository extends JpaRepository<Zeitberechnungen, Integer> {
    Optional<Zeitberechnungen> findByMitarbeiterIdAndDatum(Integer mitarbeiterId, LocalDate datum);
}
