import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import com.remediatetheflag.global.model.Organization;
import com.remediatetheflag.global.model.OrganizationStatus;
import com.remediatetheflag.global.model.Team;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.model.UserStatus;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;
import com.remediatetheflag.global.utils.RandomGenerator;

public class StessTest {

	private static HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();
	
	public static void main(String args[]){	
		
		StessTest s = new StessTest();

		Organization o = new Organization();
		o.setDateJoined(new Date());
		o.setEmail("stresstest@remediatetheflag.com");
		o.setMaxUsers(200);
		o.setName("StressTest Org");
		o.setStatus(OrganizationStatus.ACTIVE);
		Integer idOrg = hpc.addOrganization(o);
		
		User u = hpc.getUserFromUsername("andrea");
		u.getManagedOrganizations().add(o);
		
		Team t = new Team();
		t.setName("StressTest Team");
		t.setOrganization(o);
		Integer teamId = hpc.addTeam(t);
		
		for(int i = 0; i<100; i++) {
			s.createUsers("User "+i,"Stress","stress."+i,"TestPassword7!","GB",teamId,idOrg);

		}
	}
	
	public void createUsers(String firstName, String lastName, String username, String password, String countryCode, Integer teamId, Integer orgId){
		User a = new User();
		a.setEmail(firstName+"@"+lastName+".com");
		a.setFirstName(firstName);
		a.setLastName(lastName);
		a.setRole(Constants.ROLE_USER);
		a.setUsername(username);
		String salt = RandomGenerator.getNextSalt();
		String pwd = DigestUtils.sha512Hex(password.concat(salt)); 
		a.setSalt(salt);
		a.setPassword(pwd);
		a.setStatus(UserStatus.ACTIVE);
		a.setInstanceLimit(1);
		a.setScore(0);
		a.setCountry(hpc.getCountryFromCode(countryCode));
		a.setEmailVerified(true);
		a.setForceChangePassword(false);
		a.setCredits(-1);
		a.setExercisesRun(0);
		a.setJoinedDateTime(new Date());
		a.setDefaultOrganization(hpc.getOrganizationById(orgId));
		Team team = hpc.getTeam(teamId);
		a.setTeam(team);
		hpc.addUser(a);

	}
	
}
