Welcome to Group 0147's Trading System.

Javadocs are available under phase1/javadoc/.
Accessing index.html will allow you to view the javadocs for the whole program.

The UML is available in the UML folder.

Should there be no .ser files under phase1/H_ser_file_infos, running class Main.java under phase1/src/
will generate new .ser files. Should an input exception occur while reading these .ser files,
deleting them and running Main.java once more will generate fresh and (hopefully) error free .ser files.

Upon loading in, you will see 3 menu options, an option to login, another to create a new user account,
and a final one to login under an administrator account. There are no users in the system at the moment;
you will have to create a user account to access user functionality (your chosen username must be unique).

An admin account already exists in the system (Username: admin, password: admin).
Login under this account to access admin functionality.

                                            ----------------------------
User Account:
Upon logging in as a USER, you will see a menu of options.
Many of these options will not be available since your account is new.

[1] Account Information : Opens up a submenu that allows you to access your account info
	[1] View your trade history : allows you to view your trade history
	[2] Set new password
	[3] View your three most frequent trading partners : will only present what partners you do have if you
	    have fewer than 3 trading partners
	[4] Your three most recent trades
	[5] Inventory : allows you to view and remove items in your personal inventory
	[6] Wishlist : allows you to view and remove items in your personal wishlist
	[7] Exit

[2] Browse through global inventory : Allows you to access all user's items, and add items to your wishlist and
    (if you aren't frozen or don't have more borrows than loans), will allow you to send the owner of that item a
    trade request or trade offer.
    Note: Upon initial user account creation, your first trade cannot be a borrow.

[3] Loan one of your items to another user : this function was created to allow you to loan one of your items to
    another user if your item is within that user's wishlist.
    Currently will only select one item, this function may be expanded upon in Phase 2.

[4] Look at your message inbox : Will let you browse through your messages. Any trade offers, updates, etc.
    sent to your account will be viewed here.
    You will be able to edit any trade offers through this menu, and you will be warned if you have met the maximum
    number of trades, and if you continue to edit, the offer will be deleted.

[5] Add a new item to the system : Will allow you to request an administrator to approve of your item.
    This menu option will ask for the name and description of the item.

[6] Send admins an unfreeze request : If your account is frozen by an administrator, this menu option will send the
    administrators a request to unfreeze your account.

[7] Exit.

Should you confirm a meeting/transaction, and log back in after the meeting was supposed to occur,
the system will automatically prompt you to confirm whether or not this meeting
(as well as any other meetings that were supposed to have happened) actually occurred. If the meeting did happen,
the items involved in the trade will be removed from their owner's accounts, and the items will no longer exist on the
global inventory (we are assuming that after trading an item, the user would not want to automatically trade it again;
this can, of course, be expanded upon/changed in phase 2).

If you have more borrows than loans, your account is frozen, you have too many incomplete trades,
or if you have sent too many trade offers this week, the system will automatically notify you that you are unable to
trade, and if the requirements are met, will automatically send the admin system a request to freeze your account.

                                            ----------------------------
Admin Account:
Upon logging in as an ADMINISTRATOR, you will see a menu of options

[1] Check your message inbox : allows you to view and interact with admin system messages.
    All admin accounts have access to these admin messages.
    Any item approval, freeze, and unfreeze requests will be handled here.

[2] Manage admin account : accesses a submenu to manage this admin account
	[1] Change your password
	[2] Add a new admin account : adds a new admin account to the system. Username must be unique.

[3] Access the information of users : browsing functionality that allows you to directly enter a username
    to manage that user's account.
    Will first print information about this user before bringing up a menu to manage this user's account.
	[1] Change lending (number of borrows v. loans) threshold
	[2] Freeze/unfreeze a user
	[3] Change maximum number of trades per week
	[4] Changes the max number of incomplete trades
	[5] Return to user selection
	[6] Return to main menu

[4] Logout