package MainFrame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class menu extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menu frame = new menu();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public menu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 434, 22);
		contentPane.add(menuBar);
		
		JMenu mnMaster = new JMenu("Master");
		menuBar.add(mnMaster);
		
		JMenuItem mntmCustomer = new JMenuItem("Customer");
		mntmCustomer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new formCustomer().setVisible(true);
			}
		});
		mnMaster.add(mntmCustomer);
		
		JMenuItem mntmFilm = new JMenuItem("Film");
		mntmFilm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new formFilm().setVisible(true);
			}
		});
		mnMaster.add(mntmFilm);
		
		JMenuItem mntmKategoriFilm = new JMenuItem("Kategori Film");
		mntmKategoriFilm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new formJenis().setVisible(true);
			}
		});
		mnMaster.add(mntmKategoriFilm);
		
		JMenu mnTransaksi = new JMenu("Transaksi");
		menuBar.add(mnTransaksi);
		
		JMenuItem mntmPersewaan = new JMenuItem("Persewaan ");
		mntmPersewaan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new formTransaksi().setVisible(true);
			}
		});
		mnTransaksi.add(mntmPersewaan);
	}
}
