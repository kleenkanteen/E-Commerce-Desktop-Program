Program to mimic kijiji like trading system, including admin and messaging functionality. Made with Java and the JavaFX user interface library.

Features include:
- User sign up and log in
- Admin log in. Can ban/unban users, change borrowing limit
- Adding items to global inventory, searching items in global inventory, along with their description, and adding items to wishlist
- View personal inventory and wishlist
- Offering a permanent or temporary trade. 1 way trade or both way trade. Ability to choose time and date and meeting place 
- Auto lend items to any user who has one in their wishlist
- Messaging and reporting other users. Trade requests come in the form of messages, which can be accepted or denied
- View trade history and most frequent trading partners
- Program demo mode

View video demo: https://www.youtube.com/watch?v=ubOZN-V_8oo&ab_channel=cynkek

To get a big picture view of the classes and the layers they are in, look at the UML here: https://github.com/kleenkanteen/E-Commerce-Desktop-Program/blob/master/UML/UML2.pdf

To run the program, first download JavaFX with these steps
- Clone the repo and open it in IntelliJ.
- Download  JavaFX Runtime. Scroll down and download the installer for your operating system: https://gluonhq.com/products/javafx/
- Unzip 
- (In IntelliJ) File -> Project Structure -> Project
- Project SDK should be set to "1.8 java version "1.8.0_261""
- SDK default should be set to (8 - Lambdas, type annotations etc.)
- Go to File -> Project Structure -> Libraries
- Click on plus sign to add JavaFX by pointing to the lib folder of the JavaFX SDK you just unzipped
- Then go to src/StartScreen.java, and run the file. It has the main() method where the program starts. To clear all program data like user accounts, inventory data, etc., delete all the files in the data/ folder. They will automatically be made fresh when the program next starts.

Future plan: deploy online so anyone can access from the browser.




