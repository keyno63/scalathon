package jp.co.something.scalafx

import scalafx.Includes.when
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.paint.Color.{LightGreen, Green, Red}
import scalafx.scene.shape.Rectangle

object HelloStageDemo extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title.value = "Hello Stage"
    width = 600
    height = 450
    scene = new Scene {
      fill = LightGreen
      content = new Rectangle {
        x = 25
        y = 40
        width = 100
        height = 100
        fill <== when(hover) choose Green otherwise Red
      }
    }
  }
}
