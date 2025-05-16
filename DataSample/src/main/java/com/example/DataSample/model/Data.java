package com.example.DataSample.model;

import java.io.Serializable;
import java.time.*;

import jakarta.persistence.*;
import java.util.Objects;


@Entity
public class Data implements Serializable {
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) 
    private User user;


    public Data() {}

    public Data(String domain, String mode, int scanDepth, LocalDateTime retrievedDate, String registrar, int ianaId, OffsetDateTime creationDate, OffsetDateTime expirationDate, String nameservers, String emails) {
        this.domain = domain;
        this.mode = mode;
        this.scanDepth = scanDepth;
        this.retrievedDate = retrievedDate;
        this.registrar = registrar;
        this.ianaId = ianaId;
        this.creationDate = creationDate;
        this.expirationDate = expirationDate;
        this.nameservers = nameservers;
        this.emails = emails;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getScanDepth() {
        return scanDepth;
    }

    public void setScanDepth(int scanDepth) {
        this.scanDepth = scanDepth;
    }

    public LocalDateTime getRetrievedDate() {
        return retrievedDate;
    }

    public void setRetrievedDate(LocalDateTime retrievedDate) {
        this.retrievedDate = retrievedDate;
    }

    public String getRegistrar() {
        return registrar;
    }

    public void setRegistrar(String registrar) {
        this.registrar = registrar;
    }

    public int getIanaId() {
        return ianaId;
    }

    public void setIanaId(int ianaId) {
        this.ianaId = ianaId;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public OffsetDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(OffsetDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getNameservers() {
        return nameservers;
    }

    public void setNameservers(String nameservers) {
        this.nameservers = nameservers;
    }

    public String getEmails() {
        return emails;
    }

    public void setEmails(String emails) {
        this.emails = emails;
    }

    
    @Override
    public String toString() {
        return "Data{" +
                "domain='" + domain + '\'' +
                ", mode='" + mode + '\'' +
                ", scanDepth=" + scanDepth +
                ", retrievedDate=" + retrievedDate +
                ", registrar='" + registrar + '\'' +
                ", iana id='" + ianaId + '\'' +
                ", creation date='" + creationDate+ '\'' +
                ", expiration date='" + expirationDate + '\'' +
                ", name servers='" + nameservers + '\'' +
                ", emails='" + emails + '\'' +
                '}';
    }


    @Override 
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null) return false;
        if (this.getClass() != object.getClass()) return false;
        Data data = (Data) object;
        return (domain.equals(data.domain))
            && (mode.equals(data.mode))
            && (scanDepth == (data.scanDepth))
            && (retrievedDate.equals(data.retrievedDate))
            && (registrar.equals(data.registrar))
            && (ianaId == (data.ianaId))
            && (creationDate.equals(data.creationDate))
            && (expirationDate.equals(data.expirationDate))
            && (nameservers.equals(data.nameservers))
            && (emails.equals(data.emails));
    }

    @Override
    public int hashCode() {
        return Objects.hash(domain, mode, scanDepth, retrievedDate, registrar, ianaId, creationDate, expirationDate, nameservers, emails);
    }

     
}
