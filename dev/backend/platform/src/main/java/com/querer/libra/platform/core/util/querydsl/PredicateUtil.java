/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.util.querydsl;

import java.util.Collection;
import java.util.Date;

import com.querydsl.core.types.dsl.DateTimePath;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

/**
 * Utils to avoid null check and build predicate for query.
 */
public final class PredicateUtil {
    /* public methods ------------------------------------------------------ */

    /*
     * StringPath
     */
    public static Predicate eq(StringPath path, String value) {
        return StringUtils.isNotBlank(StringUtils.trimToNull(value)) ? path.eq(value) : null;
    }

    public static Predicate contains(StringPath path, String value) {
        return StringUtils.isNotBlank(StringUtils.trimToNull(value)) ? path.contains(value) : null;
    }

    public static Predicate like(StringPath path, String value) {
        return StringUtils.isNotBlank(StringUtils.trimToNull(value)) ? path.like(value) : null;
    }

    /*
     * NumberPath
     */
    public static Predicate eq(NumberPath path, Number value) {
        return value != null ? path.eq(value) : null;
    }

    public static Predicate in(NumberPath path, Collection<Long> collection) {
        return CollectionUtils.isNotEmpty(collection) ? path.in(collection) : null;
    }

    /*
     * BooleanPath
     */
    public static Predicate isTrue(BooleanPath path, Boolean value) {
        return BooleanUtils.isTrue(value) ? path.isTrue() : null;
    }

    /*
     * Date
     */
    public static Predicate before(DateTimePath<Date> path, Date value) {
        return value != null ? path.before(value) : null;
    }

    public static Predicate after(DateTimePath<Date> path, Date value) {
        return value != null ? path.after(value) : null;
    }
    
    public static Predicate between(DateTimePath<Date> path, Date from, Date to) {
        return from != null && to != null ? path.between(from, to) : null;
    }
}
