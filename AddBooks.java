import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBooks {
  static  String book,author;

    public AddBooks() {
        intial();
    }

    public  void intial() {
        LibraryManagement management=new LibraryManagement();
        JFrame frame=new JFrame("ArchivX");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setIconImage(new ImageIcon("C:\\Users\\ashut\\IdeaProjects\\JavaSwingAndProjects\\src\\arch.png").getImage());
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        JLabel label=new JLabel();
        label.setText("Library Management");
        label.setFont(new Font("Sans Serif",Font.BOLD,40));
        label.setForeground(Color.cyan);
        label.setBounds(830,3,600,200);
JTextField field=new JTextField();
        field.setBounds(780,170,400,30);
        JTextField field1=new JTextField();
        field1.setBounds(780,270,400,30);
        JLabel label1=new JLabel();
        label1.setText("Enter the book name:");
        label1.setBounds(650,170,400,30);
        JLabel label2=new JLabel();
        label2.setText("Enter the author name:");
        label2.setBounds(640,270,400,30);
        JButton  add=new JButton();
        add.setBounds(800,350,150,50);
        add.setText("Add the books");
        add.setFocusable(false);
        add.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
   book=field.getText();
author=field1.getText();
management.create(book,author);
frame.dispose();
            }
        });
frame.add(label);
        frame.add(label1);
frame.add(label2);
frame.add(field1);
frame.add(field);
frame.add(add);
frame.setVisible(true);
    }
    public  String get(){
        return book+" "+author;
    }
}
