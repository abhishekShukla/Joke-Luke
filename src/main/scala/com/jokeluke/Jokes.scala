package com.jokeluke
import scala.io.Source._


object Jokes {

  var jokes: Seq[String] = Seq()

  def getJokesFromFile(file: String) = {

    fromFile(file, "UTF-8").getLines().foreach { line =>
      jokes = jokes :+ line
    }
    
  }

}