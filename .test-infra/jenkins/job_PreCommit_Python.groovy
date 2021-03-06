/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import PrecommitJobBuilder

PrecommitJobBuilder builder = new PrecommitJobBuilder(
    scope: this,
    nameBase: 'Python',
    gradleTask: ':pythonPreCommit',
    triggerPathPatterns: [
      '^model/.*$',
      '^sdks/python/.*$',
      '^release/.*$',
    ]
)
builder.build {
  // Publish all test results to Jenkins. Note that Nose documentation
  // specifically mentions that it produces JUnit compatible test results.
  publishers {
    archiveJunit('**/nosetests*.xml')
  }
}

// Temporary job for testing pytest-based testing.
// TODO(BEAM-3713): Remove this job once nose tests are replaced.
PrecommitJobBuilder builderPytest = new PrecommitJobBuilder(
    scope: this,
    nameBase: 'Python_pytest',
    gradleTask: ':pythonPreCommitPytest',
    commitTriggering: false,
    timeoutMins: 180,
)
builderPytest.build {
  // Publish all test results to Jenkins.
  publishers {
    archiveJunit('**/pytest*.xml')
  }
}
