/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bc9959;


import java.util.ArrayList;
import java.util.ListIterator;
import java.util.StringTokenizer;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import static bc9959.Book.findBookByTitle;

/**
 *
 * @author Navneet
 */
//@Named(value = "bBean")
//@Dependent
@ManagedBean
@SessionScoped

public class BBean {

    /**
     * Creates a new instance of BBean
     */
  @PersistenceContext EntityManager myEntityManager;
  @Resource private UserTransaction utx; 
  private String titlast;
  private String first;
  private String select;
  private String message = "";
  
  public void setTitlast(String s) {this.titlast=s;}
  public String getTitlast() {return titlast;}
  public void setFirst(String s) {this.first=s;}
  public String getFirst() {return first;}
  public void setSelect(String s) {this.select=s;}
  public String getSelect() {return select;}
  public void setMessage(String message){this.message = message;}
  public String getMessage(){return message;}
  
  public String displayMessage(){
      return message;
  } 

  public BBean(){}
  

  
@SuppressWarnings("null")
  public void processQuery() 
{   
    Book bk=null;
    StringBuffer sb = null;
    try 
	{
            utx.begin();
      		if (select.equals("title")) 
			{
                        sb = new StringBuffer();    
       			bk = Book.findBookByTitle(myEntityManager,titlast);
			if(bk != null) 
				{
          			sb.append("<table border='1'><tr><th>ID</th><th>TITLE</th><th>LAST NAME</th><th>FIRST NAME</th><th>PRICE</th></tr><tr><td>"+bk.getBookID()+"</td><td>"+bk.getTitle()+"</td><td>"+bk.getLastName()+"</td><td>"+bk.getFirstName()+"</td><td>"+bk.getPrice()+"</td></tr></table>");
        		}
				else
 				{
          			sb.append("<h3>no such book</h3>");
        		}
      		}
 			else if(select.equals("author"))
 			{
                            sb = new StringBuffer();
         		ArrayList<Book> books = Book.findBooksByName(myEntityManager,first,titlast);     		
				if(books != null)
			 	{
           			sb.append("<table border='1'><tr><th>ID</th><th>TITLE</th><th>LAST NAME</th><th>FIRST NAME<th>PRICE</th></th></tr>");
           			ListIterator<Book> li = books.listIterator();
           			while (li.hasNext())
 					{
             			bk = (Book)li.next();
             			sb.append("<tr><td>"+bk.getBookID()+
               				"</td><td>"+bk.getTitle()+
               				"</td><td>"+bk.getLastName()+
               				"</td><td>"+bk.getFirstName()+
			   				"</td><td>"+bk.getPrice()+
               				"</td></tr>");
           			}
           			sb.append("</table>");
         		}
                                else 
				{
           			sb.append("<h3>no such book</h3>");
           		} 
       		}
			else
			{
                                        sb = new StringBuffer();
					ArrayList<Book> books = Book.findAllBooks(myEntityManager);
	           			sb.append("<table border='1'><tr><th>ID</th><th>TITLE</th><th>LAST NAME</th><th>FIRST NAME<th>PRICE</th></th></tr>");
	           			ListIterator<Book> li = books.listIterator();
	           			while (li.hasNext())
	 					{
	             			bk = (Book)li.next();
	             			sb.append("<tr><td>"+bk.getBookID()+
	               				"</td><td>"+bk.getTitle()+
	               				"</td><td>"+bk.getLastName()+
	               				"</td><td>"+bk.getFirstName()+
				   	        "</td><td>"+bk.getPrice()+
	               				"</td></tr>");
	           			}
	           			sb.append("</table>");	
			}
               myEntityManager.flush();
      utx.commit();
     } 
	catch (Exception ex)
 	{
       sb.append(ex.toString());
    }
    message = sb.toString();
  }
  
  public void processDelete() 
{
    Book bk = null;
    StringBuffer sb = new StringBuffer();
    try 
	{
            utx.begin();                
      		if (select.equals("title")){
               bk = findBookByTitle(myEntityManager,titlast);
               if(bk != null){
                myEntityManager.remove(bk);
                   sb.append("<h3> Deleted</h3>");  
               }
                }
                else if (select.equals("author")){
                ArrayList<Book> books = Book.findBooksByName(myEntityManager,first,titlast);
                ListIterator<Book> li = books.listIterator();
                    while (li.hasNext())
 		{
             	 bk = (Book)li.next();
                 myEntityManager.remove(bk); 
                 sb.append("<h3> Deleted</h3>");
                }
                }
                else
                {
                    sb.append("<h3>No such book</h3>");
                }                                      
               myEntityManager.flush();
      utx.commit();
}catch (Exception e) {}
message= sb.toString();
}
  
}


