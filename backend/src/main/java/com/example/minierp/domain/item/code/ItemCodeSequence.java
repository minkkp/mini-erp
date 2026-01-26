package com.example.minierp.domain.item.code;

import com.example.minierp.domain.item.ItemType;
import jakarta.persistence.*;

@Entity
@Table(name = "item_code_sequence")
public class ItemCodeSequence {

    @Id
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(nullable = false)
    private int nextSeq;

    @Version
    private Long version;

    protected ItemCodeSequence() {}

    public int issue() {
        return nextSeq++;
    }
}
