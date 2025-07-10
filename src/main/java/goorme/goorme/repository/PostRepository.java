package goorme.goorme.repository;

import goorme.goorme.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
    public interface PostRepository extends JpaRepository<Post, Long> {
        List<Post> findByName(String name);
        long count();
    }

