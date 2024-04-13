package com.jokeluke
import twitter4j._
import collection.JavaConversions._
import akka.actor._


object Bot {

  case class Start
  case class Shutdown
  case class UpdateStatus(status: StatusUpdate)

  def main(args: Array[String]) {

    Jokes.getJokesFromFile("jokes.txt")
    val system = ActorSystem("TwitterBot")
    val bot = system.actorOf(Props[Bot], name = "Bot")

    bot ! Start

  }

}

class Bot extends Actor with ActorLogging {

  import Bot._
  import twitter4j._
  import collection.JavaConversions._
  import akka.actor._

  val twitter = new TwitterFactory().getInstance
  val userName = twitter.getScreenName

  val streamToActor = new StreamerToActor(context.self)

  def receive = {

    case Start => streamToActor.stream.user

    case Shutdown => streamToActor.stream.shutdown

    case status: Status =>

      log.info("New status: " + status.getText)
      val replyName = status.getInReplyToScreenName

      if (replyName == userName) {
        log.info("Replying to: " + status.getText)
        
        val statusAuthor = status.getUser.getScreenName
        val mentionedEntities = status.getUserMentionEntities.map(_.getScreenName).toList
        val participants = (statusAuthor :: mentionedEntities).toSet - userName
        
        participants.foreach{ p => 
          var text = ""
            
          if(status.getText.toLowerCase().contains("tell me a joke") || status.getText.toLowerCase().contains("tell us a joke"))
              text = ("@" + p) + " " + Jokes.jokes(random.nextInt(50))               	
          else
              text = ("@" + p) + " " + "Ask me for joke and you will get it"
           
          val reply = new StatusUpdate(text).inReplyToStatusId(status.getId)
          println("Replying: " + text)
          
          context.self ! UpdateStatus(reply)
        }
        
      }

    case UpdateStatus(update) =>
      log.info("Posting update: " + update.getStatus())
      twitter.updateStatus(update)

  }
  
  val random = new scala.util.Random

}


