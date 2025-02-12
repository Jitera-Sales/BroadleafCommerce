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
package org.broadleafcommerce.core.web.service;

import org.broadleafcommerce.core.search.domain.SearchCriteria;
import org.broadleafcommerce.core.search.domain.SearchFacetDTO;
import org.broadleafcommerce.core.search.domain.SearchFacetResultDTO;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Provides methods that facilitate interactions with SearchFacetDTOs and SearchFacetResultDTOs
 *
 * @author Andre Azzolini (apazzolini)
 */
public interface SearchFacetDTOService {

    /**
     * Given a servlet request and a list of available facets for this request (could be search or category based),
     * this method will build out a SearchCriteria object to be used by the ProductSearchService. It will
     * perform translations from query string parameters to the SearchCriteria.
     *
     * @param request
     * @return the SearchCriteria
     */
    SearchCriteria buildSearchCriteria(HttpServletRequest request);

    /**
     * Sets the "active" boolean on a given SearchFacetResultDTO as determined by the current request
     *
     * @param facets
     * @param request
     */
    void setActiveFacetResults(List<SearchFacetDTO> facets, HttpServletRequest request);

    /**
     * Returns whether or not the SearchFacetResultDTO's key/value pair is present in the servlet request
     *
     * @param result
     * @param request
     * @return if the result is active
     */
    boolean isActive(SearchFacetResultDTO result, HttpServletRequest request);

    /**
     * Gets the url abbreviation associated with a given SearchFacetResultDTO.
     *
     * @param result
     * @return the key associated with a SearchFacetResultDTO
     */
    String getUrlKey(SearchFacetResultDTO result);

    /**
     * Gets the value of the given SearchFacetResultDTO.
     * The default Broadleaf implementation will return the String value of the result if the value
     * is not empty, or "range[<min-value>:<max-value>]" if the value was empty.
     *
     * @param result
     * @return the value of the SearchFacetResultDTO
     */
    String getValue(SearchFacetResultDTO result);

    String getUrlKey(SearchFacetDTO result);

}
