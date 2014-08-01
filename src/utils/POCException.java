/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Albertus Alvin
 */
public class POCException extends Exception
{
    private String title = new String();
    
    public POCException(String message, String title)
    {
        super(message);
        this.title = title;
    }
    
    public String getTitle()
    {
        return this.title;
    }
}
