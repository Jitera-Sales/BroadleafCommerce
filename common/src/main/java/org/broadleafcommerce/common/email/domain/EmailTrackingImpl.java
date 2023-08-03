/*-
 * #%L
 * BroadleafCommerce Common Libraries
 * %%
 * Copyright (C) 2009 - 2023 Broadleaf Commerce
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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.broadleafcommerce.common.persistence.IdOverrideTableGenerator;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author jfischer
 *
 */
@Entity
@Table(name = "BLC_EMAIL_TRACKING", indexes = {
        @Index(name = "EMAILTRACKING_INDEX", columnList = "EMAIL_ADDRESS"),
        @Index(name = "DATESENT_INDEX", columnList = "DATE_SENT")})
public class EmailTrackingImpl implements EmailTracking {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "EmailTrackingId")
    @GenericGenerator(
        name="EmailTrackingId",
        type= IdOverrideTableGenerator.class,
        parameters = {
            @Parameter(name="segment_value", value="EmailTrackingImpl"),
            @Parameter(name="entity_name", value="org.broadleafcommerce.common.email.domain.EmailTrackingImpl")
        }
    )
    @Column(name = "EMAIL_TRACKING_ID")
    protected Long id;

    @Column(name = "EMAIL_ADDRESS")
    protected String emailAddress;

    @Column(name = "DATE_SENT")
    protected Date dateSent;

    @Column(name = "TYPE")
    protected String type;

    @OneToMany(mappedBy = "emailTracking", targetEntity = EmailTrackingClicksImpl.class)
    protected Set<EmailTrackingClicks> emailTrackingClicks = new HashSet<EmailTrackingClicks>();

    @OneToMany(mappedBy = "emailTracking", targetEntity = EmailTrackingOpensImpl.class)
    protected Set<EmailTrackingOpens> emailTrackingOpens = new HashSet<EmailTrackingOpens>();

    /* (non-Javadoc)
     * @see org.broadleafcommerce.common.email.domain.EmailTracking#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see org.broadleafcommerce.common.email.domain.EmailTracking#setId(java.lang.Long)
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see org.broadleafcommerce.common.email.domain.EmailTracking#getEmailAddress()
     */
    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    /* (non-Javadoc)
     * @see org.broadleafcommerce.common.email.domain.EmailTracking#setEmailAddress(java.lang.String)
     */
    @Override
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /* (non-Javadoc)
     * @see org.broadleafcommerce.common.email.domain.EmailTracking#getDateSent()
     */
    @Override
    public Date getDateSent() {
        return dateSent;
    }
    /* (non-Javadoc)
     * @see org.broadleafcommerce.common.email.domain.EmailTracking#setDateSent(java.util.Date)
     */
    @Override
    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    /* (non-Javadoc)
     * @see org.broadleafcommerce.common.email.domain.EmailTracking#getType()
     */
    @Override
    public String getType() {
        return type;
    }

    /* (non-Javadoc)
     * @see org.broadleafcommerce.common.email.domain.EmailTracking#setType(java.lang.String)
     */
    @Override
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the emailTrackingClicks
     */
    public Set<EmailTrackingClicks> getEmailTrackingClicks() {
        return emailTrackingClicks;
    }

    /**
     * @param emailTrackingClicks the emailTrackingClicks to set
     */
    public void setEmailTrackingClicks(Set<EmailTrackingClicks> emailTrackingClicks) {
        this.emailTrackingClicks = emailTrackingClicks;
    }

    /**
     * @return the emailTrackingOpens
     */
    public Set<EmailTrackingOpens> getEmailTrackingOpens() {
        return emailTrackingOpens;
    }

    /**
     * @param emailTrackingOpens the emailTrackingOpens to set
     */
    public void setEmailTrackingOpens(Set<EmailTrackingOpens> emailTrackingOpens) {
        this.emailTrackingOpens = emailTrackingOpens;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dateSent == null) ? 0 : dateSent.hashCode());
        result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        result = prime * result + ((emailTrackingClicks == null) ? 0 : emailTrackingClicks.hashCode());
        result = prime * result + ((emailTrackingOpens == null) ? 0 : emailTrackingOpens.hashCode());
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
        EmailTrackingImpl other = (EmailTrackingImpl) obj;

        if (id != null && other.id != null) {
            return id.equals(other.id);
        }

        if (dateSent == null) {
            if (other.dateSent != null)
                return false;
        } else if (!dateSent.equals(other.dateSent))
            return false;
        if (emailAddress == null) {
            if (other.emailAddress != null)
                return false;
        } else if (!emailAddress.equals(other.emailAddress))
            return false;
        if (emailTrackingClicks == null) {
            if (other.emailTrackingClicks != null)
                return false;
        } else if (!emailTrackingClicks.equals(other.emailTrackingClicks))
            return false;
        if (emailTrackingOpens == null) {
            if (other.emailTrackingOpens != null)
                return false;
        } else if (!emailTrackingOpens.equals(other.emailTrackingOpens))
            return false;
        if (type == null) {
            if (other.type != null)
                return false;
        } else if (!type.equals(other.type))
            return false;
        return true;
    }

}
