package com.vanlang.webbanquanao.Repository;

import com.vanlang.webbanquanao.Model.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckoutItemRepository extends JpaRepository<Checkout, Long>
{
}
