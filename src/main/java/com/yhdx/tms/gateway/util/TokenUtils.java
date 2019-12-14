package com.yhdx.tms.gateway.util;

import com.yhdx.baseframework.common.lang.Result;
import com.yhdx.baseservice.users.domain.request.GetTokenRequest;
import com.yhdx.baseservice.users.facade.api.AuthFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * Created by yj on 2019/12/14.
 **/
@Component
public class TokenUtils {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    private final AuthFacade authFacade;
    private static TokenUtils tokenUtil;
    private static String ERPToken;
    private static String WMSToken;
    private static String SCHEDUALToken;
    private static String ExpressToken;

    @Autowired
    public TokenUtils(AuthFacade authFacade) {
        this.authFacade = authFacade;
    }

    @PostConstruct
    public void init() {
        tokenUtil = this;
    }

    public static String getErpToken() {
        if (StringUtils.isEmpty(ERPToken)) {
            Result<String> result = tokenUtil.authFacade.getToken(buildRequest(TokenConfig.erpName, TokenConfig.erpPassword));
            logger.info("get erp token {} " , String.valueOf(result));
            if (result.wasOk()) {
                ERPToken = result.getResult();
                return ERPToken;
            }
        }
        return ERPToken;
    }

    public static String getWmsToken() {
        if (StringUtils.isEmpty(WMSToken)) {
            Result<String> result = tokenUtil.authFacade.getToken(buildRequest(TokenConfig.wmsName, TokenConfig.wmsPassword));
            logger.info("get wms token {} " , String.valueOf(result));

            if (result.wasOk()) {
                WMSToken = result.getResult();
                return WMSToken;
            }
        }
        return WMSToken;
    }

    public static String getSchedualToken() {
        if (StringUtils.isEmpty(SCHEDUALToken)) {
            Result<String> result = tokenUtil.authFacade.getToken(buildRequest(TokenConfig.schedualName, TokenConfig.schedualPassword));
            logger.info("get schedual token {} " ,String.valueOf(result));

            if (result.wasOk()) {
                SCHEDUALToken = result.getResult();
                return SCHEDUALToken;
            }
        }
        return SCHEDUALToken;
    }


    public static String getExpressToken() {
        if (StringUtils.isEmpty(ExpressToken)) {
            Result<String> result = tokenUtil.authFacade.getToken(buildRequest(TokenConfig.expressName, TokenConfig.expressPassword));
            logger.info("get express token {} " ,String.valueOf(result));
            if (result.wasOk()) {
                ExpressToken = result.getResult();
                return ExpressToken;
            }
        }
        return ExpressToken;
    }

    private static GetTokenRequest buildRequest(String name, String password) {
        GetTokenRequest tokenRequest = new GetTokenRequest();
        tokenRequest.setExpiredSeconds(31536000000L);
        tokenRequest.setUserCode(name);
        tokenRequest.setPassword(password);
        return tokenRequest;
    }
}
