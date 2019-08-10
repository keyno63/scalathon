package jp.co.something.scalafx

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.effect.BoxBlur
import scalafx.scene.paint.Color.{Black, color}
import scalafx.scene.shape.Circle

import scala.math.random



object VanishingCircles extends JFXApp {
  stage = new PrimaryStage { // sample だと Stage だったのに...

    // inline property definitions
    title = "Disappearing Circles"
    width = 800
    height = 600
    //
    scene = new Scene {
      fill = Black
      content = (0 until 50).map { i =>
        new Circle {
          centerX = random * 800
          centerY = random * 600
          radius = 150
          fill = color(random, random, random, 0.2)
          effect = new BoxBlur()
        }
      }
    }
  }
}
