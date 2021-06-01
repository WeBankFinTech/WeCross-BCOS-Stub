package com.webank.wecross.stub.bcos.account;

import com.webank.wecross.stub.Account;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ExtendedRawTransaction;
import org.fisco.bcos.web3j.crypto.ExtendedTransactionEncoder;
import org.fisco.bcos.web3j.utils.Numeric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BCOSAccount implements Account {

    private Logger logger = LoggerFactory.getLogger(BCOSAccount.class);

    private final String name;
    private final String type;
    private final Credentials credentials;

    private int keyID;

    private boolean isDefault;

    public BCOSAccount(String name, String type, Credentials credentials) {
        this.name = name;
        this.type = type;
        this.credentials = credentials;
        if (credentials != null) {
            // for luyu protocol support
            String publicKey =
                    Numeric.toHexStringNoPrefixZeroPadded(
                            credentials.getEcKeyPair().getPublicKey(), 128);
            logger.info(" name: {}, type: {}, publicKey: {}", name, type, publicKey);
        }
    }

    public Credentials getCredentials() {
        return credentials;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getIdentity() {
        return credentials.getAddress();
    }

    @Override
    public int getKeyID() {
        return keyID;
    }

    @Override
    public boolean isDefault() {
        return isDefault;
    }

    public void setKeyID(int keyID) {
        this.keyID = keyID;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public byte[] sign(ExtendedRawTransaction extendedRawTransaction) {
        return ExtendedTransactionEncoder.signMessage(extendedRawTransaction, credentials);
    }
}
