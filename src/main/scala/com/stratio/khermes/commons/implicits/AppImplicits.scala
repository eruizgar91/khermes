/*
 * Copyright (C) 2016 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stratio.khermes.commons.implicits

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.stratio.khermes.commons.constants.AppConstants
import com.stratio.khermes.persistence.dao.{BaseDAO, FileDAO, ZkDAO}
import com.typesafe.config.{Config, ConfigFactory, ConfigResolveOptions}
import com.typesafe.scalalogging.LazyLogging

/**
 * General implicits used in the application.
 */
object AppImplicits extends AppSerializer with LazyLogging {

  lazy implicit val executionContext = scala.concurrent.ExecutionContext.Implicits.global
  lazy implicit val config: Config = ConfigFactory
    .load(getClass.getClassLoader,
      ConfigResolveOptions.defaults.setAllowUnresolved(true))
    .resolve

  lazy implicit val system: ActorSystem = ActorSystem(AppConstants.AkkaClusterName, config)
  lazy implicit val configDAO: BaseDAO[String] = if(config.getBoolean(AppConstants.FileFlag))new FileDAO else new ZkDAO
  lazy implicit val materializer = ActorMaterializer()
}
