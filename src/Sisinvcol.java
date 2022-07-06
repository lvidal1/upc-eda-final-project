import java.io.*;
import java.util.LinkedList;
import java.util.List;


public class Sisinvcol {

    public static final String TIPO_LAPTOP = "laptop";
    public static final String TIPO_TABLET = "tablet";
    public static final String TIPO_IMPRESORA = "impresora";

    public static void main(String[] args) {

        List<List<String>> inventario = leerArchivo("inventario.csv");

        List<List<String>> personal = leerArchivo("personal.csv");

        mostrarInventario(inventario);

        mostrarPersonal(personal);

        mostrarTiposComponentes();
    }

    public static void mostrarInventario(List<List<String>> data){
        System.out.println(crearLineaHorizontal(80));
        System.out.printf("| %20s | %30s | %20s |\n", "CODIGO","DESCRIPCION","TIPO");
        System.out.println(crearLineaHorizontal(80));

        for (List<String> fila : data) {
            System.out.printf("| %20s | %30s | %20s |", fila.get(0), fila.get(1), fila.get(2));
            System.out.println("");
        }
    }

    public static void mostrarPersonal(List<List<String>> data){
        System.out.println(crearLineaHorizontal(80));
        System.out.printf("| %20s | %30s | %20s |\n", "NOMBRE","ROL", "USUARIO");
        System.out.println(crearLineaHorizontal(80));

        for (List<String> fila : data) {
            System.out.printf("| %20s | %30s | %20s |", fila.get(0), fila.get(1), fila.get(2));
            System.out.println("");
        }
    }

    public static void mostrarTiposComponentes(){

        List<String> tipos = obtenerTiposComponentes();

        System.out.println(crearLineaHorizontal(80));
        System.out.printf("| %20s |\n", "TIPO");
        System.out.println(crearLineaHorizontal(30));

        for (String tipo : tipos) {
            System.out.printf("| %20s |", tipo);
            System.out.println("");
        }
    }

    public static List<String> obtenerTiposComponentes(){
        List<String> tipos = new LinkedList <>();
        tipos.add(TIPO_LAPTOP);
        tipos.add(TIPO_TABLET);
        tipos.add(TIPO_IMPRESORA);
        return tipos;
    }

    public static List<List<String>> leerArchivo(String nombreArchivo){

        List<List<String>> dataset = new LinkedList <>();

        String fila;
        String delimitador = ",";

        try {
            // Crear lector de archivo
            BufferedReader lector = crearLectorArchivo(nombreArchivo);

            // Iterar cada fila del archivo .CSV
            while ( (fila = lector.readLine()) != null) {

                // Obtener valores de celda de la fila temporalmente
                String[] detallesFila = fila.split(delimitador);

                // Crear nueva fila para alojar valores
                List<String> nuevaFila = new LinkedList<>();

                // Agregar cada celda encontrada a la nueva fila
                for (int i = 0; i < detallesFila.length; i++) {
                    nuevaFila.add(detallesFila[i]);
                }

                // Agregar nueva fila al dataset
                dataset.add(nuevaFila);
            }
        }catch (IOException e)   {
            System.out.println("No se encontró el archivo de datos. Verifique que la ruta del archivo sea correcta");
            e.printStackTrace();
        }

        return dataset;
    }

    public static BufferedReader crearLectorArchivo(String nombreArchivo){
        InputStream inputStream = Sisinvcol.class.getClassLoader().getResourceAsStream(nombreArchivo);
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    public static String crearLineaHorizontal(int numero){
        String linea = "";
        for (int i = 0; i < numero; i++) {
            linea += "-";
        }
        return linea;
    }

}
