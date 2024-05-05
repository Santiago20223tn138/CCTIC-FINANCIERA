package financiera.CCTIC.security.model;
import financiera.CCTIC.person.model.Person;
import financiera.CCTIC.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AuthUser implements UserDetails {
    String username;
    String password;
    private Person person;
    private int status;
    public Collection<? extends GrantedAuthority> authorities;

    public AuthUser(String username, String password, Person person, int status, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.person = person;
        this.status = status;
        this.authorities = authorities;
    }
    public static AuthUser build(User user){
        List<GrantedAuthority> authorities =
                user.getAuthorities()
                        .stream()
                        .map(rol -> new SimpleGrantedAuthority(rol.getDescription()))
                        .collect(Collectors.toList());
        return new AuthUser(user.getUsername(), user.getPassword(), user.getPerson(),user.getStatus(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
