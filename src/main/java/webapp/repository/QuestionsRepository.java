package webapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import webapp.model.Dataset;
import webapp.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import webapp.model.User;

import java.util.List;

public interface QuestionsRepository extends JpaRepository<Questions,Integer>{

        List<Questions> findQuestionsByDatasetQuestion_Id(long id);

        List<Questions> findByDatasetQuestion_IdAndVersionAndRemoved(long id, int version, boolean removed);

        List<Questions> findQuestionsByQuestionSetId(long id);

        List<Questions> findQuestionsByDatasetQuestion_IdAndActiveVersion(long id, boolean activated);

        List<Questions> findAll();

        List<Questions> findAllQuestionsByDatasetQuestion_Id(long id);

        List<Questions> findQuestionsByDatasetQuestionIdAndQuestionSetId(long setId, long id);
        Questions findDistinctById(long id);
        Questions findTop1VersionByQuestionSetIdOrderByVersionDesc(long Id);
        Questions findTop1QuestionByQuestionSetIdAndAnotatorUserAndVersionGreaterThan(long id, User user, int v);
        Questions findQuestionSetIdById(long id);

        @Query("select e from Questions e where e.id = e.questionSetId and e.datasetQuestion = :datasetId")
        List<Questions> findByIdEqualsQuestionSetId(@Param("datasetId") Dataset datasetId);



}
