package controllers;

import be.objectify.deadbolt.java.actions.Restrict;
import models.DatabaseType;
import org.h2.value.DataType;
import pl.edu.agh.databaseadmin.security.Secured;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.serversMain;
import views.html.admin.serversType;
import views.html.index.*;

/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 28.03.13
 * Time: 23:12
 * To change this template use File | Settings | File Templates.
 */

@Security.Authenticated(Secured.class)
@Restrict("ADMIN")
public class AdminServers extends Controller {
    public static Result allServers(){
        return ok(serversMain.render(DatabaseType.all()));
    }

    public static Result serversByType(int id) {
        return ok(serversType.render(DatabaseType.all(), id));
    }
}
