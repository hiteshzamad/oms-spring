package com.ztech.order.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
@NamedEntityGraph(
    name = "OrderWithAddressAndPaymentAndPurchaseItemsWithProduct",
    attributeNodes = [
        NamedAttributeNode("address"),
        NamedAttributeNode("payment"),
        NamedAttributeNode("purchaseItems", subgraph = "PurchaseItemsWithProduct"),
    ],
    subgraphs = [
        NamedSubgraph(
            name = "PurchaseItemsWithProduct",
            attributeNodes = [
                NamedAttributeNode("product"),
            ]
        )
    ]
)
@NamedEntityGraph(
    name = "OrderWithAddressAndPaymentAndPurchaseItemsWithProductAndSeller",
    attributeNodes = [
        NamedAttributeNode("address"),
        NamedAttributeNode("payment"),
        NamedAttributeNode("purchaseItems", subgraph = "PurchaseItemsWithProductAndSeller"),
    ],
    subgraphs = [
        NamedSubgraph(
            name = "PurchaseItemsWithProductAndSeller",
            attributeNodes = [
                NamedAttributeNode("product"),
                NamedAttributeNode("seller"),
            ]
        )
    ]
)
@NamedEntityGraph(
    name = "OrderWithAddressAndPaymentAndPurchaseItemsWithProductAndTrackers",
    attributeNodes = [
        NamedAttributeNode("address"),
        NamedAttributeNode("payment"),
        NamedAttributeNode("purchaseItems", subgraph = "PurchaseItemsWithProductAndTrackers"),
    ],
    subgraphs = [
        NamedSubgraph(
            name = "PurchaseItemsWithProductAndTrackers",
            attributeNodes = [
                NamedAttributeNode("product"),
                NamedAttributeNode("trackers")
            ]
        )
    ]
)
@NamedEntityGraph(
    name = "OrderWithCustomerAndAddressAndPaymentAndPurchaseItemsWithProductAndSellerAndTrackers",
    attributeNodes = [
        NamedAttributeNode("customer"),
        NamedAttributeNode("address"),
        NamedAttributeNode("payment"),
        NamedAttributeNode("purchaseItems", subgraph = "PurchaseItemsWithProductAndSellerAndTrackers"),
    ],
    subgraphs = [
        NamedSubgraph(
            name = "PurchaseItemsWithProductAndSellerAndTrackers",
            attributeNodes = [
                NamedAttributeNode("product"),
                NamedAttributeNode("seller"),
                NamedAttributeNode("trackers")
            ]
        )
    ]
)
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    val id: Int? = null,
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    lateinit var customer: Customer

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    lateinit var address: DeliveryAddress

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    lateinit var payment: Payment

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    lateinit var purchaseItems: MutableSet<PurchaseItem>

}
