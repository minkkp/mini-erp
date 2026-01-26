package com.example.minierp.domain.item.code;

import com.example.minierp.domain.item.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCodeSequenceRepository extends JpaRepository<ItemCodeSequence, ItemType> {}