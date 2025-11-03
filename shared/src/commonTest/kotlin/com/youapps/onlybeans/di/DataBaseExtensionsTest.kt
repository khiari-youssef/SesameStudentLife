package com.youapps.onlybeans.di

import kotlin.test.Test
import kotlin.test.assertEquals


class DataBaseExtensionsTest {


  @Test
 fun `When i input a List of strings, the output should be a serialized json string`(): Unit {
      val testInput = listOf<String>("A","B","C","D")
      assertEquals(
          expected = "{\"row\":[\"A\",\"B\",\"C\",\"D\"]}",
          actual = testInput.toDBJsonRow()?.trim()
      )
 }

  @Test
 fun `When i input a serialized json string, the output should be a decoded list of strings`(): Unit {
      val testInput =  "{\"row\":[\"A\",\"B\",\"C\",\"D\"]}"
      assertEquals(
          expected = listOf<String>("A","B","C","D"),
          actual = testInput.fromDBJsonRow()
      )
 }


}