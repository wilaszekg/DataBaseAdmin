package pl.edu.agh.databaseadmin.security;

import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;
import play.Logger;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.List;


@Service
public class LdapService {
    @Autowired
    private LdapTemplate ldapTemplate;
    @Autowired
    private BaseNameHolder baseNameHolder;

    /**
     * Saves a User object in the database. Retrieves some information about the user from LDAP.
     * @param username LDAP nick
     * @throws Exception
     */
    private void createLdapUser(String username) throws Exception{
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", "hostObject")).and(new EqualsFilter("uid", username));
        List list = ldapTemplate.search(baseNameHolder.getName(), filter.toString(), new UserAttributesMapper());
        if(list.size() != 1){
            Logger.error("Couldn't create LDAP user. Should have found 1 user. Found: " + list.size());
            throw new Exception();
        }
        User user = (User)list.get(0);
        user.save();
    }

    /**
     * Authentication method.
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public boolean login(String username, String password) throws Exception{
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", "hostObject")).and(new EqualsFilter("uid", username));

        if (ldapTemplate.authenticate(baseNameHolder.getName(), filter.toString(), password)) {
            if(User.findByLogin(username) == null) {
                createLdapUser(username);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean userExists(String username) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", "hostObject")).and(new EqualsFilter("uid", username));
        List list = ldapTemplate.search(baseNameHolder.getName(), filter.toString(), new AttributesMapper() {
            @Override
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                return attributes.get("uid").get();
            }
        });
        if(list.size() == 1)
            return true;
        else
            return false;
    }
}
