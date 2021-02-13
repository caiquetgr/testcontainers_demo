package br.com.iteris.caiqueborges.testcontainersdemo.user.repository;

import br.com.iteris.caiqueborges.testcontainersdemo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
