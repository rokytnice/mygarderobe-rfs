package org.icefaces.training.applicant.view.model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 * Job Applicant model
 */
@ManagedBean(name="jobApplicant", eager=true)
@RequestScoped
public class JobApplicant implements Serializable{

	/**
     * 
     */
    private static final long serialVersionUID = 2983415893695028535L;
	private String firstName;
	private String lastName;
	
	@Override
    public String toString(){
        return "jobApplicant " + super.toString();
    }
	
    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}	
	
	public String save(){
	    System.out.print("pppppppppppppppppp");
	    return "garderobe.xhtml";
	}
}
