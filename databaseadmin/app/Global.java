import models.DatabaseType;
import play.Application;
import models.Role;
import models.User;
import play.GlobalSettings;

/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 22.03.13
 * Time: 23:21
 * To change this template use File | Settings | File Templates.
 */
public class Global extends GlobalSettings {


    @Override
    public void onStart(Application application) {
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

        if(DatabaseType.count() == 0) {
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

}
