import java.util.*;

public class Game {

	public static int  counterPairs=0,card1, card2;  //Helpers variables
	public static char column, row;                  //Saving the coordinates that the user want to flip
	public static String place, newGame;             // Receives user input.
	public static char[][] board = new char[5][4];   //Game board to print for the user.
	public static int[][] GameBoard = new int[5][4];  //Helper board  saving the numbers 0-9.


	public static void main(String[] args) {
		do {
			System.out.println("Welcome to Fatma Memory Game");
			StartGame();                                        //The game is starting, printing the table for the user.
			while (!(EndGame())) {

				System.out.println("Please choose first card to flip");
				card1 = ChooseCardToFlip();                       //User choose first card.
				PrintBoard();                                    //Printing the table with the card that as chosen.

				System.out.println("Please choose second card to flip");
				card2 = ChooseCardToFlip();                      //User choose second card.
				PrintBoard();                                    //Printing the table with the card that as chosen.

				if (MatchCards())                            //If cards are matches they are removed else they are flip back.
					RemoveCards();
				else FlipBack();
			}

		}	while(NewGame());	//Game is continuing until it finish , when finish the user will decide if to start new one. 
	}


	public static boolean MatchCards() { //Checks if card matches or not.

		if (card1 == card2) {  //If card 1 is equals card 2  return true and add 1 to the counter.
			counterPairs++;
			return true;
		}
		if (card1==0 && card2!=0) {//If card 1 is equals 0 (joker) return true and add 1 to the counter.
			counterPairs++;
			return true;

		}
		if (card1!=0 && card2==0) {//If card 2 is equals card 0 ( joker)  return true and add 1 to the counter.
			counterPairs++;
			return true;
		}

		return false;        //Return false,no match between cards.
	}



	public static boolean NewGame() {//Checks if the user want new game or not.
		counterPairs=0;	            //Reset the counter.
		boolean ask=false;
		do{
			System.out.println("Would you like to start a new game?");
			Scanner sc = new Scanner (System.in);
			newGame = sc.nextLine();
			if(newGame.equals("Y")||newGame.equals("y"))        //If the user enter y or Y return true,new game will start.
				return true;
			else if(newGame.equals("N")||newGame.equals("n"))   //If the user enter n or N return false, game will finish.
				return false;
			else {
				System.out.println("Sorry, wrong input. Please try again."); 
			ask=false;                      //Ask is false ,keep ask for correct input.
			}

		}	while(ask);  // //Ask is false ,keep ask for correct input.
		return false;
	}



	public static void FlipBack() {//Flip the cards back.

		for (int i=0; i<board.length; i++) {
			for (int j=0; j<board [i].length; j++) {
				if (board [i][j] <= '9' && board [i][j]>='0')//Checks the place that there are numbers and put back "#" instead.
					board [i][j] = '#';
			}
		}	
		System.out.println("Cards do not match!");
		PrintBoard();         //Print the board with the cards flip.
	}



	public static void RemoveCards() {//Removes cards from table.

		for (int i=0; i<board.length; i++) {
			for (int j=0; j<board [i].length; j++) {
				if (board [i][j] <= '9' && board [i][j]>='0')//Checks the place that there are numbers and put "*" instead.
					board [i][j] = '*';
			}
		}
		System.out.println("Cards match!");
		PrintBoard();           //Print the board with * at the places where the cards were.
	}



	public static void PrintBoard() {//Printing the board.

		for (int i=0; i<board.length; i++) {
			for (int j=0; j<board [i].length; j++) { 

				System.out.print(board [i][j] + "      ");//Printing the chars matrix.
			}
			System.out.println("");
		}

	}


	public static boolean EndGame() {//Checks if game need to be finished.

		int place1=-1,place2=-1,saver1=0,saver2=0;   //Helpers variables.

		if(counterPairs==9) {                          //If nine pairs were found we check if the last pair can be matched.
			for (int i=0; i<board.length; i++) {
				for (int j=0; j<board [i].length; j++) {
					if(board[i][j]=='#') {
						if(place1==-1) {
							place1=GameBoard [i][j];                  //Saves first number that was remained.
							board[i][j]=(char) ((GameBoard [i][j])+'0'); //Put the number char instead '#' to display.
							saver1=i*10+j;                               //Saves the coordinates of the card.
						}
						else
							place2=GameBoard [i][j];                 //Saves second number that was remained.
						board[i][j]=(char) ((GameBoard [i][j])+'0'); //Put the number char instead '#' to display.
						saver2=i*10+j;                               //Saves the coordinates of the card.
					}

				}
			}
			if(place1!=place2&&(place1!=0&&place2!=0)){  //The cards are not equals,and there are no 0 (joker),there are no more matches.
				System.out.println("Game is over! No more possible matches.");
				PrintBoard();                      //Print the board with the last two cards flip over.
				return true;                       // //Return true , finished the game.
			}
			else {                             //The last pair can be matched.
				board[saver1/10][saver1%10]='#';  //Put back '#' at the place of the first card in the chars matrix.
				board[saver2/10][saver2%10]='#';  //Put back '#' at the place of the second card in the chars matrix.
			}

		}


		else if(counterPairs==10) { //All pairs were found.

			System.out.println("Game is over! All cards are matched."); 
			PrintBoard();  //Print the game board with all '*'.
			return true;   //Return true , finished the game.
		}


		return false; //Return false ,game is not finished.
	}


	public static void StartGame() {//Initializing game board and helper board.

		int row1, column1;         //   Helpers variables for saving coordinates.
		
		for (int i=0; i<board.length; i++) {
			for (int j=0; j<board [i].length; j++) { //Initializing game board at first with '#'.
				board [i][j] = '#';
				System.out.print(board [i][j] + "      ");
			}
			System.out.println("");
		}
		for (int i=0; i<GameBoard.length; i++) {  //Initializing helper board at first with '-1'.
			for (int j=0; j<GameBoard[i].length; j++) {
				GameBoard [i][j] = -1;
			}
		}
		for (int i=0; i<2;i++) { //Twice Initializing numbers from 0-9 randomly in the helper matrix.
			for (int j=0; j<=9; j++) {//Loop that runs from 0-9 to initializing them in the board.
				row1 = (int) (5 * (Math.random()));  //Choose randomly row from 0-4.
				column1 = (int) (4 * (Math.random()));//Choose randomly column from 0-3.
				if (GameBoard [row1][column1] != -1) //If there is number at the coordinates keep search new random coordinates to the same number.
					j--;                          //Keeps the number the same.
				else
					GameBoard [row1][column1]=j; //There is no number ,so put the current number in the matrix.

			}
		}
		
	}
	public static int ChooseCardToFlip() { //Which card to flip.

		do {
			Scanner sc = new Scanner (System.in);
			place = sc.nextLine(); //User input as a string.
			if(place.length()>=2) { //If the user input is not one char save him as two coordinates.
				row = place.charAt(0);
				column = place.charAt(1);
			}
		}while (WrongInput(place) ) ;//Check if the input is valid, if its true ,keep ask for valid input.

		board [ row-48][column-48] = (char) ((GameBoard [row-48][column-48])+'0');//Replace the '#' at the board game with the number at the same place in the helper matrix.
		return GameBoard [ row-48][ column-48]; //Return the value of the card.

	}


	public static boolean WrongInput(String UserInput) {//Check if the user entered correct coordinates.

		boolean check=true, wrong = false; //Helpers booleans

		if (UserInput.length()!=2) { //If user input is not two chars the input is invalid.
			wrong = true;     //Return true , the input is wrong.
			check=false;      //Check is false, do not keep check other conditions.
		}
		if(check) {
			if (UserInput.charAt(0)<'0' || UserInput.charAt(0)>'4'||UserInput.charAt(1)<'0' || UserInput.charAt(1)>'3') //If the input is not two numbers that are at the correct range , return true,input invalid. 
				wrong = true;
			else if( board [ UserInput.charAt(0)-48][ UserInput.charAt(1)-48] != '#')//If the coordinates were already choose,return true,input invalid. 
				wrong=true;
		}
		if (wrong == true) { //If wrong is true , one of the conditions for invalid input is true.
			System.out.println("Sorry, wrong input. Please try again.");
			PrintBoard(); 
			return wrong;//return true.
		}else return wrong;//return false.


	}

}