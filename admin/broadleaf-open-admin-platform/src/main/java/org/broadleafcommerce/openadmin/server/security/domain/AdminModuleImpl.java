/*-
 * #%L
 * BroadleafCommerce Open Admin Platform
 * %%
 * Copyright (C) 2009 - 2024 Broadleaf Commerce
 * %%
 * Licensed under the Broadleaf Fair Use License Agreement, Version 1.0
 * (the "Fair Use License" located  at http://license.broadleafcommerce.org/fair_use_license-1.0.txt)
 * unless the restrictions on use therein are violated and require payment to Broadleaf in which case
 * the Broadleaf End User License Agreement (EULA), Version 1.1
 * (the "Commercial License" located at http://license.broadleafcommerce.org/commercial_license-1.1.txt)
 * shall apply.
 *
 * Alternatively, the Commercial License may be replaced with a mutually agreed upon license (the "Custom License")
 * between you and Broadleaf Commerce. You may not use this file except in compliance with the applicable license.
 * #L%
 */
package org.broadleafcommerce.openadmin.server.security.domain;

import org.broadleafcommerce.common.persistence.IdOverrideTableGenerator;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @author elbertbautista
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ADMIN_MODULE", indexes = {
        @Index(name = "ADMINMODULE_NAME_INDEX", columnList = "NAME")
})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blAdminSecurity")
@AdminPresentationClass(friendlyName = "AdminModuleImpl_baseAdminModule")
public class AdminModuleImpl implements AdminModule {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "AdminModuleId")
    @GenericGenerator(
            name = "AdminModuleId",
            type = IdOverrideTableGenerator.class,
            parameters = {
                    @Parameter(name = "segment_value", value = "AdminModuleImpl"),
                    @Parameter(name = "entity_name",
                            value = "org.broadleafcommerce.openadmin.server.security.domain.AdminModuleImpl")
            }
    )
    @Column(name = "ADMIN_MODULE_ID")
    @AdminPresentation(friendlyName = "AdminModuleImpl_Admin_Module_ID",
            group = "AdminModuleImpl_Primary_Key", visibility = VisibilityEnum.HIDDEN_ALL)
    protected Long id;

    @Column(name = "NAME", nullable = false)
    @AdminPresentation(friendlyName = "AdminModuleImpl_Name", order = 1,
            group = "AdminModuleImpl_Module", prominent = true)
    protected String name;

    @Column(name = "MODULE_KEY", nullable = false)
    @AdminPresentation(friendlyName = "AdminModuleImpl_Module_Key", order = 2,
            group = "AdminModuleImpl_Module", prominent = true)
    protected String moduleKey;

    @Column(name = "ICON", nullable = true)
    @AdminPresentation(friendlyName = "AdminModuleImpl_Icon", order = 3,
            group = "AdminModuleImpl_Module", prominent = true)
    protected String icon;

    @OneToMany(mappedBy = "module", targetEntity = AdminSectionImpl.class)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blAdminSecurity")
    @BatchSize(size = 50)
    protected List<AdminSection> sections = new ArrayList<AdminSection>();

    @Column(name = "DISPLAY_ORDER", nullable = true)
    @AdminPresentation(friendlyName = "AdminModuleImpl_Display_Order", order = 4,
            group = "AdminModuleImpl_Module", prominent = true)
    protected Integer displayOrder;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getModuleKey() {
        return moduleKey;
    }

    @Override
    public void setModuleKey(String moduleKey) {
        this.moduleKey = moduleKey;
    }

    @Override
    public String getIcon() {
        return icon;
    }

    @Override
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public List<AdminSection> getSections() {
        return sections;
    }

    @Override
    public void setSections(List<AdminSection> sections) {
        this.sections = sections;
    }

    @Override
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    @Override
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * Set all properties except the sections.
     *
     * @return
     */
    public AdminModuleDTO getAdminModuleDTO() {
        AdminModuleDTO dto = new AdminModuleDTO();
        dto.setDisplayOrder(displayOrder);
        dto.setIcon(icon);
        dto.setId(id);
        dto.setModuleKey(moduleKey);
        dto.setName(name);
        return dto;
    }
}
