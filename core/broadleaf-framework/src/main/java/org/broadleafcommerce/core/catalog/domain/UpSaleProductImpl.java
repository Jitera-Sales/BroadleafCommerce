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
package org.broadleafcommerce.core.catalog.domain;

import org.broadleafcommerce.common.copy.CreateResponse;
import org.broadleafcommerce.common.copy.MultiTenantCloneable;
import org.broadleafcommerce.common.copy.MultiTenantCopyContext;
import org.broadleafcommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import org.broadleafcommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import org.broadleafcommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import org.broadleafcommerce.common.persistence.IdOverrideTableGenerator;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serial;
import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PRODUCT_UP_SALE", indexes = {
        @Index(name = "UPSALE_PRODUCT_INDEX", columnList = "PRODUCT_ID"),
        @Index(name = "UPSALE_CATEGORY_INDEX", columnList = "CATEGORY_ID"),
        @Index(name = "UPSALE_RELATED_INDEX", columnList = "RELATED_SALE_PRODUCT_ID")
})
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blRelatedProducts")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps = true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG)
})
public class UpSaleProductImpl implements UpSaleProduct, MultiTenantCloneable<UpSaleProductImpl> {

    @Serial
    private static final long serialVersionUID = 1L;
    @ManyToOne(targetEntity = CategoryImpl.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "CATEGORY_ID")
    protected Category category;
    @Id
    @GeneratedValue(generator = "UpSaleProductId")
    @GenericGenerator(
            name = "UpSaleProductId",
            type = IdOverrideTableGenerator.class,
            parameters = {
                    @Parameter(name = "segment_value", value = "UpSaleProductImpl"),
                    @Parameter(name = "entity_name",
                            value = "org.broadleafcommerce.core.catalog.domain.UpSaleProductImpl")
            }
    )
    @Column(name = "UP_SALE_PRODUCT_ID")
    private Long id;
    @Column(name = "PROMOTION_MESSAGE")
    @AdminPresentation(friendlyName = "UpSaleProductImpl_Upsale_Promotion_Message",
            translatable = true, largeEntry = true)
    private String promotionMessage;
    @Column(name = "SEQUENCE", precision = 10, scale = 6)
    @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
    private BigDecimal sequence;
    @ManyToOne(targetEntity = ProductImpl.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;
    @ManyToOne(targetEntity = ProductImpl.class)
    @JoinColumn(name = "RELATED_SALE_PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    private Product relatedSaleProduct = new ProductImpl();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getPromotionMessage() {
        return promotionMessage;
    }

    @Override
    public void setPromotionMessage(String promotionMessage) {
        this.promotionMessage = promotionMessage;
    }

    @Override
    public BigDecimal getSequence() {
        return sequence;
    }

    @Override
    public void setSequence(BigDecimal sequence) {
        this.sequence = sequence;
    }

    @Override
    public Product getProduct() {
        return product;
    }

    @Override
    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public Product getRelatedProduct() {
        return relatedSaleProduct;
    }

    @Override
    public void setRelatedProduct(Product relatedSaleProduct) {
        this.relatedSaleProduct = relatedSaleProduct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (!getClass().isAssignableFrom(o.getClass()))
            return false;

        UpSaleProductImpl that = (UpSaleProductImpl) o;

        if (sequence != null ? !sequence.equals(that.sequence) : that.sequence != null)
            return false;
        if (category != null ? !category.equals(that.category) : that.category != null)
            return false;
        if (product != null ? !product.equals(that.product) : that.product != null)
            return false;
        if (relatedSaleProduct != null
                ? !relatedSaleProduct.equals(that.relatedSaleProduct)
                : that.relatedSaleProduct != null)
            return false;
        if (promotionMessage != null
                ? !promotionMessage.equals(that.promotionMessage)
                : that.promotionMessage != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (product != null ? product.hashCode() : 0);
        result = 31 * result + (relatedSaleProduct != null ? relatedSaleProduct.hashCode() : 0);
        result = 31 * result + (sequence != null ? sequence.hashCode() : 0);
        result = 31 * result + (promotionMessage != null ? promotionMessage.hashCode() : 0);
        return result;
    }

    @Override
    public <G extends UpSaleProductImpl> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        UpSaleProductImpl cloned = createResponse.getClone();
        if (product != null) {
            cloned.setProduct(product.createOrRetrieveCopyInstance(context).getClone());
        }
        if (category != null) {
            cloned.setCategory(category.createOrRetrieveCopyInstance(context).getClone());
        }
        cloned.setPromotionMessage(promotionMessage);
        if (relatedSaleProduct != null) {
            cloned.setRelatedProduct(relatedSaleProduct.createOrRetrieveCopyInstance(context).getClone());
        }
        cloned.setSequence(sequence);
        return createResponse;
    }

}
