package controllers;

import be.objectify.deadbolt.java.actions.Restrict;
import pl.edu.agh.databaseadmin.security.Secured;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.adminPanel;

/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 23.03.13
 * Time: 10:10
 * To change this template use File | Settings | File Templates.
 */

@Security.Authenticated(Secured.class)
//@Restrict("admin")
public class AdminPanel extends Controller {
    public static Result index(){
        return ok(adminPanel.render());

    }
}
