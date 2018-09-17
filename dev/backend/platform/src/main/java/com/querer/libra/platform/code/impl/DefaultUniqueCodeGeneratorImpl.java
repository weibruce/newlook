/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.code.impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import com.querer.libra.platform.code.UniqueCodeGenerator;

import java.time.Instant;
import java.util.UUID;

/**
 * One generator impl for unique identify generation
 */
public class DefaultUniqueCodeGeneratorImpl implements UniqueCodeGenerator {

    /* fields -------------------------------------------------------------- */

    private int randomCodeLength; // configurable length of random code to generate

    /* public methods ------------------------------------------------------ */
    
    /**
     * @see UniqueCodeGenerator#generateUuid
     */
    @Override
    public String generateUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    /**
     * @see UniqueCodeGenerator#generateToken()
     */
    @Override
    public String generateToken() {
        return generateUuid();
    }

    /**
     * @see UniqueCodeGenerator#generateCodeAlphanumeric()
     */
    @Override
    public String generateRandomCode() {
        return generateCodeAlphanumeric(8);
    }

    /**
     * Current datetime milli + length of random string/number.
     *
     * @see UniqueCodeGenerator#generateCodeTimestamp()
     */
    @Override
    public String generateCodeTimestamp() {
        long timePrefix = Instant.now().toEpochMilli();
        String randomSuffix = RandomStringUtils.randomAlphanumeric(getRandomCodeLength());
        String code = StringUtils.join(timePrefix, randomSuffix);
        return code;
    }

    /**
     * @see UniqueCodeGenerator#generateCodeAlphanumeric()
     */
    @Override
    public String generateCodeAlphanumeric() {
        String code = RandomStringUtils.randomAlphanumeric(getRandomCodeLength());
        return code;
    }

    /**
     * @see UniqueCodeGenerator#generateCodeAlphanumeric(int)
     */
    @Override
    public String generateCodeAlphanumeric(int count) {
        int length = getRandomCodeLength();
        if (count > 0) {
            length = count;
        }
        String code = RandomStringUtils.randomAlphanumeric(length);
        return code;
    }

    /**
     * @see UniqueCodeGenerator#generateCodeNumeric()
     */
    @Override
    public String generateCodeNumeric() {
        String code = RandomStringUtils.randomNumeric(getRandomCodeLength());
        return code;
    }

    /**
     * @see UniqueCodeGenerator#generateCodeNumeric(int)
     */
    @Override
    public String generateCodeNumeric(int count) {
        int length = getRandomCodeLength();
        if (count > 0) {
            length = count;
        }
        String code = RandomStringUtils.randomNumeric(length);
        return code;
    }

    /* getters/setters ------------------------------------------------------ */

    public int getRandomCodeLength() {
        return randomCodeLength;
    }

    public void setRandomCodeLength(int randomCodeLength) {
        this.randomCodeLength = randomCodeLength;
    }
}
