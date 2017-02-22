import java.util.*;
import java.util.concurrent.locks.ReentrantLock ;

public class Account {
	private int balance;
	private int uid;
	ReentrantLock lock;

	Account(int _uid) {
		uid  = _uid;
		balance = 0;
		lock = new ReentrantLock();
	}

	int getBalance() {
		return balance;
	}

	int getUID() {
		return uid;
	}

	void setUID(int _uid) {
		uid = _uid;
	}

	void setBalance(int _balance) {
		balance = _balance;
	}

	boolean deposit(int _amount) {
		balance+=_amount;
		return true;
	}

	boolean withdraw (int _amount) {
		if (balance-_amount <0) {
			return false;
		}

		balance-=_amount;
		return true;
	}
}