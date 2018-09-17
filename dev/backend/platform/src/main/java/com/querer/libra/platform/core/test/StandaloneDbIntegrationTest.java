/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.test;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;

/**
 * Base integration test with real DB environment and rollback disabled.
 * Transaction is set per test case.
 */
@ContextConfiguration(locations = { "classpath:standalone-db-application-context.xml" })
@Rollback
public abstract class StandaloneDbIntegrationTest extends TransactionalIntegrationTest {
}
