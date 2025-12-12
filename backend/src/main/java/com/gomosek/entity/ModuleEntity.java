package com.gomosek.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modules", uniqueConstraints = {
        @UniqueConstraint(columnNames = "module_uid")
})
public class ModuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_uid", nullable = false, unique = true)
    private Integer moduleUid;

    @Column(name = "display_name")
    private String name;

    @Column(name = "base_url", nullable = false)
    private String baseUrl;

    @Column(name = "last_seen")
    private Instant lastSeen;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    @OneToMany(mappedBy = "module", orphanRemoval = true)
    private List<PortEntity> ports = new ArrayList<>();

    @OneToMany(mappedBy = "module", orphanRemoval = true)
    private List<DriverEntity> drivers = new ArrayList<>();

    @OneToMany(mappedBy = "module", orphanRemoval = true)
    private List<BindingEntity> bindings = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public Integer getModuleUid() {
        return moduleUid;
    }

    public void setModuleUid(Integer moduleUid) {
        this.moduleUid = moduleUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Instant lastSeen) {
        this.lastSeen = lastSeen;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
