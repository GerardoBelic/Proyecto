package pruebaproyecto;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.Queue;

public class PruebaProyecto {

    public static void main(String[] args) throws IOException {
        
        Random rand = new Random();
        Writer www = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("numeros.txt"), "utf-8"));
        for (int i = 0; i < 16 - 1; ++i)
        {
            Float aa = rand.nextFloat();
            www.write(aa.toString() + ",");
        }
        Float aa = rand.nextFloat();
        www.write(aa.toString());
        www.flush();
        www.close();
        
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
        
        Scanner entradas = new Scanner(System.in);
        int opcionArchivo, opcionMetodo, opcionOrden;
        int numeroDatos = 0;
        
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("file.txt"), "utf-8"));
        writer.write("hola");
        writer.write("hey");
        writer.flush();
        writer.close();
        FileWriter fw = new FileWriter("file.txt", false);
        fw.write("eeeee");
        fw.close();
        
        
        System.out.println("Bienvenidos");
        System.out.println("Elige un archivo: ");
        for (int i = 0; i < archivosDisponibles.size(); ++i)
            System.out.print("\n[" + (i + 1) + "] - " + archivosDisponibles.get(i));
        opcionArchivo = entradas.nextInt();
        
        Path miRuta = Paths.get(archivosDisponibles.get(opcionArchivo - 1));
        Scanner leerArchivoOriginal = new Scanner(miRuta);
        System.out.println("Hola");
        //leerArchivoOriginal.useDelimiter(",");
        //Writer copiarArchivoOriginal = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("F0.txt"), "utf-8"));
        FileWriter copiarArchivoOriginal = new FileWriter("F0.txt");
        System.out.println("Hey");
        while(leerArchivoOriginal.hasNextLine())
        {
            copiarArchivoOriginal.write(leerArchivoOriginal.nextLine());   //En teoria solo deberia copiar una linea
        }
        System.out.println("yuju");
        copiarArchivoOriginal.flush();
        copiarArchivoOriginal.close();
        
        System.out.println("Aqui");
        leerArchivoOriginal.close();
        Scanner aaa = new Scanner(miRuta);
        aaa.useDelimiter(",");
        while(aaa.hasNextFloat())
        {
            aaa.nextFloat();
            ++numeroDatos;
        }
        
        System.out.println(numeroDatos);
        
        System.out.println("Por que metodo lo quieres?");
        System.out.println("1) Polifase");
        System.out.println("2) Mezcla Equilibrada");
        System.out.println("3) Distribucion");
        opcionMetodo = entradas.nextInt();
        System.out.println("En que orden lo quieres?");
        System.out.println("1) Ascendente");
        System.out.println("2) Descendente");
        opcionOrden = entradas.nextInt();
        switch(opcionMetodo)
        {
            case 1:
                polifase(((opcionOrden - 1) == 1), numeroDatos);
                break;
            /*case 2:
                mezclaEquilibrada();
                break;
            case 3:
                distribucion();
                break;*/
        }
        aaa.close();
        
    }
    
    public static void polifase(boolean orden, int numeroDatos) throws IOException
    {
        ArrayList<Float> arrDatos = new ArrayList<>();
        Path rutaF0 = Paths.get("F0.txt");
        Scanner leerF0 = new Scanner(rutaF0);
        leerF0.useDelimiter(",");
        FileWriter archivoF1 = new FileWriter("F1.txt");
        FileWriter archivoF2 = new FileWriter("F2.txt");
        boolean alternar = true;
        
        do
        {
            for (int i = 0; i < (numeroDatos / 8); ++i)
            {
                if (leerF0.hasNextFloat())
                    arrDatos.add(leerF0.nextFloat());
                else
                    break;
            }
            
            ordenar(arrDatos, orden);
            
            if (alternar)
            {
                //archivoF1.write("(");
                archivoF1.write(arrDatos.get(0).toString());
                for (/*Float fl: arrDatos*/ int i = 1; i < arrDatos.size(); ++i)
                {
                    archivoF1.write(",");
                    archivoF1.write(arrDatos.get(i).toString());
                }
                archivoF1.write("'");
                alternar = false;
            }
            else
            {
                //archivoF2.write("(");
                archivoF2.write(arrDatos.get(0).toString());
                for (/*Float fl: arrDatos*/ int i = 1; i < arrDatos.size(); ++i)
                {
                    archivoF2.write(",");
                    archivoF2.write(arrDatos.get(i).toString());
                }
                archivoF2.write("'");
                alternar = true;
            }
            
            arrDatos.clear();
            
        } while (leerF0.hasNextFloat());
        
        leerF0.close();
        archivoF1.close();
        archivoF2.close();
        
        convergerBloques(orden);
    }
    
    private static void convergerBloques(boolean orden) throws IOException
    {
        
        Path rutaF0 = Paths.get("F0.txt");
        Path rutaF1 = Paths.get("F1.txt");
        Path rutaF2 = Paths.get("F2.txt");
        
        /*FileWriter escribirF0 = new FileWriter("F0.txt", true);
        FileWriter escribirF1 = new FileWriter("F1.txt", true);
        FileWriter escribirF2 = new FileWriter("F2.txt", true);*/
        
        Scanner bloquesF1 = new Scanner(rutaF1);
        bloquesF1.useDelimiter("");
        Scanner bloquesF2 = new Scanner(rutaF2);
        bloquesF2.useDelimiter("");
        
        //int numeroBloquesF1 = 0, numeroBloquesF2 = 0;
        int temp = 0;
        
        ArrayList<Integer> numerosPorBloqueF1 = new ArrayList<>();
        ArrayList<Integer> numerosPorBloqueF2 = new ArrayList<>();
        ArrayList<Integer> numerosPorBloqueF0 = new ArrayList<>();
        
        while (bloquesF1.hasNext())
        {
            switch (bloquesF1.next())
            {
                case "'":
                    numerosPorBloqueF1.add(temp + 1);
                    temp = 0;
                    break;
                case ",":
                    ++temp;
                    break;
            }
        }
        
        bloquesF1.close();
        
        while (bloquesF2.hasNext())
        {
            switch (bloquesF2.next())
            {
                case "'":
                    numerosPorBloqueF2.add(temp + 1);
                    temp = 0;
                    break;
                case ",":
                    ++temp;
                    break;
            }
        }
        
        bloquesF2.close();
        
        if (numerosPorBloqueF1.size() != numerosPorBloqueF2.size())
            numerosPorBloqueF2.add(0);
        
        System.out.println("Bloques de F1:");
        System.out.print("[");
        for (Integer x : numerosPorBloqueF1)
            System.out.print(x + " ");
        System.out.println("]");
        System.out.println("Bloques de F2:");
        System.out.print("[");
        for (Integer x : numerosPorBloqueF2)
            System.out.print(x + " ");
        System.out.println("]");
        
        int numeroDeIteraciones = (numerosPorBloqueF1.size() + 3) / 2;
        
        for (int i = 0; i < numeroDeIteraciones; ++i)
        {
            
            System.out.println("numeros por bloque f1 " + numerosPorBloqueF1.size());
            for (int j = 0; j < numerosPorBloqueF1.size(); ++j)
            {
                numerosPorBloqueF0.add(numerosPorBloqueF1.get(j) + numerosPorBloqueF2.get(j));
            }
            
            Scanner leerLineaF1 = new Scanner(rutaF1);
            leerLineaF1.useDelimiter(",");
            Scanner leerLineaF2 = new Scanner(rutaF2);
            leerLineaF2.useDelimiter(",");
            
            FileWriter escribirF0 = new FileWriter("F0.txt", true);
            
            for (int linea = 0; linea < i; ++linea)
            {
                leerLineaF1.nextLine();
                leerLineaF2.nextLine();
            }
            
            //System.out.println("aqqq");
            escribirF0.write(System.getProperty("line.separator"));
            
            for (int j = 0; j < numerosPorBloqueF1.size(); ++j) //Un ciclo donde se recorren todos los bloques de F1
            {
                
                //escribirF0.write("(");
                
                while (numerosPorBloqueF1.get(j) + numerosPorBloqueF2.get(j) > 0)
                {
                    
                    System.out.println("bloque 1 " + numerosPorBloqueF1.get(j) + " bloque 2 " + numerosPorBloqueF2.get(j));
                    
                    if (numerosPorBloqueF1.get(j) > 0 && numerosPorBloqueF2.get(j) > 0)
                    {
                        
                        Float tempF1, tempF2;
                        
                        if (numerosPorBloqueF1.get(j) + numerosPorBloqueF2.get(j) == 3)
                        {
                            //leerLineaF1.useDelimiter("");
                            leerLineaF2.useDelimiter("");
                            //System.out.println(leerLineaF1.next());
                            System.out.println(leerLineaF2.next());
                            System.out.println("yeaaa");
                            //leerLineaF1.useDelimiter("'");
                            leerLineaF2.useDelimiter("'");
                            
                            tempF1 = leerLineaF1.nextFloat();
                            tempF2 = leerLineaF2.nextFloat();
                            
                            System.out.println(tempF1 + " " + tempF2);
                            
                            if (tempF1 < tempF2)
                            {
                                escribirF0.write(tempF1.toString() + ",");
                                escribirF0.write(tempF2.toString());
                            }
                            else
                            {
                                escribirF0.write(tempF2.toString() + ",");
                                escribirF0.write(tempF1.toString());
                            }
                            
                            numerosPorBloqueF1.set(j, numerosPorBloqueF1.get(j) - 1);
                            numerosPorBloqueF2.set(j, numerosPorBloqueF2.get(j) - 1);
                            
                            //leerLineaF1.useDelimiter("");
                            leerLineaF2.useDelimiter("");
                            
                            //System.out.println(leerLineaF1.next());
                            System.out.println(leerLineaF2.next());
                        }
                        
                        else if (numerosPorBloqueF1.get(j) + numerosPorBloqueF2.get(j) == 2)
                        {
                            leerLineaF1.useDelimiter("");
                            leerLineaF2.useDelimiter("");
                            System.out.println(leerLineaF1.next());
                            System.out.println(leerLineaF2.next());
                            System.out.println("yeaaa");
                            leerLineaF1.useDelimiter("'");
                            leerLineaF2.useDelimiter("'");
                            
                            tempF1 = leerLineaF1.nextFloat();
                            tempF2 = leerLineaF2.nextFloat();
                            
                            System.out.println(tempF1 + " " + tempF2);
                            
                            if (tempF1 < tempF2)
                            {
                                escribirF0.write(tempF1.toString() + ",");
                                escribirF0.write(tempF2.toString());
                            }
                            else
                            {
                                escribirF0.write(tempF2.toString() + ",");
                                escribirF0.write(tempF1.toString());
                            }
                            
                            numerosPorBloqueF1.set(j, numerosPorBloqueF1.get(j) - 1);
                            numerosPorBloqueF2.set(j, numerosPorBloqueF2.get(j) - 1);
                            
                            leerLineaF1.useDelimiter("");
                            leerLineaF2.useDelimiter("");
                            
                            System.out.println(leerLineaF1.next());
                            System.out.println(leerLineaF2.next());
                        }
                        
                        else
                        {

                            //leerLineaF1.next();
                            //leerLineaF1.useDelimiter("\\D");
                            tempF1 = leerLineaF1.nextFloat();
                            //leerLineaF2.next();
                            //leerLineaF2.useDelimiter("\\D");
                            System.out.println("apunto de leer temp2");
                            tempF2 = leerLineaF2.nextFloat();

                            System.out.println(tempF1 + " " + tempF2);

                            if (tempF1 < tempF2)
                            {
                                escribirF0.write(tempF1.toString() + ",");
                                escribirF0.write(tempF2.toString());
                            }
                            else
                            {
                                escribirF0.write(tempF2.toString() + ",");
                                escribirF0.write(tempF1.toString());
                            }

                            numerosPorBloqueF1.set(j, numerosPorBloqueF1.get(j) - 1);
                            numerosPorBloqueF2.set(j, numerosPorBloqueF2.get(j) - 1);

                            if (numerosPorBloqueF1.get(j) + numerosPorBloqueF2.get(j) != 0)
                                escribirF0.write(",");

                        }
                        
                    }
                    else if (numerosPorBloqueF1.get(j) > 0)
                    {
                        
                        Float tempF1;
                        
                        if (numerosPorBloqueF1.get(j) == 1)
                        {
                            leerLineaF1.useDelimiter("");
                            System.out.println(leerLineaF1.next());
                            leerLineaF1.useDelimiter("'");
                            
                            tempF1 = leerLineaF1.nextFloat();
                            
                            escribirF0.write(tempF1.toString());
                            
                            numerosPorBloqueF1.set(j, numerosPorBloqueF1.get(j) - 1);
                            
                            leerLineaF1.useDelimiter("");
                            
                            System.out.println(leerLineaF1.next());
                        }
                        else
                        {
                            //leerLineaF1.next();
                            tempF1 = leerLineaF1.nextFloat();

                            escribirF0.write(tempF1.toString() + ",");

                            numerosPorBloqueF1.set(j, numerosPorBloqueF1.get(j) - 1);

                            /*if (numerosPorBloqueF1.get(j) != 0)
                                escribirF0.write(",");
                            }*/
                        }
                        
                    }
                    else
                    {
                        
                        Float tempF2;
                        
                        if (numerosPorBloqueF2.get(j) == 1)
                        {
                            leerLineaF2.useDelimiter("");
                            System.out.println(leerLineaF2.next());
                            leerLineaF2.useDelimiter("'");
                            
                            tempF2 = leerLineaF2.nextFloat();
                            
                            escribirF0.write(tempF2.toString());
                            
                            numerosPorBloqueF2.set(j, numerosPorBloqueF2.get(j) - 1);
                            
                            leerLineaF2.useDelimiter("");
                            
                            System.out.println(leerLineaF2.next());
                        }
                        else
                        {
                            //leerLineaF1.next();
                            tempF2 = leerLineaF2.nextFloat();

                            escribirF0.write(tempF2.toString() + ",");

                            numerosPorBloqueF2.set(j, numerosPorBloqueF2.get(j) - 1);
                        }
                    }
                    
                    System.out.println(numerosPorBloqueF1.get(j) + numerosPorBloqueF2.get(j));
                    leerLineaF1.useDelimiter(",");
                    leerLineaF2.useDelimiter(",");
                    
                    
                }
                
                escribirF0.write("'");
                /*leerLineaF1.useDelimiter("");
                leerLineaF1.next();
                leerLineaF1.useDelimiter(",");
                leerLineaF2.useDelimiter("");
                leerLineaF2.next();
                leerLineaF2.useDelimiter(",");*/
                
            }
            
            escribirF0.flush();
            escribirF0.close();
            leerLineaF1.close();
            leerLineaF2.close();
            
            //
            
            if (numerosPorBloqueF0.size() == 1)
            {
                System.out.println("Terminado");
                return;
            }
            
            numerosPorBloqueF1.clear();
            numerosPorBloqueF2.clear();
            
            for (int j = 0; j < numerosPorBloqueF0.size(); ++j)
            {
                if (j % 2 == 0)
                    numerosPorBloqueF1.add(numerosPorBloqueF0.get(j));
                else
                    numerosPorBloqueF2.add(numerosPorBloqueF0.get(j));
            }
            
            if (numerosPorBloqueF1.size() != numerosPorBloqueF2.size())
            numerosPorBloqueF2.add(0);
            
            Scanner leerLineaF0 = new Scanner(rutaF0);
            for (int linea = 0; linea < i + 1; ++linea)
            {
                leerLineaF0.nextLine();
            }
            
            //boolean alternar = true;
            FileWriter escribirF1 = new FileWriter("F1.txt", true);
            FileWriter escribirF2 = new FileWriter("F2.txt", true);
            
            escribirF1.write(System.getProperty("line.separator"));
            escribirF2.write(System.getProperty("line.separator"));
            
            System.out.println("numeros por bloque f0 " + numerosPorBloqueF0.size());
            for (int j = 0; j < numerosPorBloqueF0.size(); ++j)
            {
                if (j % 2 == 0)
                {
                    
                    System.out.println("j es 0");
                    
                    leerLineaF0.useDelimiter(",");
                    
                    for (int k = 0; k < numerosPorBloqueF0.get(j); ++k)
                    {
                        
                        Float tempF0;
                        
                        if (k == (numerosPorBloqueF0.get(j) - 1))
                        {
                            
                            System.out.println("Se ejecuta?");
                            
                            leerLineaF0.useDelimiter("");
                            System.out.println(leerLineaF0.next());
                            leerLineaF0.useDelimiter("'");
                            
                            tempF0 = leerLineaF0.nextFloat();
                            System.out.println(tempF0 + " en " + k);
                            
                            escribirF1.write(tempF0.toString());
                            leerLineaF0.useDelimiter("");
                            System.out.println(leerLineaF0.next());
                            leerLineaF0.useDelimiter(",");
                            
                        }
                        else
                        {
                            tempF0 = leerLineaF0.nextFloat();
                            System.out.println(tempF0 + " en " + k);
                        
                            escribirF1.write(tempF0.toString() + ",");
                        }
                        
                    }
                    
                    escribirF1.write("'");
                    
                }
                else
                {
                    
                    System.out.println("j es 1");
                    
                    leerLineaF0.useDelimiter(",");
                    
                    for (int k = 0; k < numerosPorBloqueF0.get(j); ++k)
                    {
                        
                        Float tempF0;
                        
                        if (k == numerosPorBloqueF0.get(j) - 1)
                        {
                            leerLineaF0.useDelimiter("");
                            System.out.println(leerLineaF0.next());
                            leerLineaF0.useDelimiter("'");
                            
                            tempF0 = leerLineaF0.nextFloat();
                            
                            escribirF2.write(tempF0.toString());
                            
                            leerLineaF0.useDelimiter("");
                            System.out.println(leerLineaF0.next());
                            //leerLineaF0.useDelimiter(",")
                        }
                        else
                        {
                            tempF0 = leerLineaF0.nextFloat();
                            System.out.println(tempF0 + " en " + k);
                        
                            escribirF2.write(tempF0.toString() + ",");
                        }
                        
                    }
                    
                    escribirF2.write("'");
                    
                }
            }
            
            leerLineaF0.close();
            escribirF1.close();
            escribirF2.close();
            numerosPorBloqueF0.clear();
            
        }
        
    }
    
    public static void mezclaEquilibrada(int orden, int numeroDatos)
    {
        
    }
    
    public static void distribucion(int orden, int numeroDatos)
    {
        
    }
    
    public static void ordenar(ArrayList<Float> lista, boolean orden)
    {
        
        if (!orden)
        {
            for (int i = 0; i < lista.size(); ++i)
            {
                for (int j = 0; j < lista.size() - 1; ++j)
                {
                    if (lista.get(j) > lista.get(j + 1))
                    {
                        float temp = lista.get(j);
                        lista.set(j, lista.get(j + 1));
                        lista.set(j + 1, temp);
                    }
                }
            }
        }
        
        else
        {
            for (int i = 0; i < lista.size(); ++i)
            {
                for (int j = 0; j < lista.size() - 1; ++j)
                {
                    if (lista.get(j) < lista.get(j + 1))
                    {
                        float temp = lista.get(j);
                        lista.set(j, lista.get(j + 1));
                        lista.set(j + 1, temp);
                    }
                }
            }
        }
        
    }
    
}
