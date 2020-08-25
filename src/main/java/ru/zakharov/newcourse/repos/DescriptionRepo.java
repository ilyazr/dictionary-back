package ru.zakharov.newcourse.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zakharov.newcourse.domains.Description;

public interface DescriptionRepo extends JpaRepository<Description, Integer> {

}
