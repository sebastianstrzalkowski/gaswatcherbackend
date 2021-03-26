package pl.sebastianstrzalkowski.gasbackend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sebastianstrzalkowski.gasbackend.entities.GasPriceDao;
import pl.sebastianstrzalkowski.gasbackend.entities.GasPriceRepository;
import pl.sebastianstrzalkowski.gasbackend.models.GasPrice;
import pl.sebastianstrzalkowski.gasbackend.models.Result;
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
import java.util.logging.Logger;

@Service
public class GasService {

    @Autowired
    private GasPriceRepository gasPriceRepository;


//    private String url = System.getenv("REDISCLOUD_URL");
    private Jedis jedis = new Jedis();


    public GasService(){
        jedis = getPool().getResource();
    }

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
            poolConfig.setMaxWaitMillis(1000);

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
