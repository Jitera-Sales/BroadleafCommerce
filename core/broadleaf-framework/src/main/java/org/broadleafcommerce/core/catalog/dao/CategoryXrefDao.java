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
package org.broadleafcommerce.core.catalog.dao;

import org.broadleafcommerce.core.catalog.domain.CategoryProductXref;
import org.broadleafcommerce.core.catalog.domain.CategoryXref;
import org.broadleafcommerce.core.catalog.domain.CategoryXrefImpl;

import java.util.List;

import jakarta.annotation.Nonnull;

/**
 * {@code CategoryXrefDao} provides persistence access to the relationship
 * between a category and its sub-categories. This includes an ordering field.
 *
 * @author Jeff Fischer
 * @see CategoryXref
 */
public interface CategoryXrefDao {

    /**
     * Retrieve all the category relationships for which the passed in
     * {@code Category} primary key is a parent
     *
     * @param categoryId the parent {@code Category} primary key
     * @return the list of child category relationships for the parent primary key
     */
    @Nonnull
    List<CategoryXref> readXrefsByCategoryId(@Nonnull Long categoryId);

    /**
     * Retrieve all the category relationships for which the passed in
     * {@code Category} primary key is a sub category (or child)
     *
     * @param subCategoryId the sub-categories primary key
     * @return the list of category relationships for the sub-category primary key
     */
    @Nonnull
    List<CategoryXref> readXrefsBySubCategoryId(@Nonnull Long subCategoryId);

    /**
     * Find a specific relationship between a parent categoy and sub-category (child)
     *
     * @param categoryId    The primary key of the parent category
     * @param subCategoryId The primary key of the sub-category
     * @return The relationship between the parent and child categories
     */
    @Nonnull
    CategoryXref readXrefByIds(@Nonnull Long categoryId, @Nonnull Long subCategoryId);

    /**
     * Persist the passed in category relationship to the datastore
     *
     * @param categoryXref the relationship between a parent and child category
     * @return the persisted relationship between a parent and child category
     */
    @Nonnull
    CategoryXref save(@Nonnull CategoryXrefImpl categoryXref);

    /**
     * Remove the passed in category relationship from the datastore
     *
     * @param categoryXref the category relationship to remove
     */
    void delete(@Nonnull CategoryXref categoryXref);

    /**
     * Persist the passed in category/product relationship to the datastore
     *
     * @param categoryProductXref the relationship between a category and product
     * @return the persisted relationship between a category and product
     */
    @Nonnull
    CategoryProductXref save(CategoryProductXref categoryProductXref);

}
