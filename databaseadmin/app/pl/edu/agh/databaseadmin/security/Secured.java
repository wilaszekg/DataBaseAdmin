package pl.edu.agh.databaseadmin.security;

import controllers.routes;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;

import models.*;

public class Secured extends Security.Authenticator {

    /**
     * Returns a login of current user.
     * @param ctx
     * @return
     */
    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("user");
    }

    /**
     * Redirects to login site when user wasn't authorised.
     * @param ctx
     * @return
     */
    @Override
    public Result onUnauthorized(Context ctx) {
        return Results.redirect(routes.Application.login());
    }


}