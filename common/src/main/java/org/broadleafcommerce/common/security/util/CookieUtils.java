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
package org.broadleafcommerce.common.security.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CookieUtils {

    String CUSTOMER_COOKIE_NAME = "customerId";

    /**
     * Checks <code>cookies.use.secure</code> System Property, which determines whether to use HTTPS cookie over
     * HTTPS connection or HTTP only.
     *
     * @return value of <code>cookies.use.secure</code>
     */
    Boolean shouldUseSecureCookieIfApplicable();

    String getCookieValue(HttpServletRequest request, String cookieName);

    /**
     * @param response
     * @param cookieName
     * @param cookieValue
     * @param path
     * @param maxAge
     * @param isSecure
     */
    void setCookieValue(HttpServletResponse response, String cookieName, String cookieValue, String path, Integer maxAge, Boolean isSecure);

    void setCookieValue(HttpServletResponse response, String cookieName, String cookieValue);

    void invalidateCookie(HttpServletResponse response, String cookieName);

}
