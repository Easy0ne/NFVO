/*
 * Copyright (c) 2016 Open Baton (http://www.openbaton.org)
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
 *
 */

package org.openbaton.nfvo.common.configuration;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.Date;
import org.springframework.stereotype.Service;

/** Created by lto on 10/11/15. */
@Service
public class GsonSerializerDate implements JsonSerializer<Date> {

  @Override
  public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
    return new JsonPrimitive(src.getTime());
  }
}
