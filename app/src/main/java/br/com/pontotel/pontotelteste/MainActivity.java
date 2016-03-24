package br.com.pontotel.pontotelteste;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import br.com.pontotel.pontotelteste.Webservices.Interfaces.UsersService;
import br.com.pontotel.pontotelteste.Webservices.Models.User;
import br.com.pontotel.pontotelteste.Webservices.Models.UsersCatalog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get json data from server using retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UsersService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UsersService service = retrofit.create(UsersService.class);
        Call<UsersCatalog> requestCatalog = service.listUsers();

        requestCatalog.enqueue(new Callback<UsersCatalog>() {
            @Override
            public void onResponse(Call<UsersCatalog> call, Response<UsersCatalog> response) {
                if(response.isSuccessful()) {
                    UsersCatalog catalog = response.body();

                    // Create user list to show data in ListView
                    List<String> users = new ArrayList<>();
                    for(User user : catalog.data) {
                        users.add(user.formatData());
                    }

                    ArrayAdapter adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, users);

                    ListView userList = (ListView) findViewById(R.id.userList);
                    userList.setAdapter(adapter);
                } else {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(getString(R.string.server_error_title))
                            .setMessage(getString(R.string.server_get_data_error) + ": " + response.code())
                            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Make something
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }

            @Override
            public void onFailure(Call<UsersCatalog> call, Throwable t) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle(getString(R.string.server_error_title))
                        .setMessage(getString(R.string.server_connection_error) + ": " +t.getMessage())
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Make something
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }
}
