package com.example.project_posgre.models;

import com.example.project_posgre.services.UserService;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fullname",length = 100)
    private String fullName;

    @Column(name = "phonenumber", length = 20)
    private String phoneNumber;

    @Column(name = "address",length = 200)
    private String address;

    @Column(name = "password",length = 200)
    private String password;

    @Column(name = "email",length = 200)
    private String email;
    @Column(name = "is_active",length = 200)
    private boolean active;

    @ManyToOne()
    @JoinColumn(name = "role_id")
    private Role roleId;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorityList=new ArrayList<>();
        authorityList.add((new SimpleGrantedAuthority("ROLE_"+getRoleId().getName().toUpperCase())));
//        authorityList.add((new SimpleGrantedAuthority("ROLE_USER")));
        return authorityList;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
    }
}
