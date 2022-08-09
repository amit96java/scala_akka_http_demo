package com.globomantics

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.stream.Materializer
import com.globomantics.routes.UserRoutes
import com.globomantics.services.UserServiceImpl
import com.typesafe.config.{Config, ConfigFactory}

object Server extends App {

  implicit val system: ActorSystem = ActorSystem()

  implicit val materializer: Materializer = Materializer(system)

  def config: Config = ConfigFactory.load()

  //  val userService: UserServiceImpl = wire[UserServiceImpl]
  val userService: UserServiceImpl = new UserServiceImpl(config)


  //  val userRoutes: UserRoutes = wire[UserRoutes]
  val userRoutes: UserRoutes = new UserRoutes(userService)

  val serviceRoutes: Route = userRoutes.routes

  HttpService.run(config, serviceRoutes)
}