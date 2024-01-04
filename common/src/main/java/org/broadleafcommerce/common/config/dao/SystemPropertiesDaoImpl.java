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
package org.broadleafcommerce.common.config.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.broadleafcommerce.common.cache.AbstractCacheMissAware;
import org.broadleafcommerce.common.cache.PersistentRetrieval;
import org.broadleafcommerce.common.config.domain.NullSystemProperty;
import org.broadleafcommerce.common.config.domain.SystemProperty;
import org.broadleafcommerce.common.config.domain.SystemPropertyImpl;
import org.broadleafcommerce.common.extensibility.jpa.SiteDiscriminator;
import org.broadleafcommerce.common.extension.ExtensionResultHolder;
import org.broadleafcommerce.common.extension.ExtensionResultStatusType;
import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.broadleafcommerce.common.web.BroadleafRequestContext;
import org.hibernate.jpa.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Resource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

/**
 * This DAO enables access to manage system properties that can be stored in the database.
 * <p/>
 * User: Kelly Tisdell
 * Date: 6/20/12
 */
@Repository("blSystemPropertiesDao")
public class SystemPropertiesDaoImpl extends AbstractCacheMissAware<SystemProperty> implements SystemPropertiesDao {

    protected static final Log LOG = LogFactory.getLog(SystemPropertiesDaoImpl.class);

    @PersistenceContext(unitName = "blPU")
    protected EntityManager em;

    @Resource(name = "blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Resource(name = "blSystemPropertyDaoQueryExtensionManager")
    protected SystemPropertyDaoQueryExtensionManager queryExtensionManager;

    private SystemProperty nullObject;

    @Override
    public SystemProperty readById(Long id) {
        return em.find(SystemPropertyImpl.class, id);
    }

    @Override
    public SystemProperty saveSystemProperty(SystemProperty systemProperty) {
        return em.merge(systemProperty);
    }

    @Override
    public void deleteSystemProperty(SystemProperty systemProperty) {
        em.remove(systemProperty);
    }

    @Override
    public List<SystemProperty> readAllSystemProperties() {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<SystemProperty> criteria = builder.createQuery(SystemProperty.class);
        Root<SystemPropertyImpl> handler = criteria.from(SystemPropertyImpl.class);
        criteria.select(handler);
        List<Predicate> restrictions = new ArrayList<Predicate>();
        List<Order> sorts = new ArrayList<Order>();
        try {
            if (queryExtensionManager != null) {
                queryExtensionManager.getProxy().setup(SystemPropertyImpl.class, null);
                queryExtensionManager.getProxy().refineRetrieve(SystemPropertyImpl.class, null, builder, criteria, handler, restrictions);
                queryExtensionManager.getProxy().refineOrder(SystemPropertyImpl.class, null, builder, criteria, handler, sorts);
            }
            criteria.where(restrictions.toArray(new Predicate[restrictions.size()]));
            return em.createQuery(criteria).setHint(QueryHints.HINT_CACHEABLE, Boolean.TRUE).getResultList();
        } catch (NoResultException e) {
            LOG.error("An error has occurred ",e);
            return new ArrayList<SystemProperty>();
        } finally {
            if (queryExtensionManager != null) {
                queryExtensionManager.getProxy().breakdown(SystemPropertyImpl.class, null);
            }
        }
    }

    @Override
    public SystemProperty readSystemPropertyByName(final String name) {
        return getCachedObject(SystemProperty.class, "blSystemPropertyNullCheckCache", "SYSTEM_PROPERTY_MISSING_CACHE_HIT_RATE", new PersistentRetrieval<SystemProperty>() {
            @Override
            public SystemProperty retrievePersistentObject() {
                CriteriaBuilder builder = em.getCriteriaBuilder();
                CriteriaQuery<SystemProperty> criteria = builder.createQuery(SystemProperty.class);
                Root<SystemPropertyImpl> handler = criteria.from(SystemPropertyImpl.class);
                criteria.select(handler);

                List<Predicate> restrictions = new ArrayList<Predicate>();
                restrictions.add(builder.equal(handler.get("name"), name));

                try {
                    if (queryExtensionManager != null) {
                        queryExtensionManager.getProxy().setup(SystemPropertyImpl.class, null);
                        queryExtensionManager.getProxy().refineRetrieve(SystemPropertyImpl.class, null, builder, criteria, handler, restrictions);
                    }
                    criteria.where(restrictions.toArray(new Predicate[restrictions.size()]));

                    TypedQuery<SystemProperty> query = em.createQuery(criteria);
                    query.setHint(QueryHints.HINT_CACHEABLE, true);
                    List<SystemProperty> response = query.getResultList();
                    if (response.size() > 0) {
                        ExtensionResultHolder<List> resultHolder = new ExtensionResultHolder<>();
                        if (queryExtensionManager != null) {
                            ExtensionResultStatusType resultStatusType = queryExtensionManager.getProxy().refineResults(SystemPropertyImpl.class, null, response, resultHolder);
                            if (!resultStatusType.equals(ExtensionResultStatusType.NOT_HANDLED)) {
                                response = resultHolder.getResult();
                            }
                        }

                        BroadleafRequestContext broadleafRequestContext = BroadleafRequestContext.getBroadleafRequestContext();
                        if ((broadleafRequestContext == null || broadleafRequestContext.getNonPersistentSite() == null) && SiteDiscriminator.class.isAssignableFrom(SystemPropertyImpl.class)) {
                            for (SystemProperty prop : response) {
                                if (((SiteDiscriminator) prop).getSiteDiscriminator() == null) {
                                    return prop;
                                }
                            }
                        } else {
                            return response.get(0);
                        }
                    }
                    return null;
                } finally {
                    if (queryExtensionManager != null) {
                        queryExtensionManager.getProxy().breakdown(SystemPropertyImpl.class, null);
                    }
                }
            }
        }, name, getSite());
    }

    @Override
    public void removeFromCache(SystemProperty systemProperty) {
        String site = "";
        if (systemProperty instanceof SiteDiscriminator && ((SiteDiscriminator) systemProperty).getSiteDiscriminator() != null) {
            site = String.valueOf(((SiteDiscriminator) systemProperty).getSiteDiscriminator());
        }
        super.removeItemFromCache("blSystemPropertyNullCheckCache", systemProperty.getName(), site);
    }

    @Override
    public SystemProperty createNewSystemProperty() {
        return (SystemProperty) entityConfiguration.createEntityInstance(SystemProperty.class.getName());
    }

    @Override
    protected Log getLogger() {
        return LOG;
    }

    protected String getSite() {
        String site = "";
        BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
        if (brc != null) {
            if (brc.getSite() != null) {
                site = String.valueOf(brc.getSite().getId());
            }
        }
        return site;
    }

    @Override
    protected synchronized SystemProperty getNullObject(final Class<SystemProperty> responseClass) {
        if (nullObject == null) {
            nullObject = new NullSystemProperty();
        }
        return nullObject;
    }

}
