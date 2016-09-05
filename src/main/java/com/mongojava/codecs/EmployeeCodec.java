/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongojava.codecs;

import com.mongojava.model.Contact;
import com.mongojava.model.Employee;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonType;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;

/**
 *
 * @author Khurram.Shahzad
 */
public class EmployeeCodec implements CollectibleCodec<Employee> {

    private CodecRegistry codecRegistry;

    public EmployeeCodec() {

    }

    public EmployeeCodec(CodecRegistry codecRegistry) {
        this.codecRegistry = codecRegistry;
    }

    @Override
    public void encode(BsonWriter writer, Employee employee, EncoderContext ec) {

        writer.writeStartDocument();
        writer.writeObjectId("_id", new ObjectId(employee.getId()));
        writer.writeString("name", employee.getName());
        writer.writeInt32("age", employee.getAge());
        writer.writeInt32("salary", employee.getSalary());
        writer.writeString("designation", employee.getDesignation());

        if (employee.getContact() != null) {

            writer.writeName("contact");
            ec.encodeWithChildContext(codecRegistry.get(Contact.class), writer, employee.getContact());

        }

        writer.writeEndDocument();

    }

    @Override
    public Class<Employee> getEncoderClass() {
        return Employee.class;
    }

    @Override
    public Employee decode(BsonReader reader, DecoderContext dc) {

        Employee employee = new Employee();

        reader.readStartDocument();

        reader.readName();
        employee.setId(reader.readObjectId().toString());

        reader.readName();
        employee.setName(reader.readString());

        reader.readName();
        employee.setAge(reader.readInt32());

        reader.readName();
        employee.setSalary(reader.readInt32());

        reader.readName();
        employee.setDesignation(reader.readString());

        Contact contact = null;
        Codec<Contact> codec = this.codecRegistry.get(Contact.class);

        if (reader.readBsonType() != BsonType.END_OF_DOCUMENT) {

            reader.readName();
            contact = codec.decode(reader, dc);

        }
        employee.setContact(contact);
        reader.readEndDocument();

        return employee;
    }

    @Override
    public Employee generateIdIfAbsentFromDocument(Employee employee) {
        if (!documentHasId(employee)) {
            employee.setId(new ObjectId().toString());
        }
        return employee;
    }

    @Override
    public boolean documentHasId(Employee employee) {
        return employee.getId() != null;
    }

    @Override
    public BsonValue getDocumentId(Employee employee) {
        if (!documentHasId(employee)) {
            throw new IllegalStateException("The document does not contain an _id");
        }

        return new BsonString(employee.getId());
    }

}
