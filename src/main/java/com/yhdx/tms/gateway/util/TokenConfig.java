package com.yhdx.tms.gateway.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by yj on 2019/12/14.
 **/
@Component
public class TokenConfig {

    @Value("${myconf.user.erp.name}")
    protected static String erpName;
    @Value("${myconf.user.erp.password}")
    protected static String erpPassword;
    @Value("${myconf.user.wms.name}")
    protected static String wmsName;
    @Value("${myconf.user.wms.password}")
    protected static String wmsPassword;
    @Value("${myconf.user.schedual.name}")
    protected static String schedualName;
    @Value("${myconf.user.schedual.password}")
    protected static String schedualPassword;
    @Value("${myconf.user.express.name}")
    protected static String expressName;
    @Value("${myconf.user.express.password}")
    protected static String expressPassword;
}
