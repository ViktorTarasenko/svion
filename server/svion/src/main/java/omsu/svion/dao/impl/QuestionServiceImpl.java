package omsu.svion.dao.impl;

import omsu.svion.dao.QuestionService;
import omsu.svion.entities.Question;
import omsu.svion.questions.Cost;
import omsu.svion.questions.Theme;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * Created by victor on 11.05.14.
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private SessionFactory sessionFactory;
    @Transactional(readOnly = true)
    public Long getCountOfQuestionsByThemeAndCost(Theme theme, Cost cost) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery(Question.GET_COUNT_OF_QUESTIONS);
        query.setParameter("theme",theme);
        query.setParameter("cost",cost);
        return (Long) query.uniqueResult();
    }
    @Transactional(readOnly = true)
    public Long getCountOfRemainingQuestionsByThemeAndCost(Theme theme, Cost cost, List<Long> alreadyUsedIds) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery(Question.GET_COUNTS_OF_NOT_USED_QUESTIONS);
        query.setParameter("theme",theme);
        query.setParameter("cost",cost);
        query.setParameter("alreadyUsedIds",alreadyUsedIds);
        return (Long) query.uniqueResult();
    }
    @Transactional(readOnly = true)
    public List<Question> findByThemeAndCost(Theme theme, Cost cost) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery(Question.FIND_BY_THEME_AND_COST);
        query.setParameter("theme",theme);
        query.setParameter("cost",cost);
        return (List<Question>) query.list();
    }
    @Transactional(readOnly = true)
    public Question findNotUsedByThemeAndCost(Theme theme, Cost cost, List<Long> alreadyUsedIds, int questionNumber) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery(Question.FIND_BY_THEME_AND_COST_EXCLUDE);
        query.setParameter("theme",theme);
        query.setParameter("cost",cost);
        query.setParameter("alreadyUsedIds",alreadyUsedIds);
        query.setFirstResult(questionNumber);
        query.setMaxResults(1);
        return ((List<Question>) query.list()).isEmpty() ? null : ((List<Question>) query.list()).get(0);
    }
}
