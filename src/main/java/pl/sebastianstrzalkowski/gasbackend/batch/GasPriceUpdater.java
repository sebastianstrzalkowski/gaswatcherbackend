package pl.sebastianstrzalkowski.gasbackend.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.sebastianstrzalkowski.gasbackend.service.GasServiceRest;
import pl.sebastianstrzalkowski.gasbackend.entities.GasPriceDao;
import pl.sebastianstrzalkowski.gasbackend.entities.GasPriceRepository;
import pl.sebastianstrzalkowski.gasbackend.models.GasPrice;

@Component
public class GasPriceUpdater {

    @Autowired
    private GasServiceRest gasServiceRest;

    @Autowired
    private GasPriceRepository gasPriceRepository;

    @Scheduled(fixedRate = 1250)
    public void execute(){
        GasPrice gasPrice = gasServiceRest.getPrice();
        GasPriceDao gasPriceDao = new GasPriceDao(gasPrice.getResult());
        gasPriceRepository.save(gasPriceDao);
//        Logger.getAnonymousLogger().info(gasPrice.toString());
    }

}
