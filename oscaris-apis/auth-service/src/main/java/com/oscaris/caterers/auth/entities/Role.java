package com.oscaris.caterers.auth.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Author: kev.Ameda
 */


@Entity
@Table(name = "tbl_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String roleId;
    @Column(nullable = false,unique = true)
    private String name;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;
}
