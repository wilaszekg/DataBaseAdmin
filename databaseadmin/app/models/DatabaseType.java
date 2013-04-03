package models;

import org.codehaus.jackson.node.ObjectNode;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 28.03.13
 * Time: 21:17
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class DatabaseType extends Model implements Comparable<DatabaseType> {
    @Override
    public int compareTo(DatabaseType databaseType) {
        return this.toString().compareTo(databaseType.toString());
    }

    @Id
    public int id;

    public String vendor;
    public String name;
    public String version;

    @OneToMany
    public List<DatabaseServer> databaseServers;

    /*
    JSON helpers
     */
    @Transient
    static final String TYPE_ID = "Id";
    @Transient
    static final String TYPE_VENDOR = "Vendor";
    @Transient
    static final String TYPE_NAME = "Name";
    @Transient
    static final String TYPE_VERSION = "Version";


    public static Model.Finder<Integer, DatabaseType> find = new Finder<Integer, DatabaseType>(
            Integer.class, DatabaseType.class
    );

    public String toString(){
        if(version != null)
            return name + " " + version;
        else
            return name;

    }

    public ObjectNode toJsonObject() {
        ObjectNode node = Json.newObject();

        node.put(TYPE_ID, id);
        node.put(TYPE_NAME, name);
        node.put(TYPE_VERSION, version);
        node.put(TYPE_VENDOR, vendor);

        return node;
    }

    public ObjectNode simpleJson(){
        ObjectNode node = Json.newObject();
        node.put("Value", id);
        node.put("DisplayText", this.toString());
        return node;
    }

    public static List<DatabaseType> all() {
        return find.all();
    }

    public static void create(DatabaseType databaseType) {
        databaseType.save();
    }

    public static void remove(int typeId) {
        find.ref(typeId).delete();
    }

    public static DatabaseType findById(int typeId) {
        return find.byId(typeId);
    }

    public static int count() {
        return find.findRowCount();
    }

}
