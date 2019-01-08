/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privatemoviecollection.BLL.Exception;

/**
 *
 * @author kokus
 */
public class BLLException extends Exception
{

    public BLLException()
    {
        super();
    }

    public BLLException(String message)
    {
        super(message);
    }

    public BLLException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public BLLException(String message, Exception ex)
    {
        super(message, ex);
    }
    
}
