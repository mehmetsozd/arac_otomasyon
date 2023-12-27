import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class aracotomasyon extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JLabel lblMAdVal, lblMSoyadVal, lblMTelNoVal, lblMAdresVal, lblAMarkaVal, lblAModelVal, lblAPlakaVal,
            lblGirisTarihiVal, lblCikisTarihiVal, lblOdenenUcretVal;

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/aracotomasyon?enabledTLSProtocols=TLSv1.2";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);

            if (connection != null && !connection.isClosed()) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            aracotomasyon frame = new aracotomasyon();
                            frame.setVisible(true);
                            frame.setTitle("Araç Otomasyonu");
                            frame.veriTabanindanBilgiCekmeFonksiyonu(connection);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                System.out.println("Veritabanına bağlandı.");
            } else {
                System.out.println("Veritabanına bağlanılamadı.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    protected void veriTabanindanBilgiCekmeFonksiyonu(Connection connection) {
        try {
            String query = "SELECT * FROM data";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            table.setModel(model);

            String[] columnNames = { "ID", "MAd", "MSoyad", "MTelNo", "MAdres", "AMarka", "AModel", "APlaka",
                    "GirisTarihi",
                    "CikisTarihi", "OdenenUcret" };
            model.setColumnIdentifiers(columnNames);

            // Veritabanından gelen verileri tabloya ekle
            while (resultSet.next()) {
                Object[] rowData = {
                        resultSet.getInt("ID"),
                        resultSet.getString("MAd"),
                        resultSet.getString("MSoyad"),
                        resultSet.getString("MTelNo"),
                        resultSet.getString("MAdres"),
                        resultSet.getString("AMarka"),
                        resultSet.getString("AModel"),
                        resultSet.getString("APlaka"),
                        resultSet.getString("GirisTarihi"),
                        resultSet.getString("CikisTarihi"),
                        resultSet.getString("OdenenUcret")
                };
                model.addRow(rowData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void temizleButonFonskiyon() {
        lblMAdVal.setText("");
        lblMSoyadVal.setText("");
        lblMTelNoVal.setText("");
        lblMAdresVal.setText("");
        lblAMarkaVal.setText("");
        lblAModelVal.setText("");
        lblAPlakaVal.setText("");
        lblGirisTarihiVal.setText("");
        lblCikisTarihiVal.setText("");
        lblOdenenUcretVal.setText("");
    }

    protected void ekleButonFonksiyon() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(11, 2));

        JTextField lblMAd = new JTextField();
        JTextField lblMSoyad = new JTextField();
        JTextField lblMTelNo = new JTextField();
        JTextField lblMAdres = new JTextField();
        JTextField lblAMarka = new JTextField();
        JTextField lblAModel = new JTextField();
        JTextField lblAPlaka = new JTextField();
        JTextField lblGirisTarihi = new JTextField();
        JTextField lblCikisTarihi = new JTextField();
        JTextField lblOdenenUcret = new JTextField();

        panel.add(new JLabel("Ad:"));
        panel.add(lblMAd);
        panel.add(new JLabel("Soyad:"));
        panel.add(lblMSoyad);
        panel.add(new JLabel("Tel No:"));
        panel.add(lblMTelNo);
        panel.add(new JLabel("Adres:"));
        panel.add(lblMAdres);
        panel.add(new JLabel("Marka:"));
        panel.add(lblAMarka);
        panel.add(new JLabel("Model:"));
        panel.add(lblAModel);
        panel.add(new JLabel("Plaka:"));
        panel.add(lblAPlaka);
        panel.add(new JLabel("Giriş Tarihi:"));
        panel.add(lblGirisTarihi);
        panel.add(new JLabel("Çıkış Tarihi:"));
        panel.add(lblCikisTarihi);
        panel.add(new JLabel("Ödenen Ücret:"));
        panel.add(lblOdenenUcret);

        int result = JOptionPane.showConfirmDialog(null, panel, "Müşteri Bilgileri Ekleyin",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String query = "INSERT INTO data (MAd, MSoyad, MTelNo, MAdres, AMarka, AModel, APlaka, GirisTarihi, CikisTarihi, OdenenUcret) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                Connection connection = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/aracotomasyon?enabledTLSProtocols=TLSv1.2", "root", "");

                PreparedStatement statement = connection.prepareStatement(query);

                statement.setString(1, lblMAd.getText());
                statement.setString(2, lblMSoyad.getText());
                statement.setString(3, lblMTelNo.getText());
                statement.setString(4, lblMAdres.getText());
                statement.setString(5, lblAMarka.getText());
                statement.setString(6, lblAModel.getText());
                statement.setString(7, lblAPlaka.getText());
                statement.setString(8, lblGirisTarihi.getText());
                statement.setString(9, lblCikisTarihi.getText());
                statement.setString(10, lblOdenenUcret.getText());

                int rowCount = statement.executeUpdate();

                if (rowCount > 0) {
                    System.out.println("Müşteri bilgileri başarıyla eklendi.");
                    JOptionPane.showMessageDialog(null, "Müşteri bilgileri başarıyla eklendi.");
                    veriTabanindanBilgiCekmeFonksiyonu(connection);
                } else {
                    System.out.println("Müşteri bilgileri eklenirken bir hata oluştu.");
                    JOptionPane.showMessageDialog(null, "Müşteri bilgileri eklenirken bir hata oluştu.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public aracotomasyon() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 936, 668);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(192, 192, 192));
        panel.setBounds(10, 11, 271, 201);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Müşteri Bilgileri");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblNewLabel.setBackground(Color.WHITE);
        lblNewLabel.setBounds(10, 10, 251, 29);
        panel.add(lblNewLabel);

        JLabel lblMAd = new JLabel("Ad:");
        lblMAd.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMAd.setBounds(10, 50, 200, 20);
        panel.add(lblMAd);

        JLabel lblMSoyad = new JLabel("Soyad:");
        lblMSoyad.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMSoyad.setBounds(10, 90, 200, 20);
        panel.add(lblMSoyad);

        JLabel lblMTelNo = new JLabel("Tel No:");
        lblMTelNo.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMTelNo.setBounds(10, 130, 200, 20);
        panel.add(lblMTelNo);

        JLabel lblMAdres = new JLabel("Adres:");
        lblMAdres.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblMAdres.setBounds(10, 170, 200, 20);
        panel.add(lblMAdres);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(192, 192, 192));
        panel_1.setBounds(291, 11, 317, 201);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        JLabel lblAraBilgileri = new JLabel("Araç Bilgileri");
        lblAraBilgileri.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblAraBilgileri.setBackground(Color.WHITE);
        lblAraBilgileri.setBounds(10, 10, 251, 29);
        panel_1.add(lblAraBilgileri);

        JLabel lblAMarka = new JLabel("Marka:");
        lblAMarka.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblAMarka.setBounds(10, 50, 200, 20);
        panel_1.add(lblAMarka);

        JLabel lblAModel = new JLabel("Model:");
        lblAModel.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblAModel.setBounds(10, 90, 200, 20);
        panel_1.add(lblAModel);

        JLabel lblAPlaka = new JLabel("Plaka:");
        lblAPlaka.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblAPlaka.setBounds(10, 130, 200, 20);
        panel_1.add(lblAPlaka);

        JPanel panel_2 = new JPanel();
        panel_2.setBackground(new Color(192, 192, 192));
        panel_2.setBounds(618, 11, 292, 201);
        contentPane.add(panel_2);
        panel_2.setLayout(null);

        JLabel lblBilgiler = new JLabel("Bilgiler");
        lblBilgiler.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblBilgiler.setBackground(Color.WHITE);
        lblBilgiler.setBounds(10, 11, 251, 29);
        panel_2.add(lblBilgiler);

        JLabel lblGirisTarihi = new JLabel("Giriş Tarihi:");
        lblGirisTarihi.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblGirisTarihi.setBounds(10, 50, 200, 20);
        panel_2.add(lblGirisTarihi);

        JLabel lblCikisTarihi = new JLabel("Çıkış Tarihi:");
        lblCikisTarihi.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblCikisTarihi.setBounds(10, 90, 200, 20);
        panel_2.add(lblCikisTarihi);

        JLabel lblOdenenUcret = new JLabel("Ödenen Ücret:");
        lblOdenenUcret.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblOdenenUcret.setBounds(10, 130, 200, 20);
        panel_2.add(lblOdenenUcret);

        JButton btnTemizle = new JButton("Temizle");
        btnTemizle.setBounds(10, 170, 89, 23);
        panel_2.add(btnTemizle);

        btnTemizle.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temizleButonFonskiyon();
            }
        });

        JButton btnEkle = new JButton("Ekle");
        btnEkle.setBounds(109, 170, 89, 23);
        panel_2.add(btnEkle);

        btnEkle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ekleButonFonksiyon();
            }
        });

        lblMAdVal = new JLabel("");
        lblMAdVal.setBounds(80, 50, 200, 20);
        panel.add(lblMAdVal);

        lblMSoyadVal = new JLabel("");
        lblMSoyadVal.setBounds(80, 90, 200, 20);
        panel.add(lblMSoyadVal);

        lblMTelNoVal = new JLabel("");
        lblMTelNoVal.setBounds(80, 130, 200, 20);
        panel.add(lblMTelNoVal);

        lblMAdresVal = new JLabel("");
        lblMAdresVal.setBounds(80, 170, 200, 20);
        panel.add(lblMAdresVal);

        lblAMarkaVal = new JLabel("");
        lblAMarkaVal.setBounds(80, 50, 200, 20);
        panel_1.add(lblAMarkaVal);

        lblAModelVal = new JLabel("");
        lblAModelVal.setBounds(80, 90, 200, 20);
        panel_1.add(lblAModelVal);

        lblAPlakaVal = new JLabel("");
        lblAPlakaVal.setBounds(80, 130, 200, 20);
        panel_1.add(lblAPlakaVal);

        lblGirisTarihiVal = new JLabel("");
        lblGirisTarihiVal.setBounds(120, 50, 200, 20);
        panel_2.add(lblGirisTarihiVal);

        lblCikisTarihiVal = new JLabel("");
        lblCikisTarihiVal.setBounds(120, 90, 200, 20);
        panel_2.add(lblCikisTarihiVal);

        lblOdenenUcretVal = new JLabel("");
        lblOdenenUcretVal.setBounds(120, 130, 200, 20);
        panel_2.add(lblOdenenUcretVal);

        Object[][] data = {
                { "xxx", "xxx", "xxx", "xxx", "xxx", "xxx", "xxx", "xxx", "xxx", "xxx", "xxx" },
        };

        String[] columnNames = { "ID", "MAd", "MSoyad", "MTelNo", "MAdres", "AMarka", "AModel", "APlaka", "GirisTarihi",
                "CikisTarihi", "OdenenUcret" };

        table = new JTable(data, columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 260, 900, 363);

        contentPane.add(scrollPane);

        JLabel lblIslemler = new JLabel("Geçmiş İşlemler");
        lblIslemler.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblIslemler.setBackground(Color.WHITE);
        lblIslemler.setBounds(10, 220, 900, 29);
        contentPane.add(lblIslemler);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    lblMAdVal.setText(table.getValueAt(selectedRow, 1).toString());
                    lblMSoyadVal.setText(table.getValueAt(selectedRow, 2).toString());
                    lblMTelNoVal.setText(table.getValueAt(selectedRow, 3).toString());
                    lblMAdresVal.setText(table.getValueAt(selectedRow, 4).toString());
                    lblAMarkaVal.setText(table.getValueAt(selectedRow, 5).toString());
                    lblAModelVal.setText(table.getValueAt(selectedRow, 6).toString());
                    lblAPlakaVal.setText(table.getValueAt(selectedRow, 7).toString());
                    lblGirisTarihiVal.setText(table.getValueAt(selectedRow, 8).toString());
                    lblCikisTarihiVal.setText(table.getValueAt(selectedRow, 9).toString());
                    lblOdenenUcretVal.setText(table.getValueAt(selectedRow, 10).toString());

                }
            }
        });
    }
}
