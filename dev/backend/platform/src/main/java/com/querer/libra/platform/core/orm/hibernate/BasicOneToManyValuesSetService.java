/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.orm.hibernate;

import com.querer.libra.platform.core.domain.entity.BaseEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.querer.libra.platform.core.domain.entity.BusinessEntity;
import com.querer.libra.platform.core.domain.model.common.BaseModel;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * BasicOneToManyValuesSetService. Basic logic flow to save many side in
 * one-to-many relationship.
 */
public abstract class BasicOneToManyValuesSetService implements OneToManyValuesSetService {

    /* public methods ------------------------------------------------------ */

    /**
     * @see OneToManyValuesSetService#updateValuesToManySide(Set, List, BusinessEntity, Object...)
     */
    @Override
    public void updateValuesToManySide(@NotNull Set<? extends BusinessEntity> entitySet,
            @NotNull List<? extends BaseModel> modelList, @NotNull BusinessEntity oneSideEntity, Object... contextArguments) {
        if (CollectionUtils.isEmpty(modelList)) {
            // model return null collection means no modification on collection
            // elements.
            return;
        }

        // case: client set value array to update
        if (CollectionUtils.isNotEmpty(entitySet)) {
            // update case

            // found the object that not exist in model list anymore
            List<BusinessEntity> toBeRemovedList = new ArrayList<>();
            entitySet.forEach(e -> {
                BusinessEntity found = null;
                for (BaseModel item : modelList) {
                    if (item.getOid() != null) {
                        if (NumberUtils.compare(e.getOid(), item.getOid()) == 0) {
                            // found the item in entity list
                            found = e;
                            break;
                        }
                    }
                }

                // if not found an entity, add it to be removed
                if (found == null) {
                    toBeRemovedList.add(e);
                }
            });

            // remove items in entity first
            if (CollectionUtils.isNotEmpty(toBeRemovedList)) {
                // give opportunity to do additional logic on delete
                toBeRemovedList.forEach(e -> removeEntity(e, contextArguments));
                // remove items from set then later will be deleted
                entitySet.removeAll(toBeRemovedList);
            }

            // then update and add new one
            modelList.forEach(e -> {
                if (e.getOid() == null) {
                    // case: new added attribute value

                    // update values from model to entity
                    addNewEntityToExistingMany(entitySet, e, oneSideEntity, contextArguments);
                } else {
                    // case: update

                    // update values from model to entity
                    for (BusinessEntity entity : entitySet) {
                        if (NumberUtils.compare(entity.getOid(), e.getOid()) != 0)
                            continue;
                        if (updateExistingEntity(entity, e, contextArguments) != null)
                            break;
                    }
                }
            });
        } else {
            // new many-side entities and all model values to be added to entity
            createManySideEntities(modelList, oneSideEntity, contextArguments);
        }
    }

    /* protected methods --------------------------------------------------- */

    /**
     * Remove entity from many side. Left subclass to overwrite the logic how to remove one many side entity.
     *
     * @param entity the entity to be remove in many side
     * @param contextArguments the context arguments
     */
    protected void removeEntity(BusinessEntity entity, Object[] contextArguments) {
        // default to empty logic processing in base class
    }

    /**
     * Create many side entities and copy model to it. Different subclass should
     * overwrite this method and provide its own biz process.
     *
     * @param modelList
     *            the model list to add to many side
     * @param oneSideEntity
     *            the one side entity to set to many-side entity
     * @param contextArguments
     *            extra arguments used to detail business
     * @return values all set many side entity
     */
    protected abstract BusinessEntity createManySideEntities(List<? extends BaseModel> modelList,
            BusinessEntity oneSideEntity, Object[] contextArguments);

    /**
     * Add new one to many side case. Different subclass should overwrite this
     * method and provide its own biz process.
     *
     * @param model
     *            the model to add to many side
     * @param oneSideEntity
     *            the one side entity to set to many-side entity
     * @param contextArguments
     *            extra arguments used to detail business
     * @return values all set many side entity
     */
    protected abstract BusinessEntity addNewEntity(BaseModel model, BusinessEntity oneSideEntity, Object[] contextArguments);

    /**
     * Update existing one in many side case. Different subclass should
     * overwrite this method and provide its own biz process.
     *
     * @param entity
     *            the many side entity existing to be saved
     * @param model
     *            the model with values to be set to entity
     * @param contextArguments
     *            extra arguments used to detail business
     * @return values updated set for entity
     */
    protected abstract BaseEntity updateExistingEntity(BusinessEntity entity, BaseModel model, Object[] contextArguments);

    /* private methods ------------------------------------------------------ */

    private void addNewEntityToExistingMany(Set entitySet, BaseModel model, BusinessEntity oneSideEntity, Object[] contextArguments) {
        BusinessEntity entity = addNewEntity(model, oneSideEntity, contextArguments);
        if (entity != null) {
            entitySet.add(entity);
        }
    }
}
