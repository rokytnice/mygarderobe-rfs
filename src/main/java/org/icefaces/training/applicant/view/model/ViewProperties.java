package org.icefaces.training.applicant.view.model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * View model
 */
@ManagedBean
@SessionScoped
public class ViewProperties implements Serializable{

    @Override
    public String toString(){
        return "viewProperties " + super.toString();
    }
}
