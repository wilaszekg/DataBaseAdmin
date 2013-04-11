package controllers;

import models.AuthenticationMode;
import models.Role;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.databaseadmin.security.LdapService;
import pl.edu.agh.databaseadmin.security.Secured;
import play.Logger;
import play.data.Form;
import play.mvc.*;

@org.springframework.stereotype.Controller
public class Application extends Controller {

    @Autowired
    private LdapService ldapService;

    public static class Login {

        public String login;
        public String password;

    }

    static Form<Login> loginForm = Form.form(Login.class);

    /**
     * Login action. Clears the session and displays login view.
     * @return
     */
    public Result login() {

        session().clear();
        return ok(
                views.html.login.render(Form.form(Login.class))
        );
    }

    /**
     * An action taken after sending login form.
     * Clears user's session. Redirects to login view when user's credentials were not valid.
     * When credentials are correct, user's login is saved in session cookies. Then the controller redirects to appropriate view (user or admin view).
     * @todo When the role is neither ADMIN nor USER, a simple text is displayed. If this situation is possible it should be redirected to a separete view.
     * @return
     */
    public Result authenticate() {
        session().clear();
        // getting a filled form and a Login object from inside
        Form<Login> filledLoginForm = loginForm.bindFromRequest();
        Login login = filledLoginForm.get();

        boolean authenticationSuccessful = false;
        User user = User.findByLogin(login.login);
        if(user == null || user.authenticationMode == AuthenticationMode.LDAP) {
            try {
                try {
                authenticationSuccessful = ldapService.login(login.login, login.password);
                } catch (Exception e) {
                    filledLoginForm.reject("Wystąpił problem podczas uwierzytelniania.");
                    return badRequest(views.html.login.render(filledLoginForm));
                }
                if(user == null) {
                    user = User.findByLogin(login.login);
                }
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } else if (user.authenticationMode == AuthenticationMode.PASSWORD) {
            authenticationSuccessful = User.authenticate(login.login, login.password);

        }
        if (!authenticationSuccessful) {
            filledLoginForm.reject("Niepoprawny login lub hasło.");             // setting a global error
            return badRequest(views.html.login.render(filledLoginForm));
        } else {
            // if authenticated, setting SESSION parameters
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

    /**
     * Logout action. It clears the user's session.
     * @return
     */
    public Result logout() {
        session().clear();
        flash("success", "Wylogowałeś się");
        return redirect(
                routes.Application.login()
        );
    }

    /**
     * Main site.
     * @todo To decide where it should redirect. Maybe it should check admin or user main panel, or login site when the user is not authenticated.
     * @return
     */

    @Security.Authenticated(Secured.class)
    public Result index() {
        User user = User.findByLogin(session().get("user"));
        if (user.role == Role.ADMIN) {
            return redirect(routes.AdminPanel.index());
        } else if (user.role == Role.USER) {
            return redirect(routes.UserPanel.index());
        } else
            return ok("internal error");
    }

}
