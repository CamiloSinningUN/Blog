package lab01_juanjulio_jorgesalazar_camilosinning;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class BIK_Blog extends javax.swing.JFrame {

    Blog Raiz = new Blog();
    
    //Variables auxiliares 
    int PrincipalHere;
    int UserHere;      
    User UserNow;

    int user;

    boolean sw;

    public BIK_Blog() {
        initComponents();
        setLocationRelativeTo(null);
        userInfo.setLocationRelativeTo(null);
        comments.setLocationRelativeTo(null);
        Arbol.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Images/blogger.png")).getImage()); 
        userInfo.setIconImage(new ImageIcon(getClass().getResource("/Images/blogger.png")).getImage());
        Arbol.setIconImage(new ImageIcon(getClass().getResource("/Images/blogger.png")).getImage());
        
        //Inicio
        Begin();

    }

    public void Begin() {
        String[] comments;
        String[] posts;
        String[] users;
        comments = JsonMethods.SplitComments();
        users = JsonMethods.SplitUsers();
        posts = JsonMethods.SplitPosts();

        //Agregar usuarios a raiz
        for (String user : users) {
            User u = JsonMethods.StringToUser(user);
            //Agregar post a usuario
            for (String post : posts) {
                Post p = JsonMethods.StringToPost(post);
                if (p.userId == u.id) {
                    u.InsertPost(p);
                }
                //agregar comments a post
                for (String comment : comments) {
                    Comment c = JsonMethods.StringToComment(comment);
                    if (c.postId == p.id) {
                        p.InsertComment(c);
                    }
                }
            }
            Raiz.InsertUser(u);
        }

        //poner post inicial        
        String title = Raiz.UserPTR.PostPTR.title;
        String post = Raiz.UserPTR.PostPTR.post;
        principalEditorPane.setText("<b>" + title + "</b><br>" + "<br>" + post);
        PrincipalHere = 1;

        //no hay nodo anterior
        backButton.setEnabled(false);
        nextButton.setEnabled(true);

    }

    public void BeginUser(User u) {

        UserNow = u;
        if (UserNow != null) {
            this.setVisible(false);
            userInfo.setVisible(true);
            infoUserEditorPane.setText("<b> Name: </b>" + UserNow.name + "<br>"
                    + "<b> username: </b>" + UserNow.username + "<br>"
                    + "<b> email: </b>" + UserNow.username + "<br>"
                    + "<b> address: </b><br>"
                    + "<b>&nbsp;street: </b>" + UserNow.address.street + "<br>"
                    + "<b>&nbsp;suite: </b>" + UserNow.address.suite + "<br>"
                    + "<b>&nbsp;city: </b>" + UserNow.address.city + "<br>"
                    + "<b>&nbsp;zipdoce: </b>" + UserNow.address.zipcode + "<br>"
                    + "<b>&nbsp;geo: </b><br>"
                    + "<b>&nbsp;&nbsp;lat: </b>" + UserNow.address.geo.lat + "<br>"
                    + "<b>&nbsp;&nbsp;lng: </b>" + UserNow.address.geo.lng + "<br>"
                    + "<b> phone: </b>" + UserNow.phone + "<br>"
                    + "<b> website: </b>" + UserNow.website + "<br>"
                    + "<b> company: </b><br>"
                    + "<b>&nbsp;name: </b>" + UserNow.company.name + "<br>"
                    + "<b>&nbsp;catchPhrase: </b>" + UserNow.company.catchPhrase + "<br>"
                    + "<b>&nbsp;bs: </b>" + UserNow.company.bs + "<br>");

            String title = UserNow.PostPTR.title;
            String post = UserNow.PostPTR.post;

            infoUserPostEditorPane.setText("<b>" + title + "</b><br>" + "<br>" + post);

            //no hay nodo anterior
            infoUserBackButton.setEnabled(false);
            infoUserNextButton.setEnabled(true);
            UserHere = UserNow.PostPTR.id;
        } else {
            ErrorMessage.setText("Please enter a valid ID/Name");
        }
        UserID.setText("");

    }

    public Post SearchPost(int id) {
        User u = Raiz.UserPTR;
        while (u != null) {
            Post p = u.PostPTR;
            while (p != null) {

                if (p.id == id) {

                    return p;

                }
                p = p.Link;
            }

            u = u.Link;
        }
        return null;
    }

    public void BeginComments(Post p) {

        Comment c = p.CommentPTR;
        String temp = "";
        while (c != null) {
            String name = c.name;
            String email = c.email;
            String comment = c.comment;

            commentsEditorPane.setText(temp
                    + "<b>name: </b>" + name + "<br>"
                    + "<b>email: </b>" + email + "<br><br>"
                    + comment);

            temp = temp + "<b>name: </b>" + name + "<br>" + "<b>email: </b>" + email + "<br><br>" + comment + "<br><br><hr>";
            c = c.Link;

        }       
        commentsEditorPane.setCaretPosition(0);      
    }

    public void DrawUser(Graphics g) {
        double diametro;
        int i = 1, x = 50, y = 400;

        diametro = 1050 / 10 / 2;
        g.setColor(Color.black);
        g.drawOval(505, 180, (int) diametro, (int) diametro);
        g.drawString("Bik Blog", 509, 210);
        while (i <= 10) {
            g.setColor(Color.black);
            g.drawOval(x, y, (int) diametro, (int) diametro);

            g.setColor(Color.red);
            g.drawLine(531, 231, x + 25, y);
            g.setColor(Color.gray);
            g.drawString("User #" + i, x + 4, y + 30);
            i++;
            x = x + 100;
        }
    }

    public void DrawPost(Graphics g, int j) {
        user = j;
        double diametro;
        int i = 1, x = 50, y = 400;

        diametro = 1050 / 10 / 2;
        g.setColor(Color.black);
        g.drawOval(600, 100, (int) diametro, (int) diametro);
        g.drawString("BikBlog", 605, 130);
        g.setColor(Color.red);
        g.drawLine(606, 141, 555, 198);
        g.setColor(Color.black);
        g.drawOval(505, 180, (int) diametro, (int) diametro);
        g.drawString("User #" + j, 509, 210);
        while (i <= 10) {
            g.setColor(Color.black);
            g.drawOval(x, y, (int) diametro, (int) diametro);
            g.setColor(Color.red);
            g.drawLine(531, 231, x + 25, y);
            g.setColor(Color.gray);
            g.drawString("Post #" + i, x + 4, y + 30);
            i++;
            x = x + 100;
        }
    }

    public void DrawComment(Graphics g, int j, int hijos) {
        int i = 1, x = 50, y = 400;

        g.setColor(Color.black);
        g.drawOval(505, 50, 50, 50);
        g.drawString("BikBlog", 509, 80);
        g.setColor(Color.red);
        g.drawLine(556, 77, 605, 109);
        g.setColor(Color.black);
        g.drawOval(600, 100, 50, 50);
        g.drawString("User #" + user, 605, 130);
        g.setColor(Color.red);
        g.drawLine(606, 141, 555, 198);
        g.setColor(Color.black);
        g.drawOval(505, 180, 50, 50);
        g.drawString("Post #" + j, 509, 210);
        while (i <= hijos) {
            g.setColor(Color.black);
            g.drawOval(x, y, 50, 50);
            g.setColor(Color.red);
            g.drawLine(531, 231, x + 25, y);
            g.setColor(Color.gray);
            g.drawString("Cmt #" + i, x + 4, y + 30);
            i++;
            x = x + 1050 / hijos / 2 + 100;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userInfo = new javax.swing.JFrame();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        infoUserEditorPane = new javax.swing.JEditorPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        infoUserBackButton = new javax.swing.JButton();
        infoUserNextButton = new javax.swing.JButton();
        commentsButton1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        infoUserPostEditorPane = new javax.swing.JEditorPane();
        jLabel10 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        comments = new javax.swing.JDialog();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        commentsEditorPane = new javax.swing.JEditorPane();
        jLabel19 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        Arbol = new javax.swing.JFrame();
        tablero = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Graficar = new javax.swing.JButton();
        Graficar1 = new javax.swing.JButton();
        Display = new javax.swing.JTextField();
        Buscar2 = new javax.swing.JButton();
        Buscar3 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        UserID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Buscar = new javax.swing.JButton();
        ErrorMessage = new javax.swing.JTextField();
        VistaArbol = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        commentsButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        principalEditorPane = new javax.swing.JEditorPane();
        infoPostButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        userInfo.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        userInfo.setUndecorated(true);
        userInfo.setResizable(false);
        userInfo.setSize(new java.awt.Dimension(1060, 580));
        userInfo.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/account-circle.png"))); // NOI18N
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 130, 120));

        infoUserEditorPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 8));
        infoUserEditorPane.setContentType("text/html"); // NOI18N
        infoUserEditorPane.setFont(new java.awt.Font("Maiandra GD", 0, 15)); // NOI18N
        infoUserEditorPane.setFocusable(false);
        jScrollPane2.setViewportView(infoUserEditorPane);

        jPanel4.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 250, 390));

        userInfo.getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 580));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setBackground(new java.awt.Color(58, 108, 146));
        jLabel14.setFont(new java.awt.Font("Maiandra GD", 0, 70)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(58, 108, 146));
        jLabel14.setText("Posts:");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 250, 100));

        infoUserBackButton.setBackground(new java.awt.Color(255, 255, 255));
        infoUserBackButton.setForeground(new java.awt.Color(255, 255, 255));
        infoUserBackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/return.png"))); // NOI18N
        infoUserBackButton.setBorderPainted(false);
        infoUserBackButton.setContentAreaFilled(false);
        infoUserBackButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        infoUserBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoUserBackButtonActionPerformed(evt);
            }
        });
        jPanel5.add(infoUserBackButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 80, 60));

        infoUserNextButton.setBackground(new java.awt.Color(255, 255, 255));
        infoUserNextButton.setForeground(new java.awt.Color(255, 255, 255));
        infoUserNextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/share.png"))); // NOI18N
        infoUserNextButton.setBorderPainted(false);
        infoUserNextButton.setContentAreaFilled(false);
        infoUserNextButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        infoUserNextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoUserNextButtonActionPerformed(evt);
            }
        });
        jPanel5.add(infoUserNextButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 450, 80, 60));

        commentsButton1.setBackground(new java.awt.Color(255, 255, 255));
        commentsButton1.setForeground(new java.awt.Color(255, 255, 255));
        commentsButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/android-messages.png"))); // NOI18N
        commentsButton1.setBorderPainted(false);
        commentsButton1.setContentAreaFilled(false);
        commentsButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        commentsButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                commentsButton1MouseClicked(evt);
            }
        });
        commentsButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commentsButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(commentsButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 60, 40, 50));

        infoUserPostEditorPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 20));
        infoUserPostEditorPane.setContentType("text/html"); // NOI18N
        infoUserPostEditorPane.setFont(new java.awt.Font("Maiandra GD", 0, 24)); // NOI18N
        jScrollPane3.setViewportView(infoUserPostEditorPane);

        jPanel5.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 710, 310));

        jLabel10.setBackground(new java.awt.Color(160, 194, 211));
        jLabel10.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(160, 194, 211));
        jLabel10.setText("Bik Blog");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 470, 66, 32));

        userInfo.getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 770, 530));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setForeground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Multiply_32px.png"))); // NOI18N
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        jPanel6.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, -1, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/image.png"))); // NOI18N
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        jPanel6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 20, -1, -1));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Expand_Arrow_32px.png"))); // NOI18N
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel17MouseClicked(evt);
            }
        });
        jPanel6.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, -1, -1));

        userInfo.getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, -10, 770, 60));

        comments.setBackground(new java.awt.Color(255, 255, 255));
        comments.setMinimumSize(new java.awt.Dimension(1050, 500));
        comments.setUndecorated(true);
        comments.setResizable(false);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setMinimumSize(new java.awt.Dimension(1050, 500));
        jPanel7.setPreferredSize(new java.awt.Dimension(1050, 500));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane4.setFocusable(false);
        jScrollPane4.setOpaque(false);
        jScrollPane4.setRequestFocusEnabled(false);

        commentsEditorPane.setContentType("text/html"); // NOI18N
        commentsEditorPane.setFont(new java.awt.Font("Maiandra GD", 0, 22)); // NOI18N
        commentsEditorPane.setFocusable(false);
        jScrollPane4.setViewportView(commentsEditorPane);

        jPanel7.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 990, 280));

        jLabel19.setBackground(new java.awt.Color(58, 108, 146));
        jLabel19.setFont(new java.awt.Font("Maiandra GD", 0, 60)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(58, 108, 146));
        jLabel19.setText("Comments");
        jPanel7.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 300, 100));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/image.png"))); // NOI18N
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel18MouseClicked(evt);
            }
        });
        jPanel7.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 10, -1, -1));

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Multiply_32px.png"))); // NOI18N
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });
        jPanel7.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, -1, -1));

        jLabel13.setBackground(new java.awt.Color(160, 194, 211));
        jLabel13.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(160, 194, 211));
        jLabel13.setText("Bik Blog");
        jPanel7.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 460, 60, 32));

        javax.swing.GroupLayout commentsLayout = new javax.swing.GroupLayout(comments.getContentPane());
        comments.getContentPane().setLayout(commentsLayout);
        commentsLayout.setHorizontalGroup(
            commentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        commentsLayout.setVerticalGroup(
            commentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, commentsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        Arbol.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        Arbol.setUndecorated(true);
        Arbol.setResizable(false);
        Arbol.setSize(new java.awt.Dimension(1060, 580));
        Arbol.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tablero.setBackground(new java.awt.Color(255, 255, 255));
        tablero.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableroMouseClicked(evt);
            }
        });
        tablero.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/image.png"))); // NOI18N
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel23MouseClicked(evt);
            }
        });
        tablero.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 20, -1, -1));

        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Expand_Arrow_32px.png"))); // NOI18N
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel24MouseClicked(evt);
            }
        });
        tablero.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 20, -1, -1));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Multiply_32px.png"))); // NOI18N
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });
        tablero.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 20, -1, -1));

        Graficar.setBackground(new java.awt.Color(255, 255, 255));
        Graficar.setText("GRAFICAR ÁRBOL");
        Graficar.setBorderPainted(false);
        Graficar.setContentAreaFilled(false);
        Graficar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Graficar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                GraficarMouseClicked(evt);
            }
        });
        Graficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GraficarActionPerformed(evt);
            }
        });
        Graficar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                GraficarKeyPressed(evt);
            }
        });
        tablero.add(Graficar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 180, 50));

        Graficar1.setBackground(new java.awt.Color(255, 255, 255));
        Graficar1.setBorderPainted(false);
        Graficar1.setContentAreaFilled(false);
        Graficar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Graficar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Graficar1MouseClicked(evt);
            }
        });
        Graficar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Graficar1ActionPerformed(evt);
            }
        });
        Graficar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Graficar1KeyPressed(evt);
            }
        });
        tablero.add(Graficar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 0, 180, 50));
        tablero.add(Display, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 539, 170, 30));

        Buscar2.setBackground(new java.awt.Color(255, 255, 255));
        Buscar2.setText("User");
        Buscar2.setBorderPainted(false);
        Buscar2.setContentAreaFilled(false);
        Buscar2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Buscar2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Buscar2MouseClicked(evt);
            }
        });
        Buscar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Buscar2ActionPerformed(evt);
            }
        });
        Buscar2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Buscar2KeyPressed(evt);
            }
        });
        tablero.add(Buscar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 540, 180, 30));

        Buscar3.setBackground(new java.awt.Color(255, 255, 255));
        Buscar3.setText("Post");
        Buscar3.setBorderPainted(false);
        Buscar3.setContentAreaFilled(false);
        Buscar3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Buscar3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Buscar3MouseClicked(evt);
            }
        });
        Buscar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Buscar3ActionPerformed(evt);
            }
        });
        Buscar3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Buscar3KeyPressed(evt);
            }
        });
        tablero.add(Buscar3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 540, 180, 30));

        Arbol.getContentPane().add(tablero, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 580));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(1060, 580));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/account-circle.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 34, -1, -1));

        jSeparator1.setAutoscrolls(true);
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 260, 180, 10));

        UserID.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        UserID.setForeground(new java.awt.Color(153, 153, 153));
        UserID.setText("Enter ID/Name");
        UserID.setBorder(null);
        UserID.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        UserID.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UserIDMouseClicked(evt);
            }
        });
        UserID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserIDActionPerformed(evt);
            }
        });
        UserID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                UserIDKeyReleased(evt);
            }
        });
        jPanel1.add(UserID, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 120, 40));

        jLabel3.setBackground(new java.awt.Color(160, 194, 211));
        jLabel3.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(160, 194, 211));
        jLabel3.setText("Bik Blog");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 520, 66, 32));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/account.png"))); // NOI18N
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 30, 20));

        jLabel6.setBackground(new java.awt.Color(160, 194, 211));
        jLabel6.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(160, 194, 211));
        jLabel6.setText("Search:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 210, 70, 32));

        Buscar.setBackground(new java.awt.Color(255, 255, 255));
        Buscar.setForeground(new java.awt.Color(255, 255, 255));
        Buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/magnify.png"))); // NOI18N
        Buscar.setBorderPainted(false);
        Buscar.setContentAreaFilled(false);
        Buscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Buscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BuscarMouseClicked(evt);
            }
        });
        Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BuscarActionPerformed(evt);
            }
        });
        Buscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BuscarKeyPressed(evt);
            }
        });
        jPanel1.add(Buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 40, 50));

        ErrorMessage.setEditable(false);
        ErrorMessage.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        ErrorMessage.setForeground(new java.awt.Color(255, 99, 71));
        ErrorMessage.setBorder(null);
        ErrorMessage.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        ErrorMessage.setFocusable(false);
        ErrorMessage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ErrorMessageMouseClicked(evt);
            }
        });
        ErrorMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ErrorMessageActionPerformed(evt);
            }
        });
        ErrorMessage.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ErrorMessageKeyReleased(evt);
            }
        });
        jPanel1.add(ErrorMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 190, 40));

        VistaArbol.setBackground(new java.awt.Color(255, 255, 255));
        VistaArbol.setForeground(new java.awt.Color(255, 255, 255));
        VistaArbol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/family-tree.png"))); // NOI18N
        VistaArbol.setBorderPainted(false);
        VistaArbol.setContentAreaFilled(false);
        VistaArbol.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        VistaArbol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                VistaArbolMouseClicked(evt);
            }
        });
        VistaArbol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VistaArbolActionPerformed(evt);
            }
        });
        VistaArbol.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                VistaArbolKeyPressed(evt);
            }
        });
        jPanel1.add(VistaArbol, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, 40, 40));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 580));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setBackground(new java.awt.Color(58, 108, 146));
        jLabel4.setFont(new java.awt.Font("Maiandra GD", 0, 70)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(58, 108, 146));
        jLabel4.setText("Posts:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 250, 100));

        backButton.setBackground(new java.awt.Color(255, 255, 255));
        backButton.setForeground(new java.awt.Color(255, 255, 255));
        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/return.png"))); // NOI18N
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        jPanel2.add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 80, 60));

        nextButton.setBackground(new java.awt.Color(255, 255, 255));
        nextButton.setForeground(new java.awt.Color(255, 255, 255));
        nextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/share.png"))); // NOI18N
        nextButton.setBorderPainted(false);
        nextButton.setContentAreaFilled(false);
        nextButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });
        jPanel2.add(nextButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 450, 80, 60));

        commentsButton.setBackground(new java.awt.Color(255, 255, 255));
        commentsButton.setForeground(new java.awt.Color(255, 255, 255));
        commentsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/android-messages.png"))); // NOI18N
        commentsButton.setBorderPainted(false);
        commentsButton.setContentAreaFilled(false);
        commentsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        commentsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                commentsButtonMouseClicked(evt);
            }
        });
        commentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commentsButtonActionPerformed(evt);
            }
        });
        jPanel2.add(commentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 60, 40, 50));

        principalEditorPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 20));
        principalEditorPane.setContentType("text/html"); // NOI18N
        principalEditorPane.setFont(new java.awt.Font("Maiandra GD", 0, 24)); // NOI18N
        jScrollPane1.setViewportView(principalEditorPane);

        jPanel2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 710, 310));

        infoPostButton.setBackground(new java.awt.Color(255, 255, 255));
        infoPostButton.setForeground(new java.awt.Color(255, 255, 255));
        infoPostButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/account-question (1).png"))); // NOI18N
        infoPostButton.setBorderPainted(false);
        infoPostButton.setContentAreaFilled(false);
        infoPostButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        infoPostButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                infoPostButtonMouseClicked(evt);
            }
        });
        infoPostButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoPostButtonActionPerformed(evt);
            }
        });
        jPanel2.add(infoPostButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 60, 40, 50));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 770, 530));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Multiply_32px.png"))); // NOI18N
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, -1, -1));

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Expand_Arrow_32px.png"))); // NOI18N
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, -1, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, -10, 770, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // TODO add your handling code here:
        this.setState(BIK_Blog.ICONIFIED);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        // TODO add your handling code here:
        System.exit(0);

    }//GEN-LAST:event_jLabel7MouseClicked

    private void commentsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }


    private void UserIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UserIDActionPerformed

    private void UserIDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UserIDMouseClicked
        // TODO add your handling code here:
        UserID.selectAll();
    }//GEN-LAST:event_UserIDMouseClicked

    private void UserIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UserIDKeyReleased
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Buscar.doClick();
        }
    }//GEN-LAST:event_UserIDKeyReleased

    private void BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BuscarActionPerformed
        String id = UserID.getText();
        try {
            BeginUser(Raiz.SearchUser(Integer.parseInt(id)));
        } catch (NumberFormatException e) {
            BeginUser(Raiz.SearchName(id));
            ErrorMessage.setText("Please enter a valid ID/Name");
        }

    }//GEN-LAST:event_BuscarActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        PrincipalHere = Raiz.back(PrincipalHere, principalEditorPane);
        if (PrincipalHere == 1) {
            backButton.setEnabled(false);
        }
        if (PrincipalHere < 100) {
            nextButton.setEnabled(true);
        }
    }//GEN-LAST:event_backButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        PrincipalHere = Raiz.next(PrincipalHere, principalEditorPane);
        if (PrincipalHere > 1) {
            backButton.setEnabled(true);
        }
        if (PrincipalHere >= 100) {
            nextButton.setEnabled(false);
        }
    }//GEN-LAST:event_nextButtonActionPerformed

    private void infoUserBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoUserBackButtonActionPerformed
        UserHere = UserNow.back(UserHere, infoUserPostEditorPane);
        if (UserHere == UserNow.PostPTR.id) {
            infoUserBackButton.setEnabled(false);
        }
        if (UserHere < UserNow.PostsSize() + UserNow.PostPTR.id - 1) {
            infoUserNextButton.setEnabled(true);
        }

    }//GEN-LAST:event_infoUserBackButtonActionPerformed

    private void infoUserNextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoUserNextButtonActionPerformed
        UserHere = UserNow.next(UserHere, infoUserPostEditorPane);
        if (UserHere > UserNow.PostPTR.id) {
            infoUserBackButton.setEnabled(true);
        }

        if (UserHere >= UserNow.PostsSize() + UserNow.PostPTR.id - 1) {
            infoUserNextButton.setEnabled(false);
        }


    }//GEN-LAST:event_infoUserNextButtonActionPerformed

    private void commentsButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commentsButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_commentsButton1ActionPerformed

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
        // TODO add your handling code here:
        userInfo.setVisible(false);
        setVisible(true);
        ErrorMessage.setText("");
    }//GEN-LAST:event_jLabel16MouseClicked

    private void jLabel17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel17MouseClicked
        // TODO add your handling code here:
        userInfo.setState(userInfo.ICONIFIED);
    }//GEN-LAST:event_jLabel17MouseClicked


    private void infoPostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoPostButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_infoPostButtonActionPerformed


    private void infoPostButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoPostButtonMouseClicked
        Post post = SearchPost(PrincipalHere);
        BeginUser(Raiz.SearchUser(post.userId));
        String title = post.title;
        String info = post.post;
        infoUserPostEditorPane.setText("<b>" + title + "</b><br>" + "<br>" + info);
        infoUserBackButton.setEnabled(false);
        infoUserNextButton.setEnabled(false);
    }//GEN-LAST:event_infoPostButtonMouseClicked

    private void commentsButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_commentsButton1MouseClicked
        // TODO add your handling code here:
        userInfo.setVisible(false);
        comments.setVisible(true);
        Post post = SearchPost(UserHere);
        BeginComments(post);
        sw = false;
    }//GEN-LAST:event_commentsButton1MouseClicked

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jLabel11MouseClicked

    private void jLabel18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel18MouseClicked
        if (sw) {
            comments.setVisible(false);
            setVisible(true);
        } else {
            comments.setVisible(false);
            userInfo.setVisible(true);
        }

    }//GEN-LAST:event_jLabel18MouseClicked

    private void ErrorMessageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ErrorMessageMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ErrorMessageMouseClicked

    private void ErrorMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ErrorMessageActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ErrorMessageActionPerformed

    private void ErrorMessageKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ErrorMessageKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_ErrorMessageKeyReleased

    private void commentsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_commentsButtonMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        comments.setVisible(true);
        Post post = SearchPost(PrincipalHere);
        System.out.println(post.id);
        BeginComments(post);
        sw = true;
    }//GEN-LAST:event_commentsButtonMouseClicked

    private void VistaArbolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VistaArbolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VistaArbolActionPerformed

    private void BuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BuscarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarMouseClicked

    private void BuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BuscarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BuscarKeyPressed

    private void VistaArbolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_VistaArbolMouseClicked
        // TODO add your handling code here:
        this.setVisible(false);
        Arbol.setVisible(true);
    }//GEN-LAST:event_VistaArbolMouseClicked

    private void VistaArbolKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_VistaArbolKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_VistaArbolKeyPressed

    private void tableroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableroMouseClicked
        // TODO add your handling code here:
        Point p = MouseInfo.getPointerInfo().getLocation();
        System.out.println(p.x + "," + p.y);
    }//GEN-LAST:event_tableroMouseClicked

    private void Graficar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Graficar1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Graficar1MouseClicked

    private void Graficar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Graficar1ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_Graficar1ActionPerformed

    private void Graficar1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Graficar1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Graficar1KeyPressed

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel24MouseClicked

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel23MouseClicked

    private void GraficarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GraficarKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_GraficarKeyPressed

    private void GraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GraficarActionPerformed
        // TODO add your handling code here:
        Graphics g = tablero.getGraphics();
        Graficar.setVisible(false);
        DrawUser(g);
    }//GEN-LAST:event_GraficarActionPerformed

    private void GraficarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GraficarMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_GraficarMouseClicked

    private void Buscar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Buscar2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Buscar2MouseClicked

    private void Buscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Buscar2ActionPerformed
        // TODO add your handling code here:
        Graphics g = tablero.getGraphics();
        g.clearRect(50, 50, 1000, 1000);
        g.setColor(Color.white);
        g.fillRect(50, 50, 1000, 1000);
        DrawPost(g, Integer.parseInt(Display.getText()));

    }//GEN-LAST:event_Buscar2ActionPerformed

    private void Buscar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Buscar2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Buscar2KeyPressed

    private void Buscar3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Buscar3MouseClicked
        // TODO add your handling code here:
        int hijos;
        Graphics g = tablero.getGraphics();
        g.clearRect(50, 50, 1000, 1000);
        g.setColor(Color.white);
        g.fillRect(50, 50, 1000, 1000);
        hijos = Raiz.SearchUser(2).PostPTR.CommentsSize();
        DrawComment(g, Integer.parseInt(Display.getText()), hijos);
    }//GEN-LAST:event_Buscar3MouseClicked

    private void Buscar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Buscar3ActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_Buscar3ActionPerformed

    private void Buscar3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Buscar3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Buscar3KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BIK_Blog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BIK_Blog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BIK_Blog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BIK_Blog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BIK_Blog().setVisible(true);

            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFrame Arbol;
    private javax.swing.JButton Buscar;
    private javax.swing.JButton Buscar2;
    private javax.swing.JButton Buscar3;
    private javax.swing.JTextField Display;
    private javax.swing.JTextField ErrorMessage;
    private javax.swing.JButton Graficar;
    private javax.swing.JButton Graficar1;
    private javax.swing.JTextField UserID;
    private javax.swing.JButton VistaArbol;
    private javax.swing.JButton backButton;
    private javax.swing.JDialog comments;
    private javax.swing.JButton commentsButton;
    private javax.swing.JButton commentsButton1;
    private javax.swing.JEditorPane commentsEditorPane;
    private javax.swing.JButton infoPostButton;
    private javax.swing.JButton infoUserBackButton;
    private javax.swing.JEditorPane infoUserEditorPane;
    private javax.swing.JButton infoUserNextButton;
    private javax.swing.JEditorPane infoUserPostEditorPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton nextButton;
    private javax.swing.JEditorPane principalEditorPane;
    private javax.swing.JPanel tablero;
    private javax.swing.JFrame userInfo;
    // End of variables declaration//GEN-END:variables
}
