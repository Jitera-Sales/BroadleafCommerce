/*-
 * #%L
 * BroadleafCommerce Framework
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
package org.broadleafcommerce.core.offer.domain;

import org.broadleafcommerce.common.copy.CreateResponse;
import org.broadleafcommerce.common.copy.MultiTenantCopyContext;
import org.broadleafcommerce.common.persistence.IdOverrideTableGenerator;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serial;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_OFFER_INFO")
public class OfferInfoImpl implements OfferInfo {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "OfferInfoId")
    @GenericGenerator(
            name = "OfferInfoId",
            type = IdOverrideTableGenerator.class,
            parameters = {
                    @Parameter(name = "segment_value", value = "OfferInfoImpl"),
                    @Parameter(name = "entity_name",
                            value = "org.broadleafcommerce.core.offer.domain.OfferInfoImpl")
            }
    )
    @Column(name = "OFFER_INFO_ID")
    protected Long id;

    @ElementCollection
    @MapKeyColumn(name = "FIELD_NAME")
    @Column(name = "FIELD_VALUE")
    @CollectionTable(name = "BLC_OFFER_INFO_FIELDS", joinColumns = @JoinColumn(name = "OFFER_INFO_FIELDS_ID"))
    @BatchSize(size = 50)
    protected Map<String, String> fieldValues = new HashMap<>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Map<String, String> getFieldValues() {
        return fieldValues;
    }

    @Override
    public void setFieldValues(Map<String, String> fieldValues) {
        this.fieldValues = fieldValues;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((fieldValues == null) ? 0 : fieldValues.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!getClass().isAssignableFrom(obj.getClass()))
            return false;
        OfferInfoImpl other = (OfferInfoImpl) obj;

        if (id != null && other.id != null) {
            return id.equals(other.id);
        }

        if (fieldValues == null) {
            if (other.fieldValues != null)
                return false;
        } else if (!fieldValues.equals(other.fieldValues))
            return false;
        return true;
    }

    @Override
    public <G extends OfferInfo> CreateResponse<G> createOrRetrieveCopyInstance(
            MultiTenantCopyContext context
    ) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        OfferInfo cloned = createResponse.getClone();
        for (Map.Entry<String, String> entry : fieldValues.entrySet()) {
            cloned.getFieldValues().put(entry.getKey(), entry.getValue());
        }
        return createResponse;
    }

}
