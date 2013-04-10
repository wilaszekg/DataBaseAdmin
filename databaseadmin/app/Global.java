import models.DatabaseType;
import models.Role;
import models.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import play.Application;
import play.GlobalSettings;
import play.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 22.03.13
 * Time: 23:21
 * To change this template use File | Settings | File Templates.
 */
public class Global extends GlobalSettings {

    private ApplicationContext ctx;

    @Override
    public void onStart(Application application) {
        /*
        This is for final application version:
         */

        ctx = new ClassPathXmlApplicationContext("components.xml");

        /*
        This is for development stage:
         */
        if (User.findByLogin("user") == null) {
            User u1 = new User();
            u1.login = "user";
            u1.password = "user";
            u1.role = Role.USER;
            u1.save();
        }

        if (User.findByLogin("admin") == null) {
            User admin = new User();
            admin.login = "admin";
            admin.password = "admin";
            admin.role = Role.ADMIN;
            admin.save();
        }

        if (DatabaseType.count() == 0) {
            DatabaseType dt1 = new DatabaseType();
            dt1.vendor = "Microsoft";
            dt1.name = "MSSQL";
            dt1.version = "1.0";
            dt1.save();

            DatabaseType dt2 = new DatabaseType();
            dt2.vendor = "Postgres";
            dt2.name = "PostgreSQL";
            dt2.version = "2.0";
            dt2.save();
        }
    }

    @Override
    public <A> A getControllerInstance(Class<A> clazz) {
        // Spring components are taken from context
        if (clazz.isAnnotationPresent(Controller.class)) {
            return ctx.getBean(clazz);
        }
        else
        {
            // there are some other controllers (e.g. for security tasks)
            try {
                return super.getControllerInstance(clazz);
            } catch (Exception e) {
                Logger.error("Controller not found:" + clazz.getName());
            }
        }
        return null;
    }

}
