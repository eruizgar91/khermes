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

import com.stratio.khermes.commons.exceptions.KhermesException
import org.junit.runner.RunWith
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FileDAOTest extends FlatSpec
  with Matchers{
  val khermesConfigDAO = new FileDAO

  "An KhermesConfig" should "be save in a directory path and could be loaded" in {
    khermesConfigDAO.create("config/testFilePath", "myConfig")
    val data = khermesConfigDAO.read("config/testFilePath")
    khermesConfigDAO.delete("config")
    data shouldBe "myConfig"
  }

  "An KhermesConfig" should "be updated when we save over an existing config" in {
    khermesConfigDAO.create("config/testFilePath", "myConfig")
    khermesConfigDAO.create("config/testFilePath", "myConfig2")
    val data = khermesConfigDAO.read("config/testFilePath")
    khermesConfigDAO.delete("config")
    data shouldBe "myConfig2"
  }

  "An KhermesConfig" should "have a way to obtain the list of configs" in {
    khermesConfigDAO.create("a/b/c","config1")
    khermesConfigDAO.create("a/b/d","config2")
    khermesConfigDAO.list("a/b") shouldBe "d\nc"
    khermesConfigDAO.delete("config")
  }
  it should "raise an exception when it save or load a config in a path that does not exists" in {
    an[KhermesException] should be thrownBy khermesConfigDAO.read("")
  }

}
