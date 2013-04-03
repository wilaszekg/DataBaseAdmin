package models;

import com.avaje.ebean.Ebean;
import org.codehaus.jackson.node.ObjectNode;
import play.Logger;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
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

    /*
    JSON helpers
     */
    @Transient
    public static final String SERVER_ID = "id";
    @Transient
    public static final String SERVER_DB_TYPE = "databaseType";
    @Transient
    public static final String SERVER_IP = "ip";
    @Transient
    public static final String SERVER_PORT = "port";
    @Transient
    public static final String SERVER_NAME = "name";
    @Transient
    public static final String SERVER_LOGIN = "login";
    @Transient
    public static final String SERVER_PASSWD = "password";

    public static Model.Finder<Long, DatabaseServer> find = new Finder<Long, DatabaseServer>(
            Long.class, DatabaseServer.class
    );

    public ObjectNode toJsonObject(){
        ObjectNode node = Json.newObject();
        node.put(SERVER_ID, id);
        node.put(SERVER_DB_TYPE, databaseType.id);
        node.put(SERVER_IP, ip);
        node.put(SERVER_PORT, port);
        node.put(SERVER_NAME, name);
        node.put(SERVER_LOGIN, login);
        node.put(SERVER_PASSWD, password);

        return node;
    }

    public static List<DatabaseServer> all() {
        return find.all();
    }

    public static List<DatabaseServer> allSorted(String sortPattern) {
        return find.filter().sort(sortPattern).filter(find.all());
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
