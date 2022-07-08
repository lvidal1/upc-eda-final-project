import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Sisinvcol {

    // Listado de valores constantes
    public static final int ID_LAPTOP = 1;
    public static final int ID_TABLET = 2;
    public static final int ID_IMPRESORA = 3;
    public static final String TIPO_LAPTOP = "Laptop";
    public static final String TIPO_TABLET = "Tablet";
    public static final String TIPO_IMPRESORA = "Impresora";
    public static final String ESTADO_DISPONIBLE = "Disponible";
    public static final String ESTADO_REPARACION = "En reparacion";

    public static void main(String[] args) {

        // Inicia simulador de base de datos: Lectura de informacion desde archivos CSV
        // - Almacenar la información del inventario en una matriz, usando List<List<String>> (Matriz)
        List<List<String>> inventario = leerArchivo("inventario.csv");

        // - Almacenar la información del personal en una matriz, usando List<List<String>> (Matriz)
        List<List<String>> personal = leerArchivo("personal.csv");

        System.out.println("###### Sistema Sisinvcol v1.0 ######\n");

        int opcionMenu;

        // Muestra el menu de opciones en cada funcion del sistema para mejorar la experiencia,
        // hasta que el usuario decida terminarlo, ingresando la opcion 6
        do {

            // Obtiene la opcion del usuario
            opcionMenu = ingresarOpcionMenu();

            // Ejecuta una funcion especifica del sistema
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

    // Captura la opción del menu ingresada por el teclado.
    // - Solo acepta valores enteros del 1 al 6.
    // - Ingresar otro valor no se tomará en cuenta y hará que el bucle( while ) interno se repita.
    public static int ingresarOpcionMenu(){

        // Crea la variable opción y la inicia en 0.
        // Crea la variable read para almacenar temporalmente el valor ingresado
        int opcion = 0, read;

        // Crea lector
        Scanner sc = new Scanner(System.in);

        // Inicia el bucle para mostrar el menu,
        // Si la opcion es 0, siempre se mostrará el menu y pedirá ingresar una opción
        while(opcion == 0){
            System.out.println("*-------- Menu Principal -------*");
            System.out.println("1. Listar componentes inventariados");
            System.out.println("2. Listar tipos de componentes");
            System.out.println("3. Listar informacion del personal");
            System.out.println("4. Guardar nuevo componente");
            System.out.println("5. Mostrar estadisticas");
            System.out.println("6. Salir");
            System.out.println("Escoja una opcion:");

            // Lectura del valor ingresado
            read = sc.nextInt();

            // Verificamos si el valor de la opción ingresada cumple con alguna de las opciones
            if(read == 1 || read == 2 || read == 3 || read == 4 || read == 5  || read == 6){

                // Guardamos la opción
                opcion = read;

            // En caso contrario,
            }else{

                // Mostramos alerta
                System.out.println("Elija una opción válida. Intente nuevamente\n");

            }
        }
        return opcion;
    }

    // Registra un nuevo componente en la matriz del inventario
    public static void registrarNuevoComponente(List<List<String>> inventario){

        // Obtiene los datos del componente y los almacena en una lista de String
        List<String> componente = ingresarDatosNuevoComponente();

        // Agrega el nuevo componente a la matriz
        inventario.add(componente);

        // Muestra alerta
        System.out.println("Componente registrado correctamente");

        // Lista todos los datos del inventario actualizado
        listarInventario(inventario);
    }

    // Captura los datos del nuevo componente ingresados por el teclado
    public static List<String> ingresarDatosNuevoComponente(){

        // Crea lector
        Scanner sc = new Scanner(System.in);

        // Crea lista de String temporal para almacenar los datos
        List<String> componente = new LinkedList<>();

        // Mostrar las opciones disponibles para ingresar el TIPO DE COMPONENTE
        // - Solo le permitimos escoger el tipo de componente para mejorar la experiencia
        //   y mantener la integridad de la información
        System.out.println("*---- Tipos de componente disponibles -----*");
        System.out.println(ID_LAPTOP + ". "+ TIPO_LAPTOP);
        System.out.println(ID_TABLET + ". "+ TIPO_TABLET);
        System.out.println(ID_IMPRESORA + ". "+ TIPO_IMPRESORA);
        System.out.println("Escoja el tipo: ");

        // Captura el valor entero ingresado para el TIPO DE COMPONENTE
        int idTipo = sc.nextInt();

        // Captura el valor del código ingresado
        System.out.println("Codigo: ");
        String codigo = sc.next();

        // Captura el valor de la descripción ingresada
        System.out.println("Descripcion: ");
        sc.nextLine();
        String descripcion = sc.nextLine();

        // Guardamos los diferentes datos en el List componente
        // - Guardamos el código
        componente.add(codigo);

        // - Guardamos la descripción
        componente.add(descripcion);

        // - Guardamos el nombre del tipo de componente
        //      Importante! Como el valor ingresado para el tipo es un número,
        //      debemos transformar el número a un nombre del tipo de componente.
        //      Ej.  1  -> "Laptop"
        //      Ej.  2  -> "Tablet"
        //      Para eso, usamos la funcion obtenerTipoUsandoId que retorna el nombre del tipo de componente
        componente.add(obtenerTipoUsandoId(idTipo));

        // - Guardamos el estado como DISPONIBLE por defecto
        componente.add(ESTADO_DISPONIBLE);

        // Retornamos el componente con los datos capturados
        return  componente;
    }

    // Permite intercambiar un número ingresado por un texto que es el nombre del tipo de componente
    // - Se usa las constantes definidas en la clase
    public static String obtenerTipoUsandoId(int idTipo){

        // Dependiendo del número ingresado, devolverá un nombre
        switch (idTipo){
            case ID_LAPTOP: // 1
                return TIPO_LAPTOP; // "Laptop"
            case ID_TABLET: // 2
                return TIPO_TABLET; // "Tablet"
            case ID_IMPRESORA: // 2
                return TIPO_IMPRESORA; // "Impresora"
            // Otro valor, no regresa nada
            default:
                return "-";
        }
    }

    public static void listarDetalleInventario(List<List<String>> data){

        // Obtiene la informacion sobre cantidades de los componentes
        // - Filtra todos los componentes donde el Tipo sea igual a "Impresora" y los contabiliza
        Long cantidadImpresora = data.stream().filter(fila -> fila.get(2).equals(TIPO_IMPRESORA)).count();

        // - Filtra todos los componentes donde el Tipo sea igual a "Impresora" y los contabiliza
        Long cantidadLaptop = data.stream().filter(fila -> fila.get(2).equals(TIPO_LAPTOP)).count();

        // - Filtra todos los componentes donde el Tipo sea igual a "Tablet" y los contabiliza
        Long cantidadTablet = data.stream().filter(fila -> fila.get(2).equals(TIPO_TABLET)).count();

        /// Obtiene la informacion sobre el estado de los componentes
        // - Filtra todos los componentes donde el Estado sea igual a "Disponible" y los contabiliza
        Long cantidadDisponible = data.stream().filter(fila -> fila.get(3).equals(ESTADO_DISPONIBLE)).count();

        // - Filtra todos los componentes donde el Estado sea igual a "En reparación" y los contabiliza
        Long cantidadEnReparacion = data.stream().filter(fila -> fila.get(3).equals(ESTADO_REPARACION)).count();

        // Obtiene la informacion sobre los componentes en reparación
        // - Filtra todos los componentes donde el Estado sea igual a "En reparación" y los almacena
        List<List<String>> componentesEnReparacion = data.stream().filter(fila -> fila.get(3).equals(ESTADO_REPARACION)).collect(Collectors.toList());


        // Mostrar encabezado de CANTIDAD DE COMPONENTES
        System.out.println("CANTIDAD DE COMPONENTES:");
        System.out.println(crearLineaHorizontal(55));
        System.out.printf("| %10S | %10S | %10S | %10S |\n", "","LAPTOP","TABLET","IMPRESORA");
        System.out.println(crearLineaHorizontal(55));

        // Mostrar información sobre las cantidades
        System.out.printf("| %10S | %10S | %10S | %10S |\n", "CANT.",cantidadLaptop,cantidadTablet,cantidadImpresora);

        // Mostrar encabezado de ESTADO DE COMPONENTES
        System.out.println("\nESTADO DE COMPONENTES:");
        System.out.println(crearLineaHorizontal(45));
        System.out.printf("| %10S | %10S | %15S |\n", "","DISPONIBLE","EN REPARACION");
        System.out.println(crearLineaHorizontal(45));

        // Mostrar información sobre los estados
        System.out.printf("| %10S | %10S | %15S |\n", "CANT.",cantidadDisponible,cantidadEnReparacion);

        // Mostrar encabezado de COMPONENTES EN REPARACION
        System.out.println("\nCOMPONENTES EN REPARACION:");
        System.out.println(crearLineaHorizontal(47));
        System.out.printf("| %10S | %30s |\n", "CODIGO","DESCRIPCION");
        System.out.println(crearLineaHorizontal(47));

        // Recorre los componentes en reparación y los muestra
        for (List<String> fila : componentesEnReparacion) {
            System.out.printf("| %10S | %30s |", fila.get(0), fila.get(1));
            System.out.println("");
        }

    }

    // Lista la información de cada componente en el inventario
    public static void listarInventario(List<List<String>> data){

        // Muestra el encabezado para la lista
        // - Crea una linea horizontal de guiones (-) de 103 símbolos
        System.out.println(crearLineaHorizontal(103));

        // - Formatea los textos del encabezado
        //      o El formato %20s, %30s, etc permite dar el ancho a las columnas del listado
        System.out.printf("| %20s | %30s | %20s | %20s |\n", "CODIGO","DESCRIPCION","TIPO","ESTADO");

        // - Crea una linea horizontal de guiones (-) de 103 símbolos
        System.out.println(crearLineaHorizontal(103));

        // Recorre cada componente(fila) dentro de la matriz del inventario
        // - la variable data representa la matriz del inventario
        for (List<String> fila : data) {
            // Formatea los datos del componente
            // - Cada componente es un Listado (List<String>) y para obtener cada dato del componente(fila)
            //   se debe usar la función .get()
            //      Ej.
            //          fila.get(0)  -> Columna 1 -> Código
            //          fila.get(1)  -> Columna 2 -> Descripción
            //          fila.get(2)  -> Columna 3 -> Tipo
            //          fila.get(3)  -> Columna 4 -> Estado
            // El orden de las columnas se define en el archivo inventario.csv
            System.out.printf("| %20s | %30s | %20s | %20s |", fila.get(0), fila.get(1), fila.get(2), fila.get(3));
            System.out.println("");
        }
    }

    // Lista la información de cada persona en el personal
    public static void listarPersonal(List<List<String>> data){
        // Muestra el encabezado para la lista
        // - Crea una linea horizontal de guiones (-) de 80 símbolos
        System.out.println(crearLineaHorizontal(80));
        // - Formatea los textos del encabezado
        //      o El formato %20s, %30s, etc permite dar el ancho a las columnas del listado
        System.out.printf("| %20s | %30s | %20s |\n", "NOMBRE","ROL", "USUARIO");
        // - Crea una linea horizontal de guiones (-) de 80 símbolos
        System.out.println(crearLineaHorizontal(80));

        // Recorre cada persona(fila) dentro de la matriz del personal
        // - la variable data representa la matriz del personal
        for (List<String> fila : data) {
            // Formatea los dato de la persona
            // - Cada persona es un Listado (List<String>) y para obtener cada dato de la persona(fila)
            //   se debe usar la función .get()
            //      Ej.
            //          fila.get(0)  -> Columna 1 -> Nombre
            //          fila.get(1)  -> Columna 2 -> Rol
            //          fila.get(2)  -> Columna 3 -> Usuario
            // El orden de las columnas se define en el archivo personal.csv
            System.out.printf("| %20s | %30s | %20s |", fila.get(0), fila.get(1), fila.get(2));
            System.out.println("");
        }
    }

    // Lista la información de cada tipo de componentes
    public static void listarTiposComponentes(){

        // Obtiene los tipos de componentes y los almacena en listado List<String>
        List<String> tipos = obtenerTiposComponentes();

        // Muestra el encabezado para la lista
        // - Crea una linea horizontal de guiones (-) de 80 símbolos
        System.out.println(crearLineaHorizontal(80));
        // - Formatea los textos del encabezado
        System.out.printf("| %20s |\n", "TIPO");
        // - Crea una linea horizontal de guiones (-) de 80 símbolos
        System.out.println(crearLineaHorizontal(30));

        // Recorre cada tipo dentro de los
        // - la variable tipos representa el listado de componentes
        for (String tipo : tipos) {
            System.out.printf("| %20s |", tipo);
            System.out.println("");
        }
    }

    // Obtiene información de los tipos de componente
    public static List<String> obtenerTiposComponentes(){

        // Crea una lista para almacenar los tipos
        List<String> tipos = new LinkedList <>();

        // Agrega los tipos a la lista usando las constantes
        tipos.add(TIPO_LAPTOP); // Agrega el texto "Laptop"
        tipos.add(TIPO_TABLET); // Agrega el texto "Tablet"
        tipos.add(TIPO_IMPRESORA); // Agrega el texto "Impresora"

        // Retorna la lista de tipos
        return tipos;
    }

    // Lee un archivo .CSV dentro de la carpeta y almacena la información obtenida en una matriz
    // - La matriz tiene una estructura de List<List<String>> : listas dentro de una lista
    public static List<List<String>> leerArchivo(String nombreArchivo){
        
        // Crea una matriz(dataset) de forma List<List<String>>
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

                // Agregar nueva fila al matriz
                dataset.add(nuevaFila);
            }
        }catch (IOException e)   {
            System.out.println("No se encontró el archivo de datos. Verifique que la ruta del archivo sea correcta");
            e.printStackTrace();
        }

        return dataset;
    }

    // Crea un lector de archivo usando el nombre del archivo
    public static BufferedReader crearLectorArchivo(String nombreArchivo){
        InputStream inputStream = Sisinvcol.class.getClassLoader().getResourceAsStream(nombreArchivo);
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    // Crea una texto que contiene simbolos horizontales como separador
    // -    Ej1: crearLineaHorizontal(5)    ->      -----
    // -    Ej1: crearLineaHorizontal(10)    ->     ----------
    public static String crearLineaHorizontal(int numero){
        String linea = "";
        for (int i = 0; i < numero; i++) {
            linea += "-";
        }
        return linea;
    }

}
