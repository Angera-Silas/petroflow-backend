package com.angerasilas.petroflow_backend.entity;

import lombok.*;
import java.util.Date;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private Employees sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private Employees receiver;

    @Column(name = "message")
    private String message;

    @Column(name = "date_sent")
    private Date dateSent;

    @Column(name = "status")
    private String status;
}