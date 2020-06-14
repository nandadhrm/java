/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainFrame;
import javax.swing.GroupLayout.Alignment;


import java.sql.ResultSet;

import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Statement;

import connect.MysqlConnection;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 *
 * @author D2J
 */
public class formTransaksi extends javax.swing.JFrame {

    /**
     * Creates new form formTransaksi
     */
    public formTransaksi() {
    	getContentPane().setPreferredSize(new Dimension(550, 450));
    	getContentPane().setSize(new Dimension(600, 500));
    	setSize(new Dimension(601, 539));
    	setTitle("Program Sewa");
        initComponents();
        mati();
        updateCmb1();
        updateCmb2();
        datatable();
        getContentPane().setLayout(null);
        getContentPane().add(btn_simpan);
        getContentPane().add(btn_batal);
        getContentPane().add(btn_keluar);
        getContentPane().add(jLabel1);
        getContentPane().add(jLabel3);
        getContentPane().add(jLabel2);
        getContentPane().add(spn_sewa);
        getContentPane().add(txt_kodesewa);
        getContentPane().add(jLabel7);
        getContentPane().add(jLabel6);
        getContentPane().add(txt_nama);
        getContentPane().add(cmb_customer);
        getContentPane().add(jLabel4);
        getContentPane().add(spn_kembali);
        getContentPane().add(cmb_kodefilm);
        getContentPane().add(jLabel5);
        getContentPane().add(jScrollPane1);
        getContentPane().add(jLabel8);
        getContentPane().add(lblJudul);
        getContentPane().add(txt_judul);
        getContentPane().add(lblHarga);
        getContentPane().add(txt_harga);
        getContentPane().add(btnTambah);
        getContentPane().add(btnHapus);
        getContentPane().add(txt_total);
        
        txt_totalhg = new JTextField();
        txt_totalhg.setBounds(453, 205, 72, 29);
        getContentPane().add(txt_totalhg);
        txt_totalhg.setColumns(10);
        
        lblBayar = new JLabel("Jumlah Bayar");
        lblBayar.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblBayar.setBounds(344, 252, 85, 14);
        getContentPane().add(lblBayar);
        
        txt_jmlbayar = new JTextField();
        txt_jmlbayar.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent e) {
        		int total = Integer.parseInt(txt_totalhg.getText());
        		int jml = Integer.parseInt(txt_jmlbayar.getText());
        		int kembali = jml - total;
        		
        		String kmb = Integer.toString(kembali);
        		
        		txt_kembali.setText(kmb);
        		
        		
        		        		 	
        		}
        });
        txt_jmlbayar.setBounds(453, 245, 72, 31);
        getContentPane().add(txt_jmlbayar);
        txt_jmlbayar.setColumns(10);
        
        lblKembali = new JLabel("Kembali");
        lblKembali.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblKembali.setBounds(344, 294, 81, 14);
        getContentPane().add(lblKembali);
        
        txt_kembali = new JTextField();
        txt_kembali.setBounds(453, 287, 72, 31);
        getContentPane().add(txt_kembali);
        txt_kembali.setColumns(10);
        
        JButton btn_edit = new JButton("Edit");
        btn_edit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String ks = txt_kodesewa.getText();
        		String tgls = new SimpleDateFormat("yyyy-MM-dd").format(spn_sewa.getValue());
        		String tglk = new SimpleDateFormat("yyyy-MM-dd").format(spn_kembali.getValue());
        		String kc = (String) cmb_customer.getSelectedItem();
        		int total = Integer.parseInt(txt_totalhg.getText());
        		
        		try {
        			Statement statement = (Statement)MysqlConnection.GetConnection().createStatement();
        			statement.executeUpdate("UPDATE pbo_sewa SET id = '"+kc+"', tgl_sewa = '"+tgls+"', tgl_kembali = '"+tglk+"', total_harga = '"+total+"' WHERE kode_sewa = '"+ks+"' ");
        			JOptionPane.showMessageDialog(null, "Data Berhasil Di Edit");
        		}catch(Exception t) {
        			JOptionPane.showMessageDialog(null, "Data Gagal Di Edit");
        		}
        	datatable();	
        	}
        });
        btn_edit.setBounds(97, 358, 74, 31);
        getContentPane().add(btn_edit);
        
        JButton btn_baru = new JButton("Baru");
        btn_baru.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		hidup();
        	}
        });
        btn_baru.setBounds(88, 400, 162, 23);
        getContentPane().add(btn_baru);
        
        
    }
    

    private void datatable() {
    	DefaultTableModel tbl = new DefaultTableModel();
    	tbl.addColumn("Judul Film");
    	tbl.addColumn("Harga Sewa");
    	tbl_transaksi.setModel(tbl);
    	String kode = txt_kodesewa.getText();
    	 try {
    		Statement statement = (Statement)MysqlConnection.GetConnection().createStatement();
    		ResultSet res = statement.executeQuery("select judul, harga_sewa from pbo_kelfilm join pbo_film on pbo_kelfilm.jenis = pbo_film.jenis join pbo_detailsewa on pbo_film.kode_film = pbo_detailsewa.kode_film where kode_Sewa = '"+kode+"' ");
        	int total = 0;
    		while(res.next()) {
    			tbl.addRow(new Object[]{
    				res.getString(1),
    				res.getString(2)
    			});  
			total += res.getInt(2);
    		tbl_transaksi.setModel(tbl);
    		}
    		txt_totalhg.setText(String.valueOf(total));
    	 }catch(Exception e) {
    		 JOptionPane.showMessageDialog(rootPane, "Salah Di Tabel Data");
    	 }
    }
    
    private void mati() {
    	try {
    	txt_nama.setEnabled(false);
    	txt_judul.setEnabled(false);
    	txt_kodesewa.setEnabled(false);
    	txt_harga.setEnabled(false);
    	txt_jmlbayar.setEnabled(false);
    	txt_total.setEnabled(false);
    	txt_kembali.setEnabled(false);
    	txt_totalhg.setEnabled(false);
    	spn_sewa.setEnabled(false);
    	spn_kembali.setEnabled(false);
    	}catch (Exception t) {
    		JOptionPane.showMessageDialog(null, "status gagal");
    	}
    }
    
    private void hidup() {
    	try {
    	txt_nama.setEnabled(true);
    	txt_judul.setEnabled(true);
    	txt_kodesewa.setEnabled(true);
    	txt_harga.setEnabled(true);
    	txt_jmlbayar.setEnabled(true);
    	txt_total.setEnabled(true);
    	txt_kembali.setEnabled(true);
    	txt_totalhg.setEnabled(true);
    	spn_sewa.setEnabled(true);
    	spn_kembali.setEnabled(true);
    	}catch(Exception e) {
    		JOptionPane.showMessageDialog(null, "SALAH");
    	}
    }
    
    private void bersih() {
    	txt_nama.setText("");
    	txt_judul.setText("");
    	txt_kodesewa.setText("");
    	txt_harga.setText("");
    	txt_jmlbayar.setText("");
    	txt_total.setText("");
    	txt_kembali.setText("");
    	txt_totalhg.setText("");
    }	

    
    private void updateCmb1() {
    	try {
    		Statement pst = (Statement)MysqlConnection.GetConnection().createStatement();
    		ResultSet rs = pst.executeQuery("select * from pbo_film order by kode_film");
    		while(rs.next()) {
    			cmb_kodefilm.addItem(rs.getString("kode_film"));
    		}
    	}catch(Exception e){
    	}
    }
    
    private void updateCmb2() {
    	try {
    		Statement pst = (Statement)MysqlConnection.GetConnection().createStatement();
    		ResultSet rs = pst.executeQuery("select * from pbo_customer order by id");
    		while(rs.next()) {
    			cmb_customer.addItem(rs.getString("id"));
    		}
    	}catch(Exception e){
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

        jLabel1 = new javax.swing.JLabel();
        jLabel1.setBounds(203, 11, 162, 22);
        jLabel2 = new javax.swing.JLabel();
        jLabel2.setBounds(13, 46, 59, 15);
        jLabel3 = new javax.swing.JLabel();
        jLabel3.setBounds(13, 77, 75, 15);
        jLabel4 = new javax.swing.JLabel();
        jLabel4.setBounds(13, 113, 89, 15);
        jLabel5 = new javax.swing.JLabel();
        jLabel5.setBounds(13, 139, 59, 15);
        txt_kodesewa = new javax.swing.JTextField();
        txt_kodesewa.addFocusListener(new FocusAdapter() {
        	@Override
        	public void focusLost(FocusEvent e) {
        		try {
        			Statement statement = (Statement)MysqlConnection.GetConnection().createStatement();
        			ResultSet sql = statement.executeQuery("SELECT pbo_sewa.tgl_sewa, pbo_sewa.tgl_kembali, pbo_sewa.id, pbo_film.kode_film FROM pbo_sewa,pbo_film WHERE kode_sewa = '"+txt_kodesewa.getText()+"' ");
        			while(sql.next()) {
        				Object[] data = new Object[4];
        				data[0] = sql.getString(1);
        				data[1] = sql.getString(2);
        				data[2] = sql.getString(3);
        				data[3] = sql.getString(4);
        				spn_sewa.setValue((String) data[0]);
        				spn_kembali.setValue((String) data[1]);
        				cmb_customer.setSelectedItem((String) data[2]);
        				cmb_kodefilm.setSelectedItem((String) data[3]);
        			}
        			JOptionPane.showMessageDialog(null, "Nothing To Show");
        		}catch(Exception t) {
        			JOptionPane.showMessageDialog(null, "Data Fetched");
        		}
        		datatable();
        	}
        });
        txt_kodesewa.setBounds(117, 39, 96, 31);
        cmb_kodefilm = new javax.swing.JComboBox<>();
        cmb_kodefilm.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		try {
        			Statement statement = (Statement)MysqlConnection.GetConnection().createStatement();
        			ResultSet sql = statement.executeQuery("select pbo_film.kode_film,pbo_film.judul,pbo_kelfilm.harga_sewa from pbo_film,pbo_kelfilm Where pbo_film.jenis = pbo_kelfilm.jenis and pbo_film.kode_film = '"+cmb_kodefilm.getSelectedItem()+"' ");
        			
        			while(sql.next()) {
        				Object[] data = new Object[3];
        				data[0] = sql.getString(1);
        				data[1] = sql.getString(2);
        				data[2] = sql.getString(3);
        				txt_judul.setText((String) data[1]);
        				txt_harga.setText((String) data[2]);
        			}
        			sql.close(); statement.close();
        		}catch(Exception e) {
        			System.out.println(e.getMessage());
        		}
        		
        	}
        });
        cmb_kodefilm.setBounds(13, 164, 59, 32);
        txt_judul = new javax.swing.JTextField();
        txt_judul.setBounds(80, 165, 149, 31);
        jLabel6 = new javax.swing.JLabel();
        jLabel6.setBounds(291, 47, 74, 14);
        jLabel7 = new javax.swing.JLabel();
        jLabel7.setBounds(291, 87, 115, 14);
        txt_nama = new javax.swing.JTextField();
        txt_nama.setBounds(440, 79, 85, 31);
        cmb_customer = new javax.swing.JComboBox<>();
        cmb_customer.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
        		try {
        			Statement statement = (Statement)MysqlConnection.GetConnection().createStatement();
        			ResultSet sql = statement.executeQuery("select pbo_customer.id,pbo_customer.nama from pbo_customer where pbo_customer.id = '"+cmb_customer.getSelectedItem()+"' ");
        			
        			while(sql.next()) {
        				Object[] data = new Object[2];
        				data[0] = sql.getString(1);
        				data[1] = sql.getString(2);
        				txt_nama.setText((String) data[1]);
        			}
        			sql.close(); statement.close();
        		}catch(Exception e) {
        			System.out.println(e.getMessage());
        		}
        	}
        });
        cmb_customer.setBounds(440, 41, 85, 26);
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setBounds(13, 207, 321, 140);
        tbl_transaksi = new javax.swing.JTable();
        tbl_transaksi.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		int row = tbl_transaksi.getSelectedRow();
        		txt_judul.setText((String) tbl_transaksi.getValueAt(row, 0));
        	}
        });
        jLabel8 = new javax.swing.JLabel();
        jLabel8.setBounds(344, 211, 62, 15);
        txt_total = new javax.swing.JTextField();
        txt_total.setBounds(476, 538, 99, 20);
        btn_simpan = new javax.swing.JButton();
        btn_simpan.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String ks = txt_kodesewa.getText();
        		String tgls = new SimpleDateFormat("yyyy-MM-dd").format(spn_sewa.getValue());
        		String tglk = new SimpleDateFormat("yyyy-MM-dd").format(spn_kembali.getValue());
        		String kc = (String) cmb_customer.getSelectedItem();
        		int total = Integer.parseInt(txt_totalhg.getText());
        		
        		try {
        			Statement statement = (Statement)MysqlConnection.GetConnection().createStatement();
        			statement.executeUpdate("INSERT INTO pbo_sewa VALUES('"+ks+"', '"+kc+"', '"+tgls+"', '"+tglk+"','"+total+"')");
        			JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
        		}catch(Exception t) {
        			JOptionPane.showMessageDialog(null, "Data Gagal Disimpan");
        		}
        		
        	}
        });
        btn_simpan.setBounds(13, 358, 74, 31);
        btn_batal = new javax.swing.JButton();
        btn_batal.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		bersih();
        	}
        });
        btn_batal.setBounds(176, 358, 74, 31);
        btn_keluar = new javax.swing.JButton();
        btn_keluar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose();
        	}
        });
        btn_keluar.setBounds(260, 358, 74, 31);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Transaksi Sewa DVD");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Kode Sewa");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Tanggal Sewa");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Tanggal Kembali");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Kode Film");

      

        jLabel6.setText("Penyewa");

        jLabel7.setText("Nama Penyewa");

        tbl_transaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbl_transaksi);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Total Harga");

        btn_simpan.setText("Simpan");

        btn_batal.setText("Batal");

        btn_keluar.setText("Keluar");
        
        spn_sewa = new JSpinner();
        spn_sewa.setBounds(117, 70, 111, 31);
        spn_sewa.setModel(new SpinnerDateModel(new Date(1561654800000L), null, null, Calendar.DAY_OF_MONTH));
        spn_sewa.setEditor(new JSpinner.DateEditor(spn_sewa, "yyyy-MM-dd"));
        
        spn_kembali = new JSpinner();
        spn_kembali.setBounds(117, 106, 114, 31);
        spn_kembali.setModel(new SpinnerDateModel(new Date(1561654800000L), null, null, Calendar.DAY_OF_MONTH));
        spn_kembali.setEditor(new JSpinner.DateEditor(spn_kembali, "yyyy-MM-dd"));
        
        btnHapus = new JButton("Hapus");
        btnHapus.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String kode = txt_kodesewa.getText();
        		String jdl = txt_judul.getText();
        		try {
        			Statement statement = (Statement)MysqlConnection.GetConnection().createStatement();
        			statement.executeUpdate("DELETE FROM pbo_detailsewa where kode_sewa = '"+kode+"' AND kode_film IN (SELECT kode_film FROM pbo_film WHERE judul = '"+jdl+"') ");
        			JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
        			datatable();
        		}catch(Exception t) {
        			JOptionPane.showMessageDialog(null, "Data Gagal Dihapus");
        		}
        		datatable();
        	}
        });
        btnHapus.setBounds(453, 160, 72, 31);
        
        lblJudul = new JLabel("Judul");
        lblJudul.setBounds(82, 140, 51, 14);
        
        txt_harga = new JTextField();
        txt_harga.setBounds(239, 165, 82, 31);
        txt_harga.setColumns(10);
        
        lblHarga = new JLabel("Harga");
        lblHarga.setBounds(239, 140, 54, 14);
        
        btnTambah = new JButton("Tambah");
        btnTambah.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String ks = txt_kodesewa.getText();
        		String kf =  (String) cmb_kodefilm.getSelectedItem();
        		
        		try {
        			Statement statement = (Statement) MysqlConnection.GetConnection().createStatement();
        			statement.executeUpdate("insert into pbo_detailsewa VALUES('"+ks+"', '"+kf+"')");
        			
        			JOptionPane.showMessageDialog(null, "Data Berhasil Ditambah");
        		}catch(Exception t) {
        			JOptionPane.showMessageDialog(null, "Data Gagal Ditambah");
        		}
        		datatable();	
        	}});	
        btnTambah.setLocation(344, 164);
        btnTambah.setSize(new Dimension(96, 32));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formTransaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formTransaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JComboBox<String> cmb_customer;
    private javax.swing.JComboBox<String> cmb_kodefilm;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl_transaksi;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_judul;
    private javax.swing.JTextField txt_kodesewa;
    private javax.swing.JTextField txt_total;
    private JTextField txt_harga;
    private JButton btnTambah;
    private JSpinner spn_sewa;
    private JSpinner spn_kembali;
    private JLabel lblJudul;
    private JLabel lblHarga;
    private JButton btnHapus;
    private JTextField txt_totalhg;
    private JLabel lblBayar;
    private JTextField txt_jmlbayar;
    private JLabel lblKembali;
    private JTextField txt_kembali;
}
