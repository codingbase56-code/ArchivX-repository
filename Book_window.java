import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Random;

public class Book_window {
    int pass;
    String book;
    String author;
static LibraryManagement management;
    public Book_window(String author, String book, int pass) {
        this.author = author;
        this.book = book;
        this.pass = pass;
        init(book,author,pass);
    }



    static void main(String[] args) {
        init("Java For Dummies","Barry A. Burd",1);
    }

    public static void init(String book,String author,int pass1){
       Random random=new Random();
      management=new LibraryManagement();
       int pass= random.nextInt(0,10000000);
        JFrame frame=new JFrame("ArchivX");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setIconImage(new ImageIcon("C:\\Users\\ashut\\IdeaProjects\\JavaSwingAndProjects\\src\\arch.png").getImage());
        JLabel label=new JLabel();
        label.setText("Library Management");
        label.setFont(new Font("Sans Serif",Font.BOLD,40));
        label.setForeground(Color.cyan);
        label.setBounds(830,3,600,200);
        JLabel label1=new JLabel(book);
        label1.setFont(new Font("Sans Serif",Font.BOLD,38));
        label1.setForeground(Color.cyan);
        label1.setBounds(780,160,1000,200);
        JLabel label2=new JLabel("By "+author);
        label2.setFont(new Font("Sans Serif",Font.BOLD,30));
        label2.setForeground(Color.cyan);
        label2.setBounds(800,220,600,200);
        JButton btn=new JButton();
        btn.setBounds(1300,380,140,40);
        btn.setFont(new Font("Sans Serif",Font.BOLD,15));
        btn.setText("Issue Book");
        btn.setFocusable(false);
        btn.setBackground(Color.ORANGE);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                management.issue(book,author,pass);
                JOptionPane.showMessageDialog(null,"You have successfully obtained a pass for yourself and that is :"+pass+"\n Do not forget it\n Please close the program and open it again","Library Management",JOptionPane.PLAIN_MESSAGE);
                JOptionPane.showMessageDialog(null,"Exiting....","Library management",JOptionPane.PLAIN_MESSAGE);
              System.exit(0);
            }
        });
        JButton verify=new JButton();
        verify.setBounds(830,650,350,60);
        verify.setText("Verify the pass and give book");
        verify.setFocusable(false);
        verify.setBackground(Color.orange);
        verify.setFont(new Font("Dialog",Font.BOLD,20));
verify.setVisible(false);
        NumberFormat format=NumberFormat.getIntegerInstance();
JTextField field=new JTextField();
field.setBounds(830,500,100,50);
field.setFont(new Font("DialogInput",Font.BOLD,18));
field.setVisible(false);
        JButton btn1=new JButton();
        btn1.setBounds(1300,380,155,40);
        btn1.setFont(new Font("Sans Serif",Font.BOLD,15));
        btn1.setText("Give Back Book");
        btn1.setFocusable(false);
        btn1.setBackground(Color.GREEN);
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
field.setVisible(true);
verify.setVisible(true);
btn1.setVisible(false);
            }
        });
        verify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(field.getText().toLowerCase().contains("abcdefghijklmnopqrstuvwxyz"))  {
                   JOptionPane.showMessageDialog(frame,"So but you have to enter the pass again because your pass contains the letters");
                   JOptionPane.showMessageDialog(frame,"The numeric pass only contain numbers\n For example:00123");
                }else{
                    int input=Integer.parseInt(field.getText());
                    if(input==pass1){
                        String[] words=author.split("-");
                        management.give_back(book,words[0],pass1);
                        JOptionPane.showMessageDialog(null,"The book is successfully returned","Book return",JOptionPane.PLAIN_MESSAGE);
                        frame.dispose();
                    }else{
                        JOptionPane.showMessageDialog(null,"You have entered the wrong pass pls try again later","Book return",JOptionPane.PLAIN_MESSAGE);
                    }
                }
            }
        });
        if(pass1==0){
            btn.setVisible(true);
            btn1.setVisible(false);
        }else{
            btn.setVisible(false);
            btn1.setVisible(true);
        }
        frame.add(verify);
        frame.add(label);
        frame.add(field);
        frame.add(label1);
        frame.add(label2);
        frame.add(btn);
        frame.add(btn1);
        frame.setVisible(true);
    }

}
