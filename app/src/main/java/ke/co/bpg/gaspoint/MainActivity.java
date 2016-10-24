package ke.co.bpg.gaspoint;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public final static String EXTRA_MESSAGE = "ke.co.bpg.gaaspoint.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        } else if (id == R.id.nav_login) {
            startActivity(new Intent(getApplicationContext(), Login.class));
        } else if (id == R.id.nav_sign_up) {
            startActivity(new Intent(getApplicationContext(), SignUp.class));
        } else if (id == R.id.nav_help) {
            Toast.makeText(getApplicationContext(), "Submit your feedback", Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_settings) {
            Toast.makeText(getApplicationContext(), "Settings", Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void orderGas(View view) {
        Toast.makeText(getApplicationContext(), "Order placed! Please wait 15minutes for delivery", Toast.LENGTH_LONG).show();
        // Get a reference to the {@link Spinner} brands
        Spinner brand = (Spinner) findViewById(R.id.brand);
        // Get a reference to the gas weight {@link Spinner}
        Spinner gasWeight = (Spinner) findViewById(R.id.weight);

        // Get selected items in the spinners
        String brandType = String.valueOf(brand.getSelectedItem());
        String cylinderWeight = String.valueOf(gasWeight.getSelectedItem());

        // Get a reference to the location {@link EditText}
        EditText location = (EditText) findViewById(R.id.location);
        String locationString = location.getText().toString();

        // Find the checkboxes in the view hierarchy
        CheckBox mpesaCheckBox = (CheckBox) findViewById(R.id.mpesa);
        CheckBox airtelCheckBox = (CheckBox) findViewById(R.id.airtel_money);
        CheckBox cashCheckBox = (CheckBox) findViewById(R.id.cash);

        // Store the checked state in a boolean
        boolean hasMpesa = mpesaCheckBox.isChecked();
        boolean hasAirtelMoney = airtelCheckBox.isChecked();
        boolean hasCash = cashCheckBox.isChecked();

        String orderSummary = createOrderSummary(brandType, cylinderWeight, hasMpesa, hasAirtelMoney, hasCash);
        Intent intent = new Intent(getApplicationContext(), DisplayOrderActivity.class);
        intent.putExtra(EXTRA_MESSAGE, orderSummary);
        startActivity(intent);


    }

    public String createOrderSummary(String brandType, String cylinderWeight, boolean addMpesa, boolean addAirtelMoney,
                                     boolean addCash) {
        String orderMessage = "Brand: " + brandType + "\n";
        orderMessage += "Cylinder Weight: " + cylinderWeight + "\n";
        orderMessage += "Pay through Mpesa? " + addMpesa + "\n";
        orderMessage += "Pay through Airtel Money? " + addAirtelMoney + "\n";
        orderMessage += "Pay cash? " + addCash + "\n";
        //// TODO: 10/24/16 Add quantity
        orderMessage = orderMessage + "\nThank you. Our sales rep will get in touch shortly";
        return orderMessage;
    }
}
