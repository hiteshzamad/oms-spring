package com.ztech.order.model.entity

import jakarta.persistence.*

@Entity
@Table(name = "orders")
@NamedEntityGraph(
    name = "Order.address_payment_items_statuses",
    attributeNodes = [
        NamedAttributeNode("orderAddress"),
        NamedAttributeNode("orderPayment"),
        NamedAttributeNode("orderItems", subgraph = "orderItemProductSeller"),
        NamedAttributeNode("orderStatuses")
    ],
    subgraphs = [
        NamedSubgraph(
            name = "orderItemProductSeller",
            attributeNodes = [NamedAttributeNode("product"), NamedAttributeNode("seller")]
        )
    ]
)
@NamedEntityGraph(
    name = "Order.all",
    attributeNodes = [
        NamedAttributeNode("orderAddress"),
        NamedAttributeNode("orderPayment"),
        NamedAttributeNode("orderItems", subgraph = "orderItemProductSeller"),
        NamedAttributeNode("orderStatuses"),
        NamedAttributeNode("customer")
    ],
    subgraphs = [
        NamedSubgraph(
            name = "orderItemProductSeller",
            attributeNodes = [NamedAttributeNode("product"), NamedAttributeNode("seller")]
        )
    ]
)
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    val orderId: Int? = null
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    lateinit var orderStatuses: MutableSet<OrderStatus>

}
