package ru.zakharov.newcourse.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zakharov.newcourse.domains.Words;

public interface WordsRepo extends JpaRepository<Words, Integer> {

}
