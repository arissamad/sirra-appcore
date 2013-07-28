package com.sirra.appcore.transactions;

import com.sirra.appcore.accounts.*;
import com.sirra.appcore.util.*;

/**
 * Used to find the next transaction number for a single credit-card charge on the user's account.
 * 
 * @author aris
 */
public class TransactionNumber {
	
	public static String getNext(BaseAccount account) {
		int next = account.getNextTransactionNumber();
		account.setNextTransactionNumber(next+1);
		
		return "PMT-" + account.getId() + "-" + StringUtil.pad("" + next, '0', 4);
	}
}