/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.packtpub.javaee8.boundary;

import com.packtpub.javaee8.domain.Book;
import com.packtpub.javaee8.domain.Bookshelf;
import com.packtpub.javaee8.domain.Library;
import com.packtpub.javaee8.domain.Loan;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

/**
 *
 * @author VMC027
 */
@RequestScoped
public class LoanResource {
    @Inject
    private Bookshelf bookshelf;
 
    @Inject
    private Library library;
    
    @Inject
    private Logger logger;
    
    private String isbn;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response loans(){
        
        logger.log(Level.INFO, "Getting loans for book with ISBN {0}.", this.isbn);
        Book book= bookshelf.findByISBN(this.isbn);
        return Response.ok(book.getLoans()).build();

    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{loanId}")
    public Response loan(@PathParam("loanId") String loanId){
        Loan loan=library.loanInfo(loanId);
        return Response.ok(loan).build();
    }
    
    
    @DELETE 
    @Path("/{loanId}")
    public Response returnBook(@PathParam("loanId")String loanId){
        library.returnBook(this.isbn, loanId);
        return Response.ok().build();
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response lendBook(Loan loan){
        
        library.lendBook(this.isbn,loan);
        
        URI location=UriBuilder.fromResource(BookResource.class)
                .path("/{isbn}/loans/{loanId}")
                .resolveTemplate("isbn", this.isbn)
                .resolveTemplate("loanId", loan.getId())
                .build();
        return Response.created(location).build();
    }
    
    
}
