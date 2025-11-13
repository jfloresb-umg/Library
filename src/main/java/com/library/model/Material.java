package com.library.model;

import java.time.LocalDate;

/**
 *
 * @author josef
 */
public abstract class Material {

    protected int materialId;
    protected String title;
    protected String author;
    protected String materialType;
    protected int totalQuantity;
    protected int availableQuantity;
    protected LocalDate publicationDate;

    public Material() {
    }

    public Material(String title, String author, int totalQuantity) {
        this.title = title;
        this.author = author;
        this.totalQuantity = totalQuantity;
    }
    
    public Material(int materialId, String title, String author, String materialType, int totalQuantity, int availableQuantity, LocalDate publicationDate) {
        this.materialId = materialId;
        this.title = title;
        this.author = author;
        this.materialType = materialType;
        this.totalQuantity = totalQuantity;
        this.availableQuantity = availableQuantity;
        this.publicationDate = publicationDate;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMaterialType() {
        return materialType;
    }

    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    // Método abstracto: cada tipo puede tener reglas distintas
    public abstract int getMaxLoanDays();

    @Override
    public String toString() {
        return "[" + materialType.toUpperCase() + "] " + title + " - " + author
                + " (" + availableQuantity + "/" + totalQuantity + " disponibles)";
    }
}
