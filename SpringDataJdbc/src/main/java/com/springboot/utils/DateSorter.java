package com.springboot.utils;

import com.springboot.entity.TransactionEntity;

import java.util.Comparator;



    public class DateSorter implements Comparator<TransactionEntity>
    {
        @Override
        public int compare(TransactionEntity e1, TransactionEntity e2)
        {
            return e2.getTransaction_date().compareTo(e1.getTransaction_date());
        }
    }

