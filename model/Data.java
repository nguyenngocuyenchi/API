package com.example.DataSample.model;

import java.time.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "data")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 255)
    private String domain;

    @Column(nullable = false, length = 50)
    private String mode;

    @Column(nullable = false)
    private int scanDepth;

    @Column(nullable = false)
    private LocalDateTime retrievedDate;

    @Column(nullable = false, length = 255)
    private String registrar;

    @Column(nullable = false)
    private int ianaId;

    @Column(nullable = false)
    private OffsetDateTime creationDate;

    @Column(nullable = false)
    private OffsetDateTime expirationDate;

    @Column(name = "nameservers", length = 1000)
    private String nameservers;

    @Column(name = "emails", length = 1000)
    private String emails;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = true)
    private User user;
}
