package ru.zakharov.newcourse.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zakharov.newcourse.domains.Dictionary;

public interface DictionaryRepo extends JpaRepository<Dictionary, Integer> {

}
