package com.example.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//import com.google.api.gax.paging.Page;
//import com.google.cloud.storage.Bucket;
//import com.google.cloud.storage.Storage;
//import com.google.cloud.storage.StorageOptions;

@SpringBootApplication
@ComponentScan(basePackages = { "com.example.tasks", "com.example.users", "com.example.config" , "com.example.service" })

// Below 2 annotations are for to enable @Autowired annotation in different packages
@EnableJpaRepositories(basePackages = { "com.example.tasks", "com.example.users" , "com.example.config", "com.example.service"})
@EntityScan(basePackages = { "com.example.tasks", "com.example.users", "com.example.config", "com.example.service" })
public class TaskManagerApplication {
	
	
		  
//		  static void authImplicit() {
//		    // If you don't specify credentials when constructing the client, the client library will
//		    // look for credentials via the environment variable GOOGLE_APPLICATION_CREDENTIALS.
//		    org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties.Storage storage = StorageOptions.getDefaultInstance().getService();
//
//		    System.out.println("Buckets:");
//		    org.springframework.data.domain.Page<org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties.Bucket> buckets = storage.list();
//		    for (Bucket bucket : buckets.iterateAll()) {
//		    
//		    
//		      System.out.println(bucket.toString());
//		      
//		    }
//		  }
		  

	public static void main(String[] args) {
		
		//authImplicit();
		SpringApplication.run(TaskManagerApplication.class, args);
	}

}
