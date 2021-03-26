package pl.sebastianstrzalkowski.gasbackend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sebastianstrzalkowski.gasbackend.entities.GasPriceDao;
import pl.sebastianstrzalkowski.gasbackend.entities.GasPriceRepository;
import pl.sebastianstrzalkowski.gasbackend.models.GasPrice;
import pl.sebastianstrzalkowski.gasbackend.models.Result;
import redis.clients.jedis.Jedis;

import java.util.logging.Logger;

@Service
public class GasService {

    @Autowired
    private GasPriceRepository gasPriceRepository;

    private Jedis jedis = new Jedis();

    public Result getGasPrice(){
        String fastPrice = jedis.get("price:fast");
        String safePrice = jedis.get("price:safe");
        String proposePrice = jedis.get("price:propose");

        Logger.getAnonymousLogger().info(fastPrice + " " + safePrice + " " + proposePrice);
        Result result = new Result();
        result.setFastGasPrice(fastPrice);
        result.setSafeGasPrice(safePrice);
        result.setProposeGasPrice(proposePrice);
        return result;
    }

}
