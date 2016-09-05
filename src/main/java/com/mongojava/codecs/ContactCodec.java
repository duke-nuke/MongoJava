
package com.mongojava.codecs;

import com.mongojava.model.Contact;
import org.bson.BsonReader;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

/**
 *
 * @author Khurram.Shahzad
 */
public class ContactCodec implements CollectibleCodec<Contact> {

    public ContactCodec() {

    }

    @Override
    public Contact generateIdIfAbsentFromDocument(Contact contact) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean documentHasId(Contact contact) {
        return false;
    }

    @Override
    public BsonValue getDocumentId(Contact contact) {
        return null;
    }

    @Override
    public void encode(BsonWriter writer, Contact contact, EncoderContext ec) {

        writer.writeStartDocument();

        writer.writeString("email", contact.getEmail());
        writer.writeString("cell", contact.getCell());

        writer.writeEndDocument();

    }

    @Override
    public Class<Contact> getEncoderClass() {
        return Contact.class;
    }

    @Override
    public Contact decode(BsonReader reader, DecoderContext dc) {

        Contact contact = new Contact();
        reader.readStartDocument();
        reader.readName();
        contact.setEmail(reader.readString());
        reader.readName();
        contact.setCell(reader.readString());

        reader.readEndDocument();
        return contact;
    }

}
