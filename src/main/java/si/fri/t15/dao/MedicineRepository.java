package si.fri.t15.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import si.fri.t15.models.Medicine;
import si.fri.t15.models.user.PatientData;

@Repository
public interface MedicineRepository extends CrudRepository<Medicine, Integer> {

	@Query("SELECT c.medicines FROM Checkup c "
			+ "WHERE c.patient = :patient")
	List<List<Medicine>> findByPatient(@Param(value = "patient") PatientData patient);
	
	@Query("SELECT c.medicines FROM Checkup c "
			+ "WHERE c.id = :id")
	List<List<Medicine>> findByCheckupID(@Param(value = "id") int id);
	
}
