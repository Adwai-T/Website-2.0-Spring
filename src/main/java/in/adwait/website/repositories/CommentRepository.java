package in.adwait.website.repositories;

import in.adwait.website.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, String> {
    public Optional<Comment> findById(String id);

    public List<Comment> findByTopic(String topic);

    public List<Comment> findByUser(String user);
}
