/*-
 * #%L
 * BroadleafCommerce Framework Web
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
package org.broadleafcommerce.core.web.catalog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.util.BLCRequestUtils;
import org.broadleafcommerce.common.web.BLCAbstractHandlerMapping;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

/**
 * This handler mapping works with the Category entity to determine if a category has been configured for
 * the passed in URL.
 * <p>
 * If the URL matches a valid Category then this mapping returns the handler configured via the
 * controllerName property or blCategoryController by default.
 *
 * @author bpolster
 * @see org.broadleafcommerce.core.catalog.domain.Category
 * @see CatalogService
 * @since 2.0
 */
public class CategoryHandlerMapping extends BLCAbstractHandlerMapping {

    public static final String CURRENT_CATEGORY_ATTRIBUTE_NAME = "category";
    private static final Log LOG = LogFactory.getLog(CategoryHandlerMapping.class);
    @Value("${request.uri.encoding}")
    public String charEncoding;
    protected String defaultTemplateName = "catalog/category";
    private String controllerName = "blCategoryController";
    @Resource(name = "blCatalogService")
    private CatalogService catalogService;

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        Category category = null;
        if (allowCategoryResolutionUsingIdParam()) {
            category = findCategoryUsingIdParam(request);
        }

        if (category == null) {
            category = findCategoryUsingUrl(request);
        }

        if (category != null) {
            request.setAttribute(CURRENT_CATEGORY_ATTRIBUTE_NAME, category);
            return controllerName;
        }

        return null;
    }

    protected Category findCategoryUsingIdParam(HttpServletRequest request) throws ServletRequestBindingException {
        Long categoryId = ServletRequestUtils.getLongParameter(request, "categoryId");

        if (categoryId != null) {
            Category category = catalogService.findCategoryById(categoryId);
            if (category != null && LOG.isDebugEnabled()) {
                LOG.debug("Obtained the category using ID=" + categoryId);
            }
            return category;
        }

        return null;
    }

    protected Category findCategoryUsingUrl(HttpServletRequest request) throws UnsupportedEncodingException {
        String requestUri = URLDecoder.decode(BLCRequestUtils.getRequestURIWithoutContext(request), charEncoding);

        Category category = catalogService.findCategoryByURI(requestUri);
        if (category != null && LOG.isDebugEnabled()) {
            LOG.debug("Obtained the category using URI=" + requestUri);
        }

        return category;
    }

    public String getDefaultTemplateName() {
        return defaultTemplateName;
    }

    public void setDefaultTemplateName(String defaultTemplateName) {
        this.defaultTemplateName = defaultTemplateName;
    }

}
