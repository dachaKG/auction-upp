package auction.registration;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import auction.user.User;
import auction.user.UserService;

@Component
public class RegistrationService {
	
	@Autowired
	UserService userService;

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	TaskService taskService;

	public void check(User user, String task) {
		System.out.println("ime korisnika " + user.getFirstName());
		//Task task = taskService.createTaskQuery().active().singleResult();
		System.out.println("task u registration service " + task);
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, true);

			helper.setFrom("uppauctionmain@gmail.com");
			helper.setTo(user.getEmail());
			helper.setSubject("Registratcija");
			helper.setText("Postovani korisnice da biste potvrtdili vasu registraciju molimo Vas da kliknete link ispod \n\n"
					+ ""
					+ "http://localhost:8080/confirm_registration?token="+user.getConfirmationMail()+"&task="+task);
			mailSender.send(message);
			System.out.println("poslat mail");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//helper.addAttachment(file.getName(), file);
		

		System.out.println("proslooooooo u service task");
	}
	
	public void register(User user) {
		
		System.out.println("usao u register service");
		user.setConfirmationMail(null);
		user.setConfirmed(true);
		user = userService.save(user);
		System.out.println("Korisnik " + user.getUsername() + " uspesno izvrsio registraciju.");
		
	}
	
	public void deleteUser(User user) {
		
		System.out.println("usao u delete user");
	}

}
