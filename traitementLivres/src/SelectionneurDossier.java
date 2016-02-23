import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;


public class SelectionneurDossier implements ActionListener { 

	/**proprietes */
	FenetreApplication fenetre;
		
	SelectionneurDossier(FenetreApplication pFenetre){
		this.fenetre = pFenetre;
	}
	
	public void actionPerformed(ActionEvent contexte) {
		JButton source = (JButton)contexte.getSource();
		if (source.equals(fenetre.getBoutonDossierIn())){
			fenetre.setFileChooserIn(new JFileChooser());
			fenetre.getFileChooserIn().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fenetre.getFileChooserIn().setDialogTitle(fenetre.getLblChoixIn());
			fenetre.getFileChooserIn().setApproveButtonText("OK");
			if (fenetre.getFileChooserIn().showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				File f = fenetre.getFileChooserIn().getSelectedFile();
				fenetre.setDossierIn(f);
				fenetre.getTexteDossierIn().setText(f.getAbsolutePath());
			}
		}else{
			fenetre.setFileChooserOut(new JFileChooser());
			fenetre.getFileChooserOut().setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fenetre.getFileChooserOut().setDialogTitle(fenetre.getLblChoixOut());
			fenetre.getFileChooserOut().setApproveButtonText("OK");
			if (fenetre.getFileChooserOut().showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				File f = fenetre.getFileChooserOut().getSelectedFile();
				fenetre.setDossierOut(f);
				fenetre.getTexteDossierOut().setText(f.getAbsolutePath());
			}
		}
	}
}
