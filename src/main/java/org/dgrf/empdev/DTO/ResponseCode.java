/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.empdev.DTO;

/**
 *
 * @author dgrfiv
 */
public class ResponseCode {
    public static final int SUCCESS = 0;
    public static final int INVALID = 1;
    public static final int WRONG_CREDENTIALS = 2;
    public static final int INACTIVE = 3;
    public static final int LOGIN_ATTEMPTS_EXCEEDED = 4;
    public static final int ALREADY_EXISISTS = 5;
    public static final int ILLEGAL_ORPHAN = 6;
    public static final int EXCEPTION_CAUGHT = 99;
    public static final int CONTACT_ADMIN = 999;
  
}