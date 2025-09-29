package com.paiva.eurotreino.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a user in the EuroTreino system.
 * 
 * Each user has a name, an email, and a list of {@link Macro} training cycles.
 * These macrocycles represent the highest level of training periodization for the user.
 * 
 * <p><strong>Example:</strong>  
 * A user might have 2 macrocycles:
 * <ul>
 *   <li>Macro 1: January to June</li>
 *   <li>Macro 2: July to December</li>
 * </ul>
 * </p>
 * 
 * This class is mapped to the table named "users".
 */
@Entity (name = "users")
@Getter @Setter
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    /**
     * The full name of the user.
     */
	private String name;
	
    /**
     * The email address of the user.
     */
	private String email;
	
    /**
     * List of macrocycles associated with this user.
     * 
     * This is a one-to-many bidirectional relationship.
     * Each Macro must have a 'user' field referencing this entity.
     */
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Macro> macroCycles;
	
    /**
     * Constructs a user with name, email, and list of macrocycles.
     *
     * @param name the user's full name
     * @param email the user's email address
     * @param macroCycles the list of macrocycles owned by the user
     */
	public User(String name, String email, List<Macro> macroCycles) {
		this.name = name;
		this.email = email;
		this.macroCycles = macroCycles;
	}

    public void addMacro(Macro macro){
        this.macroCycles.add(macro);
        macro.setUser(this);
    }

}
