/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Wrapper to always return a reference to the Spring Application Context from
 * within non-Spring enabled beans. Unlike Spring MVC's
 * WebApplicationContextUtils we do not need a reference to the Servlet context
 * for this. All we need is for this bean to be initialized during application
 * startup.
 */
public class SpringApplicationContext implements ApplicationContextAware, DisposableBean {

    /* fields ------------------------------------------------------ */

    private static ApplicationContext CONTEXT = null;

    /* public methods ------------------------------------------------------ */

    /**
     * This is about the same as context.getBean("beanName"), except it has its
     * own static handle to the Spring context, so calling this method
     * statically will give access to the beans by name in the Spring
     * application context. As in the context.getBean("beanName") call, the
     * caller must cast to the appropriate target class. If the bean does not
     * exist, then a Runtime error will be thrown.
     *
     * @param beanName
     *            the name of the bean to get.
     * @param <T>
     *            the type of object to return
     * @return an T of object reference to the named bean.
     */
    public static <T> T getBean(String beanName) {
        return (T) CONTEXT.getBean(beanName);
    }

    /**
     * This is about the same as context.getBean("beanName"), except it has its
     * own static handle to the Spring context, so calling this method
     * statically will give access to the beans by name in the Spring
     * application context. As in the context.getBean("beanName") call, the
     * caller must cast to the appropriate target class. If the bean does not
     * exist, then a Runtime error will be thrown.
     *
     * @param beanName
     *            the name of the bean to get.
     * @param clazz
     *            the class of bean to get
     * @param <T>
     *            the type of object to return
     * @return an T of object reference to the named bean.
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        return CONTEXT.getBean(beanName, clazz);
    }

    /**
     * This is about the same as context.getBean("beanName"), except it has its
     * own static handle to the Spring context, so calling this method
     * statically will give access to the beans by name in the Spring
     * application context. As in the context.getBean("beanName") call, the
     * caller must cast to the appropriate target class. If the bean does not
     * exist, then a Runtime error will be thrown.
     *
     * @param clazz
     *            the class of bean to get
     * @param <T>
     *            the type of object to return
     * @return an T of object reference to the named bean.
     */
    public static <T> T getBean(Class<T> clazz) {
        return CONTEXT.getBean(clazz);
    }

    /**
     * This method is called from within the ApplicationContext once it is done
     * starting up, it will stick a reference to itself into this bean.
     *
     * @param context
     *            a reference to the ApplicationContext.
     */
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        CONTEXT = context;
    }

    @Override
    public void destroy() throws Exception {
        CONTEXT = null;
    }
}
