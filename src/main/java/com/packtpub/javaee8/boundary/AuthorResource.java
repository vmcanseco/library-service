/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.javaee8.boundary;

import com.packtpub.javaee8.domain.Author;
import com.packtpub.javaee8.domain.Book;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author VMC027
 */

@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private final Book book;
    public AuthorResource(Book book) {
        
        this.book=book;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @GET
    public Author get(){
        return book.getAuthor();
    }
    
   //TODO Implemnt me
    
    
}
