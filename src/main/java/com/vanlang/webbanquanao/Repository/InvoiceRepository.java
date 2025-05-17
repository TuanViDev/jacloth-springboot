package com.vanlang.webbanquanao.Repository;

import com.vanlang.webbanquanao.Model.Invoice;
import com.vanlang.webbanquanao.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>
{
    @Query(value = "SELECT * FROM invoices WHERE user_id=?",nativeQuery = true)
    public List<Invoice> findInvoicesByUserId(Long id);

    @Query(value = "SELECT SUM(total_amount) from invoices", nativeQuery = true)
    public int getAmountOfAllInvoices();
}
