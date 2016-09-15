package ccv.dam.isi.frsf.utn.edu.ar.lab02c2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {

    ElementoMenu[] listaBebidas;
    ElementoMenu[] listaPlatos;
    ElementoMenu[] listaPostre;
    Boolean confirmado = false;
    DecimalFormat f = new DecimalFormat("##.00");
    private String TAG;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] horarios = new String[]{"20:00", "20:30", "21:00", "21:30", "22:00"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> listaHorarios = new ArrayList<String>();
        listaHorarios.addAll(Arrays.asList(horarios));
        iniciarListas();

        ArrayAdapter<String> listAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listaHorarios);
        listAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(listAdapter1);
        final ArrayList<ElementoMenu> pedido = new ArrayList<ElementoMenu>();
        Button botonAgregar = (Button) findViewById(R.id.buttonAgregar);
        Button botonReiniciar = (Button) findViewById(R.id.buttonReiniciar);
        Button botonConfirmar = (Button) findViewById(R.id.buttonConfirmar);
        final TextView itemsAgregados = (TextView) findViewById(R.id.textView3);
        final ListView listaSeleccionada = (ListView) findViewById(R.id.listaSeleccionada);
        final ArrayList<ElementoMenu> lista  = new ArrayList<ElementoMenu>();
        final ArrayAdapter<ElementoMenu> seleccion = new ArrayAdapter<ElementoMenu>(this,android.R.layout.simple_list_item_multiple_choice, lista);
        listaSeleccionada.setAdapter(seleccion);


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup arg0, int id) {
                lista.clear();

                switch (id) {
                    case -1:
                        Log.v(TAG, "Choicescleared!");
                        break;
                    case R.id.radioPlato:
                        lista.addAll(Arrays.asList(listaPlatos));
                        seleccion.notifyDataSetChanged();
                        Log.v(TAG, "Plato");
                        break;
                    case R.id.radioPostre:
                        lista.addAll(Arrays.asList(listaPostre));
                        seleccion.notifyDataSetChanged();
                        Log.v(TAG, "Postre");
                        break;
                    case R.id.radioBebida:
                        lista.addAll(Arrays.asList(listaBebidas));
                        seleccion.notifyDataSetChanged();
                        Log.v(TAG, "Bebida");
                        break;
                }
            }
        });

        listaSeleccionada.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
        });

        itemsAgregados.setMovementMethod(new ScrollingMovementMethod());

        botonAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = listaSeleccionada.getCount();
                SparseBooleanArray viewItems = listaSeleccionada.getCheckedItemPositions();
                if(confirmado)
                    Toast.makeText(getApplicationContext(), "El pedido ya ha sido confirmado", Toast.LENGTH_LONG).show();
                else{
                    if(listaSeleccionada.getCheckedItemCount()==0)
                        Toast.makeText(getApplicationContext(), "Debe seleccionar al menos un elemento de menu", Toast.LENGTH_LONG).show();
                    else {
                        for (int i = 0; i < count; i++) {
                            if (viewItems.get(i)) {
                                itemsAgregados.append(listaSeleccionada.getItemAtPosition(i).toString() + "\n");
                                pedido.add((ElementoMenu) listaSeleccionada.getItemAtPosition(i));
                            }
                        }
                    }
                }
                }

            });

        botonReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemsAgregados.setText("");
                pedido.clear();
                confirmado = false;

            }

        });

        botonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Double costo=0.00;
                if(confirmado)
                    Toast.makeText(getApplicationContext(), "El pedido ya ha sido confirmado", Toast.LENGTH_LONG).show();
                else {
                    for (int i = 0; i < pedido.size(); i++) {
                        costo += pedido.get(i).getPrecio();
                    }
                    //itemsAgregados.append("\n\nEl Precio total del pedido es: $" + costo.toString());
                    itemsAgregados.append("\n\nEl Precio total del pedido es: $" + format("%.2f", costo) );

                    confirmado = true;
                }
            }

        });

        }


    class ElementoMenu {
        private Integer id;
        private String nombre;
        private Double precio;

        public ElementoMenu() {
        }

        public ElementoMenu(Integer i, String n, Double p) {
            this.setId(i);
            this.setNombre(n);
            this.setPrecio(p);
        }

        public ElementoMenu(Integer i, String n) {
            this(i, n, 0.0);
            Random r = new Random();
            this.precio = (r.nextInt(3) + 1) * ((r.nextDouble() * 100));
        }


        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public Double getPrecio() {
            return precio;
        }

        public void setPrecio(Double precio) {
            this.precio = precio;
        }

        @Override
        public String toString() {
            return this.nombre + "( " + f.format(this.precio) + ")";
        }
    }

    private void iniciarListas() {
        // inicia lista de bebidas
        listaBebidas = new ElementoMenu[7];
        listaBebidas[0] = new ElementoMenu(1, "Coca");
        listaBebidas[1] = new ElementoMenu(4, "Jugo");
        listaBebidas[2] = new ElementoMenu(6, "Agua");
        listaBebidas[3] = new ElementoMenu(8, "Soda");
        listaBebidas[4] = new ElementoMenu(9, "Fernet");
        listaBebidas[5] = new ElementoMenu(10, "Vino");
        listaBebidas[6] = new ElementoMenu(11, "Cerveza");
        // inicia lista de platos
        listaPlatos = new ElementoMenu[14];
        listaPlatos[0] = new ElementoMenu(1, "Ravioles");
        listaPlatos[1] = new ElementoMenu(2, "Gnocchi");
        listaPlatos[2] = new ElementoMenu(3, "Tallarines");
        listaPlatos[3] = new ElementoMenu(4, "Lomo");
        listaPlatos[4] = new ElementoMenu(5, "Entrecot");
        listaPlatos[5] = new ElementoMenu(6, "Pollo");
        listaPlatos[6] = new ElementoMenu(7, "Pechuga");
        listaPlatos[7] = new ElementoMenu(8, "Pizza");
        listaPlatos[8] = new ElementoMenu(9, "Empanadas");
        listaPlatos[9] = new ElementoMenu(10, "Milanesas");
        listaPlatos[10] = new ElementoMenu(11, "Picada 1");
        listaPlatos[11] = new ElementoMenu(12, "Picada 2");
        listaPlatos[12] = new ElementoMenu(13, "Hamburguesa");
        listaPlatos[13] = new ElementoMenu(14, "Calamares");
        // inicia lista de postres
        listaPostre = new ElementoMenu[15];
        listaPostre[0] = new ElementoMenu(1, "Helado");
        listaPostre[1] = new ElementoMenu(2, "Ensalada de Frutas");
        listaPostre[2] = new ElementoMenu(3, "Macedonia");
        listaPostre[3] = new ElementoMenu(4, "Brownie");
        listaPostre[4] = new ElementoMenu(5, "Cheescake");
        listaPostre[5] = new ElementoMenu(6, "Tiramisu");
        listaPostre[6] = new ElementoMenu(7, "Mousse");
        listaPostre[7] = new ElementoMenu(8, "Fondue");
        listaPostre[8] = new ElementoMenu(9, "Profiterol");
        listaPostre[9] = new ElementoMenu(10, "Selva Negra");
        listaPostre[10] = new ElementoMenu(11, "Lemon Pie");
        listaPostre[11] = new ElementoMenu(12, "KitKat");
        listaPostre[12] = new ElementoMenu(13, "IceCreamSandwich");
        listaPostre[13] = new ElementoMenu(14, "Frozen Yougurth");
        listaPostre[14] = new ElementoMenu(15, "Queso y Batata");

    }
}
