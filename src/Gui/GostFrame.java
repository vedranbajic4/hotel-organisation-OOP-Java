package Gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.toedter.calendar.JDateChooser;

import customClasses.CustomTableModel;
import customClasses.LicniPodaci;
import customClasses.MyTable;
import entity.Gost;
import entity.Rezervacija;
import entity.TipSobe;
import managerKlase.ManagerFactory;
import net.miginfocom.swing.MigLayout;


public class GostFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	private ManagerFactory mf;
	private Gost korisnik;
	private JTabbedPane tabbedPane;
	
	private JPanel prikazTipovaSoba() {
        JPanel ret = new JPanel(new BorderLayout());
        String[] columnNames = {"Broj kreveta", "Raspored"};
        List<TipSobe> tipoviSoba = mf.getMTS().getTipoviSoba();
        String[][] matrica = new String[tipoviSoba.size()][2];

        int br = 0;
        for (TipSobe ts : tipoviSoba) {
            matrica[br] = ts.forTable(); // Pretpostavljamo da forTable() vraća niz sa 2 elementa
            br++;
        }

        JTable jTable1 = new JTable(new CustomTableModel(matrica, columnNames));
        JScrollPane scrollPane = new JScrollPane(jTable1);
        ret.setPreferredSize(new Dimension(800, 600));
        ret.add(scrollPane, BorderLayout.CENTER);

        return ret;
    }
	private JPanel dodavanjeRezervacije() {
		JPanel p = new JPanel();
		JLabel ldatum1 = new JLabel("Datum od:");
		JLabel ldatum2 = new JLabel("Datum do:");
		
		JDateChooser tfdatum1 = new JDateChooser();
		
		JDateChooser tfdatum2 = new JDateChooser();
	
		tfdatum1.setDateFormatString("dd-MM-yyyy");
		tfdatum2.setDateFormatString("dd-MM-yyyy");
		
		tfdatum1.setPreferredSize(new Dimension(200, 20));
		tfdatum2.setPreferredSize(new Dimension(200, 20));
		
		
		p.setLayout(new MigLayout());
		
		p.add(ldatum1, "split 2");
		p.add(tfdatum1, "wrap");
		p.add(ldatum2, "split 2");
		p.add(tfdatum2, "wrap");
		
		int br = mf.getMTS().getTipoviSoba().size();
		TipSobe[] tipoviSoba = new TipSobe[br];
		for (int i = 0; i < br; i++) 
			tipoviSoba[i] = mf.getMTS().getTipoviSoba().get(i);
		
        JComboBox<TipSobe> cbsobe = new JComboBox<>(tipoviSoba);
        p.add(cbsobe, "wrap");

        Object[][] dodatneUsluge = new Object[mf.getMU().getUsluge().size()-1][2];
		for (int i = 0; i < mf.getMU().getUsluge().size()-1; i++) {
			dodatneUsluge[i][0] = mf.getMU().getUsluge().get(i+1).getNaziv();
			dodatneUsluge[i][1] = false;
			//System.out.println(dodatneUsluge[i][0] + " " + dodatneUsluge[i][1]);
		}
        
		MyTable model = new MyTable(dodatneUsluge);
        JTable table = new JTable(model);
        JScrollPane panelc = new JScrollPane(table);
        table.setPreferredSize(new Dimension(600, 400));
        p.add(panelc, "span 2, wrap");
		
        JButton dodaj = new JButton("Dodaj");
        dodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	int red = cbsobe.getSelectedIndex();
            	TipSobe tipSobe = mf.getMTS().getTipoviSoba().get(red);
            	Date d1 = tfdatum1.getDate();
            	Date d2 = tfdatum2.getDate();
            	
                if (d1 != null && d2 != null) {
                    // Konvertujemo u java.time.LocalDate
                    LocalDate ld1 = d1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate ld2 = d2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    TableModel model = table.getModel();
                    Rezervacija r = new Rezervacija(tipSobe, ld1, ld2);	//pravljenje rezervacije
                    
                    r.dodajUslugu(mf.getMU().getUsluge().get(0));	//nocenje
                    
                    int rowCount = model.getRowCount();
                    for (int i = 0; i < rowCount; i++) {
                        Object value = model.getValueAt(i, 1);
                        if(value instanceof Boolean && (Boolean)value) {
                        	r.dodajUslugu(mf.getMU().getUsluge().get(i));
                        }
                    }
                    //System.out.println("Dodajem rezervaciju");
                    mf.getMRez().dodajRezervaciju(r);
                    korisnik.dodajRezervaciju(r);
                    mf.saveData();
                    tabbedPane.setComponentAt(2, prikazRezervacija());
                    JOptionPane.showMessageDialog(p, "Uspesno ste napravili zahtev za rezervaciju!", "Potvrda", JOptionPane.PLAIN_MESSAGE);
                    tfdatum1.setDate(null);
                    tfdatum2.setDate(null);
					for (int i = 0; i < rowCount; i++) {
						model.setValueAt(false, i, 1);
					}
                } else {
                	JOptionPane.showMessageDialog(p, "Niste uneli datum!", "Greska", JOptionPane.ERROR_MESSAGE);
                }
                /*
            	Rezervacija r = new Rezervacija(selectedItem, , d2);
        		mf.getMRez().dodajRezervaciju(r);
        		System.out.print(" Uspesno ste napravili rezeraciju\n Zelite li dodatne usluge? (1/2)(da/ne)");*/
            }
        });
        
		p.add(dodaj);
		return p;
	}
	private JPanel prikazRezervacija() {
        JPanel ret = new JPanel(new BorderLayout());
        String[] columnNames = {"Id", "Status", "Broj kreveta", "Raspored", "Id sobe", "Datum od", "Datum do", "Cena"};
        List<Rezervacija> rezervacije = korisnik.getRezervacije();
        String[][] matrica = new String[rezervacije.size()][10];
        ret.setPreferredSize(new Dimension(800, 600));
        int br = 0;
        for (Rezervacija r : rezervacije) {
            matrica[br] = r.forTable(); // Pretpostavljamo da forTable() vraća niz sa 2 elementa
            br++;
        }

        JTable jTable1 = new JTable(new CustomTableModel(matrica, columnNames));
        JScrollPane scrollPane = new JScrollPane(jTable1);
        ret.add(scrollPane, BorderLayout.CENTER);

        return ret;
    }
	
	public GostFrame(ManagerFactory mf, Gost korisnik) {
		this.mf = mf;
		this.korisnik = korisnik;
		setTitle("Gost");
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
		
		
		tabbedPane = new JTabbedPane();
		JPanel p1 = new JPanel();
		p1.add(prikazTipovaSoba());
		
		
		JPanel p2 = new JPanel();
		p2.add(dodavanjeRezervacije());
		
		JPanel p3 = new JPanel();
		p3.add(prikazRezervacija());
		
		JPanel p4 = new JPanel();
		p4.add(new LicniPodaci(mf, korisnik).panelIzmene());
		
		JButton logOut = new JButton("Izlogujte se");
		JPanel p5 = new JPanel();
		p5.add(logOut);
		
		tabbedPane.addTab("Tipovi soba", p1);
		tabbedPane.addTab("Rezervacija sobe", p2);
		tabbedPane.addTab("Vase rezervacije", p3);
		tabbedPane.addTab("Izmena podataka", p4);
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

	public void showFrame() {
		setVisible(true);
	}

	public void hideFrame() {
		setVisible(false);
	}

	public void closeFrame() {
		dispose();
	}
}
