package com.ztech.order.model.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "orders")
@NamedEntityGraph(
    name = "Order.address_payment_items",
    attributeNodes = [
        NamedAttributeNode("orderAddress"),
        NamedAttributeNode("orderPayment"),
        NamedAttributeNode("orderItems", subgraph = "orderItemProductSellerStatuses"),
    ],
    subgraphs = [
        NamedSubgraph(
            name = "orderItemProductSellerStatuses",
            attributeNodes = [
                NamedAttributeNode("product"),
                NamedAttributeNode("seller"),
                NamedAttributeNode("statuses")
            ]
        )
    ]
)
@NamedEntityGraph(
    name = "Order.all",
    attributeNodes = [
        NamedAttributeNode("orderAddress"),
        NamedAttributeNode("orderPayment"),
        NamedAttributeNode("orderItems", subgraph = "orderItemProductSellerStatuses"),
        NamedAttributeNode("customer")
    ],
    subgraphs = [
        NamedSubgraph(
            name = "orderItemProductSellerStatuses",
            attributeNodes = [
                NamedAttributeNode("product"),
                NamedAttributeNode("seller"),
                NamedAttributeNode("statuses")
            ]
        )
    ]
)
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    val orderId: Int? = null,
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
) {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    lateinit var customer: Customer

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    lateinit var orderAddress: OrderAddress

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "order")
    lateinit var orderPayment: OrderPayment

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    lateinit var orderItems: MutableSet<OrderItem>

}
