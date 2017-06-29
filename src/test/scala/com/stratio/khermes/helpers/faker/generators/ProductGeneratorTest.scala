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

package com.stratio.khermes.helpers.faker.generators

import com.stratio.khermes.commons.exceptions.KhermesException
import com.stratio.khermes.helpers.faker.Faker
import org.junit.runner.RunWith
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.junit.JUnitRunner

  @RunWith(classOf[JUnitRunner])
  class ProductGeneratorTest extends FlatSpec
    with Matchers {


    "A Khermes" should "should generate valid product: with EN and ES locales" in {
      val khermesEN = Faker("EN")
      khermesEN.Product.getProduct(khermesEN.Product.productModel) should contain(khermesEN.Product.productItem)

      val khermesES = Faker("ES")
      khermesES.Product.getProduct(khermesES.Product.productModel) should contain(khermesES.Product.productItem)
    }

    it should "raise a NoSuchElementException when the product locale is empty" in {
      val khermes = Faker("XX")
      an[KhermesException] should be thrownBy khermes.Product.productItem
    }

    it should "when you do not specify any locale try to use all the locales" in {
      val khermes = Faker()
      khermes.Product.getProduct(khermes.Product.productModel) should contain(khermes.Product.productItem)
    }

    it should "raise an exception when it gets a song that not exists" in {
      val khermesFR = Faker("FR")
      an[KhermesException] should be thrownBy khermesFR.Product.productItem
    }

    "getProdcut" should "return a seq with one product model" in {
      val generator = ProductGenerator("EN")
      generator.getProduct(Seq(Left("error"), Right(Seq(ProductModel("productLine", "productType", "productName", 1, 1.00))))) should be(
        Seq(ProductModel("productLine", "productType", "productName", 1, 1.00)))
    }

    "getProduct" should "return empty seq when no product model exists" in {
      val generator = ProductGenerator("EN")
      generator.getProduct(Seq(Left("error"))) should be(Seq())
    }
  }


