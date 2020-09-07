/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab01_juanjulio_jorgesalazar_camilosinning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author camil
 */
public class JsonMethods {

    //Metodo de prueba (Inicio)
    public static void ShowPosts() {

        File f = new File("Posts.json");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }

        } catch (IOException e) {
            System.out.println("Error");
        }

    }

    public static void ShowUsers() {
        File f = new File("Users.json");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }

        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public static void ShowComments() {
        File f = new File("Comments.json");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            while ((linea = br.readLine()) != null) {
                System.out.println(linea);
            }

        } catch (IOException e) {
            System.out.println("Error");
        }
    }
    //(Fin)

    //Calcular maximo de cada cosa
    //maximos numero post de un usuario
    //maximo numero de comentarios en cada post
    //cuantos usuarios hay (10)
    public static String[] SplitUsers() {

        File f = new File("Users.json");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            //el tamaño del vector debe ser hallado de otra forma
            int j = 0;
            boolean sw = false;
            String[] users = new String[10];
            //recorre cada linea
            linea = br.readLine();
            linea = br.readLine();
            while ((linea = br.readLine()) != null) {
                
                //solo lineas que valgan la pena
                if (linea.length() > 3) {
                    //Recorre cada palabra de una linea
                    int i = 0;
                    boolean sw2 = true;

                    String palabra = linea.substring(i, i + 1);
                    while ((!palabra.equals("id") || !palabra.equals("bs")) && sw2) {
                        System.out.println("ciclo: "+palabra);                                      
                        i = i + 1;
                        palabra = linea.substring(i, i + 2);

                        if (i + 2 >= linea.length()) {
                            sw2 = false;
                        }

                    }

                    //comienza info de usuario en campo de vector
                    if (palabra.equals("id")) {
                        sw = true;
                    }

                    //mete las lineas siguientes al vector
                    if (sw) {
                        users[j] = users[j] + linea;                       
                    }else{
                        j=j+1;
                    }

                    //termina info de usuario en campo de vector
                    if (palabra.equals("bs")) {
                        sw = false;
                    }
                    
                }
            }

            return users;
        } catch (IOException e) {
            System.out.println("Mucho texto (Error!)");
            return null;
        }

    }

}
