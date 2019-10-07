package com.springboot.repository;

import com.springboot.model.Customer;
import com.springboot.model.History;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.xml.ws.Holder;
import java.util.List;
import java.util.Set;

@Repository
public interface HistoryRepository extends ElasticsearchCrudRepository<History, Long> {
    Set<History> findByToPhoneNo(Long phoneNo);
    Set<History> findByFromPhoneNo(Long phoneNo);

}

