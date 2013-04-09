package pl.edu.agh.databaseadmin.security;

import be.objectify.deadbolt.core.models.Subject;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import models.User;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 23.03.13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */

public class MainDeadboltHandler extends Controller implements DeadboltHandler {

    /**
     * It doesn't do anything now.
     * @param context
     * @return
     */
    @Override
    public Result beforeAuthCheck(Http.Context context) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Returns a Subject instance - in this case a User object.
     * Needed for Deadbolt.
     * @param context
     * @return User instance or null when no user is authenticated.
     */
    @Override
    public Subject getSubject(Http.Context context) {
        if (context.session().containsKey("user"))
            return User.findByLogin(context.session().get("user"));
        else
            return null;
    }


    /**
     * Invoked when Deadbolt forbids access.
     * @todo Consider changing the content displayed to a special "forbidden" site
     * @param context
     * @param s
     * @return
     */
    @Override
    public Result onAccessFailure(Http.Context context, String s) {
        return forbidden("Access forbidden");
    }

    /**
     * Returns null
     * @todo Check what possibilities it has.
     * @param context
     * @return
     */
    @Override
    public DynamicResourceHandler getDynamicResourceHandler(Http.Context context) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
