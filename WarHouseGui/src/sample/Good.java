package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

class Good {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleIntegerProperty piece;
    private SimpleStringProperty country;

    public Good(int id, String name, int piece, String country) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.piece = new SimpleIntegerProperty(piece);
        this.country = new SimpleStringProperty(country);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public int getPiece() {
        return piece.get();
    }

    public void setPiece(int piece) {
        this.piece = new SimpleIntegerProperty(piece);
    }

    public String getCountry() {
        return country.get();
    }

    public void setCountry(String country) {
        this.country = new SimpleStringProperty(country);
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }
}
