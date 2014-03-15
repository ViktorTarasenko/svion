package omsu.svion.dao.impl;

import omsu.svion.dao.AccountService;
import omsu.svion.entities.Account;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by victor on 15.03.14.
 */
@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private SessionFactory sessionFactory;
    @Transactional(readOnly = true)
    public Account findByEmail(String email) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery(Account.FIND_BY_EMAIL);
        query.setParameter("email",email);
        return (Account) query.uniqueResult();
    }
    @Transactional
    public void createAccount(Account account) {
        sessionFactory.getCurrentSession().save(account);
    }
}
