/*-
 * #%L
 * BroadleafCommerce Common Libraries
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
package org.broadleafcommerce.common.template;

import org.broadleafcommerce.common.BroadleafEnumerationType;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * An extendible enumeration of template types
 *
 * @author bpolster
 */
public class TemplateType implements Serializable, BroadleafEnumerationType, Comparable<TemplateType> {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final Map<String, TemplateType> TYPES = new LinkedHashMap<>();

    public static final TemplateType PRODUCT = new TemplateType("PRODUCT", "Product", 1000);
    public static final TemplateType CATEGORY = new TemplateType("CATEGORY", "Category", 2000);
    public static final TemplateType PAGE = new TemplateType("PAGE", "Page", 3000);
    public static final TemplateType SKU = new TemplateType("SKU", "Sku", 4000);
    public static final TemplateType OTHER = new TemplateType("OTHER", "Other", Integer.MAX_VALUE);

    private String type;
    private String friendlyType;
    private int order;

    public TemplateType() {
        //do nothing
    }

    public TemplateType(final String type, final String friendlyType, int order) {
        this.friendlyType = friendlyType;
        setType(type);
        setOrder(order);
    }

    public static TemplateType getInstance(final String type) {
        return TYPES.get(type);
    }

    public String getType() {
        return type;
    }

    protected void setType(final String type) {
        this.type = type;
        if (!TYPES.containsKey(type)) {
            TYPES.put(type, this);
        } else {
            throw new RuntimeException("Cannot add the type: (" + type + "). It already exists as a type via "
                    + getInstance(type).getClass().getName());
        }
    }

    public String getFriendlyType() {
        return friendlyType;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        TemplateType other = (TemplateType) obj;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

    @Override
    public int compareTo(TemplateType arg0) {
        return this.order - arg0.order;
    }

}
