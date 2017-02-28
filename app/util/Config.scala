package util

import com.typesafe.config.{Config, ConfigFactory}

object Config {
  val instance: Config = ConfigFactory.load()
}
