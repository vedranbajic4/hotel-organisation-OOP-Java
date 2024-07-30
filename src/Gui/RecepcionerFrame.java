package Gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;

import customClasses.CustomTableModel;
import customClasses.LicniPodaci;
import customClasses.MyTable;
import entity.DodatnaUsluga;
import entity.Gost;
import entity.Recepcioner;
import entity.Rezervacija;
import entity.Soba;
import entity.Sobarica;
import entity.TipSobe;
import enums.Pol;
import enums.StatusRezervacije;
import enums.StatusSobe;
import managerKlase.ManagerFactory;
import net.miginfocom.swing.MigLayout;
import utils.Kalkulacija;
import filter.Filter;

public class RecepcionerFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;
	ManagerFactory mf;
	Recepcioner korisnik;
	JTabbedPane tabbedPane;
	private Filter filter;
	private Kalkulacija kalk;
	
	private JPanel sveRezervacije() {
		JPanel ret = new JPanel(new BorderLayout());
		
        String[] columnNames = {"Korisnicko ime", "Id rezervacije", "Status", "Broj kreveta", "Raspored", "Id sobe", "Datum od", "Datum do", "Cena"};
        //List<Soba> sobe = korisnik.getSobeZaSpremanje();
        int br = 0;
        for (Gost g : mf.getMG().getGosti()) {
			for (Rezervacija r : g.getRezervacije()) {
				br++;
			}
		}
        String[][] matrica = new String[br][9];
        
        ret.setPreferredSize(new Dimension(800, 600));
        br = 0;
        for (Gost g : mf.getMG().getGosti()) {
			for (Rezervacija r : g.getRezervacije()) {
				matrica[br][0] = g.getKorisnickoIme();
				for(int i = 0; i < 8; i++) {
                    matrica[br][i+1] = r.forTable()[i];
                }
				br++;
			}
		}

        JTable jTable1 = new JTable(new CustomTableModel(matrica, columnNames));
        JScrollPane scrollPane = new JScrollPane(jTable1);
        ret.add(scrollPane, BorderLayout.CENTER);
		
        ret.add(filtriranjeRez(jTable1, matrica, true), BorderLayout.SOUTH);
        
        
		return ret;
	}
    private JPanel checkInGosta() {
    	JPanel ret = new JPanel(new BorderLayout());
    	JLabel lblkorisnickoIme = new JLabel("Korisnicko ime");
    	JTextField tfKorisnickoIme = new JTextField();
    	tfKorisnickoIme.setPreferredSize(new Dimension(200, 20));
    	JButton prikazi = new JButton("Prikazi rezervacije");
    	
    	JPanel p = new JPanel();
    	p.setLayout(new MigLayout());
    	p.add(lblkorisnickoIme);
    	p.add(tfKorisnickoIme, "wrap");
    	p.add(prikazi);
    	//prikazi.setDefa
    	
    	ret.add(p, BorderLayout.NORTH);
    	
    	setSize(1000, 800);
    	
    	String[] columnNames = {"Id rezervacije", "Status", "Broj kreveta", "Raspored", "Id sobe", "Datum od", "Datum do", "Cena"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    	JTable jTable1 = new JTable(model);
    	
    	Object[][] dodatneUsluge = new Object[mf.getMU().getUsluge().size()-1][2];
		for (int i = 0; i < mf.getMU().getUsluge().size()-1; i++) {
			dodatneUsluge[i][0] = mf.getMU().getUsluge().get(i+1).getNaziv();
			dodatneUsluge[i][1] = false;
			//System.out.println(dodatneUsluge[i][0] + " " + dodatneUsluge[i][1]);
		}
        /*String columnNames[] = {"Usluga", "Dodaj"};
        JTable jTable2 = new JTable(dodatneUsluge, columnNames);*/
        
		MyTable model2 = new MyTable(dodatneUsluge);
        JTable jTable2= new JTable(model2);
        //JScrollPane panelc = new JScrollPane(table);
        jTable2.setPreferredSize(new Dimension(300, 200));
        
        JScrollPane scrollPane1 = new JScrollPane(jTable1);
        JScrollPane scrollPane2 = new JScrollPane(jTable2);
        
        scrollPane1.setPreferredSize(new Dimension(600, 450));
        scrollPane2.setPreferredSize(new Dimension(250, 450));
        
        JPanel centralniPanel = new JPanel();
        
        centralniPanel.add(scrollPane1);
        centralniPanel.add(scrollPane2);
        
        ret.add(centralniPanel, BorderLayout.CENTER);
        
        
        prikazi.addActionListener(e -> fillTableWithData1(model, tfKorisnickoIme, mf));

        JButton checkIn = new JButton("Check in");
        JPanel p2 = new JPanel();
        p2.setLayout(new MigLayout());
        ret.add(p2, BorderLayout.SOUTH);
        
        p2.add(checkIn);
        checkIn.setPreferredSize(new Dimension(150, 40));
        
        checkIn.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {        		
        		int selectedRowIndex = jTable1.getSelectedRow();
                Filter filter = new Filter(mf);
                // Check if a row is selected
                if (selectedRowIndex != -1) {
                    int id = Integer.parseInt((String)jTable1.getValueAt(selectedRowIndex, 0));
                    Rezervacija r = mf.getMRez().getRezervacijaById(id);
                    
                    Gost gost = mf.getMG().postojiKorisnik(tfKorisnickoIme.getText());
            		Soba njegovaSoba = gost.postojiSobaZaCheckOut();
            		if(njegovaSoba != null) {
						JOptionPane.showMessageDialog(null,
								"Morate prvo da izadjete iz sobe: " + njegovaSoba.getBrojSobe(), "greska",
								JOptionPane.ERROR_MESSAGE);
						return;
            		}
            		
                    List<Soba> dostupneSobe = filter.getDostupneSobe(r.getTipSobe(), r.getDatumDolaska(), r.getDatumOdlaska());
					if (dostupneSobe.size() == 0) {
						JOptionPane.showMessageDialog(null, "Nema dostupnih soba za ovu rezervaciju.\nIzvinjavamo se...", "greska", JOptionPane.INFORMATION_MESSAGE);
						int res = JOptionPane.showConfirmDialog(null, "Zelite li da odbijete rezervaciju?", "upitnik", JOptionPane.YES_NO_OPTION);
						if (res == JOptionPane.YES_OPTION) {
							r.setStatus(StatusRezervacije.ODBIJENA);
							JOptionPane.showMessageDialog(null, "Odbili ste rezervaciju", "info", JOptionPane.INFORMATION_MESSAGE);
						}
					}
					else {
						Soba s = dostupneSobe.get(0);
						boolean spremanje = false;
						if (s.getStatus() == StatusSobe.SPREMANJE) spremanje = true;
						r.setSoba(s);
						s.setStatus(StatusSobe.ZAUZETA);
						
						List<DodatnaUsluga> dosadasnjeUsluge = r.getUsluge();
						
						int rowCount = model2.getRowCount();
						
	                    for (int i = 0; i < rowCount; i++) {
	                        Object value = model2.getValueAt(i, 1);
	                        if(value instanceof Boolean && (Boolean)value) {
	                        	boolean postoji = false;
	                        	for(DodatnaUsluga du : dosadasnjeUsluge) {
	                        		if(du.getId() == mf.getMU().getUsluge().get(i+1).getId()) {
	                        			postoji = true;
	                        		}
	                        	}
	                        	if(!postoji) r.dodajUslugu(mf.getMU().getUsluge().get(i+1));
	                        }
	                    }
	                    kalk.izracunajCenu(r);
	                    
	                    if(spremanje) 
	                    	JOptionPane.showMessageDialog(null, "Dobili ste sobu: " + s.getBrojSobe() + " samo sto morate sacekati da se ocisti.\nTrebate platiti: " + Float.toString(r.getCena()), "info", JOptionPane.INFORMATION_MESSAGE);
	                    else
	                    	JOptionPane.showMessageDialog(null, "Dobili ste sobu: " + s.getBrojSobe() + "\nTrebate platiti: " + Float.toString(r.getCena()), "info", JOptionPane.INFORMATION_MESSAGE);
	                    //posto ne znam sta tacno sve treba savevoati :)
	                    mf.saveData();
					}
					JPanel p3 = new JPanel();
            		p3.add(checkInGosta());
            		fillTableWithData1(model, tfKorisnickoIme, mf);
                    tabbedPane.setComponentAt(2, p3);
                }
        	}
        });
        
    	return ret;
    }
    private static void fillTableWithData1(DefaultTableModel model, JTextField tfKorisnickoIme, ManagerFactory mf) {
    	String korIme = tfKorisnickoIme.getText();
        Gost gost = mf.getMG().postojiKorisnik(korIme);
        if(gost == null){
            JOptionPane.showMessageDialog(null, "Ne postoji korisnik sa zadatim korisnickim imenom", "error", JOptionPane.ERROR_MESSAGE);
            tfKorisnickoIme.setText("");
            model.setRowCount(0);
            
            return;
        }
        else {
            int br = 0;
            for (Rezervacija r : gost.getRezervacije()) {
            	if(r.getStatus() != StatusRezervacije.POTVRDJENA) continue;
            	if(r.getSoba() != null) continue;	//ne sme da ima dodeljenu sobu, znaci da je vec check-inovana
            	br++;
            }
            String[][] data = new String[br][8];
            br = 0;
    		for (Rezervacija r : gost.getRezervacije()) {
    			if(r.getStatus() != StatusRezervacije.POTVRDJENA) continue;
    			if(r.getSoba() != null) continue;	//ne sme da ima dodeljenu sobu, znaci da je vec check-inovana
    			
    			for(int i = 0; i < 8; i++) 
                    data[br][i] = r.forTable()[i];
    			br++;
    		}
            // Remove existing rows if any
    		model.setRowCount(0);
    		if(br == 0) {
    			JOptionPane.showMessageDialog(null, "Nema rezervacija za ovog gosta", "info", JOptionPane.INFORMATION_MESSAGE);
    			return;
    		}
            // Add rows to the model
            for (Object[] row : data) 
                model.addRow(row);
        }
    }
    private JPanel filtriranjeRez(JTable table, String[][] matrica, boolean more) {
    	String[] columnNames = {"Korisnicko ime", "Id rezervacije", "Status", "Broj kreveta", "Raspored", "Id sobe", "Datum od", "Datum do", "Cena"};
    	
    	JTextField tfMinCena = new JTextField();
        JTextField tfMaxCena = new JTextField();
        tfMinCena.setPreferredSize(new Dimension(100, 20));
        tfMaxCena.setPreferredSize(new Dimension(100, 20));
        JButton filtriraj = new JButton("Filtriraj");
        JPanel p1 = new JPanel();
        p1.setLayout(new MigLayout());
        p1.add(new JLabel("Min cena"));
        p1.add(tfMinCena, "split 3");
        p1.add(new JLabel("Max cena"));
        p1.add(tfMaxCena, "wrap");
        JComboBox<String> cb = new JComboBox<String>();
        cb.addItem("Nema filtera");
		for (TipSobe ts : mf.getMTS().getTipoviSoba()) {
			cb.addItem(ts.toString());
		}
		p1.add(new JLabel("Tip sobe"));
        p1.add(cb, "wrap");
        JCheckBox[] cbUsluge = new JCheckBox[mf.getMU().getUsluge().size()-1];
        JPanel pCheckBox = new JPanel();
		for (int i = 0; i < mf.getMU().getUsluge().size() - 1; i++) {
			cbUsluge[i] = new JCheckBox(mf.getMU().getUsluge().get(i + 1).getNaziv());
			cbUsluge[i].setSelected(true);
			pCheckBox.add(cbUsluge[i]);
		}
        p1.add(pCheckBox, "wrap, skip");
        JTextField tfBrojSobe = new JTextField();
        if(more) {
        	p1.add(new JLabel("Broj sobe"));
        	tfBrojSobe.setPreferredSize(new Dimension(100, 20));
        	p1.add(tfBrojSobe, "wrap");
        }
        
        
        p1.add(filtriraj);
        
        filtriraj.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	//filtriranje po ceni!
	        	float minCena = -1;
	        	float maxCena = 100000000;
	        	if(!tfMinCena.getText().equals("")) {
	        		try{
	        			minCena = Float.parseFloat(tfMinCena.getText());
	        		}
	        		catch (Exception e2) {
            			JOptionPane.showMessageDialog(null, "Morate uneti broj", "greska", JOptionPane.ERROR_MESSAGE);
            			return;
            		}
	        	}
	        	if(!tfMaxCena.getText().equals("")) {
	        		try{
	        			maxCena = Float.parseFloat(tfMaxCena.getText());
	        		}
	        		catch (Exception e2) {
            			JOptionPane.showMessageDialog(null, "Morate uneti broj", "greska", JOptionPane.ERROR_MESSAGE);
            			return;
            		}
	        	}
	        	String[][] matrica2 = new String[1000][9];
	        	int br = 0;
	        	for(int i = 0; i < matrica.length; i++) {
	        		if(matrica[i][8] == null) continue;
	        		float cena = Float.parseFloat(matrica[i][8]);
	        		if(cena < minCena || cena > maxCena) continue;
	        		for(int j = 0; j < 9; j++) {
	                    matrica2[br][j] = matrica[i][j];
	                }
	        		br++;
	        	}
	        	
	        	String[][] matrica3 = new String[1000][9];
	        	int red = cb.getSelectedIndex();
	        	if(red != 0) {
	        		//filtri po tipu sobe
	        		br = 0;
	        		TipSobe ts = mf.getMTS().getTipoviSoba().get(red-1);
					for (int i = 0; i < matrica2.length; i++) {
						if (matrica2[i][0] == null)
							break;
						if (Integer.parseInt(matrica2[i][3]) != ts.getBrojKreveta()
								|| !matrica2[i][4].equals(ts.getRaspored()))
							continue;
						for (int j = 0; j < 9; j++) 
							matrica3[br][j] = matrica2[i][j];
						br++;
					}
	        	}
	        	else {	//prepisivanje
					for (int i = 0; i < matrica2.length; i++) {
						if (matrica2[i][0] == null)
							break;
						for (int j = 0; j < 9; j++) 
							matrica3[i][j] = matrica2[i][j];
					}
	        	}
	        	DefaultTableModel model = new DefaultTableModel(columnNames, 0);
	        	String[][] matrica4 = new String[1000][9];
	        	br = 0;
				for (int i = 0; i < matrica3.length; i++) {
					if (matrica3[i][0] == null)
						break;
					boolean dodaj = true;
					for (int j = 0; j < mf.getMU().getUsluge().size() - 1; j++) {
						if (cbUsluge[j].isSelected() == false) {
							boolean postoji = false;
							
							Rezervacija r = mf.getMRez().getRezervacijaById(Integer.parseInt(matrica3[i][1]));
							List<DodatnaUsluga> rezUsluge = r.getUsluge();
							for (DodatnaUsluga du : rezUsluge) {
								if(du.getId() == 0) continue; //preskacem nocenje
								
								if (du.getId() == j+1) {
									postoji = true;
									break;
								}
							}
							if (postoji) {
								dodaj = false;
								break;
							}
						}
					}
					if (dodaj) {
						for (int j = 0; j < 9; j++)
							matrica4[br][j] = matrica3[i][j];
						br++;
					}
				}
	        	
	        	if(more) {
	        		String[][] matrica5 = new String[1000][9];
	        		String brojSobe = tfBrojSobe.getText();
		        	if(brojSobe.equals("")) {	//prepisivanje
						for (int i = 0; i < matrica4.length; i++) {
							if (matrica4[i][0] == null)
								break;
							for (int j = 0; j < 9; j++)
								matrica5[i][j] = matrica4[i][j];
						}
		        	}
		        	
		        	else {
		        		int brojSobeInt = 0;
	        			try {
	        				brojSobeInt = Integer.parseInt(brojSobe);
	        			}
	        			catch (Exception e2) {
	        				for (int i = 0; i < matrica4.length; i++) {
								if (matrica4[i][0] == null)
									break;
								for (int j = 0; j < 9; j++)
									matrica5[i][j] = matrica4[i][j];
							}
	        				tfBrojSobe.setText("");
	        				JOptionPane.showMessageDialog(null, "Morate uneti broj", "greska", JOptionPane.ERROR_MESSAGE);
	        			}
		        		br = 0;
		        		
		        		for (int i = 0; i < matrica4.length; i++) {
		        			if (matrica4[i][0] == null) break;
		        			int idSobe = Integer.parseInt(matrica4[i][5]);
		        			if(idSobe == -1) continue;
		        			//System.out.println(mf.getMSoba().getSobaById(idSobe).getBrojSobe());
		        			//System.out.println(brojSobe);
		        			
		        			if (brojSobeInt != mf.getMSoba().getSobaById(idSobe).getBrojSobe()) continue;
		        			//System.out.println("Pogodoak");
							for (int j = 0; j < 9; j++)
								matrica5[br][j] = matrica4[i][j];
							br++;
		        		}
		        	}
	        		for (Object[] row : matrica5) 
	        			if (row[0] != null)
	        				model.addRow(row);

	        	}
	        	else {	        		
	        		for (Object[] row : matrica4) 
	        			if (row[0] != null)
	        				model.addRow(row);
	        	}
	        	
	        	table.setModel(model);
	        }
        });
        return p1;
    }
    private JPanel checkOutGosta() {
    	JPanel ret = new JPanel(new BorderLayout());
    	JLabel lblkorisnickoIme = new JLabel("Korisnicko ime");
    	JTextField tfKorisnickoIme = new JTextField();
    	tfKorisnickoIme.setPreferredSize(new Dimension(300, 40));
    	JButton checkOut = new JButton("Check out");
    	checkOut.setPreferredSize(new Dimension(150, 40));
    	
    	JPanel p = new JPanel();
    	p.setLayout(new MigLayout());
    	p.add(lblkorisnickoIme);
    	p.add(tfKorisnickoIme, "wrap");
    	p.add(checkOut);
    	
    	checkOut.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			String korIme = tfKorisnickoIme.getText();
    			Gost gost = mf.getMG().postojiKorisnik(korIme);
    			if(gost == null) 
    				JOptionPane.showMessageDialog(null, "Ne postoji korisnik sa zadatim korisnickim imenom", "error", JOptionPane.ERROR_MESSAGE);
    			else {
    				System.out.println("Korisnik" + gost.getKorisnickoIme());
    				
    				Soba njegovaSoba = gost.postojiSobaZaCheckOut();
    				if(njegovaSoba != null) {	//samo proveri njegove rez, nadje potvrdjenu sa postojeceom sobom
    	        		JOptionPane.showMessageDialog(null, "Uspesno ste check out-ovali gosta iz sobe: " + njegovaSoba.getBrojSobe());
    	        		njegovaSoba.setStatus(StatusSobe.SPREMANJE);
    	        		JOptionPane.showMessageDialog(null, "Soba je data sobarici na spremanje", "info", JOptionPane.INFORMATION_MESSAGE);
    	        		mf.getMS().dodeliSobu(njegovaSoba);
    	        		gost.postojiRezervacijaZaCheckOut().setStatus(StatusRezervacije.ZAVRSENA);	
    	        	}
    	        	else 
    	        		JOptionPane.showMessageDialog(null, "Ne postoji soba za check out!", "error", JOptionPane.ERROR_MESSAGE);
    			}
    			tfKorisnickoIme.setText("");
    			mf.saveData();
    		}
    	});
    	
    	ret.add(p, BorderLayout.CENTER);
    	return ret;
    }
	
    private JPanel potvrdaRegistracije() {
		JPanel ret = new JPanel(new BorderLayout());
		String[] columnNames = {"Korisnicko ime", "Id rezervacije", "Status", "Broj kreveta", "Raspored", "Id sobe", "Datum od", "Datum do", "Cena"};
		Filter filter = new Filter(mf);
		//List<Rezervacija> rezNaCekanju = filter.getRezervacijePoStatusu(StatusRezervacije.NACEKANJU);
		
		int br = 0;
        for (Gost g : mf.getMG().getGosti()) {
			for (Rezervacija r : g.getRezervacije()) {
				if(r.getStatus() != StatusRezervacije.NACEKANJU) continue;
				br++;
			}
		}
        String[][] matrica = new String[br][9];
        //List<Rezervacija> rezNaCekanju = new ArrayList<Rezervacija>();
        ret.setPreferredSize(new Dimension(800, 600));
        br = 0;
        for (Gost g : mf.getMG().getGosti()) {
			for (Rezervacija r : g.getRezervacije()) {
				if(r.getStatus() != StatusRezervacije.NACEKANJU) continue;
				//rezNaCekanju.add(r);
				matrica[br][0] = g.getKorisnickoIme();
				for(int i = 0; i < 8; i++) {
                    matrica[br][i+1] = r.forTable()[i];
                }
				br++;
			}
		}
       
        JTable jTable1 = new JTable(new CustomTableModel(matrica, columnNames));
        JScrollPane scrollPane = new JScrollPane(jTable1);
        System.out.println(br);
        ret.add(scrollPane, BorderLayout.CENTER);
        
        JButton potvrdi = new JButton("Potvrdi registraciju");
        JPanel p = new JPanel();
        ret.add(p, BorderLayout.NORTH);
        p.setLayout(new MigLayout());
        //p.setBorder(new javax.swing.border.EmptyBorder(10, 10, 10, 10));
        potvrdi.setPreferredSize(new Dimension(150, 40));
        p.add(potvrdi);
        
        potvrdi.addActionListener(new ActionListener() {
        	@Override
			public void actionPerformed(ActionEvent e) {
        		int selectedRowIndex = jTable1.getSelectedRow();
                
                // Check if a row is selected
                if (selectedRowIndex != -1) {
                    int id = Integer.parseInt((String)jTable1.getValueAt(selectedRowIndex, 1));
                    Rezervacija mojaR = mf.getMRez().getRezervacijaById(id);
                    
                    List<Soba> listaDostupnihSoba = new ArrayList<Soba>();
                    
                    listaDostupnihSoba = filter.getDostupneSobe(mojaR.getTipSobe(), mojaR.getDatumDolaska(), mojaR.getDatumOdlaska());
            		if(listaDostupnihSoba.size() == 0) {
            			int result = JOptionPane.showConfirmDialog(null,"Nema dostupnih soba za ovu rezervaciju.\nZelite li da odbijete rezrvaciju?", "info", JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							JOptionPane.showMessageDialog(null, "Odbili ste rezrvaciju", "info", JOptionPane.INFORMATION_MESSAGE);
							mojaR.setStatus(StatusRezervacije.ODBIJENA);
							mojaR.setCena(0);
							PrintWriter pw = null;
							try {
								pw = new PrintWriter(new FileWriter("data/rezervacija_potvrdjena.csv", true));
								pw.println(mojaR.getId()+","+LocalDate.now()+",2");
								pw.close();
							} catch (IOException e2) {
								e2.printStackTrace();
							}
						}
            		}
            		else {
						String tekst = "Dostupnih soba za ovu rezervaciju ima : " + listaDostupnihSoba.size() + "\nZelite li da prihavtite ovu rezervaciju?";
						int res1 = JOptionPane.showConfirmDialog(null, tekst, "info", JOptionPane.YES_NO_OPTION);
						if (res1 == JOptionPane.YES_OPTION) {
							JOptionPane.showMessageDialog(null, "Prihvatili ste rezervaciju", "Potvrda",
									JOptionPane.INFORMATION_MESSAGE);
							mojaR.setStatus(StatusRezervacije.POTVRDJENA);
							PrintWriter pw = null;
							try {
								pw = new PrintWriter(new FileWriter("data/rezervacija_potvrdjena.csv", true));
								pw.println(mojaR.getId()+","+LocalDate.now()+",1");
								pw.close();
							} catch (IOException e2) {
								e2.printStackTrace();
							}
						}
					}
                }
                else {
                    JOptionPane.showMessageDialog(null, "Niste izabrali nijednu rezervaciju");
                }
                mf.saveData();
                
                JPanel p5 = new JPanel();
        		p5.add(potvrdaRegistracije());
        		tabbedPane.setComponentAt(4, p5);
        		
        		JPanel p1 = new JPanel();
        		p1.add(sveRezervacije());
        		tabbedPane.setComponentAt(0, p1);
			}
        	
        });
		
        ret.add(filtriranjeRez(jTable1, matrica, false), BorderLayout.SOUTH);
        
		return ret;
	}
	private JPanel registracijaGosta() {
		JPanel ret = new JPanel(new MigLayout());
		
		JTextField korisnickoIme = new JTextField();
		JTextField lozinka = new JTextField();
		JTextField ime = new JTextField();
		JTextField prezime = new JTextField();
		JTextField brojTelefona = new JTextField();
		JTextField adresa = new JTextField();
		
		adresa.setPreferredSize(new Dimension(200, 20));
		lozinka.setPreferredSize(new Dimension(200, 20));
		ime.setPreferredSize(new Dimension(200, 20));
		prezime.setPreferredSize(new Dimension(200, 20));
		brojTelefona.setPreferredSize(new Dimension(200, 20));
		korisnickoIme.setPreferredSize(new Dimension(200, 20));
		
		
		JDateChooser datum = new JDateChooser();
		Pol[] polovi;
		polovi = new Pol[] {Pol.MUSKI, Pol.ZENSKI};
	    JComboBox<Pol> cbsobe = new JComboBox<Pol>(polovi);
        
		JLabel lblKorisnickoIme = new JLabel("Korisnicko ime");
		JLabel lblLozinka = new JLabel("Lozinka");
		JLabel lblIme = new JLabel("Ime");
		JLabel lblPrezime = new JLabel("Prezime");
		JLabel lblBrojTelefona = new JLabel("Broj telefona");
		JLabel lblAdresa = new JLabel("Adresa");
		JLabel lblPol = new JLabel("Pol");
		JLabel lblDatumRodjenja = new JLabel("Datum rodjenja");
		
		ret.add(lblKorisnickoIme);
		ret.add(korisnickoIme, "wrap");
		
		ret.add(lblLozinka);
		ret.add(lozinka, "wrap");
		
		ret.add(lblPol);
		ret.add(cbsobe, "wrap");
		
		ret.add(lblDatumRodjenja);
		ret.add(datum, "wrap");
		
		ret.add(lblIme);
		ret.add(ime, "wrap");
		
		ret.add(lblPrezime);
		ret.add(prezime, "wrap");
		
		ret.add(lblBrojTelefona);
		ret.add(brojTelefona, "wrap");
		
		ret.add(lblAdresa);
		ret.add(adresa, "wrap");
		
		JButton dodaj = new JButton("Dodaj gosta");
		dodaj.setPreferredSize(new Dimension(150, 40));		
		ret.add(dodaj, "wrap");
				
		
		dodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String korIme = korisnickoIme.getText();
				String loz = lozinka.getText();
				String imeG = ime.getText();
				String prez = prezime.getText();
				String brTel = brojTelefona.getText();
				String adr = adresa.getText();
				Pol pol = (Pol) cbsobe.getSelectedItem();
				LocalDate datumRodjenja;
				
				if (datum.getDate() == null) datumRodjenja = null;
				else datumRodjenja= datum.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				
				if(brTel.equals("")) brTel = "null";
				if(adr.equals("")) adr = "null";
				
				if (korIme.equals("") || loz.equals("") || imeG.equals("") || prez.equals("")) {
					JOptionPane.showMessageDialog(null, "Odredjena polja ne smeju biti prazna", "greska",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				mf.getMG().dodajGosta(imeG, prez, pol, datumRodjenja, brTel, adr, korIme, loz);
				JOptionPane.showMessageDialog(null, "Uspesno ste dodali gosta", "info",
						JOptionPane.INFORMATION_MESSAGE);
				mf.getMG().saveData();
				
				korisnickoIme.setText("");
				lozinka.setText("");
				ime.setText("");
				prezime.setText("");
				brojTelefona.setText("");
				adresa.setText("");
			}
		});
		
		return ret;
	}
	public RecepcionerFrame(ManagerFactory mf, Recepcioner korisnik) {
		this.mf = mf;
		this.korisnik = korisnik;
		this.filter = new Filter(mf);
		this.kalk = new Kalkulacija(mf.getMC());
		setTitle("Recepcioner");
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
		
		
		tabbedPane = new JTabbedPane();
		JPanel p1 = new JPanel();
		p1.add(sveRezervacije());
		
		
		JPanel p2 = new JPanel();
		p2.add(new LicniPodaci(mf, korisnik).panelIzmene());
		
		JPanel p3 = new JPanel();
		p3.add(checkInGosta());
		
		JPanel p4 = new JPanel();
		p4.add(checkOutGosta());
		
		JPanel p5 = new JPanel();
		p5.add(potvrdaRegistracije());
		
		JPanel p6 = new JPanel();
		p6.add(registracijaGosta());
		
		JButton logOut = new JButton("Izlogujte se");
		JPanel p7 = new JPanel();
		p7.add(logOut);
		
		tabbedPane.addTab("Sve rezervacije", p1);
		tabbedPane.addTab("Izmena podataka", p2);
		tabbedPane.addTab("Check in gosta", p3);
		tabbedPane.addTab("Check out gosta", p4);
		tabbedPane.addTab("Potvrda registracije", p5);
		tabbedPane.addTab("Registracija gosta", p6);
		tabbedPane.addTab("Log out", p7);

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
