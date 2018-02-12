package auction;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AuctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuctionApplication.class, args);
	}
	
	
	/*public static Scanner scanner;

	@Bean
    public CommandLineRunner init() {
		
        return new CommandLineRunner() {
        	
        	@Autowired
        	RepositoryService repositoryService;
        	
        	@Autowired
        	RuntimeService runtimeService;
        	
        	@Autowired
        	TaskService taskService;
        	
            @Override
            public void run(String... strings) throws Exception {
            	
            	AuctionApplication.scanner = new Scanner(System.in);
                Map<String, Object> variables = new HashMap<String, Object>();
                
                System.out.println("Ukupan broj deployment-a: " + repositoryService.createDeploymentQuery().count());
                ProcessDefinition pdf = (ProcessDefinition)repositoryService.createProcessDefinitionQuery().list().get(
                  repositoryService.createProcessDefinitionQuery().list().size() - 1);
                
                MockUser u = new MockUser();
                u.setUsername("zzgembo");
                u.setAddress("Novi Grad");
                u.setType(User.Type.firm);
                u.setMail("milos@localhost");
                
                variables.put("user", u);
                
                ProcessInstance pi=runtimeService.startProcessInstanceById(pdf.getId(), variables);
                System.out.println(pdf.getId());
                System.out.println(pi.getId());
                variables.put("pi", pi.getId());
                //runtimeService.startProcessInstanceByKey("hireProcess",variables);
                List<Task> tasks = taskService.createTaskQuery().active().list();
                System.out.println(tasks);
                Task t=tasks.get(0);
                taskService.complete(t.getId(),variables);
                
                System.out.println("kraj");
                /*for (Deployment d : repositoryService.createDeploymentQuery().list()) {
                  repositoryService.deleteDeployment(d.getId(), true);
                }
            }
        };

    }
*/
}
