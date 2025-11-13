package com.library.model;

import java.time.LocalDate;

/**
 *
 * @author josef
 */
public class Book extends Material {

    public Book(String title, String author, int totalQuantity) {
        super(title, author, totalQuantity);
    }

    public Book(int materialId, String title, String author, int totalQuantity, int availableQuantity, LocalDate publicationDate) {
        super(materialId, title, author, "book", totalQuantity, availableQuantity, publicationDate);
    }
    
    

    @Override
    public int getMaxLoanDays() {
        return 14; // Los libros se prestan por 14 días
    }
}
