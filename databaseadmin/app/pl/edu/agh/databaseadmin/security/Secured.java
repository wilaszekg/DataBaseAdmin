package pl.edu.agh.databaseadmin.security;

import controllers.routes;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

public class Secured extends Security.Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("user");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return Results.redirect(routes.Application.login());
    }


}