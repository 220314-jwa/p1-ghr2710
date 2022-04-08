package dev.roberts;
import io.javalin.Javalin;
import io.javalin.http.HttpCode;
import java.util.Map;
import java.util.Date;
import dev.roberts.Person;
import dev.roberts.User;
import dev.roberts.Story;
import dev.roberts.DBHandler;
import dev.roberts.UserService;

public class MainApp {
	public static void main(String[] args) {
		
		UserService service = new UserService();
		
		Javalin app = Javalin.create();
		app.start();
		
		app.post("/login", ctx -> {
			User u = service.login(ctx.formParam("uName"), ctx.formParam("pass"));
			
			if (u != null) {
				System.out.println("Welcome " + u.getPerson().getName() + "!");
				ctx.json(u);
			}
			else {
				ctx.redirect("/fail");
			}
		});
		
		app.post("/create", ctx -> {
			Person p = new Person(ctx.formParam("fName"), ctx.formParam("lName"));
			User u = new User(p, ctx.formParam("uName"), ctx.formParam("pass"), ctx.formParam("roles"));
			try {
			Boolean res = service.createAcct(u);
			if (res) {
					ctx.result("Account created successfully");
				}
				else {
					ctx.result("Account creation failed");
				}
			}
			catch (Exception e) {
				String err = e.toString();
				ctx.result(err);
			}
		});
		
		
		app.get("/home", ctx -> {
			Map<String,String> credentials = ctx.bodyAsClass(Map.class);
			String uName = credentials.get("user");
			String pass = credentials.get("pass");
			User u = service.login(uName, pass);
			ctx.result("Welcome " + u.getPerson().getName() + "!");
		});
	}
}
