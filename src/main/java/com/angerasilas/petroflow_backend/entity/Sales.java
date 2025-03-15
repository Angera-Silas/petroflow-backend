package com.angerasilas.petroflow_backend.entity;


import jakarta.persistence.*;
import lombok.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sales")
public class Sales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_time")
    private Date dateTime;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne
    @JoinColumn(name = "employee_no", nullable = false, referencedColumnName="employeeNo")
    private OrganizationEmployees employee;

    @ManyToOne
    @JoinColumn(name = "sell_point_id", nullable = false)
    private SellPoint sellPoint;

    @ManyToOne
    @JoinColumn(name = "shift_id", nullable = false)
    private Shift shift;

    @Column(name = "units_sold")
    private String unitsSold;

    @Column(name = "amount_billed")
    private double amountBilled;

    @Column(name = "discount")
    private double discount;

    @Column(name="amount_paid")
    private double amountPaid;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    private String paymentStatus;

    @Transient
    private double balance;

    @PostLoad
    @PostPersist
    @PostUpdate
    private void calculateBalance() {
        this.balance = (this.amountPaid + this.discount) - this.amountBilled;
    }

    @Column(name = "sale_status")
    private String status = "to be reviewed";

    

}