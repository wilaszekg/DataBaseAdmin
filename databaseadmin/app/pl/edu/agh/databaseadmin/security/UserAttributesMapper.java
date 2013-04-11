package pl.edu.agh.databaseadmin.security;

import models.AuthenticationMode;
import models.Role;
import models.User;
import models.UserInfo;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

/**
 * Created with IntelliJ IDEA.
 * User: Grzegorz
 * Date: 11.04.13
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */
public class UserAttributesMapper implements AttributesMapper {
    @Override
    public Object mapFromAttributes(Attributes attributes) throws NamingException {
        User user = new User();
        user.authenticationMode = AuthenticationMode.LDAP;
        user.userInfo = new UserInfo();
        user.login = attributes.get("uid").get().toString();
        if(attributes.get("cn") != null){
            String[] names = attributes.get("cn").get().toString().split(" ");
            if(names.length == 2){
                user.userInfo.setFirstName(names[0]);
                user.userInfo.setSecondName(names[1]);
            } else if(attributes.get("sn") != null){
                user.userInfo.setSecondName(attributes.get("sn").get().toString());
            }
        }
        user.role = Role.USER;
        return user;

    }
}
