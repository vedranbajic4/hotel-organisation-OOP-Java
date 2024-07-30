package Gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.Styler;

import com.toedter.calendar.JDateChooser;

import customClasses.CustomTableModel;
import customClasses.LicniPodaci;
import customClasses.MyTable;
import customClasses.MyTable2;
import entity.Administrator;
import entity.Cenovnik;
import entity.Gost;
import entity.Recepcioner;
import entity.Rezervacija;
import entity.Soba;
import entity.Sobarica;
import entity.TipSobe;
import entity.Zaposleni;
import enums.Pol;
import enums.StatusSobe;
import enums.StrucnaSprema;
import filter.Filter;
import managerKlase.ManagerFactory;
import net.miginfocom.swing.MigLayout;

public class AdminFrame extends JFrame{
	private static final long serialVersionUID = 3L;
	private static final String PieStyler = null;
	ManagerFactory mf;
	Administrator korisnik;
	JTabbedPane tabbedPane;
	private Filter filter;
	
	private void initDialogIzmeneSobe(JDialog d, Soba s) {
		JTextField brojSobe = new JTextField(20);
		brojSobe.setText(String.valueOf(s.getBrojSobe()));
		JButton btnOk = new JButton("OK");
		JButton btnCancel = new JButton("Cancel");
		d.getRootPane().setDefaultButton(btnOk);
		
		d.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new MigLayout());
		panel.add(new Label("Broj sobe: "));
		panel.add(brojSobe, "wrap");
		
		JComboBox<TipSobe> tipSobe = new JComboBox<>();
		List<TipSobe> tipoviSoba = mf.getMTS().getTipoviSoba();
		for (TipSobe ts : tipoviSoba) {
			tipSobe.addItem(ts);
			if(ts == s.getTipSobe()) tipSobe.setSelectedItem(ts);
		}
		
		panel.add(new Label("Tip sobe: "));
		panel.add(tipSobe, "wrap");
		
		panel.add(btnOk);
		panel.add(btnCancel);
		d.add(panel, BorderLayout.CENTER);
		
		btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int broj = Integer.parseInt(brojSobe.getText());
                    s.setBrojSobe(broj);
                    s.setTipSobe((TipSobe) tipSobe.getSelectedItem());
                    d.setVisible(false);
                    d.dispose();
                    JOptionPane.showMessageDialog(null, "Uspesno ste izmenili sobu");
                    JPanel p = new JPanel(); 
                    p.add(izmenaSobe());
	                tabbedPane.setComponentAt(6, p);
	                mf.getMSoba().saveData(mf.getMS());
                } catch (NumberFormatException e1) {
                    JOptionPane.showMessageDialog(null, "Unesite broj sobe");
                }
            }
        });
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d.setVisible(false);
				d.dispose();
				JOptionPane.showMessageDialog(null, "Niste nista izmenili");
			}
		});
	}
	private void prikaziDialogIzmeneSobe(Soba s) {
		JDialog d = new JDialog();
		d.setTitle("Izmena sobe");
		d.setLocationRelativeTo(null);
		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		d.setResizable(false);
		initDialogIzmeneSobe(d, s);
		d.pack();
		d.setVisible(true);
	}
	private JDialog prikaziDialogIzmeneCenovnika(Cenovnik c) {
		JDialog d = new JDialog();
		d.setTitle("Izmena cenovnika");
		d.setLocationRelativeTo(null);
		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		d.setResizable(false);
		JPanel p = new JPanel();
		p.setLayout(new MigLayout());
		
		Object[][] dodatneUsluge = new Object[mf.getMU().getUsluge().size()-1][3];
		for (int i = 0; i < mf.getMU().getUsluge().size()-1; i++) {
			dodatneUsluge[i][0] = mf.getMU().getUsluge().get(i+1).getNaziv();
			dodatneUsluge[i][1] = mf.getMC().isOnCenovnik(mf.getMU().getUsluge().get(i+1).getId(), c.getId());
			float cena = mf.getMC().getCena(mf.getMU().getUsluge().get(i+1).getId(), c.getId());
			String cena1 = String.valueOf(cena);
			dodatneUsluge[i][2] = cena1;
			//System.out.println(dodatneUsluge[i][0] + " " + dodatneUsluge[i][1]);
		}
        
		MyTable2 model = new MyTable2(dodatneUsluge);
        JTable table = new JTable(model);
        JScrollPane panelc = new JScrollPane(table);
        table.setPreferredSize(new Dimension(400, 400));
        p.add(panelc, "wrap");
		
        JDateChooser datumOd = new JDateChooser();
        datumOd.setPreferredSize(new Dimension(100, 20));
        
        JDateChooser datumDo = new JDateChooser();
        datumDo.setPreferredSize(new Dimension(100, 20));
        
        datumOd.setDate(java.sql.Date.valueOf(c.getDatumPocetak()));
        if(c.getDatumKraj() != null)
        	datumDo.setDate(java.sql.Date.valueOf(c.getDatumKraj()));
        
        p.add(new Label("Datum od: "), "split 2");
        p.add(datumOd, "wrap");
        
        p.add(new Label("Datum do: "), "split 2");
        p.add(datumDo, "wrap");
        
        JButton btnOk = new JButton("OK");
        btnOk.setPreferredSize(new Dimension(100, 30));
        p.add(btnOk, "skip");
        
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LocalDate datumOd1 = null;
				LocalDate datumDo1 = null;
				if (datumOd.getDate() != null)
					datumOd1 = new java.sql.Date(datumOd.getDate().getTime()).toLocalDate();
				else {
					JOptionPane.showMessageDialog(null, "Unesite datum pocetka", "greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (datumDo.getDate() != null)
					datumDo1 = new java.sql.Date(datumDo.getDate().getTime()).toLocalDate();
				
				c.setDatumPocetak(datumOd1);
				c.setDatumKraj(datumDo1);
				for (int i = 0; i < dodatneUsluge.length; i++) {
					boolean uzima = (boolean) dodatneUsluge[i][1];
					float cena = Float.parseFloat((String) dodatneUsluge[i][2]);
					if (uzima) {
						//System.out.println("Uzimam stavku sa id: " + mf.getMU().getUsluge().get(i+1).getId());
						mf.getMC().setCenaForUsluga(mf.getMU().getUsluge().get(i+1), c.getId(), cena);
					} else {
						//System.out.println("Brisem stavku sa id: " + mf.getMU().getUsluge().get(i+1).getId());
						mf.getMC().removeStavka(mf.getMU().getUsluge().get(i+1).getId(), c.getId());
					}
				}
				
				mf.saveData();
				JOptionPane.showMessageDialog(null, "Uspesno ste izmenili cenovnik");
				d.setVisible(false);
				d.dispose();
			}
		});
        
        d.add(p);
		d.pack();
		d.setVisible(true);
		return d;
	}
	private JPanel izmenaSobe() {
		JPanel ret = new JPanel(new BorderLayout());
        String[] columnNames = {"Id", "Broj sobe", "Raspored", "Broj kreveta", "Status"};
        
        List<Soba> sobe = filter.getSlobodneSobe();
        
        String[][] matrica = new String[sobe.size()][5];
        
        ret.setPreferredSize(new Dimension(700, 600));
        int br = 0;
		for (Soba soba : sobe) {
			matrica[br][0] = String.valueOf(soba.getId());
			matrica[br][1] = String.valueOf(soba.getBrojSobe());
			matrica[br][2] = soba.getTipSobe().getRaspored();
			matrica[br][3] = String.valueOf(soba.getTipSobe().getBrojKreveta());
			matrica[br][4] = soba.getStatus().toString();
			br++;
		}       

        JTable jTable1 = new JTable(new CustomTableModel(matrica, columnNames));
        JScrollPane scrollPane = new JScrollPane(jTable1);
        ret.add(scrollPane, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        JButton izmeni = new JButton("Izmeni");
        panel.add(izmeni);
        izmeni.setPreferredSize(new Dimension(100, 30));
        ret.add(panel, BorderLayout.SOUTH);
        
        izmeni.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            int red = jTable1.getSelectedRow();
	            if (red == -1) {
	                JOptionPane.showMessageDialog(null, "Morate selektovati sobu", "greska", JOptionPane.ERROR_MESSAGE);
	            } else {
	                int id = Integer.parseInt((String) jTable1.getValueAt(red, 0));
	                Cenovnik c = mf.getMC().getCenovnikById(id);
	                prikaziDialogIzmeneCenovnika(c);
	            }
	        }
        });
		
		return ret;
	}
	private JPanel dodavanjeSobe() {
		JPanel ret = new JPanel();
		JTextField brojSobe = new JTextField(20);
		JButton btnDodaj = new JButton("Dodaj");
		
		ret.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new MigLayout());
		panel.add(new Label("Broj sobe: "));
		panel.add(brojSobe, "wrap");
		
		JComboBox<TipSobe> tipSobe = new JComboBox<>();
		List<TipSobe> tipoviSoba = mf.getMTS().getTipoviSoba();
		for (TipSobe ts : tipoviSoba) 
			tipSobe.addItem(ts);
		
		panel.add(new Label("Tip sobe: "));
		panel.add(tipSobe, "wrap");
		
		panel.add(btnDodaj);

		ret.add(panel, BorderLayout.CENTER);
		
		btnDodaj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(brojSobe.getText().equals("")) {
            		JOptionPane.showMessageDialog(null, "Unesite broj sobe");
            		return;
            	}
            	else{
                    TipSobe tipSobe1 = (TipSobe) tipSobe.getSelectedItem();
                    int brojSobe1 = Integer.parseInt(brojSobe.getText());
                    
                    JOptionPane.showMessageDialog(null, "Uspesno ste dodali sobu");
                    JPanel p = new JPanel(); 
                    p.add(dodavanjeSobe());
	                tabbedPane.setComponentAt(7, p);
	                mf.getMSoba().dodajSobu(tipSobe1, brojSobe1);
	                JPanel p1 = new JPanel(); 
                    p1.add(izmenaSobe());
	                tabbedPane.setComponentAt(6, p1);
	                
	                mf.getMSoba().saveData(mf.getMS());
                }
            }
        });
		return ret;
	}
	private boolean rasporedOdgovara(int brojKreveta, String raspored) {
		boolean ret = true;
		int suma = 0;
		String tokeni[] = raspored.split("\\+");
		
		for(String token : tokeni) {
			//System.out.println(token);
			try {
				suma += Integer.parseInt(token);
			}
			catch(Exception e) {
				ret = false;
				break;
			}
        }
		if (suma != brojKreveta)
			ret = false;
		return ret;
	}
	private boolean postojiTipSobe(int brojKreveta, String raspored) {
		for (TipSobe ts : mf.getMTS().getTipoviSoba()) {
			if (ts.getBrojKreveta() == brojKreveta && ts.getRaspored().equals(raspored)) {
				return true;
			}
		}
		return false;
	}
	private JPanel dodavanjeTipaSobe() {
		JPanel ret = new JPanel();
		JTextField brojKreveta = new JTextField(20);
		JTextField raspored = new JTextField(20);
		JButton btnDodaj = new JButton("Dodaj");
		
		ret.setLayout(new BorderLayout());
		JPanel panel = new JPanel(new MigLayout());
		panel.add(new Label("Broj kreveta: "));
		panel.add(brojKreveta, "wrap");
		panel.add(new Label("Raspored: "));
		panel.add(raspored, "wrap");
		panel.add(btnDodaj);
		ret.add(panel, BorderLayout.CENTER);
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int brojKreveta1 = 0;
				if (brojKreveta.getText().equals("") || raspored.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Unesite sve podatke", "greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
				String raspored1 = raspored.getText();
				try {
					brojKreveta1 = Integer.parseInt(brojKreveta.getText());
				} catch (NumberFormatException e2) {
					JOptionPane.showMessageDialog(null, "Unesite broj kreveta", "greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (rasporedOdgovara(brojKreveta1, raspored1) && !postojiTipSobe(brojKreveta1, raspored1)) {
					mf.getMTS().dodajTipSobe(new TipSobe(brojKreveta1, raspored1));
					JOptionPane.showMessageDialog(null, "Uspesno ste dodali tip sobe");
					mf.getMTS().saveData();
					JPanel p = new JPanel(); 
                    p.add(dodavanjeSobe());
	                tabbedPane.setComponentAt(7, p);
				} else {
					JOptionPane.showMessageDialog(null, "Tip sobe vec postoji ili raspored ne odgovara broju kreveta", "greska", JOptionPane.ERROR_MESSAGE);
				}
				brojKreveta.setText("");
				raspored.setText("");
			}
		});
		
		return ret;
	}
	private JPanel pregledZaposlenih() {		//TREBA TESTIRATI OTKaZ
		JPanel ret = new JPanel(new MigLayout());
		String[] columnNames = {"Id", "Ime", "Prezime", "Korisnicko ime", "Pol", "Adresa", "Broj telefona", "Datum rodjenja", "Plata", "Staz", "Strucna sprema", "Uloga"};
        //List<Soba> sobe = korisnik.getSobeZaSpremanje();
        int br = 0;
        List<Zaposleni> zaposleni = filter.getZaposleni();
        
        String[][] matrica = new String[zaposleni.size()][12];
       
        //ret.setPreferredSize(new Dimension(800, 600));
        
        br = 0;
        for (Zaposleni z : zaposleni) {
        	matrica[br][0] = String.valueOf(z.getId());
        	matrica[br][1] = z.getIme();
        	matrica[br][2] = z.getPrezime();
        	matrica[br][3] = z.getKorisnickoIme();
            if(z.getPol() != null) matrica[br][4] = z.getPol().toString();
            else matrica[br][4] = "";
            matrica[br][5] = z.getAdresa();
            matrica[br][6] = z.getTelefon();
            if(z.getDatumRodjenja() != null) matrica[br][7] = z.getDatumRodjenja().toString();
			else matrica[br][7] = "";
            matrica[br][8] = String.valueOf(z.getPlata());
            matrica[br][9] = String.valueOf(z.getStaz());
            if(z.getStrucnaSprema() != null) matrica[br][10] = z.getStrucnaSprema().toString();
            else matrica[br][10] = "";
			if (z.getClass().getSimpleName().equals("Recepcioner"))
				matrica[br][11] = "Recepcioner";
			else
				matrica[br][11] = "Sobarica";
            br++;
        }
       

        JTable jTable1 = new JTable(new CustomTableModel(matrica, columnNames));
        JScrollPane scrollPane = new JScrollPane(jTable1);
        scrollPane.setPreferredSize(new Dimension(900, 600));
        
        ret.add(scrollPane, "wrap");
		
        JButton btnObrisi = new JButton("Daj otkaz");
        btnObrisi.setPreferredSize(new Dimension(100, 30));
        ret.add(btnObrisi);
        btnObrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = jTable1.getSelectedRow();
				if (red == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati zaposlenog");
				} else {
					String korisnickoIme = jTable1.getValueAt(red, 3).toString();
					Recepcioner r = mf.getMR().getRecepcionerByKorisnickoIme(korisnickoIme);
					
					if(r != null) {
						JOptionPane.showMessageDialog(null, "Otpustili ste recepcionera: " + r.getIme() + " " + r.getPrezime());
						mf.getMR().ukloniRecepcionera(r.getId());
						mf.getMR().saveData();
						JPanel p = new JPanel();
						p.add(pregledZaposlenih());
						tabbedPane.setComponentAt(2, p);
						return;
					}
					else {
						Sobarica s = mf.getMS().getSobaricaByKorisnickoIme(korisnickoIme);
						if(s != null) {
							for (Soba soba : s.getSobeZaSpremanje()) {
								soba.setStatus(StatusSobe.SLOBODNA);
							}
			                JOptionPane.showMessageDialog(null, "Otpustili ste sobaricu: " + s.getIme() + " " + s.getPrezime());
			                mf.getMS().ukloniSobaricu(s.getId());
			                
			                mf.getMS().saveData();
							JPanel p = new JPanel();
							p.add(pregledZaposlenih());
							tabbedPane.setComponentAt(2, p);
			                return;
			            }
			            else JOptionPane.showMessageDialog(null, "Zaposleni nije pronadjen", "greska", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
        
		return ret;
	}
	private JPanel registrujZaposlenog() {
		JPanel ret = new JPanel(new MigLayout());
		JTextField ime = new JTextField(20);
		JTextField prezime = new JTextField(20);
		JTextField korisnickoIme = new JTextField(20);
		JTextField lozinka = new JTextField(20);
		JTextField telefon = new JTextField(20);
		JTextField adresa = new JTextField(20);
		JTextField plata = new JTextField(20);
		JTextField staz = new JTextField(20);
		JComboBox<String> pol = new JComboBox<>();
		pol.addItem(Pol.MUSKI.toString());
		pol.addItem(Pol.ZENSKI.toString());
		
		JComboBox<String> strucnaSprema = new JComboBox<>();
		for (StrucnaSprema ss : StrucnaSprema.values()) 
			strucnaSprema.addItem(ss.toString());
		JDateChooser datum = new JDateChooser();
		
		ret.add(new Label("Ime: "));
		ret.add(ime, "wrap");
		
		ret.add(new Label("Prezime: "));
		ret.add(prezime, "wrap");
		
		ret.add(new Label("Pol: "));
		ret.add(pol, "wrap");

		ret.add(new Label("Korisnicko ime: "));
		ret.add(korisnickoIme, "wrap");
		
		ret.add(new Label("Lozinka: "));
		ret.add(lozinka, "wrap");
		
		ret.add(new Label("Telefon: "));
		ret.add(telefon, "wrap");
		
		ret.add(new Label("Adresa: "));
		ret.add(adresa, "wrap");
		
		ret.add(new Label("Datum rodjenja: "));
		ret.add(datum, "wrap");
		
		ret.add(new Label("Plata: "));
		ret.add(plata, "wrap");
		
		ret.add(new Label("Staz: "));
		ret.add(staz, "wrap");
		
		ret.add(new Label("Strucna sprema: "));
		ret.add(strucnaSprema, "wrap");
		
		JRadioButton recepcioner = new JRadioButton("Recepcioner");
		JRadioButton sobarica = new JRadioButton("Sobarica");
		ButtonGroup bg = new ButtonGroup();
		bg.add(recepcioner);
		bg.add(sobarica);
		ret.add(recepcioner);
		ret.add(sobarica, "wrap");
		
		JButton btnRegistruj = new JButton("Registruj");
		ret.add(btnRegistruj);
	
		btnRegistruj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ime1 = ime.getText();
                String prezime1 = prezime.getText();
                String korisnickoIme1 = korisnickoIme.getText();
                String lozinka1 = lozinka.getText();
                if (filter.postojiKorisnik(korisnickoIme1)) {
					JOptionPane.showMessageDialog(null, "Korisnicko ime vec postoji", "greska",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (ime1.equals("") || prezime1.equals("") || korisnickoIme1.equals("") || lozinka1.equals("")) {
					JOptionPane.showMessageDialog(null, "Neki podaci su obavezni", "greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				String telefon1 = telefon.getText();
                String adresa1 = adresa.getText();
                Pol pol1 = Pol.valueOf(pol.getSelectedItem().toString());
                StrucnaSprema strucnaSprema1 = StrucnaSprema.valueOf(strucnaSprema.getSelectedItem().toString());
                int plata1 = 0;
                int staz1 = 0;
                
                try {
                	if(plata.getText().equals("")) plata1 = 0;
                	else plata1 = Integer.parseInt(plata.getText());
					if (staz.getText().equals("")) staz1 = 0;
					else staz1 = Integer.parseInt(staz.getText());
                } catch (NumberFormatException e2) {
                    JOptionPane.showMessageDialog(null, "Unesite brojcanu vrednost za platu i staz", "greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LocalDate datum1 = null;
				if (datum.getDate() != null) 
					datum1 = new java.sql.Date(datum.getDate().getTime()).toLocalDate();
				
				if(recepcioner.isSelected()) {
					mf.getMR().dodajRecepcionera(ime1, prezime1, pol1, datum1, telefon1, adresa1, korisnickoIme1, lozinka1, plata1, staz1, strucnaSprema1);
					JOptionPane.showMessageDialog(null, "Uspesno ste dodali recepcionera "+ime1+" "+prezime1);
					mf.getMR().saveData();
				}
				else if(sobarica.isSelected()) {
					mf.getMS().dodajSobaricu(ime1, prezime1, pol1, datum1, telefon1, adresa1, korisnickoIme1, lozinka1, plata1, staz1, strucnaSprema1);
					JOptionPane.showMessageDialog(null, "Uspesno ste dodali sobaricu "+ime1+" "+prezime1);
					mf.getMS().saveData();
				}
				else {
					JOptionPane.showMessageDialog(null, "Izaberite tip zaposlenog", "greska",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				ime.setText("");
				prezime.setText("");
				korisnickoIme.setText("");
				lozinka.setText("");
				telefon.setText("");
				adresa.setText("");
				plata.setText("");
				staz.setText("");
				bg.clearSelection();
				
				JPanel p = new JPanel();
				p.add(pregledZaposlenih());
				tabbedPane.setComponentAt(2, p);
            }
		});
		
		return ret;
	}
	private JPanel kreirajCenovnik() {
		JPanel ret = new JPanel();
		JDateChooser datumOd = new JDateChooser();
		JDateChooser datumDo = new JDateChooser();
		datumOd.setPreferredSize(new Dimension(100, 20));
		datumDo.setPreferredSize(new Dimension(100, 20));
		
		JComboBox<TipSobe> tipSobe = new JComboBox<>();
		List<TipSobe> tipoviSoba = mf.getMTS().getTipoviSoba();
		for (TipSobe ts : tipoviSoba) 
			tipSobe.addItem(ts);
		JPanel p = new JPanel(new MigLayout());
		p.add(new Label("Datum od: "));
		p.add(datumOd, "wrap");
		p.add(new Label("Datum do: "));
		p.add(datumDo, "wrap");
		p.add(new Label("Tip sobe: "));
		p.add(tipSobe, "wrap");
		JButton btnDodaj = new JButton("Dodaj");
		p.add(btnDodaj);
		ret.add(p);
		btnDodaj.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (datumOd.getDate() == null || datumDo.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Unesite datume", "greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (datumOd.getDate().after(datumDo.getDate())) {
					JOptionPane.showMessageDialog(null, "Datum od mora biti pre datuma do", "greska",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				TipSobe ts = (TipSobe) tipSobe.getSelectedItem();

				LocalDate d1 = datumOd.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
				LocalDate d2 = datumDo.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
				Cenovnik cenovnik = new Cenovnik(d1, d2);
				int idc = cenovnik.getId();
				mf.getMC().kreirajCenovnik(ts, cenovnik);
				mf.getMC().dodajStavku(mf.getMU().getUslugaById(0), idc, 1800);	//default nocenje
				
				JOptionPane.showMessageDialog(null, "Uspesno ste dodali cenovnik");
				
				mf.getMC().saveData();
				
				JPanel p = new JPanel();
				p.add(kreirajCenovnik());
				tabbedPane.setComponentAt(3, p);
				
				JPanel p1 = new JPanel();
				p1.add(izmeniCenovnik());
				tabbedPane.setComponentAt(4, p1);
			}
		});
		
		return ret;
	}
	private JPanel pregledSobarica() {
		JDateChooser datumOd = new JDateChooser();
		JDateChooser datumDo = new JDateChooser();
		datumOd.setPreferredSize(new Dimension(100, 20));
		datumDo.setPreferredSize(new Dimension(100, 20));
		JPanel sob = new JPanel(new MigLayout());
		sob.add(new Label("Sobarice"), "wrap");
		sob.add(new Label("Datum od: "), "split 4");
		sob.add(datumOd);
		sob.add(new Label("Datum do: "));
		sob.add(datumDo, "wrap");
		JButton btnPrikaziSobarice = new JButton("Prikazi");
		sob.add(btnPrikaziSobarice, "wrap");
		
		
		String[] columnNames = {"Id", "Ime", "Prezime", "Korisnicko ime", "Broj Soba"};
        DefaultTableModel modelSobarice = new DefaultTableModel(columnNames, 0);
    	JTable tableSobarice = new JTable(modelSobarice);
        JScrollPane scrollPaneSobarice = new JScrollPane(tableSobarice);
        scrollPaneSobarice.setPreferredSize(new Dimension(400, 120));
        sob.add(scrollPaneSobarice, "wrap");
    	
    	btnPrikaziSobarice.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
				if (datumOd.getDate() == null || datumDo.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Unesite datume", "greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
    	    	List<Sobarica> sobarice = mf.getMS().getSobarice();
    	    	String[][] matrica = new String[sobarice.size()][5];
    	    	int[] spremljenihSoba = new int[1000];
    	    	try {
    	    		BufferedReader citac = new BufferedReader(new FileReader("data/sobarica_spremanje.csv"));
    	    		String linija = null;
    	    		Sobarica sobarica = null;
    	    		int brojOciscenihSoba = 0;
    	    		while ((linija = citac.readLine()) != null) {
    	    			if(linija.equals("")) continue;
    	    			//System.out.println(linija);
    	    			String[] tokeni = linija.split(",");
    	    			LocalDate datum = null;
    	    			
    	    			String[] brojS = tokeni[2].split("-");
    	    			datum = LocalDate.of(Integer.parseInt(brojS[0]), Integer.parseInt(brojS[1]), Integer.parseInt(brojS[2]));
    	    			LocalDate d1 = datumOd.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    	    			LocalDate d2 = datumDo.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    	    			if (!(datum.isBefore(d1) || datum.isAfter(d2))) 
    	    				spremljenihSoba[Integer.parseInt(tokeni[0])]++;
    	    		}
    	    		citac.close();
    	    	} catch (IOException e2) {
    	    		e2.printStackTrace();
    	    	}
    	    	int br = 0;
    	    	for (Sobarica s : mf.getMS().getSobarice()) {
    	    		matrica[br][0] = String.valueOf(s.getId());
    	    		matrica[br][1] = s.getIme();
    	    		matrica[br][2] = s.getPrezime();
    	    		matrica[br][3] = s.getKorisnickoIme();
    	    		matrica[br][4] = String.valueOf(spremljenihSoba[s.getId()]);
    	    		br++;
    	    	}
    	    	modelSobarice.setRowCount(0);
    	    	// Add rows to the model
    	    	for (Object[] row : matrica) 
    	    		modelSobarice.addRow(row);
    	    }
    	});
    	
		return sob;
	}
	private JPanel pregledRezervacija() {
		JDateChooser datumOd = new JDateChooser();
		JDateChooser datumDo = new JDateChooser();
		datumOd.setPreferredSize(new Dimension(100, 20));
		datumDo.setPreferredSize(new Dimension(100, 20));
		JPanel rez = new JPanel(new MigLayout());
		rez.add(new Label("Rezervacije"), "wrap");
		rez.add(new Label("Datum od: "), "split 4");
		rez.add(datumOd);
		rez.add(new Label("Datum do: "));
		rez.add(datumDo, "wrap");
		JButton btnPrikaziRezervacije = new JButton("Prikazi");
		rez.add(btnPrikaziRezervacije, "wrap");
		
		String[] columnNames = {"Potvrdjeno", "Odbijeno", "Otkazano"};
        DefaultTableModel modelRez = new DefaultTableModel(columnNames, 0);
    	JTable tableRez = new JTable(modelRez);
        JScrollPane scrollPaneRez = new JScrollPane(tableRez);
        scrollPaneRez.setPreferredSize(new Dimension(400, 120));
        rez.add(scrollPaneRez, "wrap");
    	
    	btnPrikaziRezervacije.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
				if (datumOd.getDate() == null || datumDo.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Unesite datume", "greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
    	    
    	    	String[][] matrica = new String[1][3];
    	    	int brojPotvrdjenih = 0, brojOdbijenihSoba = 0, brojOtkazanihSoba = 0;
    	    	
    	    	try {
    	    		BufferedReader citac = new BufferedReader(new FileReader("data/rezervacija_potvrdjena.csv"));
    	    		String linija = null;
    	    		
    	    		
    	    		while ((linija = citac.readLine()) != null) {
    	    			if(linija.equals("")) continue;
    	    			
    	    			String[] tokeni = linija.split(",");
    	    			LocalDate datum = null;
    	    			
    	    			String[] brojS = tokeni[1].split("-");
    	    			datum = LocalDate.of(Integer.parseInt(brojS[0]), Integer.parseInt(brojS[1]), Integer.parseInt(brojS[2]));
    	    			LocalDate d1 = datumOd.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    	    			LocalDate d2 = datumDo.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    	    			if (!(datum.isBefore(d1) || datum.isAfter(d2))) {
							if (tokeni[2].equals("1"))
								brojPotvrdjenih++;
							else if (tokeni[2].equals("2"))
								brojOdbijenihSoba++;
							else if (tokeni[2].equals("3"))
								brojOtkazanihSoba++;
    	    			}
    	    		}
    	    		citac.close();
    	    	} catch (IOException e2) {
    	    		e2.printStackTrace();
    	    	}
    	    	matrica[0][0] = String.valueOf(brojPotvrdjenih);
    	    	matrica[0][1] = String.valueOf(brojOdbijenihSoba);
    	    	matrica[0][2] = String.valueOf(brojOtkazanihSoba);
    	    	modelRez.setRowCount(0);
    	    	// Add rows to the model
    	    	for (Object[] row : matrica) 
    	    		modelRez.addRow(row);
    	    }
    	});
    	
		return rez;
	}
	private JPanel pregledSoba() {
		JDateChooser datumOd = new JDateChooser();
		JDateChooser datumDo = new JDateChooser();
		datumOd.setPreferredSize(new Dimension(100, 20));
		datumDo.setPreferredSize(new Dimension(100, 20));
		JPanel sob = new JPanel(new MigLayout());
		sob.add(new Label("Sobe"), "wrap");
		sob.add(new Label("Datum od: "), "split 4");
		sob.add(datumOd);
		sob.add(new Label("Datum do: "));
		sob.add(datumDo, "wrap");
		JButton btnPrikaziSobe = new JButton("Prikazi");
		sob.add(btnPrikaziSobe, "wrap");
		
		String[] columnNames = {"Id", "Broj Sobe", "Broj kreveta", "Raspored", "Nocenja", "Zarada"};
        DefaultTableModel modelSobe = new DefaultTableModel(columnNames, 0);
    	JTable tableSobe = new JTable(modelSobe);
        JScrollPane scrollPaneSobe = new JScrollPane(tableSobe);
        scrollPaneSobe.setPreferredSize(new Dimension(400, 120));
        sob.add(scrollPaneSobe, "wrap");
    	
    	btnPrikaziSobe.addActionListener(new ActionListener() {
    	    @Override
    	    public void actionPerformed(ActionEvent e) {
				if (datumOd.getDate() == null || datumDo.getDate() == null) {
					JOptionPane.showMessageDialog(null, "Unesite datume", "greska", JOptionPane.ERROR_MESSAGE);
					return;
				}
    	    	List<Soba> sobe = mf.getMSoba().getSobe();
    	    	String[][] matrica = new String[sobe.size()][6];
    	    	Float[] zarada = new Float[1000];
    	    	int[] brojNocenja = new int[1000];
				for (Rezervacija r : mf.getMRez().getRezervacije()) {
					LocalDate datum = null;
					
					if(r.getSoba() == null) continue;	//to ne gledam
					if(zarada[r.getSoba().getId()] == null) zarada[r.getSoba().getId()] = 0f;
					if(brojNocenja[r.getSoba().getId()] == 0) brojNocenja[r.getSoba().getId()] = 0;
					
					int idSobe = r.getSoba().getId();
					LocalDate d1 = r.getDatumDolaska();
					LocalDate d2 = r.getDatumOdlaska();
					
					LocalDate dOd = datumOd.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
					LocalDate dDo = datumDo.getDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
					
					if (d2.isBefore(dOd) || d1.isAfter(dDo)) continue;
					
					LocalDate pravid1 = d1.isBefore(dOd) ? dOd : d1;
					LocalDate pravid2 = d2.isAfter(dDo) ? dDo : d2;
					
					int ukupnoNocenja = (int)ChronoUnit.DAYS.between(d1, d2)+1;
					
					float ukupnaCena = ((int)ChronoUnit.DAYS.between(pravid1, pravid2)+1)*(r.getCena()/ukupnoNocenja);
					brojNocenja[idSobe] += (int)ChronoUnit.DAYS.between(pravid1, pravid2)+1;
					zarada[idSobe] += ukupnaCena;
				}
    	    	int br = 0;
    	    	for (Soba s : sobe) {
    	    		matrica[br][0] = String.valueOf(s.getId());
    	    		matrica[br][1] = String.valueOf(s.getBrojSobe());
    	    		matrica[br][2] = s.getTipSobe().getBrojKreveta() + "";
    	    		matrica[br][3] = s.getTipSobe().getRaspored();
    	    		matrica[br][4] = brojNocenja[s.getId()] + "";
    	    		if(zarada[s.getId()] == null) zarada[s.getId()] = 0f;
    	    		matrica[br][5] = Math.round(zarada[s.getId()]) + "";
    	    		br++;
    	    	}
    	    	modelSobe.setRowCount(0);
    	    	// Add rows to the model
    	    	for (Object[] row : matrica) 
    	    		modelSobe.addRow(row);
    	    }
    	});
    	
		return sob;
	}
	private JPanel pregledPrihoda() {
		JPanel ret = new JPanel(new MigLayout());
		ret.add(pregledSobarica(), "wrap");
		ret.add(pregledRezervacija(), "wrap");
		ret.add(pregledSoba(), "wrap");
		return ret;
	}
	private JPanel izmeniCenovnik() {
		JPanel ret = new JPanel(new MigLayout());
		String[] columnNames = {"Id", "Raspored", "Broj kreveta", "Datum od", "Datum do"};
        
		HashMap<TipSobe, ArrayList<Cenovnik>> cenovnici = mf.getMC().getCenovnici();
        int sajz = 0;
		for (TipSobe ts : cenovnici.keySet()) {
			sajz += cenovnici.get(ts).size();
		}
        String[][] matrica = new String[sajz][5];
        
        ret.setPreferredSize(new Dimension(700, 600));
        int br = 0;
		for (TipSobe ts : cenovnici.keySet()) {
			for (Cenovnik c : cenovnici.get(ts)) {
				matrica[br][0] = String.valueOf(c.getId());
				matrica[br][1] = ts.getRaspored();
				matrica[br][2] = String.valueOf(ts.getBrojKreveta());
				matrica[br][3] = c.getDatumPocetak().toString();
				if (c.getDatumKraj() != null)
					matrica[br][4] = c.getDatumKraj().toString();
				else
					matrica[br][4] = "";
				br++;
			}
		}

        JTable jTable1 = new JTable(new CustomTableModel(matrica, columnNames));
        JScrollPane scrollPane = new JScrollPane(jTable1);
        ret.add(scrollPane, "wrap");
		
		JButton btnIzmeni = new JButton("Izmeni");
		btnIzmeni.setPreferredSize(new Dimension(100, 30));
		btnIzmeni.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = jTable1.getSelectedRow();
				if (red == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati cenovnik", "greska",
							JOptionPane.ERROR_MESSAGE);
				} else {
					int id = Integer.parseInt((String) jTable1.getValueAt(red, 0));
					Cenovnik c = mf.getMC().getCenovnikById(id);
					prikaziDialogIzmeneCenovnika(c);
				}
			}
		});
		ret.add(btnIzmeni, "split 2");
		
		JButton btnObrisi = new JButton("Obrisi");
		btnObrisi.setPreferredSize(new Dimension(100, 30));
		ret.add(btnObrisi, "wrap");
		
		btnObrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = jTable1.getSelectedRow();
				if (red == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati cenovnik", "greska",
							JOptionPane.ERROR_MESSAGE);
				} else {
					
					JOptionPane.showMessageDialog(null, "Obrisali ste cenovnik (nisam jos)");
				}
			}
		});
		btnObrisi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int red = jTable1.getSelectedRow();
				if (red == -1) {
					JOptionPane.showMessageDialog(null, "Morate selektovati cenovnik", "greska",
							JOptionPane.ERROR_MESSAGE);
				} else {
					int id = Integer.parseInt((String) jTable1.getValueAt(red, 0));
					Cenovnik c = mf.getMC().getCenovnikById(id);
					mf.getMC().ukloniCenovnik(c.getId());
					mf.saveData();
					
					JPanel p = new JPanel();
					p.add(kreirajCenovnik());
					tabbedPane.setComponentAt(3, p);

					JPanel p1 = new JPanel();
					p1.add(izmeniCenovnik());
					tabbedPane.setComponentAt(4, p1);
				}
			}
		});
		return ret;
	}
	private JPanel prikazChartova1() {
		JPanel ret = new JPanel();
        setLayout(new BorderLayout());

        Map<String, double[]> prihod = new HashMap<>();

        List<TipSobe> tipoviSoba = mf.getMTS().getTipoviSoba();
        for (TipSobe tipSobe: tipoviSoba) 
            prihod.put(tipSobe.getBrojKreveta()+", "+tipSobe.getRaspored(), new double[12]);
        
        YearMonth trenMesec = YearMonth.now();


        for (int i = 0; i < 12; i++) {
            YearMonth month = trenMesec.minusMonths(i);
			for (TipSobe tipSobe : tipoviSoba) {
				try {
					BufferedReader br = new BufferedReader(new FileReader("data/rezervacija_potvrdjena.csv"));
					String linija = null;
					while ((linija = br.readLine()) != null) {
						if(linija.equals("")) continue;
						
						String[] tokeni = linija.split(",");
						if(!tokeni[2].equals("1")) continue;	//rez mora biti potvrdjena
						LocalDate datum = null;
						String[] brojS = tokeni[1].split("-");
						datum = LocalDate.of(Integer.parseInt(brojS[0]), Integer.parseInt(brojS[1]), Integer.parseInt(brojS[2]));
						if (datum.getMonth() == month.getMonth() && datum.getYear() == month.getYear()) {
							int idRez = Integer.parseInt(tokeni[0]);
							Rezervacija r = mf.getMRez().getRezervacijaById(idRez);
							if (r.getTipSobe().getRaspored() == tipSobe.getRaspored()
									&& r.getTipSobe().getBrojKreveta() == tipSobe.getBrojKreveta()) {
								//System.out.println("Za "+tipSobe.getBrojKreveta()+", "+tipSobe.getRaspored()+" "+r.getCena());
								prihod.get(tipSobe.getBrojKreveta()+", "+tipSobe.getRaspored())[11-i] += r.getCena();
							}
						}
					}
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
        }
        CategoryChart grafikon = new CategoryChartBuilder().width(700).height(550)
                .title("Prihod po tipu sobe").xAxisTitle("Meseci").yAxisTitle("Prihod").build();

 
        grafikon.getStyler().setLegendBackgroundColor(Color.GRAY);
        grafikon.getStyler().setChartTitleFont(new Font("Arial", Font.BOLD, 18));
        grafikon.getStyler().setLegendFont(new Font("Arial", Font.PLAIN, 14));
        grafikon.getStyler().setXAxisLabelRotation(45);

        List<String> meseci = IntStream.range(0, 12)
                .mapToObj(i -> LocalDate.now().minusMonths(11 - i).getMonth().getDisplayName(java.time.format.TextStyle.FULL, Locale.ENGLISH))
                .collect(Collectors.toList());

        for (Map.Entry<String, double[]> entry : prihod.entrySet()) {
        	grafikon.addSeries(entry.getKey(), meseci, Arrays.stream(entry.getValue()).boxed().collect(Collectors.toList()));
        }
        ret.add(new XChartPanel<>(grafikon), BorderLayout.CENTER);

        
		
		return ret;
	}
	private JPanel prikazChartova2() {
		JPanel ret = new JPanel();
        Map<String, Integer> mapaSobarica = new HashMap<>();

        try {
        	FileReader fr = new FileReader("data/sobarica_spremanje.csv");
        	BufferedReader br = new BufferedReader(fr);
        	String linija = null;
			while ((linija = br.readLine()) != null) {
				if(linija.equals("")) continue;
				String[] tokeni = linija.split(",");
				
				LocalDate datum = null;
				String[] brojS = tokeni[2].split("-");
				datum = LocalDate.of(Integer.parseInt(brojS[0]), Integer.parseInt(brojS[1]), Integer.parseInt(brojS[2]));
				
				if (datum.isAfter(LocalDate.now().minusDays(31))) {
					Sobarica sobarica = mf.getMS().getSobaricaById(Integer.parseInt(tokeni[0]));
					if (sobarica != null) {
                        String korisnickoIme = sobarica.getKorisnickoIme();
                        int pre = mapaSobarica.getOrDefault(korisnickoIme, 0);
                        mapaSobarica.put(korisnickoIme, pre+1);
					}
				}
			}
			br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        PieChart grafikon = new PieChartBuilder().width(600).height(400)
                .title("Prikaz opterećenja sobarica za prethodnih 30 dana").build();

        grafikon.getStyler().setLegendVisible(true);
        grafikon.getStyler().setChartTitleFont(new Font("Arial", Font.BOLD, 18));
        grafikon.getStyler().setLegendFont(new Font("Arial", Font.PLAIN, 14));
        grafikon.getStyler().setLabelsDistance(1.15);
        grafikon.getStyler().setPlotContentSize(0.7);

        for (Map.Entry<String, Integer> entry : mapaSobarica.entrySet()) {
        	grafikon.addSeries(entry.getKey(), entry.getValue());
        }

       
        ret.add(new XChartPanel<>(grafikon));
        
		return ret;
	}
	private JPanel prikazChartova3() {
		JPanel ret = new JPanel();
        Map<String, Integer> mapaRez = new HashMap<>();

        try {
        	FileReader fr = new FileReader("data/rezervacija_potvrdjena.csv");
        	BufferedReader br = new BufferedReader(fr);
        	String linija = null;
			while ((linija = br.readLine()) != null) {
				if(linija.equals("")) continue;
				String[] tokeni = linija.split(",");
				
				LocalDate datum = null;
				String[] brojS = tokeni[1].split("-");
				datum = LocalDate.of(Integer.parseInt(brojS[0]), Integer.parseInt(brojS[1]), Integer.parseInt(brojS[2]));
				
				if (datum.isAfter(LocalDate.now().minusDays(31))) {
                    String status = "POTVRDJENA";
					if (tokeni[2].equals("2"))
						status = "ODBIJENA";
					else if (tokeni[2].equals("3"))
						status = "OTKAZANA";
					
                    int pre = mapaRez.getOrDefault(status, 0);
                    mapaRez.put(status, pre+1);
				}
			}
			br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        for(Rezervacija r: mf.getMRez().getRezervacije()) {
        	if(r.getDatumKreiranja().isAfter(LocalDate.now().minusDays(31))) {
        		int pre = mapaRez.getOrDefault("NACEKANJU", 0);
                mapaRez.put("NACEKANJU", pre+1);
        	}
        }

        PieChart grafikon = new PieChartBuilder().width(600).height(400)
                .title("Prikaz statusa svih rezervacija kreiranih u prethodnih 30 dana").build();

        /*grafikon.getStyler().setLegendVisible(true);
        grafikon.getStyler().setChartTitleFont(new Font("Arial", Font.BOLD, 18));
        grafikon.getStyler().setLegendFont(new Font("Arial", Font.PLAIN, 14));
        grafikon.getStyler().setLabelsDistance(1.15);
        grafikon.getStyler().setPlotContentSize(0.7);*/

        for (Map.Entry<String, Integer> entry : mapaRez.entrySet()) 
        	grafikon.addSeries(entry.getKey(), entry.getValue());
       
        ret.add(new XChartPanel<>(grafikon));
        
		return ret;
	}
	
	public AdminFrame(ManagerFactory mf, Administrator korisnik) {
		this.mf = mf;
		this.korisnik = korisnik;
		this.filter = new Filter(mf);
		//this.kalk = new Kalkulacija(mf.getMC());
		setTitle("Administrator");
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
		
		
		tabbedPane = new JTabbedPane();
		JPanel p1 = new JPanel();
		p1.add(registrujZaposlenog());
		
		JPanel p2 = new JPanel();
		p2.add(pregledPrihoda());
		
		JPanel p3 = new JPanel();
		p3.add(pregledZaposlenih());
		
		JPanel p4 = new JPanel();
		p4.add(kreirajCenovnik());
		
		JPanel p5 = new JPanel();
		p5.add(izmeniCenovnik());
		
		JPanel p6 = new JPanel();
		p6.add(new LicniPodaci(mf, korisnik).panelIzmene());
		
		JPanel p7 = new JPanel();
		p7.add(izmenaSobe());
		
		JPanel p8 = new JPanel();
		p8.add(dodavanjeSobe());
		
		JPanel p9 = new JPanel();
		p9.add(dodavanjeTipaSobe());
		
		JPanel p10 = new JPanel();
		p10.add(new Label("Izlogujte se"));
		
		JPanel p11 = new JPanel();
		p11.add(prikazChartova1());
		
		JPanel p12 = new JPanel();
		p12.add(prikazChartova2());
		
		JPanel p13 = new JPanel();
		p13.add(prikazChartova3());
		
		JButton logOut = new JButton("Izlogujte se");
		p10.add(logOut);
		
		tabbedPane.addTab("Registracija zaposlenog", p1);
		tabbedPane.addTab("Pregled prihoda", p2);
		tabbedPane.addTab("Pregled zaposlenih", p3);
		tabbedPane.addTab("Kreiraj novi cenovnik", p4);
		tabbedPane.addTab("Izmeni cenovnik", p5);
		tabbedPane.addTab("Izmena licnih podataka", p6);
		tabbedPane.addTab("Izmena sobe", p7);
		tabbedPane.addTab("Dodavanje sobe", p8);
		tabbedPane.addTab("Dodavanje tipa sobe", p9);
		tabbedPane.addTab("Izlogujte se", p10);
		tabbedPane.addTab("Prikaz chartova1", p11);
		tabbedPane.addTab("Prikaz chartova2", p12);
		tabbedPane.addTab("Prikaz chartova3", p13);
		
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
