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
package org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.predicate;

import org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.FieldPathBuilder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

/**
 * @author Jeff Fischer
 */
@Component("blCollectionSizeEqualPredicateProvider")
public class CollectionSizeEqualPredicateProvider implements PredicateProvider<Collection, Integer> {

    @Override
    public Predicate buildPredicate(
            CriteriaBuilder builder,
            FieldPathBuilder fieldPathBuilder,
            From root,
            String ceilingEntity,
            String fullPropertyName,
            Path<Collection> explicitPath,
            List<Integer> directValues
    ) {
        Path<Collection> path;
        if (explicitPath != null) {
            path = explicitPath;
        } else {
            path = fieldPathBuilder.getPath(root, fullPropertyName, builder);
        }
        return builder.equal(builder.size(path), directValues.get(0));
    }

}
