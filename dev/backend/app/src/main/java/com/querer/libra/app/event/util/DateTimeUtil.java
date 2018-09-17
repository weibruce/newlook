/******************************************************************************
 *                         Philips Medical Systems
 *                © 2016 Koninklijke Philips Electronics N.V.
 *
 * All rights are reserved. Reproduction in whole or in part is
 * prohibited without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Date/time util methods for all functions.
 */
public class DateTimeUtil {

    /* public methods ------------------------------------------------------ */

    /**
     * Get the beginning of target day.
     *
     * @param date the date wants to get
     * @return the start of day
     */
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    /**
     * Get the end of target day.
     *
     * @param date the date wants to get
     * @return the end of day
     */
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    /**
     * Convert from LocalDateTime to Date.
     *
     * @param localDateTime the target object to convert
     * @return the date object converted
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Convert from Date to LocalDateTime.
     *
     * @param date the target object to convert
     * @return the LocalDateTime object converted
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    }

    /**
     * Convert from Date to LocalDate.
     *
     * @param date the target object to convert
     * @return the LocalDate object converted
     */
    public static LocalDate dateToLocalDate(Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Convert from localDate to date.
     *
     * @param localDate the target object to convert
     * @return the Date object converted
     */
    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
