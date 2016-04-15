package cli.models

import cli.views.Observer

trait Observable {

  private var observers : List[Observer] = Nil
  private var state : String = ""

  def setState(s: String) : Unit = state = s

  def getState : String = state

  def attach(observer: Observer) : Unit = observers :+= observer

  def notifyAllObservers() : Unit = {
    for(observer <- observers)
      observer.update(this)
  }

}