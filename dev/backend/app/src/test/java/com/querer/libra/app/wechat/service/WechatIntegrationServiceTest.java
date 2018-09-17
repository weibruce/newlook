/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.wechat.service;

import java.util.Date;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.querer.libra.app.event.domain.model.EventModel;
import com.querer.libra.app.event.domain.model.UserCouponModel;
import com.querer.libra.app.event.service.business.UserCouponBizService;
import com.querer.libra.app.integration.wechat.mvc.model.WechatJsConfig;
import com.querer.libra.app.integration.wechat.service.WechatIntegrationService;
import com.querer.libra.platform.code.UniqueCodeGenerator;
import com.querer.libra.platform.code.impl.DefaultUniqueCodeGeneratorImpl;
import com.querer.libra.platform.core.test.BaseServiceIntegrationTest;

import static org.junit.Assert.assertNotNull;

public class WechatIntegrationServiceTest extends BaseServiceIntegrationTest
//        extends InMemoryDbIntegrationTest
{
    /* fields          ------------------------------------------------------*/

    @Autowired
    private WechatIntegrationService wechatIntegrationService;

    /* public methods  ------------------------------------------------------*/

    @Test
    public void testGetWechatJsConfig() {

        String url = "http://www.5hac.com/newlook?params=value";


        String string1 = "jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com?params=value";
        String signature = Hashing.sha1().hashString(string1, Charsets.UTF_8).toString();

        System.out.println(signature);

        //WechatJsConfig wechatJsConfig = wechatIntegrationService.getWechatJsConfig(url);

        //assertNotNull(wechatJsConfig);

        //try {
        //    ObjectMapper objectMapper = new ObjectMapper();
        //    String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(wechatJsConfig);
        //    System.out.println(json);
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}

    }

}
