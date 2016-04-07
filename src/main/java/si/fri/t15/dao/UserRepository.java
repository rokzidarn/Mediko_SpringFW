package si.fri.t15.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import si.fri.t15.models.user.User;

@Repository
public interface UserRepository extends CrudRepository<User, String> {

	User findByUsername(String username);

}