package customClasses;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import entity.Administrator;
import entity.Gost;
import entity.Korisnik;
import entity.Recepcioner;
import entity.Sobarica;
import enums.Pol;
import managerKlase.ManagerFactory;
import net.miginfocom.swing.MigLayout;


public class LicniPodaci extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ManagerFactory mf;
    private Korisnik k;
    
	public LicniPodaci(ManagerFactory mf, Korisnik k) {
		this.mf = mf;
		this.k = k;
    }
	
	public JPanel panelIzmene() {
		JPanel ret = new JPanel();
		
		ret.setLayout(new MigLayout());
		
		JLabel lIme = new JLabel("Ime:");
		JTextField tfIme = new JTextField(k.getIme());
		tfIme.setPreferredSize(new Dimension(200, 20));
		ret.add(lIme);
		ret.add(tfIme, "wrap");
		
	    JLabel lPrezime = new JLabel("Prezime:");
	    JTextField tfPrezime = new JTextField(k.getPrezime());
	    tfPrezime.setPreferredSize(new Dimension(200, 20));
	    ret.add(lPrezime);
	    ret.add(tfPrezime, "wrap");
	    
	    JLabel lPol = new JLabel("Pol:");
	    ret.add(lPol);
	    Pol[] polovi;
	    if (k.getPol() == Pol.MUSKI) polovi = new Pol[] {Pol.MUSKI, Pol.ZENSKI};
        else polovi = new Pol[] {Pol.ZENSKI, Pol.MUSKI};
        JComboBox<Pol> cbsobe = new JComboBox<Pol>(polovi);
        ret.add(cbsobe, "wrap");
	    
        JLabel lKorisnickoIme = new JLabel("Korisnicko ime:");
        JTextField tfKorisnickoIme = new JTextField(k.getKorisnickoIme());
        tfKorisnickoIme.setPreferredSize(new Dimension(200, 20));
        ret.add(lKorisnickoIme);
        ret.add(tfKorisnickoIme, "wrap");
        
        JLabel lLozinka = new JLabel("Lozinka:");
        JTextField tfLozinka = new JTextField(k.getLozinka());
        tfLozinka.setPreferredSize(new Dimension(200, 20));
        ret.add(lLozinka);
        ret.add(tfLozinka, "wrap");
        
        JLabel lDatumRodjenja = new JLabel("Datum rodjenja:");
        JDateChooser tfdatum1 = new JDateChooser();
        tfdatum1.setDateFormatString("dd-MM-yyyy");
        if(k.getDatumRodjenja() != null) {
        	Date datum = java.util.Date.from(k.getDatumRodjenja().atStartOfDay(ZoneId.systemDefault()).toInstant());
        	tfdatum1.setDate(datum);
        }
		ret.add(lDatumRodjenja);
		ret.add(tfdatum1, "wrap");
		
		JLabel lTelefon = new JLabel("Telefon:");
		JTextField tfTelefon = new JTextField(k.getTelefon());
		if(k.getTelefon().equals("null"))
			tfTelefon.setText("");
		tfTelefon.setPreferredSize(new Dimension(200, 20));
		ret.add(lTelefon);
		ret.add(tfTelefon, "wrap");
		
		JLabel lAdresa = new JLabel("Adresa:");
		JTextField tfAdresa = new JTextField(k.getAdresa());
		if(k.getAdresa().equals("null")) 
			tfAdresa.setText("");
		tfAdresa.setPreferredSize(new Dimension(200, 20));
		ret.add(lAdresa);
		ret.add(tfAdresa, "wrap");
		
		JButton b = new JButton("Izmeni");
		ret.add(b, "wrap");
	    
		b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	if(tfIme.getText().equals("") || tfPrezime.getText().equals("") || tfKorisnickoIme.getText().equals("") || tfLozinka.getText().equals("")) {
            		JOptionPane.showMessageDialog(null, "Neka polja su obavezna, morate ih popuniti", "Greska", JOptionPane.ERROR_MESSAGE);
            		return;
            	}
            	
            	k.setIme(tfIme.getText());
            	k.setPrezime(tfPrezime.getText());
            	k.setPol((Pol) cbsobe.getSelectedItem());
            	k.setKorisnickoIme(tfKorisnickoIme.getText());
            	k.setLozinka(tfLozinka.getText());
            	
            	if(tfTelefon.getText().equals("")) k.setTelefon("null");
            	else k.setTelefon(tfTelefon.getText());
            	
            	if(tfAdresa.getText().equals("")) k.setAdresa("null");
            	else k.setAdresa(tfAdresa.getText());
            	
            	if(tfdatum1.getDate() != null) {
            		//System.out.println("Local date namestam na : " + tfdatum1.getDate());
            		k.setDatumRodjenja(LocalDate.of(tfdatum1.getJCalendar().getYearChooser().getYear(), tfdatum1.getJCalendar().getMonthChooser().getMonth() + 1, tfdatum1.getJCalendar().getDayChooser().getDay()));
            	}
            	else k.setDatumRodjenja(null);
            	
				if (k instanceof Gost) {
					//System.out.println("Cuvam goste");
					mf.getMG().saveData();
				}
				else if (k instanceof Sobarica) {
					//System.out.println("Cuvam sobarice");
					mf.getMS().saveData();
				}
				else if (k instanceof Recepcioner) {
					mf.getMR().saveData();
					//System.out.println("Cuvam Recepcionere");
				}
				else if (k instanceof Administrator) {
					mf.getMA().saveData();
					//System.out.println("Cuvam admine");
				}
				
            	mf.getMG().saveData();
            	
            	JOptionPane.showMessageDialog(null, "Izmena uspesna", "potvrda", JOptionPane.INFORMATION_MESSAGE);
            }
        });
		
        return ret;
	}
}
