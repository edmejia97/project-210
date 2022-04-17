# The Satoshi Tracker

## A bitcoin transaction tracker


The purpose of this application is to provide users with the ability to *track* their  Bitcoin holdings in
one single location. Just like with banks, most Bitcoin users have multiple wallets where they securely keep their
holdings. Because of this, it is difficult for users to know how much Bitcoin they hold at any given moment. 

The satoshi tracker is an application where the user can input deposits and withdrawals so that they know how much 
bitcoin they hold at any given time, rather than adding the balance in all their wallets every single time. The Satoshi
Tracker is in **no way** connected to the bitcoin blockchain, it is simply an application to track the user's holdings. 

This project is of interest to me because I have personally experienced needing an application to track my holdings.
There are many similar currency and stock tracking applications but there are less in the crypto environment. 

This tracker will run using units of Satoshis, known globally as the fraction currency of Bitcoin. One bitcoin is worth 
10,000,000 Satoshis. Because, for now, satoshis are such a small unit, it will be represented as an integer for this 
application, although this may change in the decades to come. 

## User Stories

The following are the perceived user stories for this application :
- As a user, I want to be able to create an account
- As a user, I want to be able to check the balance of an account
- As a user, I want to add, or *deposit* to my holdings.
- As a user, I want to remove, or *withdraw* from my holdings.
- As a user, I want to be able to transfer satoshis between accounts
- As a user, I want to see a list of all the accounts and their balances

The account list meets the add an X to Y criteria as it adds a new account to an arbitrary list of 
accounts

The following are the data persistence user stories for this application:
- As a user, I want to be able to save all accounts to file
- As a user, I want to be given the option to load all my accounts 

## Phase 4: Task 2

Thu Mar 31 22:54:06 PDT 2022
Account TestAccount1 added

Thu Mar 31 22:54:11 PDT 2022
Account TestAccount2 added

Thu Mar 31 22:54:17 PDT 2022
Account TestAccount3 added

Thu Mar 31 22:54:20 PDT 2022
Account TestAccount2 removed

Thu Mar 31 22:54:28 PDT 2022
Account TestAccount4 added

Thu Mar 31 22:54:31 PDT 2022
Account TestAccount1 removed

Process finished with exit code 0

## Phase 4: Task 3

Drawing the UML allowed me to see how far this project has come since I began working on it in January.
The program went from a playground, barely knowing Java, (let alone OOP), to a working project with 
many different classes working together. 

If I had more time, I would go back and tidy up the mess that is my GUI class. Going back to make changes I 
realized it is not easy to read, and it is even harder to change the actual behaviour of the JFrame.

With more time I would also implement try catch to prevent users from accidentally 
crashing the application. 