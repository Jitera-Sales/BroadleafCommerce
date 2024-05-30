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
package org.broadleafcommerce.openadmin.web.rulebuilder.enums;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;

/**
 * Extension Manager used to aggregate option values for all registered {@link RuleBuilderEnumOptionsExtensionListener}
 *
 * @author Andre Azzolini (apazzolini)
 */
@Component("blRuleBuilderEnumOptionsExtensionManager")
public class RuleBuilderEnumOptionsExtensionManager implements RuleBuilderEnumOptionsExtensionListener {

    @Resource(name = "blRuleBuilderEnumOptionsExtensionListeners")
    protected List<RuleBuilderEnumOptionsExtensionListener> listeners = new ArrayList<>();

    @Override
    public String getOptionValues() {
        StringBuilder sb = new StringBuilder();
        for (RuleBuilderEnumOptionsExtensionListener listener : listeners) {
            sb.append(listener.getOptionValues()).append("\r\n");
        }
        return sb.toString();
    }

    public List<RuleBuilderEnumOptionsExtensionListener> getListeners() {
        return listeners;
    }

    public void setListeners(List<RuleBuilderEnumOptionsExtensionListener> listeners) {
        this.listeners = listeners;
    }

}
