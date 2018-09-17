/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.code;

/**
 * Used for random/unique code generation.
 * <p>
 * Used for file directory, file name, random password...etc scenarios.
 * <p>
 * Reference Sample:
 * <p>
 * Carzoon
 * <p>
 * http://image.carzone.cn/acc_pic/FDB541_1.jpg
 * http://image.carzone.cn/acc_detail/001273/001273_1.jpg
 * http://image.carzone.cn/acc_detail/000946/000946_2.jpg
 * http://image.carzone.cn/acc_detail/001273/001273_12.jpg
 * <p>
 * http://image.carzone.cn/acc_pic/022905_FRN-6-QW-54MF_1.jpg
 * http://image.carzone.cn/acc_detail/022887/022887_2.jpg
 * http://image.carzone.cn/acc_detail/022887/022887_4.jpg
 * <p>
 * JD
 * http://img10.360buyimg.com/n1/jfs/t1273/244/116303910/170066/7ed02d4c/55000a95N5274e5f1.jpg
 * http://img10.360buyimg.com/n1/jfs/t934/328/122064978/358209/41458907/55000a84N3967588a.jpg
 * <p>
 * http://img10.360buyimg.com/n0/jfs/t1273/244/116303910/170066/7ed02d4c/55000a95N5274e5f1.jpg
 * http://img10.360buyimg.com/n0/jfs/t934/328/122064978/358209/41458907/55000a84N3967588a.jpg
 * <p>
 * Taobao
 * <p>
 * https://img.alicdn.com/imgextra/i3/TB1mXrKHpXXXXXhXVXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg
 * https://img.alicdn.com/imgextra/i2/2086758625/TB2HXdXcpXXXXbwXXXXXXXXXXXX-2086758625.jpg_430x430q90.jpg
 * https://img.alicdn.com/imgextra/i3/2086758625/TB2MX13bVXXXXcfXXXXXXXXXXXX-2086758625.jpg_430x430q90.jpg
 */
public interface UniqueCodeGenerator {
    /**
     * Generate unique UUID/GUID string.
     *
     * @return random code return
     */
    String generateUuid();
    
    /**
     * Generate unique token string.
     *
     * @return random code return
     */
    String generateToken();
    
    /**
     * Generate random string with timestamp and random length of strings.
     *
     * @return random code return
     */
    String generateRandomCode();

    /**
     * Generate random string with timestamp and random length of strings. The
     * generation strategy should be configurable.
     *
     * @return the random string
     */
    String generateCodeTimestamp();

    /**
     * Generate random code as string format with alpha and numeric combination.
     * The generation strategy should be configurable.
     *
     * @return unique code return
     */
    String generateCodeAlphanumeric();

    /**
     * Generate random code as string format with alpha and numeric combination.
     * The generation strategy should be configurable.
     *
     * @param count
     *            the count of code to generate. if not provided, then use
     *            system default setting
     * @return unique code return
     */
    String generateCodeAlphanumeric(int count);

    /**
     * Generate random code as string format with numeric. The generation
     * strategy should be configurable.
     *
     * @return unique code return
     */
    String generateCodeNumeric();

    /**
     * Generate random code as string format with numeric. The generation
     * strategy should be configurable.
     *
     * @param count
     *            the count of code to generate. if not provided, then use
     *            system default setting
     * @return unique code return
     */
    String generateCodeNumeric(int count);
}
