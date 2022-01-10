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

        //Atributos del frame
        setLocationRelativeTo(null);
        userInfo.setLocationRelativeTo(null);
        comments.setLocationRelativeTo(null);
        tree.setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Images/blogger.png")).getImage());
        userInfo.setIconImage(new ImageIcon(getClass().getResource("/Images/blogger.png")).getImage());
        tree.setIconImage(new ImageIcon(getClass().getResource("/Images/blogger.png")).getImage());

        //Se Carga la informacion a mostrar al abrir el programa
        Begin();

    }

    //Metodo para el inicio del frame principal
    public void Begin() {
        //variables auxiliares
        String[] comments;
        String[] posts;
        String[] users;

        //Metodos para fragmentar los json en arreglos
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
                //Agregar comments a post
                for (String comment : comments) {
                    Comment c = JsonMethods.StringToComment(comment);
                    if (c.postId == p.id) {
                        p.InsertComment(c);
                    }
                }
            }
            Raiz.InsertUser(u);
        }

        //Post inicial        
        String title = Raiz.UserPTR.PostPTR.title;
        String post = Raiz.UserPTR.PostPTR.post;
        principalEditorPane.setText("<b>" + title + "</b><br>" + "<br>" + post);

        //auxiliar
        PrincipalHere = 1;

        //No hay post anterior por lo tanto el boton de atras se bloquea       
        backButton.setEnabled(false);
        nextButton.setEnabled(true);

    }

    //Metodo para el inicio del frame userInfo
    public void BeginUser(User u) {

        UserNow = u;

        //validar que el usuario no es nulo
        if (UserNow != null) {
            //Informacion del usuario
            this.setVisible(false);
            userInfo.setVisible(true);
            userInfoEditorPane.setText("<b> Name: </b>" + UserNow.name + "<br>"
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
            //Post inicial
            String title = UserNow.PostPTR.title;
            String post = UserNow.PostPTR.post;
            infoUserPostEditorPane.setText("<b>" + title + "</b><br>" + "<br>" + post);

            //No hay post anterior por lo tanto el boton de atras se bloquea 
            userInfoBackButton.setEnabled(false);
            userInfoNextButton.setEnabled(true);

            //auxiliar
            UserHere = UserNow.PostPTR.id;
        } else {
            errorTextField.setText("Please enter a valid ID/Name");
        }
        //Borrar informacion de textField
        searchTextField.setText("");

    }

    //Metodo para encontrar un post dado un id
    public Post SearchPost(int id) {
        //recorrer usuarios
        User u = Raiz.UserPTR;
        while (u != null) {
            //recorrer posts
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

    //Metodo para el inicio del frame comments
    public void BeginComments(Post p) {

        //recorrer los comenarios del post dado
        Comment c = p.CommentPTR;
        String temp = "";
        while (c != null) {
            String name = c.name;
            String email = c.email;
            String comment = c.comment;

            //Se escriben los comentarios
            commentsEditorPane.setText(temp
                    + "<b>name: </b>" + name + "<br>"
                    + "<b>email: </b>" + email + "<br><br>"
                    + comment);

            temp = temp + "<b>name: </b>" + name + "<br>" + "<b>email: </b>" + email + "<br><br>" + comment + "<br><br><hr>";
            c = c.Link;

        }
        commentsEditorPane.setCaretPosition(0);
    }

    //Metodo para dibujar el primer subarbol de dos niveles donde se encuentran los usuarios
    public void DrawUser(Graphics g) {
        double diametro;
        int i = 1, x = 50, y = 400;
        
        if (user == 0){
            Buscar3.setEnabled(false);
            Buscar3.setVisible(false);
        }
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

    //Metodo para dibujar el primer subarbol de dos niveles donde se encuentran los post
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

    //Metodo para dibujar el primer subarbol de dos niveles donde se encuentran los commentarios
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
        userInfoPanel = new javax.swing.JPanel();
        userInfoPhoto = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        userInfoEditorPane = new javax.swing.JEditorPane();
        userInfoPostsPanel = new javax.swing.JPanel();
        userInfoPostsLabel = new javax.swing.JLabel();
        userInfoBackButton = new javax.swing.JButton();
        userInfoNextButton = new javax.swing.JButton();
        userInfoCommentsButton = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        infoUserPostEditorPane = new javax.swing.JEditorPane();
        userInfoBikBlog = new javax.swing.JLabel();
        userInfoClosePanel = new javax.swing.JPanel();
        userInfoCloseButton = new javax.swing.JLabel();
        userInfoGoBackButton = new javax.swing.JLabel();
        userInfoMinimizeButton = new javax.swing.JLabel();
        comments = new javax.swing.JDialog();
        commentsPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        commentsEditorPane = new javax.swing.JEditorPane();
        commentsLabel = new javax.swing.JLabel();
        commentsGoBackButton = new javax.swing.JLabel();
        commentsCloseButton = new javax.swing.JLabel();
        commentsBikBlog = new javax.swing.JLabel();
        tree = new javax.swing.JFrame();
        tablero = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Graficar = new javax.swing.JButton();
        Display = new javax.swing.JTextField();
        Buscar2 = new javax.swing.JButton();
        Buscar3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        searchPanel = new javax.swing.JPanel();
        userPhoto = new javax.swing.JLabel();
        searchLine = new javax.swing.JSeparator();
        searchTextField = new javax.swing.JTextField();
        bikBlogLogo = new javax.swing.JLabel();
        searchLogo = new javax.swing.JLabel();
        searchLabel = new javax.swing.JLabel();
        searchButton = new javax.swing.JButton();
        errorTextField = new javax.swing.JTextField();
        graphicTreeButton = new javax.swing.JButton();
        postsPanel = new javax.swing.JPanel();
        postsLabel = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        nextButton = new javax.swing.JButton();
        commentsButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        principalEditorPane = new javax.swing.JEditorPane();
        infoPostButton = new javax.swing.JButton();
        closePanel = new javax.swing.JPanel();
        closeLabel = new javax.swing.JLabel();
        minimizeLabel = new javax.swing.JLabel();

        userInfo.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        userInfo.setUndecorated(true);
        userInfo.setResizable(false);
        userInfo.setSize(new java.awt.Dimension(1060, 580));
        userInfo.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userInfoPanel.setBackground(new java.awt.Color(255, 255, 255));
        userInfoPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        userInfoPanel.setForeground(new java.awt.Color(255, 255, 255));
        userInfoPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userInfoPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/account-circle.png"))); // NOI18N
        userInfoPanel.add(userInfoPhoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 40, 130, 120));

        userInfoEditorPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 8));
        userInfoEditorPane.setContentType("text/html"); // NOI18N
        userInfoEditorPane.setFont(new java.awt.Font("Maiandra GD", 0, 15)); // NOI18N
        userInfoEditorPane.setFocusable(false);
        jScrollPane2.setViewportView(userInfoEditorPane);

        userInfoPanel.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 250, 390));

        userInfo.getContentPane().add(userInfoPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 580));

        userInfoPostsPanel.setBackground(new java.awt.Color(255, 255, 255));
        userInfoPostsPanel.setForeground(new java.awt.Color(255, 255, 255));
        userInfoPostsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userInfoPostsLabel.setBackground(new java.awt.Color(58, 108, 146));
        userInfoPostsLabel.setFont(new java.awt.Font("Maiandra GD", 0, 70)); // NOI18N
        userInfoPostsLabel.setForeground(new java.awt.Color(58, 108, 146));
        userInfoPostsLabel.setText("Posts:");
        userInfoPostsPanel.add(userInfoPostsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 250, 100));

        userInfoBackButton.setBackground(new java.awt.Color(255, 255, 255));
        userInfoBackButton.setForeground(new java.awt.Color(255, 255, 255));
        userInfoBackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/return.png"))); // NOI18N
        userInfoBackButton.setBorderPainted(false);
        userInfoBackButton.setContentAreaFilled(false);
        userInfoBackButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userInfoBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userInfoBackButtonActionPerformed(evt);
            }
        });
        userInfoPostsPanel.add(userInfoBackButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 80, 60));

        userInfoNextButton.setBackground(new java.awt.Color(255, 255, 255));
        userInfoNextButton.setForeground(new java.awt.Color(255, 255, 255));
        userInfoNextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/share.png"))); // NOI18N
        userInfoNextButton.setBorderPainted(false);
        userInfoNextButton.setContentAreaFilled(false);
        userInfoNextButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userInfoNextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userInfoNextButtonActionPerformed(evt);
            }
        });
        userInfoPostsPanel.add(userInfoNextButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 450, 80, 60));

        userInfoCommentsButton.setBackground(new java.awt.Color(255, 255, 255));
        userInfoCommentsButton.setForeground(new java.awt.Color(255, 255, 255));
        userInfoCommentsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/android-messages.png"))); // NOI18N
        userInfoCommentsButton.setBorderPainted(false);
        userInfoCommentsButton.setContentAreaFilled(false);
        userInfoCommentsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userInfoCommentsButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userInfoCommentsButtonMouseClicked(evt);
            }
        });
        userInfoCommentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userInfoCommentsButtonActionPerformed(evt);
            }
        });
        userInfoPostsPanel.add(userInfoCommentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 60, 40, 50));

        infoUserPostEditorPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 20));
        infoUserPostEditorPane.setContentType("text/html"); // NOI18N
        infoUserPostEditorPane.setFont(new java.awt.Font("Maiandra GD", 0, 24)); // NOI18N
        jScrollPane3.setViewportView(infoUserPostEditorPane);

        userInfoPostsPanel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 710, 310));

        userInfoBikBlog.setBackground(new java.awt.Color(160, 194, 211));
        userInfoBikBlog.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 24)); // NOI18N
        userInfoBikBlog.setForeground(new java.awt.Color(160, 194, 211));
        userInfoBikBlog.setText("Bik Blog");
        userInfoPostsPanel.add(userInfoBikBlog, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 470, 66, 32));

        userInfo.getContentPane().add(userInfoPostsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 770, 530));

        userInfoClosePanel.setBackground(new java.awt.Color(255, 255, 255));
        userInfoClosePanel.setForeground(new java.awt.Color(255, 255, 255));
        userInfoClosePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userInfoCloseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Multiply_32px.png"))); // NOI18N
        userInfoCloseButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userInfoCloseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userInfoCloseButtonMouseClicked(evt);
            }
        });
        userInfoClosePanel.add(userInfoCloseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, -1, -1));

        userInfoGoBackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/image.png"))); // NOI18N
        userInfoGoBackButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userInfoGoBackButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userInfoGoBackButtonMouseClicked(evt);
            }
        });
        userInfoClosePanel.add(userInfoGoBackButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 20, -1, -1));

        userInfoMinimizeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Expand_Arrow_32px.png"))); // NOI18N
        userInfoMinimizeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userInfoMinimizeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userInfoMinimizeButtonMouseClicked(evt);
            }
        });
        userInfoClosePanel.add(userInfoMinimizeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, -1, -1));

        userInfo.getContentPane().add(userInfoClosePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, -10, 770, 60));

        comments.setBackground(new java.awt.Color(255, 255, 255));
        comments.setMinimumSize(new java.awt.Dimension(1050, 500));
        comments.setUndecorated(true);
        comments.setResizable(false);

        commentsPanel.setBackground(new java.awt.Color(255, 255, 255));
        commentsPanel.setMinimumSize(new java.awt.Dimension(1050, 500));
        commentsPanel.setPreferredSize(new java.awt.Dimension(1050, 500));
        commentsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane4.setFocusable(false);
        jScrollPane4.setOpaque(false);
        jScrollPane4.setRequestFocusEnabled(false);

        commentsEditorPane.setContentType("text/html"); // NOI18N
        commentsEditorPane.setFont(new java.awt.Font("Maiandra GD", 0, 22)); // NOI18N
        commentsEditorPane.setFocusable(false);
        jScrollPane4.setViewportView(commentsEditorPane);

        commentsPanel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 990, 280));

        commentsLabel.setBackground(new java.awt.Color(58, 108, 146));
        commentsLabel.setFont(new java.awt.Font("Maiandra GD", 0, 60)); // NOI18N
        commentsLabel.setForeground(new java.awt.Color(58, 108, 146));
        commentsLabel.setText("Comments");
        commentsPanel.add(commentsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 300, 100));

        commentsGoBackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/image.png"))); // NOI18N
        commentsGoBackButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        commentsGoBackButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                commentsGoBackButtonMouseClicked(evt);
            }
        });
        commentsPanel.add(commentsGoBackButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 10, -1, -1));

        commentsCloseButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Multiply_32px.png"))); // NOI18N
        commentsCloseButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        commentsCloseButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                commentsCloseButtonMouseClicked(evt);
            }
        });
        commentsPanel.add(commentsCloseButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 10, -1, -1));

        commentsBikBlog.setBackground(new java.awt.Color(160, 194, 211));
        commentsBikBlog.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 24)); // NOI18N
        commentsBikBlog.setForeground(new java.awt.Color(160, 194, 211));
        commentsBikBlog.setText("Bik Blog");
        commentsPanel.add(commentsBikBlog, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 460, 60, 32));

        javax.swing.GroupLayout commentsLayout = new javax.swing.GroupLayout(comments.getContentPane());
        comments.getContentPane().setLayout(commentsLayout);
        commentsLayout.setHorizontalGroup(
            commentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(commentsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        commentsLayout.setVerticalGroup(
            commentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, commentsLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(commentsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tree.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        tree.setUndecorated(true);
        tree.setResizable(false);
        tree.setSize(new java.awt.Dimension(1060, 580));
        tree.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        Graficar.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        Graficar.setForeground(new java.awt.Color(53, 118, 153));
        Graficar.setText("GRAFICAR BLOQUE UNICIAL DEL ÁRBOL");
        Graficar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

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

        tablero.add(Graficar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 270, 30));


        Display.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        Display.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DisplayMouseClicked(evt);
            }
        });
        tablero.add(Display, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 530, 170, 30));

        Buscar2.setBackground(new java.awt.Color(255, 255, 255));
        Buscar2.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        Buscar2.setForeground(new java.awt.Color(53, 118, 153));
        Buscar2.setText("USER");
        Buscar2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Buscar2.setContentAreaFilled(false);
        Buscar2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Buscar2.setEnabled(false);
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
        tablero.add(Buscar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 530, 160, 30));

        Buscar3.setBackground(new java.awt.Color(255, 255, 255));
        Buscar3.setFont(new java.awt.Font("Tw Cen MT Condensed", 1, 18)); // NOI18N
        Buscar3.setForeground(new java.awt.Color(53, 118, 153));
        Buscar3.setText("POST");
        Buscar3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Buscar3.setContentAreaFilled(false);
        Buscar3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Buscar3.setEnabled(false);
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
        tablero.add(Buscar3, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 530, 170, 30));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT Condensed Extra Bold", 0, 48)); // NOI18N
        jLabel1.setText("AQUÍ SERÁ GRAFICADO SU ÁRBOL");
        tablero.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 620, 130));

        tree.getContentPane().add(tablero, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 580));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(1060, 580));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchPanel.setBackground(new java.awt.Color(255, 255, 255));
        searchPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        searchPanel.setForeground(new java.awt.Color(255, 255, 255));
        searchPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/account-circle.png"))); // NOI18N
        searchPanel.add(userPhoto, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 34, -1, -1));

        searchLine.setAutoscrolls(true);
        searchPanel.add(searchLine, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 260, 180, 10));

        searchTextField.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        searchTextField.setForeground(new java.awt.Color(153, 153, 153));
        searchTextField.setText("Enter ID/Name");
        searchTextField.setBorder(null);
        searchTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        searchTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchTextFieldMouseClicked(evt);
            }
        });
        searchTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextFieldActionPerformed(evt);
            }
        });
        searchTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTextFieldKeyReleased(evt);
            }
        });
        searchPanel.add(searchTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 230, 120, 40));

        bikBlogLogo.setBackground(new java.awt.Color(160, 194, 211));
        bikBlogLogo.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 24)); // NOI18N
        bikBlogLogo.setForeground(new java.awt.Color(160, 194, 211));
        bikBlogLogo.setText("Bik Blog");
        searchPanel.add(bikBlogLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(114, 520, 66, 32));

        searchLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/account.png"))); // NOI18N
        searchPanel.add(searchLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 30, 20));

        searchLabel.setBackground(new java.awt.Color(160, 194, 211));
        searchLabel.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 24)); // NOI18N
        searchLabel.setForeground(new java.awt.Color(160, 194, 211));
        searchLabel.setText("Search:");
        searchPanel.add(searchLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(34, 210, 70, 32));

        searchButton.setBackground(new java.awt.Color(255, 255, 255));
        searchButton.setForeground(new java.awt.Color(255, 255, 255));
        searchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/magnify.png"))); // NOI18N
        searchButton.setBorderPainted(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        searchButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchButtonMouseClicked(evt);
            }
        });
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });
        searchButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchButtonKeyPressed(evt);
            }
        });
        searchPanel.add(searchButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 40, 50));

        errorTextField.setEditable(false);
        errorTextField.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        errorTextField.setForeground(new java.awt.Color(255, 99, 71));
        errorTextField.setBorder(null);
        errorTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        errorTextField.setFocusable(false);
        errorTextField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                errorTextFieldMouseClicked(evt);
            }
        });
        errorTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                errorTextFieldActionPerformed(evt);
            }
        });
        errorTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                errorTextFieldKeyReleased(evt);
            }
        });
        searchPanel.add(errorTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 190, 40));

        graphicTreeButton.setBackground(new java.awt.Color(255, 255, 255));
        graphicTreeButton.setForeground(new java.awt.Color(255, 255, 255));
        graphicTreeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/family-tree.png"))); // NOI18N
        graphicTreeButton.setBorderPainted(false);
        graphicTreeButton.setContentAreaFilled(false);
        graphicTreeButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        graphicTreeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                graphicTreeButtonMouseClicked(evt);
            }
        });
        graphicTreeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                graphicTreeButtonActionPerformed(evt);
            }
        });
        graphicTreeButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                graphicTreeButtonKeyPressed(evt);
            }
        });
        searchPanel.add(graphicTreeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 530, 40, 40));

        getContentPane().add(searchPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 580));

        postsPanel.setBackground(new java.awt.Color(255, 255, 255));
        postsPanel.setForeground(new java.awt.Color(255, 255, 255));
        postsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        postsLabel.setBackground(new java.awt.Color(58, 108, 146));
        postsLabel.setFont(new java.awt.Font("Maiandra GD", 0, 70)); // NOI18N
        postsLabel.setForeground(new java.awt.Color(58, 108, 146));
        postsLabel.setText("Posts:");
        postsPanel.add(postsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 250, 100));

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
        postsPanel.add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 80, 60));

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
        postsPanel.add(nextButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 450, 80, 60));

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
        postsPanel.add(commentsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 60, 40, 50));

        principalEditorPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 20));
        principalEditorPane.setContentType("text/html"); // NOI18N
        principalEditorPane.setFont(new java.awt.Font("Maiandra GD", 0, 24)); // NOI18N
        jScrollPane1.setViewportView(principalEditorPane);

        postsPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 710, 310));

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
        postsPanel.add(infoPostButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 60, 40, 50));

        getContentPane().add(postsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 770, 530));

        closePanel.setBackground(new java.awt.Color(255, 255, 255));
        closePanel.setForeground(new java.awt.Color(255, 255, 255));
        closePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        closeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Multiply_32px.png"))); // NOI18N
        closeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        closeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeLabelMouseClicked(evt);
            }
        });
        closePanel.add(closeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, -1, -1));

        minimizeLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/icons8_Expand_Arrow_32px.png"))); // NOI18N
        minimizeLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        minimizeLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeLabelMouseClicked(evt);
            }
        });
        closePanel.add(minimizeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 20, -1, -1));

        getContentPane().add(closePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, -10, 770, 60));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void minimizeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeLabelMouseClicked
        
        this.setState(BIK_Blog.ICONIFIED);
    }//GEN-LAST:event_minimizeLabelMouseClicked

    private void closeLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeLabelMouseClicked
        System.exit(0);
    }//GEN-LAST:event_closeLabelMouseClicked

    private void commentsButtonActionPerformed(java.awt.event.ActionEvent evt) {
        
    }

    private void searchTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextFieldActionPerformed

    }//GEN-LAST:event_searchTextFieldActionPerformed

    private void searchTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchTextFieldMouseClicked
        searchTextField.selectAll();
    }//GEN-LAST:event_searchTextFieldMouseClicked

    private void searchTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextFieldKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            searchButton.doClick();
        }
    }//GEN-LAST:event_searchTextFieldKeyReleased

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        String id = searchTextField.getText();
        //Si se busca por id o por nombre
        try {
            BeginUser(Raiz.SearchUser(Integer.parseInt(id)));
        } catch (NumberFormatException e) {
            BeginUser(Raiz.SearchName(id));
        }
    }//GEN-LAST:event_searchButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        PrincipalHere = Raiz.back(PrincipalHere, principalEditorPane);

        //Si no existen mas post se bloquean los botones correspondientes
        if (PrincipalHere == 1) {
            backButton.setEnabled(false);
        }
        if (PrincipalHere < 100) {
            nextButton.setEnabled(true);
        }
    }//GEN-LAST:event_backButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        PrincipalHere = Raiz.next(PrincipalHere, principalEditorPane);

        //Si no existen mas post se bloquean los botones correspondientes
        if (PrincipalHere > 1) {
            backButton.setEnabled(true);
        }
        if (PrincipalHere >= 100) {
            nextButton.setEnabled(false);
        }
    }//GEN-LAST:event_nextButtonActionPerformed

    private void userInfoBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userInfoBackButtonActionPerformed
        UserHere = UserNow.back(UserHere, infoUserPostEditorPane);

        //Si no existen mas post se bloquean los botones correspondientes
        if (UserHere == UserNow.PostPTR.id) {
            userInfoBackButton.setEnabled(false);
        }
        if (UserHere < UserNow.PostsSize() + UserNow.PostPTR.id - 1) {
            userInfoNextButton.setEnabled(true);
        }

    }//GEN-LAST:event_userInfoBackButtonActionPerformed

    private void userInfoNextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userInfoNextButtonActionPerformed
        UserHere = UserNow.next(UserHere, infoUserPostEditorPane);

        //Si no existen mas post se bloquean los botones correspondientes
        if (UserHere > UserNow.PostPTR.id) {
            userInfoBackButton.setEnabled(true);
        }

        if (UserHere >= UserNow.PostsSize() + UserNow.PostPTR.id - 1) {
            userInfoNextButton.setEnabled(false);
        }
    }//GEN-LAST:event_userInfoNextButtonActionPerformed

    private void userInfoCommentsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userInfoCommentsButtonActionPerformed

    }//GEN-LAST:event_userInfoCommentsButtonActionPerformed

    private void userInfoCloseButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userInfoCloseButtonMouseClicked
        System.exit(0);
    }//GEN-LAST:event_userInfoCloseButtonMouseClicked

    private void userInfoGoBackButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userInfoGoBackButtonMouseClicked
        userInfo.setVisible(false);
        setVisible(true);
        errorTextField.setText("");
    }//GEN-LAST:event_userInfoGoBackButtonMouseClicked

    private void userInfoMinimizeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userInfoMinimizeButtonMouseClicked
        userInfo.setState(userInfo.ICONIFIED);
    }//GEN-LAST:event_userInfoMinimizeButtonMouseClicked

    private void infoPostButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoPostButtonActionPerformed

    }//GEN-LAST:event_infoPostButtonActionPerformed

    private void infoPostButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_infoPostButtonMouseClicked
        Post post = SearchPost(PrincipalHere);
        BeginUser(Raiz.SearchUser(post.userId));
        String title = post.title;
        String info = post.post;
        infoUserPostEditorPane.setText("<b>" + title + "</b><br>" + "<br>" + info);
        userInfoBackButton.setEnabled(false);
        userInfoNextButton.setEnabled(false);
    }//GEN-LAST:event_infoPostButtonMouseClicked

    private void userInfoCommentsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userInfoCommentsButtonMouseClicked
        userInfo.setVisible(false);
        comments.setVisible(true);
        Post post = SearchPost(UserHere);
        BeginComments(post);
        sw = false;
    }//GEN-LAST:event_userInfoCommentsButtonMouseClicked

    private void commentsCloseButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_commentsCloseButtonMouseClicked
        System.exit(0);
    }//GEN-LAST:event_commentsCloseButtonMouseClicked

    private void commentsGoBackButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_commentsGoBackButtonMouseClicked
        //verifica cual era el frame anterior
        if (sw) {
            comments.setVisible(false);
            setVisible(true);
        } else {
            comments.setVisible(false);
            userInfo.setVisible(true);
        }
    }//GEN-LAST:event_commentsGoBackButtonMouseClicked

    private void errorTextFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_errorTextFieldMouseClicked

    }//GEN-LAST:event_errorTextFieldMouseClicked

    private void errorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_errorTextFieldActionPerformed

    }//GEN-LAST:event_errorTextFieldActionPerformed

    private void errorTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_errorTextFieldKeyReleased

    }//GEN-LAST:event_errorTextFieldKeyReleased

    private void commentsButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_commentsButtonMouseClicked
        this.setVisible(false);
        comments.setVisible(true);
        Post post = SearchPost(PrincipalHere);
        //System.out.println(post.id);
        BeginComments(post);
        sw = true;
    }//GEN-LAST:event_commentsButtonMouseClicked

    private void graphicTreeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_graphicTreeButtonActionPerformed

    }//GEN-LAST:event_graphicTreeButtonActionPerformed

    private void searchButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchButtonMouseClicked

    }//GEN-LAST:event_searchButtonMouseClicked

    private void searchButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchButtonKeyPressed

    }//GEN-LAST:event_searchButtonKeyPressed

    private void graphicTreeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_graphicTreeButtonMouseClicked
        this.setVisible(false);
        tree.setVisible(true);
    }//GEN-LAST:event_graphicTreeButtonMouseClicked

    private void graphicTreeButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_graphicTreeButtonKeyPressed

    }//GEN-LAST:event_graphicTreeButtonKeyPressed

    private void tableroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableroMouseClicked
        Point p = MouseInfo.getPointerInfo().getLocation();
        //System.out.println(p.x + "," + p.y);
    }//GEN-LAST:event_tableroMouseClicked

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel22MouseClicked

    private void jLabel24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseClicked
        tree.setState(tree.ICONIFIED);
    }//GEN-LAST:event_jLabel24MouseClicked

    private void jLabel23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseClicked
        tree.setVisible(false);
        setVisible(true);
    }//GEN-LAST:event_jLabel23MouseClicked

    private void GraficarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_GraficarKeyPressed

    }//GEN-LAST:event_GraficarKeyPressed

    private void GraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GraficarActionPerformed
        user = 0;
        Buscar3.setEnabled(false);
        Buscar2.setEnabled(true);
        Graphics g = tablero.getGraphics();
        g.clearRect(50, 50, 1000, 425);
        g.setColor(Color.white);
        g.fillRect(50, 50, 1000, 425);
        DrawUser(g);
    }//GEN-LAST:event_GraficarActionPerformed

    private void GraficarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_GraficarMouseClicked

    }//GEN-LAST:event_GraficarMouseClicked

    private void Buscar2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Buscar2MouseClicked

    }//GEN-LAST:event_Buscar2MouseClicked

    private void Buscar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Buscar2ActionPerformed

        try {
            if (!Display.getText().isEmpty()) {
                if ((Integer.parseInt(Display.getText()) > 0) && (Integer.parseInt(Display.getText()) <= 10)) {
                    Buscar3.setEnabled(true);
                    Buscar3.setVisible(true);
                    Graphics g = tablero.getGraphics();
                    g.clearRect(50, 50, 1000, 425);
                    g.setColor(Color.white);
                    g.fillRect(50, 50, 1000, 425);
                    DrawPost(g, Integer.parseInt(Display.getText()));
                } else {
                    System.out.println("mucho texto");
                    Display.setText("");
                }

            } else {
                System.out.println("mucho texto");
                Display.setText("");
            }
        } catch (Exception e) {
            System.out.println("mucho texto");
            Display.setText("");
        }

    }//GEN-LAST:event_Buscar2ActionPerformed

    private void Buscar2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Buscar2KeyPressed

    }//GEN-LAST:event_Buscar2KeyPressed

    private void Buscar3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Buscar3MouseClicked

        try {
            if (!Display.getText().isEmpty()) {
                if ((Integer.parseInt(Display.getText()) > 0) && (Integer.parseInt(Display.getText()) <= 10)) {
                    int hijos;
                    Graphics g = tablero.getGraphics();
                    g.clearRect(50, 50, 1000, 425);
                    g.setColor(Color.white);
                    g.fillRect(50, 50, 1000, 425);
                    hijos = Raiz.SearchUser(2).PostPTR.CommentsSize();
                    DrawComment(g, Integer.parseInt(Display.getText()), hijos);
                } else {
                    System.out.println("mucho texto");
                    Display.setText("");
                }
            } else {
                System.out.println("mucho texto");
            }
        } catch (Exception e) {
            System.out.println("mucho texto");
            Display.setText("");
        }



    }//GEN-LAST:event_Buscar3MouseClicked

    private void Buscar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Buscar3ActionPerformed

    }//GEN-LAST:event_Buscar3ActionPerformed

    private void Buscar3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Buscar3KeyPressed

    }//GEN-LAST:event_Buscar3KeyPressed

    private void DisplayMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DisplayMouseClicked

        Display.selectAll();
    }//GEN-LAST:event_DisplayMouseClicked

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
    private javax.swing.JButton Buscar2;
    private javax.swing.JButton Buscar3;
    private javax.swing.JTextField Display;
    private javax.swing.JButton Graficar;
    private javax.swing.JButton backButton;
    private javax.swing.JLabel bikBlogLogo;
    private javax.swing.JLabel closeLabel;
    private javax.swing.JPanel closePanel;
    private javax.swing.JDialog comments;
    private javax.swing.JLabel commentsBikBlog;
    private javax.swing.JButton commentsButton;
    private javax.swing.JLabel commentsCloseButton;
    private javax.swing.JEditorPane commentsEditorPane;
    private javax.swing.JLabel commentsGoBackButton;
    private javax.swing.JLabel commentsLabel;
    private javax.swing.JPanel commentsPanel;
    private javax.swing.JTextField errorTextField;
    private javax.swing.JButton graphicTreeButton;
    private javax.swing.JButton infoPostButton;
    private javax.swing.JEditorPane infoUserPostEditorPane;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel minimizeLabel;
    private javax.swing.JButton nextButton;
    private javax.swing.JLabel postsLabel;
    private javax.swing.JPanel postsPanel;
    private javax.swing.JEditorPane principalEditorPane;
    private javax.swing.JButton searchButton;
    private javax.swing.JLabel searchLabel;
    private javax.swing.JSeparator searchLine;
    private javax.swing.JLabel searchLogo;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JTextField searchTextField;
    private javax.swing.JPanel tablero;
    private javax.swing.JFrame tree;
    private javax.swing.JFrame userInfo;
    private javax.swing.JButton userInfoBackButton;
    private javax.swing.JLabel userInfoBikBlog;
    private javax.swing.JLabel userInfoCloseButton;
    private javax.swing.JPanel userInfoClosePanel;
    private javax.swing.JButton userInfoCommentsButton;
    private javax.swing.JEditorPane userInfoEditorPane;
    private javax.swing.JLabel userInfoGoBackButton;
    private javax.swing.JLabel userInfoMinimizeButton;
    private javax.swing.JButton userInfoNextButton;
    private javax.swing.JPanel userInfoPanel;
    private javax.swing.JLabel userInfoPhoto;
    private javax.swing.JLabel userInfoPostsLabel;
    private javax.swing.JPanel userInfoPostsPanel;
    private javax.swing.JLabel userPhoto;
    // End of variables declaration//GEN-END:variables
}
