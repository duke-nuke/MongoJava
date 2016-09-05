/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mongojava.codecproviders;

import com.mongojava.codecs.ContactCodec;
import com.mongojava.model.Contact;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

/**
 *
 * @author Khurram.Shahzad
 */
public class ContactCodecProvider implements CodecProvider {

    @Override
    public <T> Codec<T> get(Class<T> type, CodecRegistry cr) {
        
        if(type == Contact.class){
            
            return (Codec<T>) new ContactCodec();
        }
        
        return null;
    }
    
    
}
