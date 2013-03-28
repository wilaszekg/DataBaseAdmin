package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 28.03.13
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class DatabaseServer extends Model {
    @Id
    public long id;
    @ManyToOne
    public DatabaseType databaseType;
    public String ip;
    public int port;
    public String name;
    public String login;
    public String password;

    public static Model.Finder<Long, DatabaseServer> find = new Finder<Long, DatabaseServer>(
            Long.class, DatabaseServer.class
    );

    public static List<DatabaseServer> all() {
        return find.all();
    }

    public static void create(DatabaseServer databaseServer) {
        databaseServer.save();
    }

    public static void remove(long serverId) {
        find.ref(serverId).delete();
    }

    public static DatabaseServer findById(long serverId) {
        return find.byId(serverId);
    }

}
