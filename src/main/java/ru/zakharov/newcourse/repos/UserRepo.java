package ru.zakharov.newcourse.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zakharov.newcourse.domains.User;

import javax.persistence.criteria.CriteriaBuilder;

public interface UserRepo extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);
    User findUserById(Integer id);
}
