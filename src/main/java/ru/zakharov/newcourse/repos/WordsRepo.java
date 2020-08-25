package ru.zakharov.newcourse.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.zakharov.newcourse.domains.Words;

import java.util.List;

public interface WordsRepo extends JpaRepository<Words, Integer> {

    /*@Query(value = "SELECT word FROM words GROUP BY word ORDER BY COUNT (*) DESC LIMIT 3",
            nativeQuery = true)
    List<Words> findTop3Words();*/

}
