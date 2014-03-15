package omsu.svion.security;

import omsu.svion.entities.Account;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by victor on 15.03.14.
 */
    public class UserDetailsImpl implements UserDetails {
        private static final long serialVersionUID = 1L;
        private Account account;
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;

        }
        public String getPassword() {
            return null;
        }

        public String getUsername() {
            return account.getEmail();
        }

        public boolean isAccountNonExpired() {
            return true;
        }

        public boolean isAccountNonLocked() {
            return true;
        }

        public boolean isCredentialsNonExpired() {
            return true;
        }

        public boolean isEnabled() {
            return true;
        }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}


