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
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

@Service
public class GasServiceRest {

//    private Jedis jedis = new Jedis();
    RestTemplate restTemplate = new RestTemplate();

    public GasPrice getPrice() {

        Jedis jedis = getPool().getResource();
        String resource
                = "https://api.etherscan.io/api?module=gastracker&action=gasoracle&apikey=2H9H8Y61IICBX26XZESEFU59E5BZ8QHRWJ";
        ResponseEntity<GasPrice> response
                = restTemplate.getForEntity(resource, GasPrice.class);
        jedis.set("price:fast",response.getBody().getResult().getFastGasPrice());
        jedis.set("price:safe",response.getBody().getResult().getSafeGasPrice());
        jedis.set("price:propose",response.getBody().getResult().getProposeGasPrice());
        return response.getBody();
    }


    public static JedisPool getPool() {
        try {
            TrustManager bogusTrustManager = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{bogusTrustManager}, new java.security.SecureRandom());

            HostnameVerifier bogusHostnameVerifier = (hostname, session) -> true;

            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(10);
            poolConfig.setMaxIdle(5);
            poolConfig.setMinIdle(1);
            poolConfig.setTestOnBorrow(true);
            poolConfig.setTestOnReturn(true);
            poolConfig.setTestWhileIdle(true);

            return new JedisPool(poolConfig,
                    URI.create(System.getenv("REDISCLOUD_URL")),
                    sslContext.getSocketFactory(),
                    sslContext.getDefaultSSLParameters(),
                    bogusHostnameVerifier);

        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException("Cannot obtain Redis connection!", e);
        }
    }

}
