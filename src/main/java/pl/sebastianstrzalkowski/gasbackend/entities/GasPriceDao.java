package pl.sebastianstrzalkowski.gasbackend.entities;

import lombok.Data;
import pl.sebastianstrzalkowski.gasbackend.models.GasPrice;
import pl.sebastianstrzalkowski.gasbackend.models.Result;

import javax.persistence.*;
import java.util.Date;
import java.util.Locale;

@Entity
@Data
@Table(name = "gas_price")
public class GasPriceDao {

    public GasPriceDao(Result gasPrice){
        this.date = new Date(System.currentTimeMillis());
        this.fastGas = Integer.parseInt(gasPrice.getFastGasPrice());
        this.proposeGas = Integer.parseInt(gasPrice.getProposeGasPrice());
        this.safeGas = Integer.parseInt(gasPrice.getSafeGasPrice());
    }

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Date date;

    private Integer safeGas;

    private Integer proposeGas;

    private Integer fastGas;

}
