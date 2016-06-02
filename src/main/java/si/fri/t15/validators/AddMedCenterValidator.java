package si.fri.t15.validators;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import si.fri.t15.models.PO_Box;

public class AddMedCenterValidator implements Validator{
	
	@Autowired
	EntityManager em;
	
	@Override
	public boolean supports(Class<?> c) {
		return c.isAssignableFrom(AddMedCenterForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "mcname", "field.required", "Zahtevano ime zdravstvene ustanove!");
		ValidationUtils.rejectIfEmpty(errors, "pid", "field.required", "Izberite kraj oziroma poštno številko!");
		
		TypedQuery<PO_Box> qu = em.createNamedQuery("PO_Box.findAll", PO_Box.class);
		List<PO_Box> res = qu.getResultList();
		
		int[] allPOs = new int[res.size()];
		for(int i = 0; i<res.size(); i++){
			allPOs[i] = res.get(i).getPO_BoxId();
		}
		
		AddMedCenterForm u = (AddMedCenterForm) target;
		
		boolean correct = false;
		for(int i = 0; i<res.size(); i++){
			if(allPOs[i] == u.getPid()){
				correct = true;
			}
		}
		
		if(!correct){
			errors.rejectValue("pid", "field.format",
					"Neveljavna pošta oziroma kraj!");
		}
	}
}
