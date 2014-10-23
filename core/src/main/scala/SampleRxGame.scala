package de.johoop.sample.game

import rx.lang.scala.observables.ConnectableObservable
import rx.libgdx.events.lifecycle.{RenderEvent, CreateEvent, LifecycleEvent}
import rx.libgdx.sources.GdxLifecycleEventSource
import rx.{Observable => ObservableJ, RxGame}
import rx.lang.scala.JavaConversions._
import rx.lang.scala.Observable

class SampleRxGame extends RxGame {
  def start(lifecycle: ObservableJ[LifecycleEvent]): Unit = {
    val connector: ConnectableObservable[LifecycleEvent] = toScalaObservable(lifecycle).publish

    // wire everything up
    val renders: Observable[RenderEvent] = GdxLifecycleEventSource render connector
    val create: Observable[CreateEvent] = GdxLifecycleEventSource create connector

    val resources = create take 1 map { _ => Resources() }
    val gameRenders = renders.scan(Game()) { case (game, render) => game.copy(t = System.currentTimeMillis) }

    val gameRendersWithResources = gameRenders combineLatest resources

    // subscribe for rendering
    gameRendersWithResources subscribe (onNext = { case (game: Game, res: Resources) => SampleRxGame render (game, res) })

    // start the whole thing
    connector.connect
  }
}

object SampleRxGame {
  def render(game: Game, res: Resources): Unit = {
    println(game)
  }
}

case class Resources()
case class Game(t: Long = System.currentTimeMillis)
