package controllers;

import models.Role;
import models.User;
import play.data.Form;
import play.mvc.*;

public class Application extends Controller {

    public static class Login {

        public String login;
        public String password;

    }

    static Form<Login> loginForm = Form.form(Login.class);

    public static Result login() {
        session().clear();
        return ok(
                views.html.login.render(Form.form(Login.class))
        );
    }


    public static Result authenticate() {
        session().clear();
        // getting a filled form and a Login object from inside
        Form<Login> filledLoginForm = loginForm.bindFromRequest();
        Login login = filledLoginForm.get();
        if (!User.authenticate(login.login, login.password)) {
            filledLoginForm.reject("Invalid user or password");             // settinf a global error
            return badRequest(views.html.login.render(filledLoginForm));
        } else {
            // if authenticated, setting SESSION parameters
            User user = User.findByLogin(filledLoginForm.get().login);
            session("user", user.login);
            //session("role", user.role.name());
            if (user.role == Role.ADMIN) {
                return redirect(routes.AdminPanel.index());
            } else if (user.role == Role.USER) {
                return redirect(routes.UserPanel.index());
            } else
                return ok("internal error");

        }
    }

    public static Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.Application.login()
        );
    }


    public static Result index() {
        return ok(views.html.index.render());
    }

}
