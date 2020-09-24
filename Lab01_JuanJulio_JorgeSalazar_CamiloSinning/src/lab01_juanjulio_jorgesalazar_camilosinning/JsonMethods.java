
package lab01_juanjulio_jorgesalazar_camilosinning;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class JsonMethods {
   

    //Convierte el Json en una arreglo de cadenas, separado por la informacion de cada usario
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
                    if (palabra.equals("\"id\"")) {
                        sw = true;
                    }

                    //mete las lineas siguientes al vector
                    if (sw) {
                        users[j] = users[j] + linea;
                    } else {
                        j = j + 1;

                    }

                    if (palabra.equals("\"bs\"")) {
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

    //convierte un dato de texto en un objeto de tipo usuario
    public static User StringToUser(String myUser) {
        int id = 0;
        String username;
        String name;
        String email;
        
        //address
        String street;
        String suite;
        String city;
        String zipcode;       
            //geo
            String lat;
            String lng;
            //
        //       
        String phone;
        String website;
        //company;
        String companyName;
        String catchPhrase;
        String bs;       
        //      
      
        id = InfoInt("\"id\"", myUser);
        //System.out.println(id);

        name = InfoString("\"name\"", myUser);
        //System.out.println(name);

        username = InfoString("\"username\"", myUser);
        //System.out.println(username);

        email = InfoString("\"email\"", myUser);
        //System.out.println(email);

        //address
        street = InfoString("\"street\"", myUser);
        //System.out.println(street);
        
        suite = InfoString("\"suite\"", myUser);
        //System.out.println(suite);
        
        city = InfoString("\"city\"", myUser);
        //System.out.println(city);
        
        zipcode = InfoString("\"zipcode\"", myUser);
        //System.out.println(zipcode);        
            //geo
            lat = InfoString("\"lat\"", myUser);
            //System.out.println(lat);
        
            lng = InfoBody("\"lng\"", myUser);
            //System.out.println(lng);
            //
        //
       
        phone = InfoString("\"phone\"", myUser);
        //System.out.println(phone);
        
        website = InfoString("\"website\"", myUser);
        //System.out.println(website);
        //Company
              
        //caso especial
        companyName = "";
        boolean sw = true;
        int i = 0;
        String palabra;
        while (sw) {
            
            palabra = myUser.substring(i, i + 9);
            if (palabra.equals("\"company\"")) {
                sw = false;
                String Antedesde = myUser.substring(i + 9);
                int desdenum = i + 12;
                while (!palabra.equals("\"name\"")) {                                     
                    
                    i = i + 1;
                    palabra = myUser.substring(i, i + 6);   
                                     
                }
                desdenum = i + 9;
                i=i+9;
                while (!palabra.equals(",")) {                                     
                    
                    i = i + 1;
                    palabra = myUser.substring(i, i + 1);   
                                     
                }
                int hastanum = i - 1;
                companyName = myUser.substring(desdenum, hastanum);
            }
            i = i + 1;
        }
        //System.out.println(companyName);
        //
      
        
        catchPhrase = InfoString("\"catchPhrase\"", myUser);
        //System.out.println(catchPhrase);
        
        bs = InfoBody("\"bs\"", myUser);  
        //System.out.println(bs);        
        
        //Company
        Company company = new Company(companyName,catchPhrase,bs);       
        //address
        Geo geo = new Geo(Float.parseFloat(lat),Float.parseFloat(lng));
        Address address = new Address(street,suite,city,zipcode,geo);
        //user
        User u = new User(id, username, name, email, address, phone, website, company);
       
        return u;
    }

    //Convierte el Json en una arreglo de cadenas, separado por la informacion de cada commentario
    public static String[] SplitComments() {
        File f = new File("Comments.json");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            int j = 0;
            boolean sw = false;
            String[] comments = new String[500];

            //Asigno "" a vector
            for (int i = 0; i < 500; i++) {
                comments[i] = "";
            }

            //recorre cada linea
            while ((linea = br.readLine()) != null) {

                //solo lineas que valgan la pena
                if (linea.length() > 4) {
                    //Recorre cada palabra de una linea
                    int i = 0;
                    boolean sw2 = true;

                    String palabra1 = linea.substring(i, i + 1);
                    while (!palabra1.equals("\"postId\"") && sw2) {
                        //System.out.println("ciclo: "+palabra);                                      
                        i = i + 1;
                        palabra1 = linea.substring(i, i + 8);

                        if (i + 8 >= linea.length()) {
                            sw2 = false;
                        }

                    }

                    i = 0;
                    sw2 = true;
                    String palabra2 = linea.substring(i, i + 1);
                    while (!palabra2.equals("\"body\"") && sw2) {
                        //System.out.println("ciclo: "+palabra);                                      
                        i = i + 1;
                        palabra2 = linea.substring(i, i + 6);

                        if (i + 6 >= linea.length()) {
                            sw2 = false;
                        }

                    }

                    //comienza info de usuario en campo de vector                    
                    if (palabra1.equals("\"postId\"")) {
                        sw = true;
                    }


                    //mete las lineas siguientes al vector
                    if (sw) {
                        //System.out.println("linea es: "+linea);

                        comments[j] = comments[j] + linea;
                    }

                    if (palabra2.equals("\"body\"")) {
                        sw = false;
                        j++;

                    }

                }
            }

            return comments;
        } catch (IOException e) {
            System.out.println("Mucho texto (Error!)");
            return null;
        }
    }

    //metodo que extrae la informacion de una liena de json, si esta es una cadena de texto
    public static String InfoString(String word, String json) {
        String answer = "";
        boolean sw = true;
        int i = 0;
        String palabra;
        while (sw) {
            
            palabra = json.substring(i, i + word.length());
            if (palabra.equals(word)) {
                sw = false;
                String desde = json.substring(i + word.length());
                int desdenum = i + word.length() + 3;
                while (!palabra.equals(",")) {                                     
                    
                    i = i + 1;
                    palabra = json.substring(i, i + 1);   
                                     
                }
                int hastanum = i - 1;
                answer = json.substring(desdenum, hastanum);
            }
            i = i + 1;
        }
        return answer;
    }

    //metodo que extrae la informacion de una liena de json, si esta es un entero
    public static int InfoInt(String word, String json) {
        int answer = 0;
        boolean sw = true;
        int i = 0;
        String palabra="";
        while (sw) {
            i = i + 1;
            
            palabra = json.substring(i, i + word.length());
            if (palabra.equals(word)) {
                sw = false;
                String desde = json.substring(i + word.length());
                int desdenum = i + word.length() + 2;
                while (!palabra.equals(",")) {
                    i = i + 1;
                    palabra = json.substring(i, i + 1);
                }
                int hastanum = i ;
                answer = Integer.parseInt(json.substring(desdenum, hastanum));
            }
        }
        return answer;
    }
  
    //metodo que extrae la informacion de una liena de json, si esta es un caso especial que llame body 
    public static String InfoBody(String word, String json){
        String answer = "";
        boolean sw = true;
        int i = 0;
        String palabra;
        while (sw) {
            
            palabra = json.substring(i, i + word.length());
            if (palabra.equals(word)) {
                sw = false;
                String desde = json.substring(i + word.length());
                int desdenum = i + word.length() + 3;
                i=i + word.length() + 3;
                while (!palabra.equals("\"")) {                                     
                    
                    i = i + 1;
                    palabra = json.substring(i, i + 1);
                   
                                     
                }
                int hastanum = i;
                answer = json.substring(desdenum, hastanum);
            }
            i = i + 1;
        }
        return answer;
    }

    //convierte un dato de texto en un objeto de tipo commentario
    public static Comment StringToComment(String myComment) {
        String comment;
        int postId;
        int id;
        String name;
        String email;

        postId = InfoInt("\"postId\"", myComment);
        //System.out.println(postId);

        id = InfoInt("\"id\"", myComment);
        //System.out.println(id);

        name = InfoString("\"name\"", myComment);
        //System.out.println(name);

        email = InfoString("\"email\"", myComment);
        //System.out.println(email);

        comment = InfoBody("\"body\"", myComment);
        //System.out.println(comment);

        Comment c = new Comment(comment, postId, id, name, email);

        return c;
    }

    //Convierte el Json en una arreglo de cadenas, separado por la informacion de cada post
    public static String[] SplitPosts() {
        File f = new File("Posts.json");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String linea;
            //el tamaño del vector debe ser hallado de otra forma
            int j = 0;
            boolean sw = false;
            String[] posts = new String[100];

            //Asigno "" a vector
            for (int i = 0; i < 100; i++) {
                posts[i] = "";
            }

            //recorre cada linea
            while ((linea = br.readLine()) != null) {

                //solo lineas que valgan la pena
                if (linea.length() > 4) {
                    //Recorre cada palabra de una linea
                    int i = 0;
                    boolean sw2 = true;

                    String palabra1 = linea.substring(i, i + 1);
                    while (!palabra1.equals("\"userId\"") && sw2) {
                        //System.out.println("ciclo: "+palabra);                                      
                        i = i + 1;
                        palabra1 = linea.substring(i, i + 8);

                        if (i + 8 >= linea.length()) {
                            sw2 = false;
                        }

                    }

                    i = 0;
                    sw2 = true;
                    String palabra2 = linea.substring(i, i + 1);
                    while (!palabra2.equals("\"body\"") && sw2) {
                        //System.out.println("ciclo: "+palabra);                                      
                        i = i + 1;
                        palabra2 = linea.substring(i, i + 6);

                        if (i + 6 >= linea.length()) {
                            sw2 = false;
                        }

                    }

                    //comienza info de usuario en campo de vector
                    if (palabra1.equals("\"userId\"")) {
                        sw = true;

                    }


                    //mete las lineas siguientes al vector
                    if (sw) {
                        //System.out.println("linea es: "+linea);

                        posts[j] = posts[j] + linea;
                    }

                    if (palabra2.equals("\"body\"")) {
                        sw = false;
                        j++;

                    }

                }
            }

            return posts;
        } catch (IOException e) {
            System.out.println("Mucho texto (Error!)");
            return null;
        }
    }

//convierte un dato de texto en un objeto de tipo post    
    public static Post StringToPost(String myPost) {
        int id;
        int userId;
        String post;
        String title;

        userId = InfoInt("\"userId\"", myPost);
        //System.out.println(userId);

        id = InfoInt("\"id\"", myPost);
        //System.out.println(id);

        title = InfoString("\"title\"", myPost);
        //System.out.println(title);

        post = InfoBody("\"body\"", myPost);
        //System.out.println(post);
        

        //falta array
        Post p = new Post(userId, post, title, id);

        return p;
    }

}
