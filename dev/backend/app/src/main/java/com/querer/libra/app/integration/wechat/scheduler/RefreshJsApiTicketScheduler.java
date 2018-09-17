/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.integration.wechat.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.querer.libra.app.integration.wechat.service.WechatIntegrationService;

@Service
public class RefreshJsApiTicketScheduler implements SchedulerJobService {
    
    /* fields          ------------------------------------------------------*/

    private final static Logger logger = LoggerFactory.getLogger(RefreshJsApiTicketScheduler.class);

    @Autowired
    private WechatIntegrationService wechatIntegrationService;

    /* public methods  ------------------------------------------------------*/

    @Override
    @Scheduled(fixedDelay = 2 * 59 * 60 * 1000, initialDelay = 60 * 1000)
    //@Scheduled(fixedDelay = 10 * 60 * 1000, initialDelay = 60 * 1000)
    public void runCronJob() {
        wechatIntegrationService.refreshJsApiTicket();
    }
}
