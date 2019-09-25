package pruebaproyecto;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;

public class PruebaProyecto {

    public static void main(String[] args) throws IOException {
        
        File folder = new File("./");
        File[] listaDeArchivos = folder.listFiles();
        ArrayList<String> archivosDisponibles = new ArrayList<>();
        
        System.out.println("Lista de archivos de texto: ");
        
        for (int i = 0; i < listaDeArchivos.length; i++) {
            if (listaDeArchivos[i].isFile() && listaDeArchivos[i].getName().toLowerCase().contains(".txt")) {
                archivosDisponibles.add(listaDeArchivos[i].getName());
            }
        }
        
        /*Path path = Paths.get("archivo.txt");
        Scanner scanner = new Scanner(path);
        System.out.println("AAAAA");
        while(scanner.hasNextLine())
        {
            System.out.println(scanner.nextLine());
        }
        scanner.close();*/
        
    }
    
}
