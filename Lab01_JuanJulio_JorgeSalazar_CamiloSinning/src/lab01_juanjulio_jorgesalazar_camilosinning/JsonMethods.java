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

            //Asigno "" a vector
            for (int i = 0; i < 10; i++) {
                users[i] = "";
            }

            //recorre cada linea
            while ((linea = br.readLine()) != null) {

                //solo lineas que valgan la pena
                if (linea.length() > 4) {
                    //Recorre cada palabra de una linea
                    int i = 0;
                    boolean sw2 = true;

                    String palabra = linea.substring(i, i + 1);
                    while (!palabra.equals("\"id\"") && sw2 && !palabra.equals("\"bs\"")) {
                        //System.out.println("ciclo: "+palabra);                                      
                        i = i + 1;
                        palabra = linea.substring(i, i + 4);

                        if (i + 4 >= linea.length()) {
                            sw2 = false;
                        }

                    }

                    //comienza info de usuario en campo de vector
                    //System.out.println("palabra es: "+palabra);
                    if (palabra.equals("\"id\"")) {
                        sw = true;

                        //System.out.println("entre a id sw true");
                    }

                    //mete las lineas siguientes al vector
                    if (sw) {
                        //System.out.println("linea es: "+linea);
                        
                        users[j] = users[j] + linea;
                    } else {
                        j = j + 1;
                       
                    }

                    if (palabra.equals("\"bs\"")) {
                        sw = false;
                        //System.out.println("entre a bs sw false");
                    }
                    

                }
            }
            

            return users;
        } catch (IOException e) {
            System.out.println("Mucho texto (Error!)");
            return null;
        }

    }

    public static User StringToUser(String myUser) {
        int id = 0;
        String username;
        String name = "";
        String email;
        String address;
        String phone;
        String website;
        String company;
        Post[] myPosts;
        
        // recorrer texto para hayar id
        //recorrer todo buscando if y omito las "
        boolean sw = true ;
        int i = 0;
        String palabra;
        while(sw){
            i=i+1;
            palabra = myUser.substring(i,i+4);           
            if(palabra.equals("\"id\"")){
                sw =false;
                String desde = myUser.substring(i+4);
                int desdenum = i+6;
                while(!palabra.equals(",")){                                       
                    i=i+1;
                    palabra = myUser.substring(i,i+1);
                }
                int hastanum = i-1;
                id = Integer.parseInt(myUser.substring(desdenum, hastanum));
            }            
        }  
        System.out.println(id);
        
        sw = true ;
        i = 0;
        while(sw){
            i=i+1;
            palabra = myUser.substring(i,i+6);           
            if(palabra.equals("\"name\"")){
                sw =false;
                String desde = myUser.substring(i+6);
                int desdenum = i+9;
                while(!palabra.equals(",")){                                       
                    i=i+1;
                    palabra = myUser.substring(i,i+1);
                }
                int hastanum = i-1;
                name = myUser.substring(desdenum, hastanum);
            }            
        }  
        System.out.println(name);
        //de momento
        return null;
    }

}
