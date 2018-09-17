/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.integration.wechat.scheduler;

/**
 * Interface for all scheduler services and jobs.
 */
public interface SchedulerJobService {
    /**
     * interface to class to implement to use @scheduler.
     * All schedule tasks can be wrapped in this interface to run as schedule tasks.
     */
    void runCronJob();
}
