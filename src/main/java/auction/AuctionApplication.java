package auction;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import auction.user.UserService;


@SpringBootApplication
public class AuctionApplication {
	
	/*@Autowired
	RepositoryService repositoryService;
	
	
	@Autowired
	static IdentityService identityService;
	
	@Autowired
	static UserService userService;*/
	
	@Autowired
	RepositoryService repositoryService;
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	UserService userService;

	public static void main(String[] args) {
		/*
		long groupsNum =  identityService.createGroupQuery().count();
		if (groupsNum == 0)
			initGroupsYml();
		long usersNum = identityService.createUserQuery().count();
		if (usersNum == 0)
			initUsersYml();*/
		SpringApplication.run(AuctionApplication.class, args);
	}
	
	/*@Bean
	public List<User> allUsers(){
		
		
		Group newGroup;
		for(int i = 0 ; i < EnumRole.values().length; i++){
				
			newGroup = identityService.newGroup(EnumRole.values()[i].toString());
			newGroup.setName(EnumRole.values()[i].toString());
			newGroup.setType("assigment");
			identityService.saveGroup(newGroup);
		}
		User newUser;
		List<auction.user.User> users = userService.findAll();
		List<User> identUsers = new ArrayList<User>();
		for(int i = 0 ; i < users.size(); i++) {
			newUser = identityService.newUser(users.get(i).getUsername());
			newUser.setFirstName(users.get(i).getFirstName());
			newUser.setLastName(users.get(i).getLastName());
			newUser.setEmail(users.get(i).getEmail());
			newUser.setPassword(users.get(i).getPassword());
			identityService.saveUser(newUser);
			identityService.createMembership(newUser.getId(), users.get(i).getRole().toString());
			identUsers.add(newUser);
		}	
		
		return identUsers;
		
	}*/
	/*private static void initGroupsYml(){
	
		Group newGroup;
		for(int i = 0 ; i < EnumRole.values().length; i++){
				
			newGroup = identityService.newGroup(EnumRole.values()[i].toString());
			newGroup.setName(EnumRole.values()[i].toString());
			newGroup.setType("assigment");
			identityService.saveGroup(newGroup);
		}
		
	}
	
	private static void initUsersYml(){
		User newUser;
		
		List<auction.user.User> users = userService.findAll();
		for(int i = 0 ; i < users.size(); i++) {
			newUser = identityService.newUser(users.get(i).getUsername());
			newUser.setFirstName(users.get(i).getFirstName());
			newUser.setLastName(users.get(i).getLastName());
			newUser.setEmail(users.get(i).getEmail());
			newUser.setPassword(users.get(i).getPassword());
			identityService.saveUser(newUser);
			identityService.createMembership(newUser.getId(), users.get(i).getRole().toString());
		}	
			
	}*/
	
	
	/*@Bean
	public List<Deployment> clearAll() {
		System.out.println("clear -----------");
		for (Deployment d : repositoryService.createDeploymentQuery().list()) {
            repositoryService.deleteDeployment(d.getId(), true);
        }
		
		return repositoryService.createDeploymentQuery().list();
	}
	*/
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
