/******************************************************************************
 *                         Libra FRAMEWORK
 *           © Libra framework, (2017). All rights reserved.
 *
 *  All rights are reserved. Reproduction in whole or in part is prohibited
 *  without the written consent of the copyright owner.
 *****************************************************************************/

package com.querer.libra.platform.core.domain.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Super class of dictionary entity.
 * <p>
 * http://stackoverflow.com/questions/229856/ways-to-save-enums-in-database
 * http://stackoverflow.com/questions/1368662/how-to-best-represent-constants-enums-in-the-database-int-vs-varchar?lq=1
 * http://stackoverflow.com/questions/10153485/best-practice-for-database-dictionary-in-java
 * http://stackoverflow.com/questions/586042/where-do-you-put-your-dictionary-data?rq=1
 */
@MappedSuperclass
public class DictionaryEntity extends GenericEntity {

    /* fields ------------------------------------------------------ */

    @Column(name = "code", length = 8)
    private String code;
    @Column(name = "label", length = 64)
    private String label;

    @Column(name = "group_name", length = 32)
    private String groupName;
    @Column(name = "group_description", length = 64)
    private String groupDescription;

    @Column(name = "display_index")
    private Integer displayIndex;

    @Column(name = "parent_code", length = 32)
    private String parentCode;
    @Column(name = "resource_key", length = 128)
    private String resourceKey;
    @Column(name = "is_visible")
    private Boolean isVisible;
    @Column(name = "is_predefined")
    private Boolean isPredefined;

    /* getters/setters ------------------------------------------------------ */

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public Integer getDisplayIndex() {
        return displayIndex;
    }

    public void setDisplayIndex(Integer displayIndex) {
        this.displayIndex = displayIndex;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean getIsPredefined() {
        return isPredefined;
    }

    public void setIsPredefined(Boolean isPredefined) {
        this.isPredefined = isPredefined;
    }
}
