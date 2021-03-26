package pl.sebastianstrzalkowski.gasbackend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @JsonProperty("LastBlock")
    private String lastBlock;
    @JsonProperty("SafeGasPrice")
    private String safeGasPrice;
    @JsonProperty("ProposeGasPrice")
    private String proposeGasPrice;
    @JsonProperty("FastGasPrice")
    private String fastGasPrice;

    public String getLastBlock() {
        return lastBlock;
    }

    public void setLastBlock(String lastBlock) {
        this.lastBlock = lastBlock;
    }

    public String getSafeGasPrice() {
        return safeGasPrice;
    }

    public void setSafeGasPrice(String safeGasPrice) {
        this.safeGasPrice = safeGasPrice;
    }

    public String getProposeGasPrice() {
        return proposeGasPrice;
    }

    public void setProposeGasPrice(String proposeGasPrice) {
        this.proposeGasPrice = proposeGasPrice;
    }

    public String getFastGasPrice() {
        return fastGasPrice;
    }

    public void setFastGasPrice(String fastGasPrice) {
        this.fastGasPrice = fastGasPrice;
    }

}