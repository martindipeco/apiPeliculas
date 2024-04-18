package modelo;

import com.google.gson.annotations.SerializedName;
import excepcion.ErrorNAException;

public class Titulo implements Comparable<Titulo> {
    @SerializedName("Title")
    private String nombre;
    @SerializedName("Year")
    private int fechaEstreno;
    private int duracionMins;

    public Titulo(String nombre, int fechaEstreno, int duracionMins) {
        this.nombre = nombre;
        this.fechaEstreno = fechaEstreno;
        this.duracionMins = duracionMins;
    }

    public Titulo(TituloOmdb miTituloOmdb) {
        if (miTituloOmdb.title().contains("N/A"))
        {
            throw new ErrorNAException("No se pudo convertir por que apareció N/A en Título");
        }
        this.nombre = miTituloOmdb.title();
        if (miTituloOmdb.year().contains("N/A"))
        {
            throw new ErrorNAException("No se pudo convertir por que apareció N/A en año");
        }
        this.fechaEstreno = Integer.valueOf(miTituloOmdb.year());
        if (miTituloOmdb.runtime().contains("N/A"))
        {
            this.duracionMins = 0;
            System.out.println("Apareció N/A en duración, se convirtió a 0");
            //throw new ErrorNAException("Apareció N/A en duración, se convirtió a 0");
        }
        else
        {
            this.duracionMins = Integer.valueOf(miTituloOmdb.runtime().substring(0, 2)); //porque el json de duracion es una string de tipo 33 mins
        }


    }

    public String getNombre() {
        return nombre;
    }

    public int getFechaEstreno() {
        return fechaEstreno;
    }

    public int getDuracionMins() {
        return duracionMins;
    }


    @Override
    public int compareTo(Titulo otroTitulo)
    {
        return this.getNombre().compareTo(otroTitulo.getNombre());
    }

    @Override
    public String toString() {
        return "Titulo: " +
                "nombre='" + nombre + '\'' +
                ", Fecha de Estreno=" + fechaEstreno +
                ", Duracion en Minutos=" + duracionMins ;
    }
}
