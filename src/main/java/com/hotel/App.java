package com.hotel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * Gestió de reserves d'un hotel.
 */
public class App {

    // --------- CONSTANTS I VARIABLES GLOBALS ---------

    // Tipus d'habitació
    public static final String TIPUS_ESTANDARD = "Estàndard";
    public static final String TIPUS_SUITE = "Suite";
    public static final String TIPUS_DELUXE = "Deluxe";

    // Serveis addicionals
    public static final String SERVEI_ESMORZAR = "Esmorzar";
    public static final String SERVEI_GIMNAS = "Gimnàs";
    public static final String SERVEI_SPA = "Spa";
    public static final String SERVEI_PISCINA = "Piscina";

    // Capacitat inicial
    public static final int CAPACITAT_ESTANDARD = 30;
    public static final int CAPACITAT_SUITE = 20;
    public static final int CAPACITAT_DELUXE = 10;

    // IVA
    public static final float IVA = 0.21f;

    // Scanner únic
    public static Scanner sc = new Scanner(System.in);

    // HashMaps de consulta
    public static HashMap<String, Float> preusHabitacions = new HashMap<String, Float>();
    public static HashMap<String, Integer> capacitatInicial = new HashMap<String, Integer>();
    public static HashMap<String, Float> preusServeis = new HashMap<String, Float>();

    // HashMaps dinàmics
    public static HashMap<String, Integer> disponibilitatHabitacions = new HashMap<String, Integer>();
    public static HashMap<Integer, ArrayList<String>> reserves = new HashMap<Integer, ArrayList<String>>();

    // Generador de nombres aleatoris per als codis de reserva
    public static Random random = new Random();

    // --------- MÈTODE MAIN ---------

    /**
     * Mètode principal. Mostra el menú en un bucle i gestiona l'opció triada
     * fins que l'usuari decideix eixir.
     */
    public static void main(String[] args) {
        inicialitzarPreus();

        int opcio = 0;
        do {
            mostrarMenu();

            System.out.println();//linea en blanco

            opcio = llegirEnter("Seleccione una opció: ");
            gestionarOpcio(opcio);
        } while (opcio != 6);

        System.out.println("Eixint del sistema... Gràcies per utilitzar el gestor de reserves!");
    }

    // --------- MÈTODES DEMANATS ---------

    /**
     * Configura els preus de les habitacions, serveis addicionals i
     * les capacitats inicials en els HashMaps corresponents.
     */
    public static void inicialitzarPreus() {
        // Preus habitacions
        preusHabitacions.put(TIPUS_ESTANDARD, 50f);
        preusHabitacions.put(TIPUS_SUITE, 100f);
        preusHabitacions.put(TIPUS_DELUXE, 150f);

        // Capacitats inicials
        capacitatInicial.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        capacitatInicial.put(TIPUS_SUITE, CAPACITAT_SUITE);
        capacitatInicial.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Disponibilitat inicial (comença igual que la capacitat)
        disponibilitatHabitacions.put(TIPUS_ESTANDARD, CAPACITAT_ESTANDARD);
        disponibilitatHabitacions.put(TIPUS_SUITE, CAPACITAT_SUITE);
        disponibilitatHabitacions.put(TIPUS_DELUXE, CAPACITAT_DELUXE);

        // Preus serveis
        preusServeis.put(SERVEI_ESMORZAR, 10f);
        preusServeis.put(SERVEI_GIMNAS, 15f);
        preusServeis.put(SERVEI_SPA, 20f);
        preusServeis.put(SERVEI_PISCINA, 25f);
    }

    /**
     * Mostra el menú principal amb les opcions disponibles per a l'usuari.
     */
    public static void mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Reservar una habitació");
        System.out.println("2. Alliberar una habitació");
        System.out.println("3. Consultar disponibilitat");
        System.out.println("4. Llistar reserves per tipus");
        System.out.println("5. Obtindre una reserva");
        System.out.println("6. Ixir");
    }

    /**
     * Processa l'opció seleccionada per l'usuari i crida el mètode corresponent.
     */
    public static void gestionarOpcio(int opcio) {
        // TODO: estructura if else para la opcion elegida por el usuario, si no elige
        // del 1-6, se muestra un mensaje de error. */

        if (opcio == 1) {
            reservarHabitacio();
        } else if (opcio == 2) {
            alliberarHabitacio();
        } else if (opcio == 3) {
            consultarDisponibilitat();
        } else if (opcio == 4) {
            obtindreReservaPerTipus();
        } else if (opcio == 5) {
            obtindreReserva();
        } else if (opcio == 6) {
            System.out.println("Ixir");
        } else {
            System.out.println("Opcion no valida");
        }
    }

    /**
     * Gestiona tot el procés de reserva: selecció del tipus d'habitació,
     * serveis addicionals, càlcul del preu total i generació del codi de reserva.
     */
    public static void reservarHabitacio() {
        System.out.println();
        System.out.println("\n===== RESERVAR HABITACIÓ =====");
        // TODO:

        System.out.println("Tipus d'habitació disponibles:");
        System.out.println("1. Estàndard - 30 disponibles - 50€");
        System.out.println("2. Suite      - 20 disponibles - 100€");
        System.out.println("3. Deluxe     - 10 disponibles - 150€");

        String tipusHabitacio = seleccionarTipusHabitacioDisponible();

        if (tipusHabitacio == null){
            return;
        }

        ArrayList<String> serveis = seleccionarServeis();

        float preuTotal = calcularPreuTotal(tipusHabitacio, serveis);

        int codiReserva;

        codiReserva = generarCodiReserva();

        //creo lista vacia para ir metiendo los datos de la reserva
        ArrayList<String> dadesReserva = new ArrayList<>();

        //guardo tipo de habitacion en la lista
        dadesReserva.add(tipusHabitacio);

        //hay  que convertir el numero a string antes de guardarlo, el float a texto
        dadesReserva.add(String.valueOf(preuTotal));

        //para cada servicio que hay en la lista servicios...
        for(String servei : serveis){
            dadesReserva.add(servei); //lo añado a dadesReserva
        }
        //lo guardo en hashmap
        reserves.put(codiReserva, dadesReserva);

        int disponibilitat;

        disponibilitat = disponibilitatHabitacions.get(tipusHabitacio);

        disponibilitat = disponibilitat -1;

        //actualizo hashmaps de disponibilidad con el numeor de habitaciones libres
        disponibilitatHabitacions.put(tipusHabitacio, disponibilitat);

        System.out.println("Reserva creada amb èxit!");
        System.out.println("Codi de reserva: "+ codiReserva);
        System.out.println();
    }

    /**
     * Pregunta a l'usuari un tipus d'habitació en format numèric i
     * retorna el nom del tipus.
     */
    public static String seleccionarTipusHabitacio() {
        // TODO: metodo scanner para imprimir por pantalla, y return para devolver el
        // valor elegido por el usuario**/
        Scanner in = new Scanner(System.in);
        int num;

        System.out.print("Introdueix un número: ");
        num = in.nextInt();

        if (num == 1) {
            return "Estàndard";
        } else if (num == 2) {
            return "Suite";
        } else if (num == 3) {
            return "Deluxe";
        } else {
            System.out.println("El numero introducido no es valido");
        }
        return null;
    }

    /**
     * Mostra la disponibilitat i el preu de cada tipus d'habitació,
     * demana a l'usuari un tipus i només el retorna si encara hi ha
     * habitacions disponibles. En cas contrari, retorna null.
     */
    public static String seleccionarTipusHabitacioDisponible() {

        /** utilizo el hashmap para relacionar una clave con un valor UD5 */

        System.out.println();

        Scanner in = new Scanner(System.in);
        System.out.println("Selecciona un tipo de habitacion:");

        System.out.println();
        System.out.println("1. Estàndard");
        System.out.println("2. Suite");
        System.out.println("3. Deluxe");

        int opcio = in.nextInt();

        String tipo = null;

        if (opcio == 1) {
            tipo = TIPUS_ESTANDARD;
        } else if (opcio == 2) {
            tipo = TIPUS_SUITE;
        } else if (opcio == 3) {
            tipo = TIPUS_DELUXE;
        } else {
            System.out.println("El numero introducido no es valido");
            return null;
        }
        if (disponibilitatHabitacions.get(tipo) > 0) {
            return tipo;
        } else {
            System.out.println("No queden habitacions disponibles d'aquest tipus.");
            return null;
        }
    }

    /**
     * Permet triar serveis addicionals (entre 0 i 4, sense repetir) i
     * els retorna en un ArrayList de String.
     */
    public static ArrayList<String> seleccionarServeis() {
        // TODO: ArrayLista almacenar elementos dentro de una lista **/

        ArrayList<String> serveiSeleccionats = new ArrayList<>();

        boolean seguir = true;
        /** controla si el blucle sigue o no */

        while (seguir) {
            System.out.println(); //linea en blanco
            System.out.print("¿Vol afegir un servei?(s/n)");
            System.out.println();
            String respuesta = sc.next();

            sc.nextLine(); //limpia el buffer

            if (respuesta.equalsIgnoreCase("n")) {
                seguir = false;
                continue;
            }
            else if (respuesta.equalsIgnoreCase("s")) {

                System.out.println("0. Finalitzar");
                System.out.println("1. Esmorzar (10€)");
                System.out.println("2. Gimnàs   (15€)");
                System.out.println("3. Spa      (20€)");
                System.out.println("4. Piscina  (25€)");

                int opcio = sc.nextInt();
                    sc.nextLine(); //limpia el buffer


                String servei = null;

                if (opcio == 1) {
                    servei = SERVEI_ESMORZAR;
                } else if (opcio == 2) {
                    servei = SERVEI_GIMNAS;
                } else if (opcio == 3) {
                    servei = SERVEI_SPA;
                } else if (opcio == 4) {
                    servei = SERVEI_PISCINA;
                } else {
                    System.out.println("Opció no vàlida");
                }

                if (servei != null) {
                    if (!serveiSeleccionats.contains(servei)) { /** aqui se evita repetir en la lista */
                        serveiSeleccionats.add(servei);
                        System.out.println();
                        System.out.println("Servei afegit:" + servei);

                    } else {
                        System.out.println("Aquest servei ja està afegit");
                    }
                }
            }

        }

        return serveiSeleccionats;
    }

    /**
     * Calcula i retorna el cost total de la reserva, incloent l'habitació,
     * els serveis seleccionats i l'IVA.
     */
    public static float calcularPreuTotal(String tipusHabitacio, ArrayList<String> serveisSeleccionats) {
        // TODO: no hay q volver a crear un hashmap solo se recorre el arraylist y se
        // consulta
        // precio base de la habitacion
        float preusHabitacio = preusHabitacions.get(tipusHabitacio);
        // sumar los precios de los servicios
        float totalServeis = 0;

        for (String servei : serveisSeleccionats) {
            totalServeis += preusServeis.get(servei);
        }

        // subtotal
        float subtotal = preusHabitacio + totalServeis;

        // se aplica el IVA
        float totalAmbIVA = subtotal + (subtotal * IVA);

        return totalAmbIVA;
    }

    /**
     * Genera i retorna un codi de reserva únic de tres xifres
     * (entre 100 i 999) que no estiga repetit.
     */
    public static int generarCodiReserva() {
        // TODO:

        int codi;

        do{ //genero el codigo
            codi = random.nextInt(900) + 100;

        }while (reserves.containsKey(codi)); //mientras este repetido(true) se vuelve a generar

        return codi;
    }

    /**
     * Permet alliberar una habitació utilitzant el codi de reserva
     * i actualitza la disponibilitat.
     */
    public static void alliberarHabitacio() {
        System.out.println();
        System.out.println("\n===== ALLIBERAR HABITACIÓ =====");
        // TODO: Demanar codi, tornar habitació i eliminar reserva

        // pide le codigo y lo guarda directamente
        int codiReserva = llegirEnter("Introdueix el codi de reserva: ");

        if (reserves.containsKey(codiReserva)) { // comprobar si la reserva existe

            ArrayList<String> reserva = reserves.get(codiReserva);// se obtiene la reserva

            String tipusHabitacio = reserva.get(0);

            int disponibilitat;

            disponibilitat = disponibilitatHabitacions.get(tipusHabitacio);

            disponibilitat = disponibilitat + 1; // se libera y se suma 1

            disponibilitatHabitacions.put(tipusHabitacio, disponibilitat); // se actualiza la lista de disponibilidad

            reserves.remove(codiReserva); // eliminar la reserva

            System.out.println("Reserva trobada!");
            System.out.println();
            System.out.println("Habitació alliberada correctament.");
            System.out.println("Disponibilitat actualitzada.");

        } else {
            System.out.println();
            System.out.println("No se ha encontrado ninguna reserva con ese codigo");
        }
    }

    /**
     * Mostra la disponibilitat actual de les habitacions (lliures i ocupades).
     */
    public static void consultarDisponibilitat() {
        // TODO: Mostrar lliures i ocupades

        System.out.println();
        System.out.println("===== DISPONIBILITAT D'HABITACIONS =====");

        System.out.println("Tipus    |   Lliures  | Ocupades");
        System.out.println("---------|------------|------------");

        mostrarDisponibilitatTipus(TIPUS_ESTANDARD);
        mostrarDisponibilitatTipus(TIPUS_SUITE);
        mostrarDisponibilitatTipus(TIPUS_DELUXE);

    }

    /**
     * Funció recursiva. Mostra les dades de totes les reserves
     * associades a un tipus d'habitació.
     */
    public static void llistarReservesPerTipus(int[] codis, String tipus) {
        // TODO: Implementar recursivitat

        if (codis.length == 0) {
            return; //si no hay codigo salgo
        }

        int primerCodi = codis [0];

        ArrayList<String> reserva = reserves.get(primerCodi);

        String tipoHabitacion = reserva.get(0); //obtener el tipo de reserva

        if (tipoHabitacion.equals(tipus)){ //lo comparo con tipo buscado
            mostrarDadesReserva(primerCodi); //muestra los datos si coincide
        }

        int[] nuevoArray = new int[codis.length -1]; //nueva lista

            //copia todos los codigos menos el primero
            System.arraycopy(codis, 1, nuevoArray, 0, nuevoArray.length);

            //el metodo se llama otra vez para comprobar el resto
            llistarReservesPerTipus(nuevoArray, tipus);

            /**termino la recursividad, la funcion procesa el primer codigo
             * luego vuelvo a llamarlo hasta que no quede ninguno */
        }


    /**
     * Permet consultar els detalls d'una reserva introduint el codi.
     */
    public static void obtindreReserva() {
        System.out.println("\n===== CONSULTAR RESERVA =====");
        // TODO: Mostrar dades d'una reserva concreta

        // pide el codigo de la reserva y lo lee
        int codigoReserva = llegirEnter("Introdueix el codi de reserva: ");
        System.out.println();

        // si el codigo existe muestor los datos
        if (reserves.containsKey(codigoReserva)) {
            mostrarDadesReserva(codigoReserva);
        } else {
            System.out.println();
            System.out.println("No s'ha trobat cap reserva amb aquest codi.");
        }
    }

    /**
     * Mostra totes les reserves existents per a un tipus d'habitació
     * específic.
     */
    public static void obtindreReservaPerTipus() {
        System.out.println("\n===== CONSULTAR RESERVES PER TIPUS =====");
        // TODO: Llistar reserves per tipus

        /**
         * este metodo pide un tipo de habitacion, recoge los codigos
         * y llama a otro metodo que se encarga de
         * mostrar las reservas de ese tipo
         */

        String tipus = seleccionarTipusHabitacio(); // pide el tipo

        // creo array para guardar los codigos de reservas
        int[] codis = new int[reserves.size()];

        int i = 0;

        for (Integer codi : reserves.keySet()) { // recorre todas las reservas
            codis[i] = codi;
            i++;
        }

        llistarReservesPerTipus(codis, tipus); // llamada al metodo 'llistarreservespertipus'
    }

    /**
     * Consulta i mostra en detall la informació d'una reserva.
     */
    public static void mostrarDadesReserva(int codi) {
        // TODO: Imprimir tota la informació d'una reserva

        // se obtiene la reserva
        ArrayList<String> reserva = reserves.get(codi);

        // se guarda el tipo de habitacion(0)
        String tipusHabitacio = reserva.get(0);
        System.out.println("Tipus d'habitació: " + tipusHabitacio);

        // se guarda el precio total de la reserva(1)
        String preuTotal = reserva.get(1);
        System.out.println("Cost total: " + preuTotal + "€");

        System.out.println("Serveis addicionals:");

        // servicios adicionales (2)

        /**
         * el bucle recorre la lista que empieza en 2 y
         * se para cuando no queden mas
         */

        for (int i = 2; i < reserva.size(); i++) {
            System.out.println(" " + reserva.get(i));
        }

    }

    // --------- MÈTODES AUXILIARS (PER MILLORAR LEGIBILITAT) ---------

    /**
     * Llig un enter per teclat mostrant un missatge i gestiona possibles
     * errors d'entrada.
     */
    static int llegirEnter(String missatge) {
        int valor = 0;
        boolean correcte = false;
        while (!correcte) {
            System.out.print(missatge);
            valor = sc.nextInt();
            correcte = true;
        }
        return valor;
    }

    /**
     * Mostra per pantalla informació d'un tipus d'habitació: preu i
     * habitacions disponibles.
     */
    static void mostrarInfoTipus(String tipus) {
        int disponibles = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        float preu = preusHabitacions.get(tipus);
        System.out.println("- " + tipus + " (" + disponibles + " disponibles de " + capacitat + ") - " + preu + "€");
    }

    /**
     * Mostra la disponibilitat (lliures i ocupades) d'un tipus d'habitació.
     */
    static void mostrarDisponibilitatTipus(String tipus) {
        int lliures = disponibilitatHabitacions.get(tipus);
        int capacitat = capacitatInicial.get(tipus);
        int ocupades = capacitat - lliures;

        String etiqueta = tipus;
        if (etiqueta.length() < 8) {
            etiqueta = etiqueta + "\t"; // per a quadrar la taula
        }

        System.out.println(etiqueta + "\t" + lliures + "\t" + ocupades);
        }
}



