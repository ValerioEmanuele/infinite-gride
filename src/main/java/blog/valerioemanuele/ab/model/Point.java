package blog.valerioemanuele.ab.model;

import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/**
 * Represent a point in a 2D space
 * @author Valerio Emanuele
 *
 */

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "executionId"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames = {"x" , "y", "executionId"})})
public class Point {
	@Id 
	@GeneratedValue(strategy=GenerationType.AUTO)
	private BigInteger id;
	private BigInteger x;
	private BigInteger y;
	private String executionId;
	
	
	/**
	 * Instatiate a new Point with the passed coordinates
	 * @param x - the x coordinate
	 * @param y - the y coordinate
 	 * @return an instance of Point
	 */
	public static Point of(@NonNull BigInteger x, @NonNull BigInteger y) {
		return Point.builder().x(x).y(y).build();
	}

	public static Point of(@NonNull Point p) {
		return of(p.getX(), p.getY());
	}
	
	/**
	 * A point is set if both x and y property are not null
	 * @return true if both x and y are different than null, false otherwise
	 */
	public boolean isSet() {
		return x != null && y != null;
	}
}
