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
package org.broadleafcommerce.common.email.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jfischer
 */
public interface EmailTrackingOpens extends Serializable {

    /**
     * @return the id
     */
    Long getId();

    /**
     * @param id the id to set
     */
    void setId(Long id);

    /**
     * @return the dateOpened
     */
    Date getDateOpened();

    /**
     * @param dateOpened the dateOpened to set
     */
    void setDateOpened(Date dateOpened);

    /**
     * @return the userAgent
     */
    String getUserAgent();

    /**
     * @param userAgent the userAgent to set
     */
    void setUserAgent(String userAgent);

    /**
     * @return the emailTracking
     */
    EmailTracking getEmailTracking();

    /**
     * @param emailTracking the emailTracking to set
     */
    void setEmailTracking(EmailTracking emailTracking);

}
