package pl.sebastianstrzalkowski.gasbackend.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;
import pl.sebastianstrzalkowski.gasbackend.models.GasPrice;
import redis.clients.jedis.Jedis;

@Service
public class GasServiceRest {

    private Jedis jedis = new Jedis();
    RestTemplate restTemplate = new RestTemplate();

    public GasPrice getPrice() {

        String resource
                = "https://api.etherscan.io/api?module=gastracker&action=gasoracle&apikey=2H9H8Y61IICBX26XZESEFU59E5BZ8QHRWJ";
        ResponseEntity<GasPrice> response
                = restTemplate.getForEntity(resource, GasPrice.class);
        jedis.set("price:fast",response.getBody().getResult().getFastGasPrice());
        jedis.set("price:safe",response.getBody().getResult().getSafeGasPrice());
        jedis.set("price:propose",response.getBody().getResult().getProposeGasPrice());
        return response.getBody();
    }


}
