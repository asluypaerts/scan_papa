import javax.swing.JFrame;


public class TraitementLivres extends JFrame {

	/** */
	private static final long serialVersionUID = 1L;
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//creation et affichage de la fenetre
		//on specifie l'interface graphique
		FenetreApplication fenetre = new FenetreApplication();

		//on ouvre la fenetre
		fenetre.pack();
	    fenetre.setVisible(true); 
	}
}
