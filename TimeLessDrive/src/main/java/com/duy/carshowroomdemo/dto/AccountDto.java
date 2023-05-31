package com.duy.carshowroomdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto implements UserDetails, OAuth2User {
    String id;
    String role;
    String name;
    String avatar;
    String email;
    String address;
    String gender;
    LocalDate dob;
    LocalDate joinDate;

    private OAuth2User oAuth2User;

    public AccountDto(OAuth2User oAuth2User){
        this.oAuth2User = oAuth2User;
    }

    public AccountDto(AdminDto adminDto){
        this.id = adminDto.getId();
        this.role = adminDto.getRole();
        this.name = adminDto.getName();
        this.avatar = adminDto.getAvatar();
        this.email = adminDto.getEmail();

    }

    public AccountDto(ClientDto clientDto){
        this.id = clientDto.getId();
        this.role = clientDto.getRole();
        this.name = clientDto.getName();
        this.avatar = clientDto.getAvatar();
        this.email = clientDto.getEmail();
        this.address = clientDto.getAddress();
        this.gender = clientDto.getGender();
        this.dob = clientDto.getDob();
        this.joinDate = clientDto.getJoinDate();
    }

    public AccountDto(StaffDto staffDto){
        this.id = staffDto.getId();
        this.role = staffDto.getRole();
        this.name = staffDto.getName();
        this.avatar = staffDto.getAvatar();
        this.email = staffDto.getEmail();
        this.address = staffDto.getAddress();
        this.dob = staffDto.getDob();
        this.joinDate = staffDto.getJoinDate();
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getPassword() {
        return this.getPassword();
    }

    @Override
    public String getUsername() {
        return this.getUsername();
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
}
