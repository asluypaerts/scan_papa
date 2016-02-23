import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class FenetreApplication extends JFrame{
	
	private static final long serialVersionUID = 1L;

	//boutons d'execution
    JButton boutonActionLivres;
    JButton boutonActionCartes;
    JButton boutonDossierIn;
    JButton boutonDossierOut;
    
    //texte fichier généré
    JFileChooser fileChooserIn;
    JFileChooser fileChooserOut;
    
    //texte d'info
    JTextField texteDossierIn;
    JTextField texteDossierOut;
    
    //Dossiers choisis
    File dossierIn;
    File dossierOut;
    
    String lblChoixIn = "Choix dossier source";
    String lblChoixOut = "Choix dossier destination";
    
    private Dimension tailleJTextFields = new Dimension(400, 25);
    
	public FenetreApplication(){
	    Toolkit k = Toolkit.getDefaultToolkit();
	    
	    //calcul de la taille de la fenetre
	    Dimension tailleEcran = k.getScreenSize();
	    int largeurEcran = tailleEcran.width;
	    int hauteurEcran = tailleEcran.height;

	    //apparence generale
	    setTitle("Utilitaire de traitement des images scannées");
	    setLocation(largeurEcran*3/8, hauteurEcran*3/8);
	    setResizable(false);
	    
	    //disposition generale
	    JPanel panel = new JPanel();
	    panel.setLayout(new GridBagLayout());
	    GridBagConstraints c = new GridBagConstraints();
		
	    //dossier source
	    boutonDossierIn = new JButton(lblChoixIn);
	    fillGridbag(c, 0, 0, 1, 1);
	    panel.add(boutonDossierIn, c);
	    
	    texteDossierIn = new JTextField();
	    texteDossierIn.setEditable(false);
	    texteDossierIn.setPreferredSize(tailleJTextFields);
	    texteDossierIn.setMinimumSize(tailleJTextFields);
	    texteDossierIn.setMaximumSize(tailleJTextFields);
	    fillGridbag(c, 1, 0, 2, 1);
	    panel.add(texteDossierIn, c);
	    
	    //dossier destination
	    boutonDossierOut = new JButton(lblChoixOut);
	    fillGridbag(c, 0, 1, 1, 1);
	    panel.add(boutonDossierOut, c);
	    
	    texteDossierOut = new JTextField();
	    texteDossierOut.setEditable(false);
	    texteDossierOut.setPreferredSize(tailleJTextFields);
	    texteDossierOut.setMinimumSize(tailleJTextFields);
	    texteDossierOut.setMaximumSize(tailleJTextFields);
	    fillGridbag(c, 1, 1, 2, 1);
	    panel.add(texteDossierOut, c);
	    
	    //exécutions
	    boutonActionLivres = new JButton("Traitement type livre");
	    fillGridbag(c, 0, 2, 1, 1);
		panel.add(boutonActionLivres, c);
		
		boutonActionCartes = new JButton("Traitement type cartes");
	    fillGridbag(c, 1, 2, 1, 1);
		panel.add(boutonActionCartes, c);
		
		//on lie les actions
		boutonDossierIn.addActionListener(new SelectionneurDossier(this));
		boutonDossierOut.addActionListener(new SelectionneurDossier(this));
		boutonActionLivres.addActionListener(new Generateur(this));
		boutonActionCartes.addActionListener(new Generateur(this));
		
	    //on ajoute tout sur la page
		panel.setPreferredSize(new Dimension(620, 170));
		panel.setMinimumSize(new Dimension(620, 170));
		panel.setMaximumSize(new Dimension(620, 170));
	    this.getContentPane().add(panel);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void fillGridbag(GridBagConstraints c, int x, int y, int w, int h){
		c.fill = GridBagConstraints.HORIZONTAL;
	    c.gridx = x;
		c.gridy = y;
		c.gridwidth = w;
		c.gridheight = h;
	}
	public JButton getBoutonDossierIn() {
		return boutonDossierIn;
	}

	public void setBoutonDossierIn(JButton boutonDossierIn) {
		this.boutonDossierIn = boutonDossierIn;
	}

	public JButton getBoutonDossierOut() {
		return boutonDossierOut;
	}

	public void setBoutonDossierOut(JButton boutonDossierOut) {
		this.boutonDossierOut = boutonDossierOut;
	}

	public JFileChooser getFileChooserIn() {
		return fileChooserIn;
	}

	public void setFileChooserIn(JFileChooser fileChooserIn) {
		this.fileChooserIn = fileChooserIn;
	}

	public JFileChooser getFileChooserOut() {
		return fileChooserOut;
	}

	public void setFileChooserOut(JFileChooser fileChooserOut) {
		this.fileChooserOut = fileChooserOut;
	}

	public JTextField getTexteDossierIn() {
		return texteDossierIn;
	}

	public void setTexteDossierIn(JTextField texteDossierIn) {
		this.texteDossierIn = texteDossierIn;
	}

	public JTextField getTexteDossierOut() {
		return texteDossierOut;
	}

	public void setTexteDossierOut(JTextField texteDossierOut) {
		this.texteDossierOut = texteDossierOut;
	}

	public File getDossierIn() {
		return dossierIn;
	}

	public void setDossierIn(File dossierIn) {
		this.dossierIn = dossierIn;
	}

	public File getDossierOut() {
		return dossierOut;
	}

	public void setDossierOut(File dossierOut) {
		this.dossierOut = dossierOut;
	}

	public String getLblChoixIn() {
		return lblChoixIn;
	}

	public void setLblChoixIn(String lblChoixIn) {
		this.lblChoixIn = lblChoixIn;
	}

	public String getLblChoixOut() {
		return lblChoixOut;
	}

	public void setLblChoixOut(String lblChoixOut) {
		this.lblChoixOut = lblChoixOut;
	}

	public JButton getBoutonActionLivres() {
		return boutonActionLivres;
	}

	public void setBoutonActionLivres(JButton boutonActionLivres) {
		this.boutonActionLivres = boutonActionLivres;
	}

	public JButton getBoutonActionCartes() {
		return boutonActionCartes;
	}

	public void setBoutonActionCartes(JButton boutonActionCartes) {
		this.boutonActionCartes = boutonActionCartes;
	}
}