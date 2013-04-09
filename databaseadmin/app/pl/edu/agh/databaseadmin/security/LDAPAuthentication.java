package pl.edu.agh.databaseadmin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;


@Service
public class LDAPAuthentication {
    //@Autowired
    //private LdapTemplate ldapTemplate;

    public String sayHello(){
        return "helo";
    }
}
