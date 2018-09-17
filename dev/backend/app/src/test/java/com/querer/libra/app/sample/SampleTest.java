/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.sample;

import org.junit.Test;

import com.querer.libra.platform.core.test.BaseServiceIntegrationTest;

public class SampleTest extends BaseServiceIntegrationTest
//        extends InMemoryDbIntegrationTest
{
    /* public methods  ------------------------------------------------------*/

    @Test
    public void testSample() {
        System.out.println("Hello, it works!");
    }
}
