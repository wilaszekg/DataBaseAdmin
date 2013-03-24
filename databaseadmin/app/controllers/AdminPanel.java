package controllers;

import models.User;
import be.objectify.deadbolt.java.actions.Restrict;
import pl.edu.agh.databaseadmin.security.Secured;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.*;



/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 23.03.13
 * Time: 10:10
 * To change this template use File | Settings | File Templates.
 */

@Security.Authenticated(Secured.class)
@Restrict("ADMIN")
public class AdminPanel extends Controller {

    /**
     *
     * @todo This is just a skeleton.
     * @return
     */
    public static Result index(){
        return ok(adminPanelMain.render());
    }
    
    public static Result users(){
		return ok(usersManagement.render());
    	
    }
    
}
