	// System.out.println("In main");
	// 	Server server =   new Server();
	// 	print("\n\n\n\nCreating Account\n\n\n\n");
	// 	for (int i = 0;i < 100 ;i++ ) {
	// 		print(server.CreateAcount());
	// 	}
	// 	print("\n\n\n\nDepositing Money\n\n\n\n\n");
	// 	int sum  = 0;
	// 	for (int i = 0;i < 100 ;i++ ) {
	// 		print(server.Deposit(i,100));
	// 	}

	// 	for (int i = 0;i < 100 ;i++ ) {
	// 		sum+=(server.Getbalance(i));
	// 	}
	// 	print("\n\n\n\nSumming Deposits\n\n\n\n");
	// 	print (sum);
	// 	int numberofThreads = 1;
	// 	int numberofIterations = 0;
	// 	ArrayList<Thread> threadsList = new ArrayList<Thread>();
	// 	print("\n\n\n\nStarting Thread\n\n\n\n");
	// 	for (int i = 0;i <numberofThreads ;i++ ) {
	// 		Thread thread = new Thread(new Runnable() {

	// 		    @Override
	// 		    public void run() {
	// 		    	for (int j = 0;j<numberofIterations ;j++ ) {
	// 			        Random rand = new Random(); 
	// 			        int  sourceAccount =(int) rand.nextInt(99);
	// 			        int depositAccount = (int)rand.nextInt(99);
	// 			        // sourceAccount = j;
	// 			        print("sourceAccount");
	// 			        print(sourceAccount);
	// 			        print("depositAccount");
	// 			        print(depositAccount);
	// 			        print(server.Transfer(sourceAccount, depositAccount, 10));


	// 			    }

	// 		    }
			            
	// 		});
			        
	// 		thread.start();
	// 		threadsList.add(thread);
	// 	}

	// 	for (Thread thread : threadsList ) {
	// 		try{
	// 		thread.join();

	// 		}catch (Exception e) {
	// 			System.out.println(e);
	// 		}
	// 	}

	// 	print("All Done");
	// 	sum =0;
	// 	for (int i = 0;i < 100 ;i++ ) {
	// 		sum+=(server.Getbalance(i));
	// 	}
	// 	print("\n\n\n\nFinal Sum\n\n\n\n\n");
	// 	print (sum);