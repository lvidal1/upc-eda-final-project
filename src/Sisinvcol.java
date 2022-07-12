import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Sisinvcol {

    public static final int ID_LAPTOP = 1;
    public static final int ID_TABLET = 2;
    public static final int ID_IMPRESORA = 3;
    public static final String TIPO_LAPTOP = "Laptop";
    public static final String TIPO_TABLET = "Tablet";
    public static final String TIPO_IMPRESORA = "Impresora";
    public static final String ESTADO_DISPONIBLE = "Disponible";
    public static final String ESTADO_REPARACION = "En reparacion";

    public static final String UBI_DIRECCION ="Direccion";

    public static final String UBI_COMPUTO ="Aula de Computo";

    public static final String UBI_AULA_1 ="Aula 1";

    public static void main(String[] args) {

        // Iniciar simulador de base de datos: Lectura de informacion desde archivos CSV
        List<List<String>> inventario = leerArchivo("inventario.csv");
        List<List<String>> personal = leerArchivo("personal.csv");

        System.out.println("###### Sistema Sisinvcol v1.0 ######\n");

        int opcionMenu;

        do {
            opcionMenu = ingresarOpcionMenu();

            if(opcionMenu == 1){
                System.out.println("*-------- Listar componentes -------*\n");
                listarInventario(inventario);
            }
            if(opcionMenu == 2){
                System.out.println("*-------- Listar tipos de componentes -------*\n");
                listarTiposComponentes();
            }
            if(opcionMenu == 3){
                System.out.println("*-------- Listar informacion del personal -------*\n");
                listarPersonal(personal);
            }
            if(opcionMenu == 4){
                System.out.println("*-------- Guardar nuevo componente -------*\n");
                registrarNuevoComponente(inventario);
            }
            if(opcionMenu == 5){
                System.out.println("*-------- Estadisticas -------*\n");
                listarDetalleInventario(inventario);
            }

            System.out.println("\n");

        } while (opcionMenu != 6);

        System.out.println("\nGracias! Tenga un buen dia.");

    }

    public static int ingresarOpcionMenu(){
        int opcion = 0, read;
        Scanner sc = new Scanner(System.in);

        while(opcion == 0){
            System.out.println("*-------- Menu Principal -------*");
            System.out.println("1. Listar componentes inventariados");
            System.out.println("2. Listar tipos de componentes");
            System.out.println("3. Listar informacion del personal");
            System.out.println("4. Guardar nuevo componente");
            System.out.println("5. Mostrar estadisticas");
            System.out.println("6. Salir");

            System.out.println("Escoja una opcion:");
            read = sc.nextInt();

            if(read == 1 || read == 2 || read == 3 || read == 4 || read == 5  || read == 6){
                opcion = read;
            }else{
                System.out.println("Elija una opción válida. Intente nuevamente\n");
            }
        }
        return opcion;
    }

    public static void registrarNuevoComponente(List<List<String>> inventario){
        List<String> componente = ingresarDatosNuevoComponente();
        inventario.add(componente);
        System.out.println("Componente registrado correctamente");
        listarInventario(inventario);
    }

    public static List<String> ingresarDatosNuevoComponente(){
        Scanner sc = new Scanner(System.in);

        List<String> componente = new LinkedList<>();

        System.out.println("*---- Tipos de componente disponibles -----*");
        System.out.println(ID_LAPTOP + ". "+ TIPO_LAPTOP);
        System.out.println(ID_TABLET + ". "+ TIPO_TABLET);
        System.out.println(ID_IMPRESORA + ". "+ TIPO_IMPRESORA);
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
        componente.add(ESTADO_DISPONIBLE);
        componente.add(UBI_DIRECCION);

        return  componente;
    }

    public static String obtenerTipoUsandoId(int idTipo){
        switch (idTipo){
            case ID_LAPTOP:
                return TIPO_LAPTOP;
            case ID_TABLET:
                return TIPO_TABLET;
            case ID_IMPRESORA:
                return TIPO_IMPRESORA;
            default:
                return "-";
        }
    }

    public static void listarDetalleInventario(List<List<String>> data){
        // Informacion sobre cantidades
        Long cantidadImpresora = data.stream().filter(fila -> fila.get(2).equals(TIPO_IMPRESORA)).count();
        Long cantidadLaptop = data.stream().filter(fila -> fila.get(2).equals(TIPO_LAPTOP)).count();
        Long cantidadTablet = data.stream().filter(fila -> fila.get(2).equals(TIPO_TABLET)).count();

        // Informacion sobre estado
        Long cantidadDisponible = data.stream().filter(fila -> fila.get(3).equals(ESTADO_DISPONIBLE)).count();
        Long cantidadEnReparacion = data.stream().filter(fila -> fila.get(3).equals(ESTADO_REPARACION)).count();

        // Informacion sobre componentes en reparacion
        List<List<String>> componentesEnReparacion = data.stream().filter(fila -> fila.get(3).equals(ESTADO_REPARACION)).collect(Collectors.toList());

        System.out.println("CANTIDAD DE COMPONENTES:");

        System.out.println(crearLineaHorizontal(55));
        System.out.printf("| %10S | %10S | %10S | %10S |\n", "","LAPTOP","TABLET","IMPRESORA");
        System.out.println(crearLineaHorizontal(55));
        System.out.printf("| %10S | %10S | %10S | %10S |\n", "CANT.",cantidadLaptop,cantidadTablet,cantidadImpresora);

        System.out.println("\nESTADO DE COMPONENTES:");

        System.out.println(crearLineaHorizontal(45));
        System.out.printf("| %10S | %10S | %15S |\n", "","DISPONIBLE","EN REPARACION");
        System.out.println(crearLineaHorizontal(45));
        System.out.printf("| %10S | %10S | %15S |\n", "CANT.",cantidadDisponible,cantidadEnReparacion);

        System.out.println("\nCOMPONENTES EN REPARACION:");

        System.out.println(crearLineaHorizontal(47));
        System.out.printf("| %10S | %30s |\n", "CODIGO","DESCRIPCION");
        System.out.println(crearLineaHorizontal(47));

        for (List<String> fila : componentesEnReparacion) {
            System.out.printf("| %10S | %30s |", fila.get(0), fila.get(1));
            System.out.println("");
        }

    }

    public static void listarInventario(List<List<String>> data){
        System.out.println(crearLineaHorizontal(125));
        System.out.printf("| %20s | %30s | %20s | %20s | %20s |\n", "CODIGO","DESCRIPCION","TIPO","ESTADO", "ULT. UBICACION");
        System.out.println(crearLineaHorizontal(125));

        for (List<String> fila : data) {
            System.out.printf("| %20s | %30s | %20s | %20s | %20s |", fila.get(0), fila.get(1), fila.get(2), fila.get(3), fila.get(4));
            System.out.println("");
        }
    }

    public static void listarPersonal(List<List<String>> data){
        System.out.println(crearLineaHorizontal(80));
        System.out.printf("| %20s | %30s | %20s |\n", "NOMBRE","ROL", "USUARIO");
        System.out.println(crearLineaHorizontal(80));

        for (List<String> fila : data) {
            System.out.printf("| %20s | %30s | %20s |", fila.get(0), fila.get(1), fila.get(2));
            System.out.println("");
        }
    }

    public static void listarTiposComponentes(){
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

        // Si no existe el archivo, devolver la matriz vacia
        if(!existeArchivo(nombreArchivo)){
            return dataset;
        }

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

    public static boolean existeArchivo(String nombreArchivo){
        File f = new File(System.getProperty("user.dir")+"/src/"+nombreArchivo);
        return f.exists();
    }

    public static String crearLineaHorizontal(int numero){
        String linea = "";
        for (int i = 0; i < numero; i++) {
            linea += "-";
        }
        return linea;
    }

}
