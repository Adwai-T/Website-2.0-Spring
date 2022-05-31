package in.adwait.website.repositories;

import in.adwait.website.models.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface NotesRepository extends MongoRepository<Note, String> {
    public Optional<Note> findById(String id);

    public Optional<Note> findByTitle(String title);

    public List<Note> findByTopic(String topic);

    public List<Note> findByUserId(Long userId);
}
