import models.DatabaseType;
import models.Role;
import models.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import play.Application;
import play.GlobalSettings;

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
        if (clazz.isInstance(org.springframework.stereotype.Controller.class))
            return ctx.getBean(clazz);
        else
            try {
                return super.getControllerInstance(clazz);
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        return null;
    }

}
