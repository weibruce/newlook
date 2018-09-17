/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.app.event.service.atom;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.querer.libra.app.event.domain.entity.Rule;

@Validated
public interface RuleService {

    Optional<Rule> save(@NotNull Rule rule);

    void delete(@NotNull Long oid);

    Optional<Rule> findById(@NotNull Long oid);

    List<Rule> findRulesToday(@NotNull Long eventOid, @NotNull Date today);
}
