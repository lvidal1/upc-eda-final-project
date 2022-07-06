import java.io.*;
import java.util.LinkedList;
import java.util.List;


public class Sisinvcol {

    public static void main(String[] args) {

        List<List<String>> data = leerData();

        mostrarInventario(data);
    }

    public static void mostrarInventario(List<List<String>> data){
        System.out.printf("| %16s | %30s | %20s |\n", "CODIGO","DESCRIPCION","TIPO");
        for (int i = 0; i < 80; i++) {
            System.out.print("-");
        }

        System.out.println("");

        for (List<String> fila : data) {
            System.out.printf("| %16s | %30s | %20s |", fila.get(0), fila.get(1), fila.get(2));
            System.out.println("");
        }
    }

    public static List<List<String>> leerData(){

        List<List<String>> dataset = new LinkedList <>();

        String fila = "";
        String delimitador = ",";

        try {
            // Crear lector de archivo
            InputStream inputStream = Sisinvcol.class.getClassLoader().getResourceAsStream("data.csv");
            BufferedReader lector = new BufferedReader(new InputStreamReader(inputStream ));

            // Iterar cada fila del archivo .CSV
            while ( (fila = lector.readLine()) != null) {
                String[] detallesFila = fila.split(delimitador);
                List<String> nuevaFila = new LinkedList<>();

                // Código
                nuevaFila.add(detallesFila[0]);
                // Descripción
                nuevaFila.add(detallesFila[1]);
                // Tipo
                nuevaFila.add(detallesFila[2]);
                // Agregar nueva fila al dataset
                dataset.add(nuevaFila);
            }
        }catch (IOException e)   {
            System.out.println("No se encontró el archivo de datos. Verifique que la ruta del archivo sea correcta");
            e.printStackTrace();
        }

        return dataset;
    }
}
