package bandas5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


public class BaseDatos {

    private String banda;
    private int arrayInt[];
    private String arrayString[];
    private int auxArray;
    private String bandaAzar;
    private Interfaz busca;

    public BaseDatos() {
    }

    public BaseDatos(String banda, int[] arrayInt, String[] arrayString, int auxArray, String bandaAzar) {
        this.banda = banda;
        this.arrayInt = arrayInt;
        this.arrayString = arrayString;
        this.auxArray = auxArray;
        this.bandaAzar = bandaAzar;
    }

    public String getBanda() {
        return banda;
    }

    public void setBanda(String banda) {
        this.banda = banda;
    }

    public int[] getArrayInt() {
        return arrayInt;
    }

    public void setArrayInt(int[] arrayInt) {
        this.arrayInt = arrayInt;
    }

    public String[] getArrayString() {
        return arrayString;
    }

    public void setArrayString(String[] arrayString) {
        this.arrayString = arrayString;
    }

    public int getAuxArray() {
        return auxArray;
    }

    public void setAuxArray(int auxArray) {
        this.auxArray = auxArray;
    }

    public String getBandaAzar() {
        return bandaAzar;
    }

    public void setBandaAzar(String bandaAzar) {
        this.bandaAzar = bandaAzar;
    }

    public Interfaz getBusca() {
        return busca;
    }

    public void setBusca(Interfaz busca) {
        this.busca = busca;
    }

    //Metodo se creo para que muestre la base de datos completa
    public void verBaseDatos() {
        int auxArrayLocal = 0;
        int auxArrayInt[];
        String auxArrayString[];
        int auxI = 0;

        Connection conexion = null;
        //Accedemos a la base de datos
        try {

            conexion = DriverManager.getConnection("jdbc:sqlite:bandasPrueba.db");

            if (conexion != null) {

                JOptionPane.showMessageDialog(null, "Conexion a la base de datos exitosa");

                //Asignamos el Statement y el ResultSet
                Statement s = conexion.createStatement();
                ResultSet rs = s.executeQuery("SELECT * FROM bandas_tabla");

                s.executeQuery("SELECT * FROM bandas_tabla");
                //Se recorre toda la base de datos y se guarda la cantidad de veces recorrida en la variable local auxArrayLocal
                while (rs.next()) {

                    auxArrayLocal = auxArrayLocal + 1;
                }
                //Se pasa el valor de la variable local auxArrayLocal al atributo global AuxArray
                setAuxArray(auxArrayLocal);
                //Se declaran los 2 arreglos locales con el atributo global AuxArray
                auxArrayInt = new int[getAuxArray()];
                auxArrayString = new String[getAuxArray()];
                //Se ejecuta el query
                s.executeQuery("SELECT * FROM bandas_tabla");
                //Utilizamos el rs.next con un while para recorrer todos los registros de la base de datos
                while (rs.next()) {
                    //Asignamos a las variables locales idBanda y nombreBanda sus respectivos valores sacados de la base de datos
                    int idBanda = rs.getInt("idBanda");
                    String nombreBanda = rs.getString("nombreBanda");
                    //Le pasamos los valores del idBanda y nombreBanda en la posicion en la que esta el auxI que arranca en 0
                    auxArrayInt[auxI] = idBanda;
                    auxArrayString[auxI] = nombreBanda;

                    auxI = auxI + 1;
                }
                //Asignamos los valores de los arreglos locales a los atributos globales
                setArrayInt(auxArrayInt);
                setArrayString(auxArrayString);

            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, "Error en la conexion a la base de datos");

        }

        try {
            //Cerramos la conexion a la base de datos
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(BaseDatos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Metodo  para encontrar 1 banda al azar dentro de los arreglos
    public void bandaAzar() throws SQLException {
        //Llamamos a este metodo para que se carguen los arreglos y no tenerlos vacios al momento de buscar los datos al azar
        verBaseDatos();

        int auxAzar = 0;
        int auxContador = 0;
        //Compruebo que la base de datos no este vacia, en caso de ser asi se muestra en pantalla lo siguiente:
         if (getArrayInt().length == 0 && getArrayString().length == 0) {

                JOptionPane.showMessageDialog(null, "No se puede mostrar ninguna banda al azar, ya que no hay elementos en la base de datos");
         }else{

        //Con este for sabremos la cantidad de registros que hay y nos servira para utilizarlo en la formula del Math.random
        for (int i = 0; i < getArrayString().length; i++) {

            auxContador = auxContador + 1;

        }
        //Casteamos el math.random como int, le asignamos la variable auxContador como el numero maximo que va a buscar y le decimos que el numero minimo va a ser 0
        auxAzar = (int) (Math.random() * auxContador) + 0;
        //Le asignamos a el atributo global BandaAzar un valor del arreglo ArrayString donde este valor va a ser el numero generado al azar anteriormente a traves de la variable auxAzar 
        setBandaAzar(getArrayString()[auxAzar]);
         }
    }

}
