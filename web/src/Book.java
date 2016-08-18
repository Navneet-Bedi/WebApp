/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bc9959;

import java.util.ArrayList;
import javax.persistence.*;

/**
 *
 * @author NAVNEET
 */

@Entity
@Table(name = "bc9959.Book")
public class Book implements java.io.Serializable
 {
   private int id;
   private String title;
   private String last_name;
   private String first_name;
   private String  price;

  	public Book() 
	{
		id=0; 
		title=null; last_name=null; first_name=null; price=null;
	}

  public Book(int id, String title, String last_name, String first_name, String price) {
    this.id=id;
    this.title=title;
    this.last_name=last_name;
    this.first_name=first_name;
    this.price=price;
  }

  @Id
  @Column(name="ID")
  public int getBookID() {
    return id;
  }

  public void setBookID(int id) {
        this.id = id;
    }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
        this.title = title;
    }

@Column(name="LAST_NAME")
  public String getLastName() {
    return last_name;
  }

  public void setLastName(String last_name) {
        this.last_name = last_name;
  }

@Column(name="FIRST_NAME")
  public String getFirstName() {
    return first_name;
  }

  public void setFirstName(String first_name) {
        this.first_name = first_name;
  }
  public String getPrice() {
	return price;
  }

  public void setPrice(String price) {
	    this.price = price;
  }

  public static Book findBookByTitle(EntityManager em, String title) {
	Book bk = null;	
   try {
        Query query = em.createQuery(
                "SELECT distinct b FROM bc9959.Book b WHERE b.title = :title");
        query.setParameter("title", title);
        bk = (Book)query.getSingleResult();
    }catch (Exception e) {}
        return bk;
  }
  

  public static ArrayList<Book> findBooksByName(EntityManager em, String first_name, String last_name) {
    ArrayList<Book> books = null;
    String sql =  "SELECT b FROM bc9959.Book b WHERE b.lastName='"+last_name+"' and b.firstName='"+first_name+"'";
    books =  new ArrayList<Book>(em.createQuery(sql).getResultList());
    return books;
  }

public static ArrayList<Book> findAllBooks(EntityManager em) {
    Query query =em.createQuery( "SELECT b FROM bc9959.Book b");
    ArrayList<Book>  books = new ArrayList<Book>(query.getResultList());
    return books;
  }

  
}

