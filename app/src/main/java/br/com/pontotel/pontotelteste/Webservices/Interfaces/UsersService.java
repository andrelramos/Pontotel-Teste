package br.com.pontotel.pontotelteste.Webservices.Interfaces;

import br.com.pontotel.pontotelteste.Webservices.Models.UsersCatalog;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by andre on 23/03/16.
 *
 * Function: This interface contains rules of retrofit requests
 *
 */
public interface UsersService {

    public static final String BASE_URL = "https://s3-sa-east-1.amazonaws.com/pontotel-docs/";

    @GET("data.json")
    Call<UsersCatalog> listUsers();
}
