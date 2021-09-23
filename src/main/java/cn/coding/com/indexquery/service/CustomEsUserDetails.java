package cn.coding.com.indexquery.service;

import cn.coding.com.indexquery.model.EsUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomEsUserDetails implements UserDetails {

    private EsUser esUser;

    public CustomEsUserDetails(EsUser esUser) {
        this.esUser = esUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return esUser.getPassword();
    }

    @Override
    public String getUsername() {
        return esUser.getUserName();
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
        return esUser.getEnable()==1?true:false;
    }
}
