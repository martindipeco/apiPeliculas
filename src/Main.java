

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import excepcion.ErrorNAException;
import modelo.Titulo;
import modelo.TituloOmdb;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        Scanner lectura = new Scanner(System.in);
        System.out.println("Escriba el nombre de una pelicula: ");
        var busqueda = lectura.nextLine();

        String clave = System.getenv("APIKEY_OMDB");
        System.out.println(clave);

        //String clave = "4a05cb95"; //COLOQUE AQUI SU CLAVE DE OMDb
        String direccion = "https://www.omdbapi.com/?t=" + busqueda + "&apikey=" +clave;

        try
        {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion))
                    .build();
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            System.out.println(json);

            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

            TituloOmdb miTituloOmdb = gson.fromJson(json, TituloOmdb.class);
            System.out.println(miTituloOmdb);

            Titulo miTitulo = new Titulo(miTituloOmdb);
            System.out.println(miTitulo);

            FileWriter escritura = new FileWriter("peliculas.txt");
            escritura.write(miTitulo.toString());
            escritura.close();
        }
        catch (NumberFormatException e)
        {
            System.out.println("Ocurrió error con números");
            System.out.println(e.getMessage());
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Ocurrió un error de argumentos");
            System.out.println(e.getMessage());
        }
        catch (ErrorNAException e)
        {
            System.out.println(e.getMessage());
            System.out.println("¿Intentamos arreglarlo?(S/N)");
            String consulta = lectura.nextLine();
            if(consulta.equals("S"))
            {
                System.out.println("Lástima, no pude hacer nada");
            }

        }
        finally {
            System.out.println("Finalizó la ejecución del programa");
        }
    }
}

