/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.javaee8.boundary;

import com.packtpub.javaee8.domain.Book;
import com.packtpub.javaee8.domain.Bookshelf;
import java.net.URI;
import java.util.Objects;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

@Path("books")
@RequestScoped
public class BookResource {
    @Inject
    private Bookshelf bookshelf;
    
    @Context
    private ResourceContext context;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response books(){
        return Response.ok(bookshelf.findAll()).build();
        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{isbn}")
    public Response get(@PathParam("isbn") String isbn){
        Book book=bookshelf.findByISBN(isbn);
        return Response.ok(book).build();
        
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Book book){
        boolean bookExists=false;
        //bookExists=bookshelf.exists(book.getIsbn());
        
       if (bookExists){
           return Response.status(Response.Status.CONFLICT).build();
       }
       bookshelf.create(book);
       
       URI location= UriBuilder.fromResource(BookResource.class)
               .path("/{isbn}")
               .resolveTemplate("isbn", book.getIsbn())
               .build();
       return Response.created(location).build();
       
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{isbn}")
    public Response update(@PathParam("isbn") String isbn, Book book){
        
        if (!Objects.equals(isbn, book.getIsbn())){
            //return Response.status(Response.Status.BAD_REQUEST).build();
            //throw new WebApplicationException("ISBN must match path parameter.", Response.Status.BAD_REQUEST);
            throw new BadRequestException("ISBN must match path parameter.");
        }
        
        bookshelf.update(isbn, book);
        return Response.ok().build();
    }
    
    @DELETE
    @Path("/{isbn}")
    public Response delete(@PathParam("isbn") String isbn){
        bookshelf.delete(isbn);
        return Response.ok().build();
    }
    
    
    @Path("/{isbn}/author")
    public AuthorResource author(@PathParam("isbn") String isbn){
        
        Book book= bookshelf.findByISBN(isbn);
        
        return new AuthorResource(book);
    }
    
    @Path("/{isbn}/loans")
    public LoanResource loans(@PathParam("isbn") String isbn){
        LoanResource loanResource=context.getResource(LoanResource.class);
        loanResource.setIsbn(isbn);
        
        return loanResource;
    }

}
