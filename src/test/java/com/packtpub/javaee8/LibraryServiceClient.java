/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.javaee8;

import com.packtpub.javaee8.domain.Author;
import com.packtpub.javaee8.domain.Book;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.jsonb.JsonBindingFeature;

/**
 *
 * @author VMC027
 */
public class LibraryServiceClient {
    private static final Logger LOGGER=Logger.getAnonymousLogger();
    public static void main(String[] args) {
        Client client=ClientBuilder.newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .register(JsonBindingFeature.class)
                .build();
        
        WebTarget api=client.target("http://localhost:8080").path("/library-service/api");
        List<Book> books=api.path("/books").request().accept(MediaType.APPLICATION_JSON).get(bookList());
        books.forEach(book -> LOGGER.log(Level.INFO,"{0}",book));
        
        LOGGER.log(Level.INFO,"Get unknown book by ISBN");
        
        Response response=api.path("/books").path("/{isbn}").resolveTemplate("isbn", "123456").request().accept(MediaType.APPLICATION_JSON).get();
        assert response.getStatus()==404;
        
        Book book;
        book = new Book("3000001", "Building Web Services with Java EE 8", new Author("M.- Leander Reimer"));
        
        LOGGER.log(Level.INFO,"Creating new {0}." , book);
        response=api.path("/books").request(MediaType.APPLICATION_JSON).post(Entity.json(book));
        
        URI bookUri=response.getLocation();
        LOGGER.log(Level.INFO,"Get created book with URI {0}",bookUri);
        
        Book createdBook=client.target(bookUri).request().accept(MediaType.APPLICATION_JSON).get(Book.class);
        
        assert book.equals(createdBook);
        
        
        assert response.getStatus()==201;
        
        client.close();
    }
    
    
    private static GenericType<List<Book>> bookList(){
        return new GenericType<List<Book>>(){
        };
    }
    
}
