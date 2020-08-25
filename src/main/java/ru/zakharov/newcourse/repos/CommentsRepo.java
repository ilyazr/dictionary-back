package ru.zakharov.newcourse.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zakharov.newcourse.domains.Comment;

public interface CommentsRepo extends JpaRepository<Comment, Integer> {

}
