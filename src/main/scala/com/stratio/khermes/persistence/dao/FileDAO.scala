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
package com.stratio.khermes.persistence.dao

import java.io.File

import com.stratio.khermes.commons.constants.AppConstants
import com.stratio.khermes.commons.exceptions.KhermesException
import org.apache.commons.io.FileUtils

import scala.util.{Failure, Success, Try}

class FileDAO(configPath: Option[String] = None) extends BaseDAO[String] {
  val config = com.stratio.khermes.commons.implicits.AppImplicits.config
  val parentPath = configPath.getOrElse(config.getString(AppConstants.FileDefaultPath))

  override def create(path: String, config: String): Unit = Try {
    val file = new File(s"$parentPath/$path")
    FileUtils.writeStringToFile(file, config)
  } match {
    case Success(ids) => ids.toString
    case Failure(e) => throw new KhermesException(e.getMessage)
  }

  override def read(path: String): String = Try(
    FileUtils.readFileToString(new File(s"$parentPath/$path"))
  )
  match {
    case Success(ids) => ids
    case Failure(e) => throw new KhermesException(e.getMessage)
  }

  override def update(path: String, config: String): Unit = {}

  override def delete(path: String): Unit = {
    val file = new File(s"$path")
    FileUtils.deleteDirectory(file)
  }

  override def exists(path: String): Boolean = {
    val file = new File(s"$parentPath/$path")
    file.exists
  }

  override def list(path: String): String = {
    val file = new File(s"$parentPath/$path")
    file.list.mkString("\n")
  }
}
