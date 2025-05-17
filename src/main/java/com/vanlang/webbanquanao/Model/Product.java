package com.vanlang.webbanquanao.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "image", length = 10000000)
    private String image;
    private String name;
    private int price;
    private int quantity;
    @Lob
    private String description;
    private int sold;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category_id;

}
