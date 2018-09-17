/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.querer.libra.app.event.domain.entity.UserCoupon;
import com.querer.libra.app.event.domain.model.EventModel;
import com.querer.libra.app.event.domain.model.UserCouponModel;
import com.querer.libra.app.event.service.business.UserCouponBizService;
import com.querer.libra.app.event.service.business.UserCouponQueryService;
import com.querer.libra.platform.code.UniqueCodeGenerator;
import com.querer.libra.platform.code.impl.DefaultUniqueCodeGeneratorImpl;
import com.querer.libra.platform.core.test.BaseServiceIntegrationTest;

import static org.junit.Assert.assertNotNull;

public class UserCouponBizServiceTest extends BaseServiceIntegrationTest
//        extends InMemoryDbIntegrationTest
{
    /* fields          ------------------------------------------------------*/

    @Autowired
    private UserCouponBizService userCouponBizService;

    @Autowired
    private UserCouponQueryService userCouponQueryService;

    private int number = 3;
    private CountDownLatch latch = new CountDownLatch(number);

    /* public methods  ------------------------------------------------------*/

    @Test
    public void testDrawCoupon() {
        UserCouponModel userCouponModel = new UserCouponModel();

        UniqueCodeGenerator uniqueCodeGenerator = new DefaultUniqueCodeGeneratorImpl();

        userCouponModel.setUid("86be404f0002605c0000000459731a93");
        //String uid = uniqueCodeGenerator.generateCodeAlphanumeric(32);
        //userCouponModel.setUid(uid);

        userCouponModel.setOccurTime(new Date());

        EventModel eventModel = new EventModel();
        eventModel.setOpeningCode("nl7mokzukhwhY1NNCP");
        userCouponModel.setEvent(eventModel);

        UserCouponModel result = userCouponBizService.doDrawCoupon(userCouponModel);

        assertNotNull(result);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testQueryUserCoupon() {

        String uid = "oSwljwwOeOy_F6NGFRRxDsscpsxo";
        List<UserCoupon> result = userCouponQueryService.findUserCoupons(1L, uid);
        //List<UserCoupon> result = userCouponQueryService.findUserCouponsToday(1L, uid);

        System.out.println("result： " + result);

    }

    @Test
    public void testBatchDrawCoupon() {

        //String openingCode = "nl7mokzukhwhY1NNCP";
        //String openingCode = "2urUfa7I8FSCSqwVDM";
        String openingCode = "GeyFGM0P3WrkQcCqIG";

        UniqueCodeGenerator uniqueCodeGenerator = new DefaultUniqueCodeGeneratorImpl();

        for (int i = 0; i < 500; i++) {
            UserCouponModel userCouponModel = new UserCouponModel();

            String uid = uniqueCodeGenerator.generateCodeAlphanumeric(32);
            userCouponModel.setUid(uid);
            userCouponModel.setOccurTime(new Date());

            EventModel eventModel = new EventModel();
            eventModel.setOpeningCode(openingCode);
            userCouponModel.setEvent(eventModel);

            UserCouponModel result = userCouponBizService.doDrawCoupon(userCouponModel);

            //assertNotNull(result);

            if (result.getCoupon().getOid() == 11) {
                System.out.println("一等奖开出来了： " + result.getOid());
            }

            if (result.getCoupon().getOid() == 12) {
                System.out.println("二等奖开出来了： " + result.getOid());
            }

            if (result.getCoupon().getOid() == 13) {
                System.out.println("三等奖开出来了： " + result.getOid());
            }
        }
    }

    @Test
    public void testBatchDrawByThreads() {

        String openingCode = "nl7mokzukhwhY1NNCP";
        UniqueCodeGenerator uniqueCodeGenerator = new DefaultUniqueCodeGeneratorImpl();

        for (int i = 0; i < number; i++) {
            String name = "Thread " + i;
            new Thread(() -> {
                System.out.println("start " + name);

                for (int j = 0; j < 3; j++) {
                    String uid = uniqueCodeGenerator.generateCodeAlphanumeric(32);
                    drawOnce(uid, openingCode);
                    //System.out.println("uid " + uid);
                }


                System.out.println("end " + name);

                latch.countDown(); // 执行完毕，计数器减1
            }).start();
        }

        try {
            latch.await(); // 主线程等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void drawOnce(String uid, String openingCode) {
        UserCouponModel userCouponModel = new UserCouponModel();

        //String uid = uniqueCodeGenerator.generateCodeAlphanumeric(32);
        userCouponModel.setUid(uid);
        userCouponModel.setOccurTime(new Date());

        EventModel eventModel = new EventModel();
        eventModel.setOpeningCode(openingCode);
        userCouponModel.setEvent(eventModel);

        UserCouponModel result = userCouponBizService.doDrawCoupon(userCouponModel);

        //assertNotNull(result);

        if (result.getCoupon().getOid() == 1) {
            System.out.println("一等奖开出来了： " + result.getOid());
        }

        if (result.getCoupon().getOid() == 2) {
            System.out.println("二等奖开出来了： " + result.getOid());
        }

        if (result.getCoupon().getOid() == 3) {
            System.out.println("三等奖开出来了： " + result.getOid());
        }
    }

    //class Runner implements Runnable {
    //
    //    @Override
    //    public void run() {
    //        latch.countDown(); // 执行完毕，计数器减1
    //    }
    //}

}



