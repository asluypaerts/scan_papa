import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;


public class Generateur implements ActionListener { 

	/**proprietes */
	FenetreApplication fenetre;
	
	private static final String UNDERSCORE = "_";
	private static final String EXTENSION = ".jpg";
	private static final String RETOUR = "\n";
	
	/**le format d'un fichier en entree est {nombre}_{nombre).jpg*/
	Pattern patternCarte = Pattern.compile("(\\d{4})_(\\d{3}).jpg");
			
	/**le format d'un livre en entree est {nombre}{ }{chiffre sans leading zero ou rien).jpeg*/
	Pattern patternLivre = Pattern.compile("(\\d{4})(\\p{Blank})??(\\d)*.jpeg");
	
	/**fichier de log*/
	BufferedWriter log = null;
	
	Generateur(FenetreApplication pFenetre){
		this.fenetre = pFenetre;
	}
	
	public void actionPerformed(ActionEvent contexte) {
		try{
			//gestion des logs
			File fichier = new File("fichierLog.txt");
			fichier.createNewFile();
			
			//buffer pour le contenu du fichier
			log = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichier)));
			
			if (!fenetre.getDossierIn().isDirectory()){
				traitementErreurTechnique(fenetre.getTexteDossierIn(), null, "La sélection n'est pas un dossier.");
				return;
			}
			if (!fenetre.getDossierOut().isDirectory()){
				traitementErreurTechnique(null, fenetre.getTexteDossierOut(), "La sélection n'est pas un dossier.");
				return;
			}
			
			log.append("Dossier IN : "+fenetre.getDossierIn()+RETOUR);
			log.append("Dossier OUT : "+fenetre.getDossierOut()+RETOUR);
			
			JButton source = (JButton)contexte.getSource();
			traitementDossier(fenetre.getDossierIn(), fenetre.getDossierOut(), source.equals(fenetre.getBoutonActionCartes()));
		}catch (IOException e) {
			traitementErreurTechnique(fenetre.getTexteDossierIn(), fenetre.getTexteDossierOut(), "Erreur d'écriture du fichier de log"+e);
		}finally{
			try{
				log.close();
			}catch (IOException e) {
				traitementErreurTechnique(fenetre.getTexteDossierIn(), fenetre.getTexteDossierOut(), "Impossible de fermer le fichier de log"+e);
			}
		}
	}
	
	private void traitementErreurTechnique(JTextField fieldIn, JTextField fieldOut, String message){
		if (fieldIn != null){
			fieldIn.setText(message);
		}
		if (fieldOut != null){
			fieldOut.setText(message);
		}
		if (log != null){
			try{
				log.append(message+RETOUR);
			}catch (IOException e1) {
				fieldIn.setText("Impossible d'écrire dans le fichier de log");
				fieldOut.setText("Impossible d'écrire dans le fichier de log");
			}
		}
	}
	
	private void traitementDossier(File fileIn, File fileOut, Boolean traitementCarte) throws IOException{
		if (traitementCarte){
			//traitement type cartes
			traitementCartes(fileIn, fileOut);
			fenetre.getBoutonActionCartes().setText("Done");
		}else{
			//traitement type livres
			traitementLivres(fileIn, fileOut);
			fenetre.getBoutonActionLivres().setText("Done");
		}
	}
	
	private void traitementCartes(File fileIn, File fileOut) throws IOException{
		log.append("traitement type cartes"+RETOUR);
		
		//on parcourt tous les fichiers du dossier donné
		File[] listeFichiers = fileIn.listFiles();
		Arrays.sort(listeFichiers, new Comparator<File>(){
		    public int compare(File f1, File f2)
		    {
		    	String nom1 = f1.getName();
		    	String nom2 = f2.getName();
		    	
		    	if (nom1.length() != nom2.length()){
		    		return nom1.length()-nom2.length();
		    	}
		    	
		    	return nom1.compareTo(nom2);
		    } });
		
		int compteurImage = -1;
		int compteurSubImage = 1;
		for(File f : listeFichiers){
			Matcher m = patternCarte.matcher(f.getName());
			log.append("Fichier initial : "+f.getName()+RETOUR);
			if (m.matches()){
				if (compteurImage == -1){
					//initialisation du premier numero d'image
					String numImage = m.group(1);
					compteurImage = Integer.parseInt(numImage);
				}else{
					compteurImage++;
				}
				
				//on ajoute les zeros si besoin
				String numImageFormatted = String.format("%04d", compteurImage);
				String numSubImageFormatted = String.format("%03d", compteurSubImage);
				
				//le format de sortie est nombre avec leading zero sur 4 caractères)_001.jpeg
				String nomFichier = numImageFormatted+UNDERSCORE+numSubImageFormatted+EXTENSION;
				String nomSortie = fenetre.getDossierOut().getCanonicalPath()+File.separator+nomFichier;
				log.append("Copié vers : "+nomSortie+RETOUR);
				
				File destination = new File(nomSortie);
				FileUtils.copyFile(f, destination, false);
			}else{
				log.append("NON copié."+RETOUR);
			}
		}
	}
	
	private void traitementLivres(File fileIn, File fileOut) throws IOException{
		log.append("traitement type livres"+RETOUR);
		
		//on parcourt tous les fichiers du dossier donné
		for(File f : fileIn.listFiles()){
			Matcher m = patternLivre.matcher(f.getName());
			log.append("Fichier initial : "+f.getName()+RETOUR);
			if (m.matches()){
				String numImage = m.group(1);
				String numSubImage = m.group(3);
				
				//cas de la premiere image
				if (numSubImage == null){
					numSubImage = "0";
				}
				
				//on ajoute les zeros si besoin
				int numSubImageInt = Integer.parseInt(numSubImage)+1;
				String numSubImageFormatted = String.format("%03d", numSubImageInt);
				
				//le format de sortie est {nombre}_{chiffre avec leading zero sur 3 caractères).jpeg
				String nomFichier = numImage+UNDERSCORE+numSubImageFormatted+EXTENSION;
				String nomSortie = fenetre.getDossierOut().getAbsolutePath()+"\\"+nomFichier;
				log.append("Copié vers : "+nomSortie+RETOUR);
				
				File destination = new File(nomSortie);
				FileUtils.copyFile(f, destination, false);
			}else{
				log.append("NON copié."+RETOUR);
			}
		}
	}
}
