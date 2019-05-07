
// import java.io.IOException;
// import java.io.ObjectInputStream;
// import java.io.ObjectOutputStream;
// import java.lang.ClassNotFoundException;
// import java.net.ServerSocket;
// import java.net.Socket;
// import java.util.Random;
// import java.util.*; // cool cool
// import java.io.Serializable;

// class Question {
//     public String [] charactors;
//     public int leftTime;
//     Question(String [] charactors,int leftTime){
//         this.charactors = charactors;
//         this.leftTime = leftTime;
//     }
// }

// class User implements Serializable{
//     public String uuid;
//     public int usedTime = 0;
//     public int leftTime;
//     public List<String> usedCharactors = new ArrayList<>();
//     public int totalAnsCharactors;
//     public String [] ansCharactors;
//     public String [] rightCharactors;
//     User(String [] rightCharactors,int leftTime){
//         this.uuid = UUID.randomUUID().toString().replaceAll("-", "");
//         this.totalAnsCharactors = rightCharactors.length;
//         this.ansCharactors = new String[rightCharactors.length];
//         this.rightCharactors = rightCharactors;
//         this.leftTime = leftTime;
//     }
// }

// class Message implements Serializable {
//     public String message;
//     public String ansCharactor;
//     public User user;
//     Message(String message){
//         this.message = message;
//     }
//     Message(String message,User user){
//         this.message = message;
//         this.user = user;
//     }
// }


// class Server {
//     private static ServerSocket server;
//     private static int port = 9876;
//     public static List<User> users = new ArrayList<>();
//     public static List<Question> questions = new ArrayList<>();
//     public static User user = null;
//     public static void main(String args[]) throws ClassNotFoundException {
//         seedQuestion();
//         int i =0;
//         Thread thread = null;
//         try {
//             server = new ServerSocket(port);

//             while(true){
//                 // ObjectInputStream ois;
//                 // ObjectOutputStream oos;
//                 System.out.println("Waiting for the client request");
//                 Socket socket = server.accept();
//                 Random rand = new Random();
//                 int newPort = rand.nextInt(9000)+1000;
//                 MultiThreadRespond mr = new MultiThreadRespond(newPort);
//                 thread = new Thread(mr);
//                 thread.start();
//                 ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//                 ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());


//                 Message inputMessage = (Message) ois.readObject();
//                 System.out.println("Message Received: " + inputMessage.message.equals("REQ_GET_QUESTION"));
//                 System.out.println("Request");
//                 switch(inputMessage.message){
//                     case "REQ_GET_QUESTION" : {
//                         System.out.println("REQ_GET_QUESTION");

//                         int randQuestIndex = (new Random()).nextInt(questions.size());
//                         Question q = questions.get(randQuestIndex);
//                         User user = new User(q.charactors,q.leftTime);
//                         users.add(user);
//                         oos.writeObject(new Message("",user));
//                         // oos.close();
//                         // oos.close();
//                         break;
//                     }
//                     case "REQ_ANS" : {
//                         System.out.println("inputMessage.ansCharactor : "+inputMessage.ansCharactor);
//                         Boolean isDuplicateAns = false;
//                         Boolean isCorrect = false;
//                         users.forEach((u) -> {
//                             if(u.uuid == inputMessage.user.uuid){
//                                 user = u;
//                             }
//                         });
//                         if(user != null){
//                             if(user.leftTime > 0){
//                                 for(String ch : user.usedCharactors){
//                                     if(ch == inputMessage.ansCharactor){
//                                         isDuplicateAns = true;
//                                         break;
//                                     }
//                                 }
//                                 if(isDuplicateAns){
//                                     oos.writeObject(new Message("THIS_CHARACTOR_ALREADY_USED",user));
//                                 }else{
//                                     // System.out.println("inputMessage.ansCharactor : "+inputMessage.ansCharactor);
//                                     user.usedCharactors.add(inputMessage.ansCharactor);
//                                     int x = 0;
//                                     for(String ch : user.rightCharactors){
//                                         if(ch == inputMessage.ansCharactor){
//                                             user.ansCharactors[x] = ch;
//                                             isCorrect = true;
//                                         }
//                                     }
//                                     if(isCorrect){
//                                         oos.writeObject(new Message("CORRECT",user));
//                                     }else{
//                                         user.leftTime =  user.leftTime - 1;
//                                         oos.writeObject(new Message("INCORRECT",user));
//                                     }
//                                 }
//                             }else{
//                                 oos.writeObject(new Message("INCORRECT",user));
//                             }
                            
//                         }else{
//                             oos.writeObject(new Message("NOT_FOUND_USER"));
//                         }

//                         break;
//                     }
//                     default : {
//                         System.out.println("else");
//                         oos.writeObject("else");
//                     }
//                 }
                

                

//                 // ois.close();
//                 // oos.close();
//                 // socket.close();
//                 i++;

//             }
//         } catch (IOException ex) {
//             try {
//                 server.close();
//             } catch (IOException e) {
//                 System.err.println("ERROR closing socket: " + e.getMessage());
//             }
//         }
//     }
//     public static void seedQuestion(){
//         String [][] movies = {
//             {"I","R","O","N","-","M","A","N"},
//             {"S","P","I","D","E","R","-","M","A","N"},
//             {"A","V","E","N","G","E","R"}
//         }; 
//         int [] guessLimit = {3,4,3};
//         int i = 0;
//         for(String [] movie : movies){
//             questions.add(new Question(movie,guessLimit[i]));
//             i++;
//         }
//         // test 
//         questions.forEach( (q) ->  System.out.println("q.leftTime "+ q.leftTime));
//     }
    
// }



import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ClassNotFoundException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

/**
 * This class implements java Socket server
 * @author pankaj
 *
 */
public class Server {

    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 1150;

    public static void main(String args[]) throws ClassNotFoundException {
        //create the socket server object

        //keep listens indefinitely until receives 'exit' call or program terminates
        int i =0;
        Thread thread = null;
        try {
            server = new ServerSocket(port);

            while(true){
                System.out.println("Waiting for the client request answer");
                Socket socket = server.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                String message = (String) ois.readObject();
                System.out.println("Message Received => " + message);
                Random rand = new Random();
                int newPort = rand.nextInt(9000)+1000;
                MultiThreadRespond mr = new MultiThreadRespond(newPort);
                thread = new Thread(mr);
                thread.start();
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(""+newPort);

                ois.close();
                oos.close();
                socket.close();
                i++;

            }
        } catch (IOException ex) {
            try {
                server.close();
            } catch (IOException e) {
                System.err.println("ERROR closing socket: " + e.getMessage());
            }
        }
    }

}
