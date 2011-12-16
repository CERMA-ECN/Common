package fr.ecn.common.core.geometry;

/**
 * 
 * @author Claire Cervera
 * @author Julien Bardonnet
 */
public class Line {

	// =============================================================================//
	// Attributs:
	// on représente les droites non verticales par l'equation y=ax+b,
	// les attributs d'une droite seront donc les coefficients a et b.
	// =============================================================================//
	protected double a;
	protected double b;

	// =============================================================================//
	// Constructeurs
	// =============================================================================//
	/**
	 * Construction d'une droite a partir de deux points
	 * 
	 * @param P1
	 * @param P2
	 */
	public Line(Point P1, Point P2) {
		this.a = (P1.getY() - P2.getY()) / (P1.getX() - P2.getX());
		this.b = (P1.getY() - this.a * P1.getX());
	}

	/**
	 * Constructeur de base: droite horizontale passant par (0,0).
	 */
	public Line() {
		this.a = 0;
		this.b = 0;
	}

	/**
	 * Constructeur a partir des coefficients a et b :
	 */
	public Line(double a, double b) {
		this.a = a;
		this.b = b;
	}

	/**
	 * @return the slope of the line
	 */
	public double getA() {
		return a;
	}

	/**
	 * @return the the y-intercept of the line
	 */
	public double getB() {
		return b;
	}

	/**
	 * Calcul de y l'ordonné d'un point appartenant à la droite et d'abscisse x.
	 * Retourne le point obtenu
	 * 
	 * @param x
	 * @return le point
	 */
	public Point calculY(double x) {
		double y = this.a * x + this.b;
		return new Point(x, y);
	}

	/**
	 * Calcul de x l'abscisse d'un point appartenant à la droite et d'ordonnée
	 * y. Retourne le point obtenu
	 * 
	 * @param y
	 * @return le point
	 */

	public Point calculX(double y) {
		double x = 0;
		if (this.a != 0) {
			x = 1 / (this.a) * (y - this.b);
		}
		// Dans le cas ou a=0, on a une droite horizontale,
		// on peut alors avoir n'importe quelle valeur de x, on choisit
		// arbitrairement 0.
		return new Point(x, y);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Line [a=" + a + ", b=" + b + "]";
	}
}
