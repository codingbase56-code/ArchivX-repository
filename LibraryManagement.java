import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibraryManagement {
    static File file;
    static FileWriter writer;
static     DefaultListModel<String> model;
static JList list;
static ArrayList<String> as;
    static void main(String[] args) {

        JFrame frame=new JFrame();
        as=read();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setBackground(Color.white);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setTitle("ArchivX");
        frame.setIconImage(new ImageIcon("C:\\Users\\ashut\\IdeaProjects\\JavaSwingAndProjects\\src\\arch.png").getImage());
//        System.out.println(new File("C:\\Users\\ashut\\IdeaProjects\\JavaSwingAndProjects\\src\\arch.png").exists());
        ArrayList<String> string=read();
        ArrayList<Integer> burrower_passes=read1();
ArrayList<Integer>indexes=getIndexes();
        for (int i = 0; i < as.size(); i++) {
            if (as.get(i).toLowerCase().contains("Issued")) {
                String[] words=as.get(i).split("-");

                String[] tokens=words[1].split(" ");
String[] word=words[0].split("by");
char a =word[0].charAt(0);
String[] token=word[0].split("");
word[0]="";
                for (int j = 0; j < token.length ; j++) {
                    if(j==0) token[0]=String.valueOf(a).toUpperCase();
                    word[0]=word[0] + token[j];
                }
                System.out.println(word[0]);

                as.remove(i);
                as.add("# "+word[0]+"by"+word[1]);

            }else {
                String temp= string.get(i).toLowerCase();
                string.remove(i);
                string.add(i,temp);
                System.out.println(string.get(i));
            }
        }
        model =new DefaultListModel<>();
        model.addAll(as);
        list=new JList<>(model);
        JLabel label=new JLabel();
        label.setText("Library Management");
        label.setFont(new Font("Sans Serif",Font.BOLD,40));
        label.setForeground(Color.cyan);
        label.setBounds(830,3,600,200);
        JLabel jLabel=new JLabel();
        jLabel.setBounds(50,180,650,60);
        jLabel.setFont(new Font("Monospaced",Font.BOLD,20));
        jLabel.setText("Enter the book name(do not enter the author name)");
        JButton jButton=new JButton();
        jButton.setBounds(1000,180,100,40);
        jButton.setFocusable(false);
        jButton.setBackground(Color.orange);
        jButton.setText("Search");
        jButton.setFont(new Font("Dialog",Font.BOLD,13));
        JTextField textField=new JTextField();
        textField.setBounds(730,180,250,40);
        textField.setFont(new Font("DialogInput",Font.BOLD,15));
        JScrollPane pane=new JScrollPane();
        pane.setBounds(500,250,800,180);
        pane.setViewportView(list);
        list.setFont(new Font("Sans-Serif",Font.BOLD,26));
        JButton btn=new JButton();
        btn.setBounds(1800,900,100,45);
        btn.setText("Add a book");
        btn.setBackground(new Color(107,142,35));
        btn.setFocusable(false);
        JButton jButton1=new JButton();
        jButton1.setBounds(1100,180,100,40);
        jButton1.setFocusable(false);
        jButton1.setBackground(Color.YELLOW);
        jButton1.setFont(new Font("Dialog",Font.BOLD,13));
        jButton1.setText("Restore");
        jButton1.setVisible(false);
        jButton.addActionListener(new ActionListener() {
     @Override
     public void actionPerformed(ActionEvent e) {
         String book=textField.getText().toLowerCase();
         if (book!=null) {
             int index=0;
             for (int i = 0; i <as.size() ; i++) {
                 if(as.get(i).toLowerCase().contains(book)){
                     index=i;
                 }
             }
             String detail = as.get(index);
             String[] tokens=detail.split("by");
             search(tokens[0],tokens[1]);
             JOptionPane.showMessageDialog(frame,"Your searched book with author is now displayed","Library Management",JOptionPane.INFORMATION_MESSAGE);
             jButton.setVisible(false);
             jButton1.setVisible(true);
             textField.setVisible(false);

         }

     }
 });
        Border  line_border=BorderFactory.createLineBorder(Color.BLACK);
        Border titled=BorderFactory.createTitledBorder(line_border,"Books available in Library(please ignore the ones with issue tag)", TitledBorder.LEFT,TitledBorder.TOP);
        pane.setBorder(titled);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restore();
                JOptionPane.showMessageDialog(frame,"Your list is restored.","Library Management",JOptionPane.INFORMATION_MESSAGE);
                jButton1.setVisible(false);
                jButton.setVisible(true);
                textField.setVisible(true);
            }
        });
        btn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            AddBooks books=new AddBooks();
        }
    });
        list.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(e.getClickCount()==1){
                indexes.clear();
                indexes.addAll(getIndexes());
                int index=list.locationToIndex(e.getPoint());
                String a =list.getSelectedValue().toString();
                System.out.println("Selected Index"+index);
                System.out.println("Stored"+indexes);
                if (a.contains("Issued")) {
                    String[] tokens=a.split("by");
String book=tokens[0];
String author=tokens[1];
int final_index=0;
                    for (int i = 0; i <indexes.size() ; i++) {
                        if(indexes.get(i)==index) final_index=i;
                    }
                    int pass=burrower_passes.get(final_index);
                    Book_window win=new Book_window(author,book,pass);
                    System.out.println(list.getSelectedValue().toString());
                }else {
                    String[] words=a.split("by");
                    String book =words[0];
                    String author=words[1];
                    Book_window win =new Book_window(author,book,0);
                }


            }
        }
    });
        String total="Total books: "+as.size();
        String issued="Issued books: ";
        String available="Available books: ";
        int no_of_issued_books=0;
        int no_of_available_books=0;
        for(int i=0;i<as.size();i++){
if(as.get(i).contains("Issued")){
    no_of_issued_books++;
}else {
    no_of_available_books++;
}
        }
        available=available+no_of_available_books;
        issued=issued+no_of_issued_books;
        String msg=total+"\n"+issued+"\n"+available;

        frame.add(label);
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

                JLabel label= (JLabel) super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
                if(as.get(index).contains("Issued")){

                    label.setForeground(Color.red);
                }
                return label;

            }
        });
        frame.add(btn);
        frame.add(pane);
        frame.add(jButton);
        frame.add(jButton1);
        frame.add(textField);
        frame.add(jLabel);
        frame.setVisible(true);
        JOptionPane.showMessageDialog(frame,msg,"Books",JOptionPane.PLAIN_MESSAGE);

    }
    public  void create(String book, String author){
        file=new File("Books.txt");
        String book_details=book+" by "+author;
        ArrayList<String> arrayList=read();
        String prev_books_details="";

        try {
            if(file.exists()){
                System.out.println("Before add:"+arrayList);
                writer=new FileWriter("Books.txt",true);

                writer.write("\n");
                writer.write(book_details);
                arrayList.add(book_details);
                System.out.println("after add"+arrayList);
            }else {
                file.createNewFile();
                writer.write(book_details);
arrayList.add(book_details);
            }

            writer.close();
            as.clear();
            as.addAll(read());
            model.clear();
            model.addAll(arrayList);
list.setModel(model);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static ArrayList<String> read(){
        ArrayList<String> as=new ArrayList<>();
        file=new File("Books.txt");
        try {
            if (file.exists()) {
                Scanner scanner =new Scanner(file);
                while(scanner.hasNextLine()){
                    as.add(scanner.nextLine());
                }
                scanner.close();
            }else{
                System.out.println("Sorry File is not found");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return as;
    }
    public static ArrayList<Integer>read1(){
        ArrayList<Integer> as=new ArrayList<>();
       File file1=new File("pass.txt");
        try {
            if (file1.exists()) {
                Scanner scanner =new Scanner(file1);
                while(scanner.hasNextLine()){
                    String[] a =scanner.nextLine().split("-");
                    System.out.println(a[0]);
          as.add(Integer.parseInt(a[0]));
                }
                scanner.close();
            }else{
                System.out.println("Sorry File is not found");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return as;
    }
    public static ArrayList<Integer> getIndexes(){
        ArrayList<Integer> as=new ArrayList<>();
        File file1=new File("pass.txt");
        try {

            if (file1.exists()) {
                Scanner scanner =new Scanner(file1);
                while(scanner.hasNextLine()){
                    String[] a =scanner.nextLine().split("-");
                    as.add(Integer.parseInt(a[1]));
                }
                scanner.close();
            }else{
                System.out.println("Sorry File is not found");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return as;
    }
    public void issue(String book,String author,int pass){
        FileWriter writer1=null;
        File file1=new File("pass.txt");
        String book_details=book+" by "+author+"- Issued";
        String book_details_of_file=book+" by "+author+" - Issued";
        ArrayList<String> books=read();
        int index=0;
        String details="";
        for (int i = 0; i <books.size() ; i++) {
            if(books.get(i).contains(book)){
                System.out.println("Issue"+book);
                System.out.println("Location"+i);
                System.out.println("Matched Entry"+books.get(i));
                index=i;
                books.remove(index);
break;
            }
        }
        for (int i = 0; i < books.size() ; i++) {
            if(i==0){
                details= books.get(i);
            }else {
                details=details+"\n"+books.get(i);
            }
        }
        try{
            writer=new FileWriter("Books.txt");
            writer.write(details+"\n"+book_details_of_file);
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            if (file1.exists()) {
                ArrayList<Integer> a = read1();
                writer1 = new FileWriter("pass.txt",true);
                String str = "";
                books.add(index, book_details);
                for (int i = 0; i < a.size(); i++) {
                    if (i == 0) {
                        str = str + a.get(i);
                    } else {
                        str = str + "\n" + a.get(i);
                    }
                }
                String a1=pass + "-" + index;
              if(file1.length()>0){
                  writer1.write("\n");
                  writer1.write(pass+"-"+index);
              }else {
                  writer1.write(a1);
              }
            } else {
                file1.createNewFile();
            writer1=new FileWriter("pass.txt");
                writer1.write(pass + "-" + index);
            }
            books.clear();
            books.addAll(read());
            writer1.close();
            model.clear();
            model.addAll(books);
            list.setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    String text = value.toString();
                    System.out.println("Selected index = " + list.getSelectedIndex());
                    System.out.println("Selected value = " + list.getSelectedValue());
                    if (text.contains("Issued")) {
                        label.setForeground(Color.red);
                    }
                    return label;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String combine(ArrayList<String>a){
        String s="";
        for (int i = 0; i < a.size(); i++) {
            if(i==0){s=a.get(0);}
            else{
                s=s+"\n"+a.get(i);
            }
        }
        return s;
    }
    public int getIndex(ArrayList<String> string,String s){
        int index=0;
        for (int i = 0; i <string.size() ; i++) {
            if(string.get(i).equals(s)||string.get(i).contains(s)) return i;
        }
        return -1;
    }
    public int getIndex(ArrayList<Integer> string,int s){
        int index=0;
        for (int i = 0; i <string.size() ; i++) {
            if(string.get(i)==s) index=i;
        }
        return index;
    }
    public void give_back(String book,String author,int pass){
        File file1=new File("pass.txt");
        ArrayList<Integer> passes=read1();
        ArrayList<Integer> indexes=getIndexes();
        ArrayList<String> books=read();
        FileWriter writer1;
        try{
            if(file1.exists()){
                ArrayList<String> as=new ArrayList<>();
                String book_details=book+" by "+author;
                int i=getIndex(books,book);
              passes.remove(Integer.valueOf(pass));
                System.out.println("books before"+books);
                System.out.println("book = [" + book + "]");
                System.out.println("author = [" + author + "]");
                System.out.println("index = " + i);
                books.remove(i);
                books.add(i,book_details);
                String content=combine(books);
                int index=0;
                for (int j = 0; j <passes.size() ; j++) {
                    index=getIndex(passes,passes.get(j));
                    as.add(String.valueOf(passes.get(j)+"-"+indexes.get(index)));
                }
                System.out.println("content new:"+content);
                String pass1=combine(as);
                writer1=new FileWriter("pass.txt");
                writer=new FileWriter("books.txt");
                writer.write(content);
                writer1.write(pass1);
                writer1.close();
                writer.close();
                passes.clear();
                passes.addAll(read1());
                books.clear();
                books.addAll(read());
                model.clear();
                model.addAll(books);
                list.setModel(model);
                list.setCellRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                        JLabel label=(JLabel) super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
                        if(value.toString().contains("Issued")){
                            label.setForeground(Color.red);
                        }
                        return label;
                    }
                });
            }
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }
    public static void search(String book,String author){
        String book_details=book+" by "+author;
        ArrayList<String> books=new ArrayList<>();
        books.add(book_details);
        model.clear();
        model.addAll(books);
        list.setModel(model);
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label=(JLabel) super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
                if(value.toString().contains("Issued")){
                    label.setForeground(Color.red);
                }
                return label;
            }
        });
    }
    public static void restore(){
        ArrayList<String> books=read();
        model.clear();
        model.addAll(books);
        list.setModel(model);
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label=(JLabel) super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
                if(value.toString().contains("Issued")){
                    label.setForeground(Color.red);
                }
                return label;
            }
        });
    }
}
