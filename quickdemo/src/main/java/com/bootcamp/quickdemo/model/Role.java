package com.bootcamp.quickdemo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, unique = true, nullable = false)
    private String roleName;

    @Column(length = 255, nullable = false)
    private String description;

    @Column(name = "date_created", nullable = false)
    @Builder.Default
    private LocalDateTime dateCreated = LocalDateTime.now();

    @Column(name = "date_modified", nullable = false)
    @Builder.Default
    private LocalDateTime dateModified = LocalDateTime.now();

     @PrePersist
    protected void onCreate() {
        this.dateCreated = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dateModified = LocalDateTime.now();
    }
}
