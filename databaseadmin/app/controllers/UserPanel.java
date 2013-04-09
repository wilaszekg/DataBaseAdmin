package controllers;

import be.objectify.deadbolt.java.actions.Restrict;
import pl.edu.agh.databaseadmin.security.Secured;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.user.userPanel;

/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 23.03.13
 * Time: 10:10
 * To change this template use File | Settings | File Templates.
 */

@org.springframework.stereotype.Controller
@Security.Authenticated(Secured.class)
@Restrict("USER")
public class UserPanel extends Controller {

    /**
     *
     * @todo This is just a skeleton.
     * @return
     */
    public Result index(){
        return ok(userPanel.render());

    }
}
