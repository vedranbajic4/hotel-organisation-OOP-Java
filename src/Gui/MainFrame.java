package Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import entity.TipSobe;
import managerKlase.ManagerFactory;
import net.miginfocom.swing.MigLayout;


public class MainFrame extends JFrame {
	private static final long serialVersionUID = 8456560429229699542L;
	private ManagerFactory mf;
	
	public MainFrame(ManagerFactory mf) {
		this.mf = mf;
		loginDialog();
		//mainFrame();
	}
	
	private void loginDialog() {
		JDialog d = new JDialog();
		d.setTitle("Prijava");
		d.setLocationRelativeTo(null);
		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		d.setResizable(false);
		initLoginGUI(d);
		d.pack();
		d.setVisible(true);
	}
	
	private void initLoginGUI(JDialog dialog) {
		MigLayout layout = new MigLayout();
		dialog.setLayout(layout);

		JTextField tfKorisnickoIme = new JTextField(20);
		JPasswordField pfLozinka = new JPasswordField(20);
		JButton btnOk = new JButton("OK");
		JButton btnCancel = new JButton("Cancel");

		// Ako postavimo dugme 'btnOK' kao defaul button, onda ce svaki pritisak tastera
		// Enter na tastaturi
		// Izazvati klik na njega
		dialog.getRootPane().setDefaultButton(btnOk);

		dialog.add(new JLabel("Dobrodošli. Molimo da se prijavite."), "wrap");
		dialog.add(new JLabel("Korisničko ime:"));
		dialog.add(tfKorisnickoIme, "wrap");
		dialog.add(new JLabel("Lozinka:"));
		dialog.add(pfLozinka, "wrap");
		dialog.add(new JLabel());
		dialog.add(btnOk, "split 2");
		dialog.add(btnCancel);
		
		//tfKorisnickoIme.setText("perica");
        //pfLozinka.setText("lozinka123");
        
		// Klik na Login dugme
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String korisnickoIme = tfKorisnickoIme.getText().trim();
				String lozinka = new String(pfLozinka.getPassword()).trim();
				//System.out.println(korisnickoIme+" "+lozinka);
				// TO DO
				// Ukoliko nesto nije uneseno, obavestimo korisnika
				// JOptionPane.showMessageDialog(null, "Niste uneli sve podatke.")
				// Provera login podataka, ispisujemo poruku ukoliko korisnik nije nadjen
				// Ukoliko su podaci ispravni, od menadzera korisnika dobijamo objekat korisnika				
				// Sakrijemo Login prozor i dispose-ujemo
				
				dialog.setVisible(false);
				//admin
				if(mf.getMA().postojiKorisnik(korisnickoIme, lozinka) != null) {
					dialog.dispose();
                    //System.out.println("Uspesno ste se ulogovali kao administrator");
                    AdminFrame af = new AdminFrame(mf, mf.getMA().postojiKorisnik(korisnickoIme, lozinka));
				}
				//gost
				else if (mf.getMG().postojiKorisnik(korisnickoIme, lozinka) != null) {
					dialog.dispose();
					//System.out.println("Uspesno ste se ulogovali kao gost");
					GostFrame gf = new GostFrame(mf, mf.getMG().postojiKorisnik(korisnickoIme, lozinka));
				}
				//recepcioner
				else if (mf.getMR().postojiKorisnik(korisnickoIme, lozinka) != null){
					dialog.dispose();
					RecepcionerFrame rf = new RecepcionerFrame(mf, mf.getMR().postojiKorisnik(korisnickoIme, lozinka));
					//System.out.println("Uspesno ste se ulogovali kao recepcioner");
				}
				//sobarica
				else if (mf.getMS().postojiKorisnik(korisnickoIme, lozinka) != null) {
					dialog.dispose();
                    //System.out.println("Uspesno ste se ulogovali kao sobarica");
                    SobaricaFrame sf = new SobaricaFrame(mf, mf.getMS().postojiKorisnik(korisnickoIme, lozinka));
                }
                else {
                    //System.out.println("Neuspesno logovanje, pokusajte ponovo");
                    JOptionPane.showMessageDialog(null, "Korisnik sa unetim podacima ne postoji!", "Greška", JOptionPane.ERROR_MESSAGE);
                    tfKorisnickoIme.setText("");
                    pfLozinka.setText("");
                    dialog.setVisible(true);
                }
			}
		});
		
		// Cancel dugme samo sakriva trenutni prozor
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Klikno na cancel dugme");
				dialog.setVisible(false);
				dialog.dispose();
			}
		});
	}
}
