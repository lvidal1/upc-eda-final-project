import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class Sisinvcol {

    public static final String TIPO_LAPTOP = "Laptop";
    public static final String TIPO_TABLET = "Tablet";
    public static final String TIPO_IMPRESORA = "Impresora";

    public static void main(String[] args) {

        List<List<String>> inventario = leerArchivo("inventario.csv");

        List<List<String>> personal = leerArchivo("personal.csv");
/*
        mostrarInventario(inventario);

        mostrarPersonal(personal);

        mostrarTiposComponentes();

        mostrarDetalleInventario(inventario);*/

        registrarNuevoComponente(inventario);

        mostrarInventario(inventario);
    }

    public static void registrarNuevoComponente(List<List<String>> data){

        List<String> componente = leerNuevoComponente();
        data.add(componente);
        System.out.println("Componente registrado correctamente");
    }

    public static List<String> leerNuevoComponente(){
        Scanner sc = new Scanner(System.in);

        List<String> componente = new LinkedList<>();

        System.out.println("*---- Tipos de componente disponibles -----*");
        System.out.println("1. "+ TIPO_LAPTOP);
        System.out.println("2. "+ TIPO_TABLET);
        System.out.println("3. "+ TIPO_IMPRESORA);
        System.out.println("Escoja el tipo: ");
        int idTipo = sc.nextInt();

        System.out.println("Codigo: ");
        String codigo = sc.next();

        System.out.println("Descripcion: ");
        sc.nextLine();
        String descripcion = sc.nextLine();

        componente.add(codigo);
        componente.add(descripcion);
        componente.add(obtenerTipoUsandoId(idTipo));

        return  componente;
    }

    public static String obtenerTipoUsandoId(int idTipo){
        switch (idTipo){
            case 1:
                return TIPO_LAPTOP;
            case 2:
                return TIPO_TABLET;
            case 3:
                return TIPO_IMPRESORA;
            default:
                return "-";
        }
    }

    public static void mostrarDetalleInventario(List<List<String>> data){
        Long cantidadImpresora = data.stream().filter(fila -> fila.get(2).equals(TIPO_IMPRESORA)).count();
        Long cantidadLaptop = data.stream().filter(fila -> fila.get(2).equals(TIPO_LAPTOP)).count();
        Long cantidadTablet = data.stream().filter(fila -> fila.get(2).equals(TIPO_TABLET)).count();

        System.out.println("CANTIDAD DE COMPONENTES:\n");

        System.out.println(crearLineaHorizontal(55));
        System.out.printf("| %10S | %10S | %10S | %10S |\n", "","LAPTOP","TABLET","IMPRESORA");
        System.out.println(crearLineaHorizontal(55));
        System.out.printf("| %10S | %10S | %10S | %10S |\n", "CANT.",cantidadLaptop,cantidadTablet,cantidadImpresora);
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
