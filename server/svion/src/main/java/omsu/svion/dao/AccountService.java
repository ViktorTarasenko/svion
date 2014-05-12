package omsu.svion.dao;

import omsu.svion.entities.Account;

/**
 * Created by victor on 15.03.14.
 */
public interface AccountService {
    public Account findByEmail(String email);
    public void setTotalScore(Long id,int totalScore);
    public void createAccount(Account account);
}
