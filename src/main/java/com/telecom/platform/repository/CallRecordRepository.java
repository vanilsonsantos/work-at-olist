package com.telecom.platform.repository;

import com.telecom.platform.repository.documents.CallRecordDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallRecordRepository extends MongoRepository<CallRecordDocument, String> { }
