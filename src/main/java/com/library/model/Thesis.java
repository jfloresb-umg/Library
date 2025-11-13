package com.library.model;

import java.time.LocalDate;

/**
 *
 * @author josef
 */
public class Thesis extends Material {

    public Thesis(int materialId, String title, String author, int totalQuantity,
            int availableQuantity, LocalDate publicationDate) {
        super(materialId, title, author, "thesis", totalQuantity, availableQuantity, publicationDate);
    }

    @Override
    public int getMaxLoanDays() {
        return 10; // Las tesis se prestan 10 días
    }
}
