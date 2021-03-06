package controllers;

import java.util.List;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import be.objectify.deadbolt.java.actions.Restrict;

import models.User;
import pl.edu.agh.databaseadmin.security.Secured;
import play.Logger;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;


//TODO: validation

@org.springframework.stereotype.Controller
@Security.Authenticated(Secured.class)
@Restrict("ADMIN")
public class UsersManipulation extends Controller{

	private static final String JTABLE_RECORDS = "Records";
	private static final String JTABLE_RECORD = "Record";
	private static final String USER_ROLE = "Role";
	private static final String USER_LOGIN = "Login";
	private static final String JTABLE_STATUS = "OK";
	private static final String JTABLE_RESULT = "Result";

	static Form<User> userForm = Form.form(User.class);

	public Result list(){

		List<User> list = User.all();
		ObjectNode result = getJsonResultOK();
		ArrayNode records = result.putArray(JTABLE_RECORDS);
		for(User u: list){
			ObjectNode row = Json.newObject();
			row.put(USER_LOGIN, u.login);
			row.put(USER_ROLE, u.role.toString());
			records.add(row);
		}
		return ok(result);
	}


	public Result create(){
		User user = getUserFromForm();
		user.save();

		ObjectNode result = getJsonResultOK();
		ObjectNode userNode = Json.newObject();
		userNode.put(USER_LOGIN, user.login);
		userNode.put(USER_ROLE, user.role.toString());
		result.put(JTABLE_RECORD, userNode);

		return ok(result);
	}

	public Result update(){//TODO: not update empty fields
		User user = getUserFromForm();
		user.update();
		return ok(getJsonResultOK());
	}

	public Result delete(){
		User user = getUserFromForm();
		String login = user.login;
		User.remove(login);
		return ok(getJsonResultOK());
	}

	private static ObjectNode getJsonResultOK() {
		ObjectNode result = Json.newObject();
		result.put(JTABLE_RESULT, JTABLE_STATUS);
		return result;
	}

	private static User getUserFromForm() {
		Form<User> filledUserForm = userForm.bindFromRequest();
		User user = filledUserForm.get();
		return user;
	}
}
