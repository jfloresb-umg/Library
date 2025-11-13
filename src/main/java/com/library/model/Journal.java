package com.library.model;

import java.time.LocalDate;

/**
 *
 * @author josef
 */
public class Journal extends Material {

    public Journal(int materialId, String title, String author, int totalQuantity,
            int availableQuantity, LocalDate publicationDate) {
        super(materialId, title, author, "journal", totalQuantity, availableQuantity, publicationDate);
    }

    @Override
    public int getMaxLoanDays() {
        return 5; // Las revistas solo 5 días
    }
}
