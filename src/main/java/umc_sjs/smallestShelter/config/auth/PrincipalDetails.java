package umc_sjs.smallestShelter.config.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import umc_sjs.smallestShelter.dto.JoinDto;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {

    private JoinDto joinDto; // 컴포지션

    public PrincipalDetails(JoinDto joinDto) {
        this.joinDto = joinDto;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(() ->{ return joinDto.getRole().toString();});

        return collector;

    }

    @Override
    public String getPassword() {
        return joinDto.getPassword();
    }

    @Override
    public String getUsername() {
        return joinDto.getUserName();
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