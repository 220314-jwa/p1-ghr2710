package test.dev.roberts;
import io.javalin.Javalin;
import test.dev.roberts.Person;
import test.dev.roberts.User;

public class ClassTest {
	
	static User[] users;
	static int index;
	
	public static void main(String[] args) {
		
		Javalin app = Javalin.create();
		app.start();
		
		users = new User[10];
		index = 0;
		
		app.get("/users", ctx -> {
			String allUsers = "";
			for(User user  : users) {
				if (user != null) {
					String uName = user.getUser();
					String pass = user.getPass();
					Person p = user.getPerson();
					String name = p.fName + " " + p.lName;
					allUsers += name + "\t" + p.role + "\t" + uName + "\t" + pass + "\n"; 
			
				}
			}
			ctx.result(allUsers);
		});
		
		app.post("/create", ctx -> {
			System.out.println(ctx.formParam("pass"));
			Person p = new Person(ctx.formParam("fName"), ctx.formParam("lName"), ctx.formParam("roles"));
			User u = new User(p, ctx.formParam("uName"), ctx.formParam("pass"));
			users[index] = u;
			index ++;
		});
		
	}
}
