package omsu.svion.dao;

import omsu.svion.entities.Question;
import omsu.svion.questions.Cost;
import omsu.svion.questions.Theme;

import java.util.List;

/**
 * Created by victor on 11.05.14.
 */
public interface QuestionService {
    public Object getCountOfQuestionsByThemeAndCost(Theme theme, Cost cost);
    public Long getCountOfRemainingQuestionsByThemeAndCost(Theme theme,Cost cost,List<Long> alreadyUsedIds);
    public List<Question> findByThemeAndCost(Theme theme, Cost cost);
    public Question findNotUsedByThemeAndCost(Theme theme, Cost cost, List<Long> alreadyUsedIds, int questionNumber);

}
