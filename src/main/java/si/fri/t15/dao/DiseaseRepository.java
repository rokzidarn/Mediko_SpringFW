package si.fri.t15.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import si.fri.t15.models.Disease;
import si.fri.t15.models.Medicine;
import si.fri.t15.models.user.PatientData;

@Repository
public interface DiseaseRepository extends CrudRepository<Disease, Integer> {

	@Query("SELECT c.diseases FROM Checkup c, IN(c.diseases) d "
			+ "WHERE c.patient = :patient AND d.is_allergy = 0")
	List<List<Medicine>> findByPatient(@Param(value = "patient") PatientData patient);
	
	@Query("SELECT c.diseases FROM Checkup c, IN(c.diseases) d "
			+ "WHERE c.patient = :patient AND d.is_allergy = 1")
	List<List<Medicine>> findAllergyByPatient(@Param(value = "patient") PatientData patient);
	
}
