package br.com.pontotel.pontotelteste.Webservices.Models;

/**
 * Created by andre on 23/03/16.
 *
 * Function: Store individual data of each user
 *
 */
public class User {

    public String id;
    public String name;
    public String pwd;

    public String formatData() {
        /* Return formated user data */
        String str = String.format("ID: %s\nName: %s\nPWD: %s\n", this.id, this.name, this.pwd);
        return str;
    }
}
