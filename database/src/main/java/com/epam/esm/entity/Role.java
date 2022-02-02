package com.epam.esm.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Audited
@Builder
@Getter
@Setter
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "name", length = 45, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private final List<User> users = new ArrayList<>();

    public Role() {}

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(String name) {
        this.name = name;
        switch (name.toUpperCase()) {
            case "ADMIN": this.id = 1L;
            case "USER": this.id = 2L;
            default: this.id = 2L;
        }
    }

    public Role(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Role role = (Role) obj;
        return id.equals(role.id) &&
                name.equals(role.name);
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;

        result = result * prime + (id != null ? id.hashCode() : 0);
        result = result * prime + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name;
    }
}
