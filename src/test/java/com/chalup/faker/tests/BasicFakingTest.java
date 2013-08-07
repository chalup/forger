/*
 * Copyright (C) 2013 Jerzy Chalupski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chalup.faker.tests;

import static org.fest.assertions.Assertions.assertThat;

import com.chalup.faker.Faker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.content.ContentResolver;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class BasicFakingTest {

  Faker<TestModels.TestModel> mTestSubject;
  ContentResolver mContentResolver;

  @Before
  public void setUp() throws Exception {
    mTestSubject = new Faker<TestModels.TestModel>(TestModels.MODEL_GRAPH);
    mContentResolver = EchoContentResolver.get();
  }

  public static class ClassOutsideOfTheModelGraph {
  }

  @Test(expected = NullPointerException.class)
  public void shouldNotAllowFakingClassesOutsideOfModelGraph() throws Exception {
    mTestSubject.iNeed(ClassOutsideOfTheModelGraph.class).in(mContentResolver);
  }

  @Test
  public void shouldCreateSimpleObject() throws Exception {
    TestModels.Room room = mTestSubject.iNeed(TestModels.Room.class).in(mContentResolver);

    assertThat(room).isNotNull();

    assertThat(room.id).isNotEqualTo(0);
    assertThat(room.name).isNotNull();
  }
}

