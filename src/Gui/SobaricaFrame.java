package Gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import customClasses.CustomTableModel;
import customClasses.LicniPodaci;
import entity.Gost;
import entity.Rezervacija;
import entity.Soba;
import entity.Sobarica;
import enums.StatusSobe;
import managerKlase.ManagerFactory;
import net.miginfocom.swing.MigLayout;

public class SobaricaFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	ManagerFactory mf;
	Sobarica korisnik;
	JTabbedPane tabbedPane;
	private JPanel sobeZaCiscenje() {
		JPanel ret = new JPanel(new BorderLayout());
		
        String[] columnNames = {"Id sobe", "Broj sobe", "Broj kreveta", "Raspored", "Status"};
        List<Soba> sobe = korisnik.getSobeZaSpremanje();
        String[][] matrica = new String[sobe.size()][5];
        
        ret.setPreferredSize(new Dimension(800, 600));
        int br = 0;
        for (Soba s : sobe) {
            matrica[br] = s.forTable(); // Pretpostavljamo da forTable() vraća niz sa 2 elementa
            br++;
        }

        JTable jTable1 = new JTable(new CustomTableModel(matrica, columnNames));
        JScrollPane scrollPane = new JScrollPane(jTable1);
        ret.add(scrollPane, BorderLayout.CENTER);
        JButton ocisti = new JButton("Ocisti sobu");
        JPanel p = new JPanel();
        ret.add(p, BorderLayout.SOUTH);
        p.setLayout(new MigLayout());
        //p.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));
        ocisti.setPreferredSize(new Dimension(200, 50));
        p.add(ocisti);
        
        ocisti.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		int selectedRowIndex = jTable1.getSelectedRow();
                
                // Check if a row is selected
                if (selectedRowIndex != -1) {
                    int id = Integer.parseInt((String)jTable1.getValueAt(selectedRowIndex, 0));
                    //System.out.println("id == "+id);
                    String brojSobe = (String) jTable1.getValueAt(selectedRowIndex, 1);
					korisnik.spremiSobu(mf.getMSoba().getSobaById(id));
					mf.getMSoba().saveData(mf.getMS());
					
					JOptionPane.showMessageDialog(null, "Ocistili ste sobu broj " + brojSobe);
					
					// Refresh table
					JPanel p1 = new JPanel();
					p1.add(sobeZaCiscenje());
					tabbedPane.setComponentAt(0, p1);
                    
                } else {
                    JOptionPane.showMessageDialog(null, "Niste izabrali nijednu sobu");
                }
			}
        });
        
		return ret;
	}
	
	public SobaricaFrame(ManagerFactory mf, Sobarica korisnik) {
		this.mf = mf;
		this.korisnik = korisnik;
		setTitle("Sobarica");
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
		
		
		tabbedPane = new JTabbedPane();
		JPanel p1 = new JPanel();
		p1.add(sobeZaCiscenje());
		
		
		JPanel p2 = new JPanel();
		p2.add(new LicniPodaci(mf, korisnik).panelIzmene());
		
		JButton logOut = new JButton("Izlogujte se");
		JPanel p5 = new JPanel();
		p5.add(logOut);
		
		tabbedPane.addTab("Sobe za ciscenje", p1);
		tabbedPane.addTab("Izmena podataka", p2);
		tabbedPane.addTab("Log out", p5);

		getContentPane().add(tabbedPane);
		
		logOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogButton = JOptionPane.YES_NO_OPTION;
				int dialogResult = JOptionPane.showConfirmDialog(null, "Da li ste sigurni da zelite da se izlogujete?", "Upozorenje", dialogButton);
				if (dialogResult == JOptionPane.YES_OPTION) {
					setVisible(false);
					dispose();
					new MainFrame(mf);
				}
			}
		});
	}
}
