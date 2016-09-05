package com.mongojava.sample;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.or;
import com.mongojava.codecproviders.ContactCodecProvider;
import com.mongojava.codecproviders.EmployeeCodecProvider;
import com.mongojava.model.Contact;
import com.mongojava.model.Employee;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;

/**
 *
 * @author Khurram.Shahzad
 */
public class RunProgram {

    private RunProgram() {

    }

    private RunProgram(int i) {

    }

    public static void main(String args[]) {

        ServerAddress server = new ServerAddress("localhost");
        List<MongoCredential> creds = new ArrayList<MongoCredential>();
        creds.add(MongoCredential.createCredential("administrator", "admin", "123".toCharArray()));

        
        CodecRegistry codecRegistry = CodecRegistries.fromRegistries(
                MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(new EmployeeCodecProvider(), new ContactCodecProvider())
                );

        MongoClientOptions options
                = MongoClientOptions.builder()
                .codecRegistry(codecRegistry)
                .build();

        MongoClient client = new MongoClient(server, creds, options);
        MongoDatabase db = client.getDatabase("hr");
        System.out.println("Connected to Database");

        //get the employees collection
        MongoCollection<Document> collection = db.getCollection("employees");
        if (collection == null) {
            System.out.println("Collection does not exit");
        }

        //create an employee
        Document employeeDoc = new Document("name", "Kamran Yousaf").append("age", 32).append("salary", 10000).append("designation", "Senior ERP analyst");
        collection.insertOne(employeeDoc);

        System.out.println("Document is inserted sucessfully");

        //create nested documents
        Document employeeComplexDoc = new Document("name", "Akmal Mughal")
                .append("age", 30)
                .append("salary", 11000)
                .append("designation", "Senior data analyst")
                .append("contact", new Document("email", "ak@msn.com").append("cell", "03234445748"));
        collection.insertOne(employeeComplexDoc);

        //create nested documents
        System.out.println("nested document is inserted sucessfully");

        //iterate all documents in collection
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                System.out.println(doc.toJson());
            }
        } finally {
            cursor.close();
        }

        //finding , filtering documents 
        //find employees whose name is Kamran
        MongoCursor<Document> cursor1 = collection.find(eq("name", "Kamran Yousaf")).iterator();
        try {
            while (cursor1.hasNext()) {
                Document doc = cursor1.next();
                System.out.println("Found document with name Kamran Yousaf=");
                System.out.println(doc.toJson());
            }
        } finally {
            cursor1.close();
        }

        //find employees whose age is between 28 and 32         
        MongoCursor<Document> cursor2 = collection.find(or(gt("age", "27"), lte("age", 34))).iterator();
        try {
            while (cursor2.hasNext()) {
                Document doc = cursor2.next();
                System.out.println("Found document with age between 28 and 32=");
                System.out.println(doc.toJson());
            }
        } finally {
            cursor2.close();
        }

         //working with objects directly ...
        // you can insert retrieve objects and can work in object oriented way.
        MongoCollection<Employee> employeeCollection = db.getCollection("employees", Employee.class);

        MongoCursor<Employee> cursor4 = employeeCollection.find().iterator();
        try {
            while (cursor4.hasNext()) {
                Employee emp = cursor4.next();
                System.out.println(emp.toString());
            }
        } finally {
            cursor4.close();
        }

        //inserting Emoployee Object directly this is done by writing codecs for beans.
        Employee emp = new Employee();
        emp.setName("Sajid Mehmood");
        emp.setAge(33);
        emp.setDesignation("Principal Software Engineer");
        emp.setSalary(250000);
        Contact contact = new Contact();
        contact.setEmail("sajid.mehmood@gmail.com");
        contact.setCell("03234447587");
        emp.setContact(contact);
        employeeCollection.insertOne(emp);
        
        Object obj = new String("Hello");
        String obj2 = new String("Hello");

        System.out.println(obj.equals(obj2));

         //finding employee object directly
        Employee employee = employeeCollection.find(eq("name", "Sajid Mehmood")).first();
        System.out.println("From db" + employee);

    }

}
