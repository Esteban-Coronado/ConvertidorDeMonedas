import java.io.IOException;
import java.util.Scanner;

public class Console {
    ApiConexion apiConexion = new ApiConexion();

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);
        int seleccion;

        while (true) {
            showMenu();
            seleccion = scanner.nextInt();

            if (seleccion == 0) {
                System.out.println("Saliendo...");
                break;
            }

            if (seleccion >= 1 && seleccion <= 7) {
                System.out.println("Ingrese la cantidad a convertir: ");
                double cantidad = scanner.nextDouble();
                startConversion(seleccion, cantidad);
            } else {
                System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }

    private void showMenu() {
        System.out.println(
            """
                Digite el numero de la conversion que desea realizar:
                1) Peso Argentino ==> Dólar
                2) Dólar ==> Peso Argentino
                3) Peso Colombiano ==> Peso Mexicano
                4) Peso Mexicano ==> Dólar
                5) Dólar ==> Peso Mexicano
                6) Euro ==> Peso Mexicano
                7) Peso Mexicano ==> Euro
                0) SALIR
                
            """
        );
    }

    private void startConversion(int seleccion, double cantidad) {
        String base = "";
        String interes = "";

        switch (seleccion) {
            case 1 -> { base = "ARS"; interes = "USD"; }
            case 2 -> { base = "USD"; interes = "ARS"; }
            case 3 -> { base = "COP"; interes = "MXN"; }
            case 4 -> { base = "MXN"; interes = "USD"; }
            case 5 -> { base = "USD"; interes = "MXN"; }
            case 6 -> { base = "EUR"; interes = "MXN"; }
            case 7 -> { base = "MXN"; interes = "EUR"; }
        }

        currencyConversion(base, interes, cantidad);
    }

    private void currencyConversion(String base, String interes, double cantidad) {
        try {
            Coin coin = apiConexion.requestAPI(base, interes, cantidad);
            if (coin.conversion_result() != null) {
                System.out.println("$" + cantidad + " " + base + " equivale a " + interes + ": " + coin.conversion_result());
            } else {
                System.out.println("Hubo un error en la conversión");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Error al realizar la consulta: " + e.getMessage());
        }
    }
}
