const i32 SUCCESS = 1
const i32 FAIL = -1
	
service BankService {
	
	i32 CreateAccount(),
	i32 Deposit(1:i32 uid, 2:i32 amount),
	i32 GetBalance(1:i32 uid),
	i32 Transfer(1:i32 source, 2:i32 destination, 3:i32 amount)
}