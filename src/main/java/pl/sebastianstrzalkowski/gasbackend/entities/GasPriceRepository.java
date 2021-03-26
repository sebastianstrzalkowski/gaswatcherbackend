package pl.sebastianstrzalkowski.gasbackend.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
//import pl.sebastianstrzalkowski.gasbackend.models.GasPrice;

@Repository
public interface GasPriceRepository extends JpaRepository<GasPriceDao, Long> {


}
