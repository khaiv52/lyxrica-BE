package com.vanlang.lyxircaspb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Builder
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "First name is required")
    @jakarta.validation.constraints.Size(max = 20, message = "First name should not exceed 20 characters")
    private String firstName;

    @NotNull(message = "Last name is required")
    @jakarta.validation.constraints.Size(max = 20, message = "Last name should not exceed 20 characters")
    private String lastName;

    @NotNull(message = "Password is required")
    @jakarta.validation.constraints.Size(min = 8, message = "Password should be at least 8 characters long")
    private String password;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    private String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "payment_information", joinColumns = @JoinColumn(name = "user_id"))
    private List<PaymentInformation> paymentInformations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Review> reviews = new ArrayList<>();

    private LocalDateTime createAt;

    public User() {

    }

    public User(Long id, String firstName, String lastName, String password, String email, Set<Role> roles, String phoneNumber, List<Address> address, List<PaymentInformation> paymentInformations, List<Rating> ratings, List<Review> reviews, LocalDateTime createAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.paymentInformations = paymentInformations;
        this.ratings = ratings;
        this.reviews = reviews;
        this.createAt = createAt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> userRoles = this.getRoles();
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotNull(message = "First name is required") @Size(max = 20, message = "First name should not exceed 20 characters") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull(message = "First name is required") @Size(max = 20, message = "First name should not exceed 20 characters") String firstName) {
        this.firstName = firstName;
    }

    public @NotNull(message = "Last name is required") @Size(max = 20, message = "Last name should not exceed 20 characters") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull(message = "Last name is required") @Size(max = 20, message = "Last name should not exceed 20 characters") String lastName) {
        this.lastName = lastName;
    }

    public @NotNull(message = "Password is required") @Size(min = 8, message = "Password should be at least 8 characters long") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "Password is required") @Size(min = 8, message = "Password should be at least 8 characters long") String password) {
        this.password = password;
    }

    public @NotNull(message = "Email is required") @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "Email is required") @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public List<PaymentInformation> getPaymentInformations() {
        return paymentInformations;
    }

    public void setPaymentInformations(List<PaymentInformation> paymentInformations) {
        this.paymentInformations = paymentInformations;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
