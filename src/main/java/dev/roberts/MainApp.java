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
		
		Javalin app = Javalin.create(config -> {
			config.enableCorsForAllOrigins();
		});

		app.start();
		
		app.post("/login", ctx -> {
			Map<String,String> credentials = ctx.bodyAsClass(Map.class);
			String uName = credentials.get("username");
			String pass = credentials.get("password");
			User u = service.login(uName, pass);
			
			if (u != null) {
				System.out.println("Welcome " + u.getPerson().getName() + "!");
				ctx.json(u);
			}
			else {
				ctx.status(HttpCode.UNAUTHORIZED);
			}
		});
		
		app.post("/create/acct", ctx -> {
			Map<String,String> credentials = ctx.bodyAsClass(Map.class);
			String fName = credentials.get("first");
			String lName = credentials.get("last");
			String uName = credentials.get("username");
			String pass = credentials.get("password");
			String role = credentials.get("role");
			Person p = new Person(fName, lName);
			User u = new User(p, uName, pass, role);
			try {
				Boolean res = service.createAcct(u);
				if (res) {
					ctx.json(u);
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
		
		app.post("/create/story", ctx -> {
			Map<String,String> credentials = ctx.bodyAsClass(Map.class);
			User a = service.getActiveUser(credentials.get("author"));
			String title = credentials.get("title");
			String genre = credentials.get("genre");
			String blurb = credentials.get("blurb");
			String desc = credentials.get("desc");
			String day = credentials.get("day");
			java.sql.Date date = java.sql.Date.valueOf(day);
			Date d = new Date(date.getTime());
			String leng = credentials.get("len");
			int len = Integer.parseInt(leng);
			Story s = new Story(a, title, genre, blurb, desc, d, len);
			try {
				Boolean res = service.createStory(s);
				if (res) {
					ctx.result("Story Created");
				}
				else {
					ctx.result("Story creation failed");
				}
			}
			catch (Exception e) {
				String err = e.toString();
				ctx.result(err);
			}
			
		});
		
		app.post("/edit/story", ctx -> {
			Map<String,String> credentials = ctx.bodyAsClass(Map.class);
			User a = service.getActiveUser(credentials.get("author"));
			String oldTitle = credentials.get("old");
			Story[] sList = service.fetchByTitle(oldTitle, a.getUser());
			Story s = sList[0];
			String title = credentials.get("title");
			s.setTitle(title);
			String genre = credentials.get("genre");
			s.setGenre(genre);
			String blurb = credentials.get("blurb");
			s.setBlurb(blurb);
			String desc = credentials.get("desc");
			s.setDesc(desc);
			String day = credentials.get("day");
			java.sql.Date date = java.sql.Date.valueOf(day);
			Date d = new Date(date.getTime());
			s.setCompDate(d);
			String leng = credentials.get("len");
			int len = Integer.parseInt(leng);
			s.setLength(len);
			s.setStatus("Awaiting Editor Approval");
			try {
				Boolean res = service.updateStory(s);
				if (res) {
					ctx.result("Story Updated");
				}
				else {
					ctx.result("Story update failed");
				}
			}
			catch (Exception e) {
				String err = e.toString();
				ctx.result(err);
			}
			
		});
		
		app.get("/stories", ctx -> {
			Story[] sList;
			sList = service.loadSeniorEditor();
			ctx.json(sList);
		});
		
		app.post("/assign", ctx -> {
			Map<String,String> credentials = ctx.bodyAsClass(Map.class);
			User a = service.getActiveUser(credentials.get("author"));
			User e = service.getActiveUser(credentials.get("assign"));
			String oldTitle = credentials.get("old");
			Story[] sList = service.fetchByTitle(oldTitle, a.getUser());
			Story s = sList[0];
			s.setEditor(e);
			s.setTitle(s.getTitle());
			try {
				Boolean res = service.updateStory(s);
				if (res) {
					ctx.result("Story Updated");
				}
				else {
					ctx.result("Story update failed");
				}
			}
			catch (Exception ex) {
				String err = ex.toString();
				ctx.result(err);
			}
		});
		
		app.get("/stories/senior/approve/{uName}/{title}", ctx -> {
			Story[] sList;
			String pathParam1;
			if (ctx.pathParam("uName").equals("null")) {
				pathParam1 = "Unassigned";
			}
			else {
				pathParam1 = ctx.pathParam("uName");
			}
			String pathParam2 = ctx.pathParam("title");
			sList = service.fetchByTitle(pathParam2, pathParam1);
			Story s = sList[0];
			s.setTitle(s.getTitle());
			s.setStatus("Approved by Senior Editor");
			try {
				Boolean res = service.updateStory(s);
				if (res) {
					ctx.result("Story Updated");
				}
				else {
					ctx.result("Story update failed");
				}
			}
			catch (Exception e) {
				String err = e.toString();
				ctx.result(err);
			}
		});
		
		app.get("/stories/senior/reject/{uName}/{title}", ctx -> {
			Story[] sList;
			String pathParam1;
			if (ctx.pathParam("uName").equals("null")) {
				pathParam1 = "Unassigned";
			}
			else {
				pathParam1 = ctx.pathParam("uName");
			}
			String pathParam2 = ctx.pathParam("title");
			sList = service.fetchByTitle(pathParam2, pathParam1);
			Story s = sList[0];
			s.setTitle(s.getTitle());
			s.setStatus("Rejected by Senior Editor");
			try {
				Boolean res = service.updateStory(s);
				if (res) {
					ctx.result("Story Updated");
				}
				else {
					ctx.result("Story update failed");
				}
			}
			catch (Exception e) {
				String err = e.toString();
				ctx.result(err);
			}
		});
		
		app.get("/stories/editors/approve/{uName}/{title}", ctx -> {
			Story[] sList;
			String pathParam1;
			if (ctx.pathParam("uName").equals("null")) {
				pathParam1 = "Unassigned";
			}
			else {
				pathParam1 = ctx.pathParam("uName");
			}
			String pathParam2 = ctx.pathParam("title");
			sList = service.fetchByTitle(pathParam2, pathParam1);
			Story s = sList[0];
			s.setTitle(s.getTitle());
			s.setStatus("Approved by Editor");
			try {
				Boolean res = service.updateStory(s);
				if (res) {
					ctx.result("Story Updated");
				}
				else {
					ctx.result("Story update failed");
				}
			}
			catch (Exception e) {
				String err = e.toString();
				ctx.result(err);
			}
		});
		
		app.get("/stories/editors/reject/{uName}/{title}", ctx -> {
			Story[] sList;
			String pathParam1;
			if (ctx.pathParam("uName").equals("null")) {
				pathParam1 = "Unassigned";
			}
			else {
				pathParam1 = ctx.pathParam("uName");
			}
			String pathParam2 = ctx.pathParam("title");
			sList = service.fetchByTitle(pathParam2, pathParam1);
			Story s = sList[0];
			s.setTitle(s.getTitle());
			s.setStatus("Rejected by Editor");
			try {
				Boolean res = service.updateStory(s);
				if (res) {
					ctx.result("Story Updated");
				}
				else {
					ctx.result("Story update failed");
				}
			}
			catch (Exception e) {
				String err = e.toString();
				ctx.result(err);
			}
		});
		
		app.get("/stories/get/{uName}/{title}", ctx -> {
			Story[] sList;
			String pathParam1;
			if (ctx.pathParam("uName").equals("null")) {
				pathParam1 = "Unassigned";
			}
			else {
				pathParam1 = ctx.pathParam("uName");
			}
			String pathParam2 = ctx.pathParam("title");
			sList = service.fetchByTitle(pathParam2, pathParam1);
			
			ctx.json(sList);
		});
		
		app.get("/stories/author/{uName}", ctx -> {
			String pathParam = ctx.pathParam("uName");
			User u = service.getActiveUser(pathParam);
			Story[] sList;
			sList = service.loadAuthor(u);
			ctx.json(sList);
		});
		
		//Added comment
		
		app.get("/stories/editor/{uName}", ctx -> {
			String pathParam = ctx.pathParam("uName");
			User u = service.getActiveUser(pathParam);
			Story[] sList;
			sList = service.loadEditor(u);
			ctx.json(sList);
		});
		
		app.get("/users/{uName}", ctx -> {
			String pathParam = ctx.pathParam("uName");
			User u = service.getActiveUser(pathParam);
			if (u != null) {
				System.out.println("Welcome " + u.getPerson().getName() + "!");
				ctx.json(u);
			}
			else {
				ctx.status(HttpCode.UNAUTHORIZED);
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
