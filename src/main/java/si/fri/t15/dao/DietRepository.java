package si.fri.t15.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import si.fri.t15.models.Diet;
import si.fri.t15.models.Medicine;
import si.fri.t15.models.user.PatientData;

@Repository
public interface DietRepository extends CrudRepository<Diet, Integer> {

	@Query("SELECT c.diets FROM Checkup c "
			+ "WHERE c.patient = :patient")
	List<List<Diet>> findByPatient(@Param(value = "patient") PatientData patient);
	
}
