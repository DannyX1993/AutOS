package com.dannyx.autos.Models.Entities;

/**
 * Created by danielgonzalez on 15/2/17.
 */

public class Manufacturer {

    private int    _id;
    private String _name;
    private String _logo;
    private String _background;

    public Manufacturer(int id, String name, String logo, String background) {
        this._id   = id;
        this._name = name;
        this._logo = logo;
        this._background = background;
    }

    public int get_id() {
        return this._id;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public String get_name() {
        return this._name;
    }

    public void set_name(String name) {
        this._name = name;
    }

    public String get_logo() {
        return this._logo;
    }

    public void set_logo(String logo) {
        this._logo = logo;
    }

    public String get_background() {
        return this._background;
    }

    public void set_background(String background) {
        this._background = background;
    }

    @Override
    public String toString() {
        return "Manufacturer {" +
                "_id=" + this._id +
                ", _name='" + this._name + '\'' +
                ", _logo=" + this._logo + "'" +
                ", _background=" + this._background + "'" +
                '}';
    }

}
