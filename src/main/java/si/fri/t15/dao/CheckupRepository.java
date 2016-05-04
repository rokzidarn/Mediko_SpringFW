package si.fri.t15.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import si.fri.t15.models.Checkup;
import si.fri.t15.models.user.DoctorData;
import si.fri.t15.models.user.PatientData;

@Repository
public interface CheckupRepository extends CrudRepository<Checkup, Integer>{
	
	//Checkup findByID(int id);
	
	@Query("SELECT c FROM Checkup c "
			+ "WHERE c.id = :id")
	Checkup findCheckup(@Param(value = "id") int id);
	
	@Query("SELECT c.patient FROM Checkup c, IN(c.patient) p "
			+ "WHERE c.id = :id")
	PatientData findPatientByCheckup(@Param(value = "id") int id);
	
	@Query("SELECT c.doctor FROM Checkup c, IN(c.doctor) p "
			+ "WHERE c.id = :id")
	List<DoctorData> findDoctorsByCheckup(@Param(value = "id") int id);
}
