package in.adwait.website.repositories;

import in.adwait.website.models.FeedbackMessage;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends MongoRepository<FeedbackMessage, String> {
    Optional<FeedbackMessage> findById(String id);

    List<FeedbackMessage> findAll();

    Optional<List<FeedbackMessage>> findAllByName(String name, PageRequest of);

    Optional<List<FeedbackMessage>> findAllByContacted(Boolean contacted, PageRequest of);

    void deleteById(String id);

}
