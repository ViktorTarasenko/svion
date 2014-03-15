package omsu.svion.entities;

import javax.persistence.*;

/**
 * Created by victor on 15.03.14.
 */
@Entity
@Table(name = "accounts")
@NamedQueries({
   @NamedQuery(name = Account.FIND_BY_EMAIL, query = "select a from Account a where a.email = :email")
})
public class Account extends AbstractEntity{
    public static final String FIND_BY_EMAIL  = "FIND_ACCOUNT_BY_EMAIL";
    private static final long serialVersionUID = 1L;
    @Column(name = "email",length = 255)
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
