package org.example;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class EjemplosVarios {
    /* Pide un número entero por teclado y lo añade al final de un fichero binario enteros.dat que contiene números enteros.
       El programa utiliza un método mostrarFichero() que se llama dos veces. La primera muestra el contenido del fichero antes
       de añadir el nuevo número y la segunda llamada muestra el fichero después de añadirlo.*/
    public static void escribirEntero() {
        Scanner sc = new Scanner(System.in);
        RandomAccessFile fichero = null;
        int numero;
        try {
            //se abre el fichero para lectura y escritura
            fichero = new RandomAccessFile("enteros.dat", "rw");
            mostrarFichero(); //muestra el contenido original del fichero
            System.out.print("Introduce un número entero para añadir al final del fichero: ");
            numero = sc.nextInt(); //se lee el entero a añadir en el fichero
            fichero.seek(fichero.length()); //nos situamos al final del fichero
            fichero.writeInt(numero);       //se escribe el entero
            mostrarFichero();//muestra el contenido del fichero después de añadir el número

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static void mostrarFichero() {
        RandomAccessFile fichero = null;
        try {
            fichero = new RandomAccessFile("enteros.dat", "r");
            int n;
            try {
                fichero.seek(0); //nos situamos al principio
                while (true) {
                    n = fichero.readInt();  //se lee  un entero del fichero
                    System.out.println(n);  //se muestra en pantalla
                }
            } catch (EOFException e) {
                System.out.println("Fin de fichero");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*Modifica un entero dentro del fichero enteros.dat con acceso aleatorio.
        Para ello se pide la posición que ocupa el entero a modificar dentro del fichero,
        a continuación se lee y muestra el valor actual, se pide el nuevo valor y finalmente se escribe el nuevo valor
        en la posición indicada, modificando de esta forma el valor antiguo por el nuevo.*/
    public static void modificarEntero() {
        Scanner sc = new Scanner(System.in);
        RandomAccessFile fichero = null;
        int pos, numero;
        long size;
        try {
            fichero = new RandomAccessFile("enteros.dat", "rw");

            //calcular cuántos enteros tiene el fichero
            size = fichero.length();
            size = size / 4;
            System.out.println("El fichero tiene " + size + " enteros");

            //Modificar el entero que se encuentra en una posición determinada
            do {
                System.out.println("Introduce una posición (>=1 y <= " + size + "): ");
                pos = sc.nextInt();
            } while (pos < 1 || pos > size);

            pos--;  //la posición 1 realmente es la 0

            //nos situamos en la posición (byte de inicio) del entero a modificar
            //en Java un entero ocupa 4 bytes
            fichero.seek(pos * 4);

            //leemos y mostramos el valor actual
            System.out.println("Valor actual: " + fichero.readInt());

            //pedimos que se introduzca el nuevo valor
            System.out.println("Introduce nuevo valor: ");
            numero = sc.nextInt();

            //nos situamos de nuevo en la posición del entero a modificar
            //esto es necesario porque después de la lectura que hemos realizado para mostrar
            //el valor el puntero de lectura/escritura ha avanzado al siguiente entero del fichero.
            //si no hacemos esto escribiremos sobre el siguiente entero
            fichero.seek(pos * 4);

            //escribimos el entero
            fichero.writeInt(numero);

        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
