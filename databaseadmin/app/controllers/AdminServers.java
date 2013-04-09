package controllers;

import be.objectify.deadbolt.java.actions.Restrict;
import models.DatabaseServer;
import models.DatabaseType;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import pl.edu.agh.databaseadmin.security.Secured;
import play.Logger;
import play.data.Form;
import play.data.format.Formatters;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.admin.serversMain;
import views.html.admin.serversType;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 28.03.13
 * Time: 23:12
 * To change this template use File | Settings | File Templates.
 */

@org.springframework.stereotype.Controller
@Security.Authenticated(Secured.class)
@Restrict("ADMIN")
public class AdminServers extends Controller {

    private static final String JTABLE_RECORDS = "Records";
    private static final String JTABLE_RECORD = "Record";
    private static final String JTABLE_STATUS = "OK";
    private static final String JTABLE_RESULT = "Result";
    private static final String JTABLE_OPTIONS = "Options";


    static Form<DatabaseServer> form = Form.form(DatabaseServer.class);

    static {
        Formatters.register(DatabaseType.class, new Formatters.SimpleFormatter<DatabaseType>() {
            @Override
            public DatabaseType parse(String s, Locale locale) throws ParseException {
                return DatabaseType.findById(Integer.parseInt(s));
            }

            @Override
            public String print(DatabaseType databaseType, Locale locale) {
                return databaseType.toString();
            }
        });
    }

    public Result allServers() {
        return ok(serversMain.render(DatabaseType.all()));
    }

    public Result serversByType(int id) {
        return ok(serversType.render(DatabaseType.all(), id));
    }

    public Result list() {
        List<DatabaseServer> list = DatabaseServer.allSorted(Form.form().bindFromRequest().get("jtSorting"));
        ObjectNode result = getJsonResultOK();
        ArrayNode records = result.putArray(JTABLE_RECORDS);
        for (DatabaseServer server : list) {
            records.add(server.toJsonObject());
        }
        return ok(result);
    }

    public Result create() {
        DatabaseServer newServer = getServerFromForm();
        newServer.save();
        ObjectNode result = getJsonResultOK();
        result.put(JTABLE_RECORD, newServer.toJsonObject());
        return ok(result);

    }

    public Result update() {
        DatabaseServer updatedServer = getServerFromForm();
        updatedServer.update();
        return ok(getJsonResultOK());
    }

    public Result delete() {
        DatabaseServer deletedServer = getServerFromForm();
        long id = deletedServer.id;
        DatabaseServer.remove(id);
        return ok(getJsonResultOK());

    }

    public Result typeOptions() {
        ObjectNode result = getJsonResultOK();
        ArrayNode options = result.putArray(JTABLE_OPTIONS);
        for (DatabaseType type : DatabaseType.all()) {
            options.add(type.simpleJson());
        }
        return ok(result);
    }

    private DatabaseServer getServerFromForm() {
        Form<DatabaseServer> filledForm = form.bindFromRequest();
        DatabaseServer dbServer = filledForm.get();

        return dbServer;
    }


    private ObjectNode getJsonResultOK() {
        ObjectNode result = Json.newObject();
        result.put(JTABLE_RESULT, JTABLE_STATUS);
        return result;
    }
}
