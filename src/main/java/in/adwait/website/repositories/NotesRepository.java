package in.adwait.website.repositories;

import in.adwait.website.models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NotesRepository extends MongoRepository<Note, String> {
    Optional<Note> findById(String id);

    Optional<Note> findByTitle(String title);

    List<Note> findByTopic(String topic);

    List<Note> findByUserId(Long userId);
}
