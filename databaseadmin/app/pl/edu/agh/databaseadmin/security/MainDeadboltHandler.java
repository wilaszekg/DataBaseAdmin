package pl.edu.agh.databaseadmin.security;

import be.objectify.deadbolt.core.models.Subject;
import be.objectify.deadbolt.java.DeadboltHandler;
import be.objectify.deadbolt.java.DynamicResourceHandler;
import models.User;
import play.mvc.Http;
import play.mvc.Result;

/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 23.03.13
 * Time: 15:26
 * To change this template use File | Settings | File Templates.
 */
public class MainDeadboltHandler implements DeadboltHandler {
    @Override
    public Result beforeAuthCheck(Http.Context context) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Subject getSubject(Http.Context context) {
        if(context.session().containsKey("user"))
            return User.findByLogin(context.session().get("user"));
        else
            return null;
    }

    @Override
    public Result onAccessFailure(Http.Context context, String s) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public DynamicResourceHandler getDynamicResourceHandler(Http.Context context) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
